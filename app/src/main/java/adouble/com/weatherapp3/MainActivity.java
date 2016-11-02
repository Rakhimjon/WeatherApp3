package adouble.com.weatherapp3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    public static int PLUS = 1;
    public static int SETTING = 2;


    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    List<String> cities = new ArrayList<>();


    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.weather));


        Set<String> defaultSet = new HashSet<>();
        defaultSet.add("Tashkent");

        cities.addAll(prefs.getStringSet(Constants.CITIES, defaultSet));

        System.out.println(cities.size());
        viewPager.setAdapter(new WeatherPageAdapter());
        tabLayout.setupWithViewPager(viewPager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_plus:
                Intent intent = new Intent(this, AddCityActivity.class);
                startActivityForResult(intent, PLUS);
                break;

            case R.id.menu_item_settings: {
                startActivityForResult(new Intent(this, SettingsActivity.class), SETTING);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            String fName = data.getStringExtra("text");
            ((WeatherPageAdapter) viewPager.getAdapter()).addItem(fName);
            prefs.edit().putStringSet(Constants.CITIES, new HashSet<>(cities)).apply();
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            cities.clear();
            cities.addAll(prefs.getStringSet(Constants.CITIES, new HashSet<String>()));

            viewPager.getAdapter().notifyDataSetChanged();
        }
    }
    public class WeatherPageAdapter extends FragmentPagerAdapter {

        public WeatherPageAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            WeatherFragment fragment = new WeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString(WeatherFragment.CITY_NAME, cities.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return cities.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return cities.get(position).toUpperCase();
        }

        public void addItem(String cityname) {
            cities.add(cityname);
            viewPager.getAdapter().notifyDataSetChanged();
            viewPager.setCurrentItem(cities.size() - 1);

        }
    }
}
