package adouble.com.weatherapp3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.button)
    Button saveButton;

    private SharedPreferences sharedPreferences;

    ArrayList<String> arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.settigs));

        arr = new ArrayList<>();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        arr.addAll(sharedPreferences.getStringSet(Constants.CITIES, new HashSet<String>()));

        System.out.println(arr.size());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new WeatherAdapter());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> set = new HashSet<String>();
                set.addAll(arr);

                sharedPreferences.edit().putStringSet(Constants.CITIES, set).commit();
                setResult(RESULT_OK);

                finish();
            }
        });


    }
    public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> implements View.OnClickListener {
        @Override
        public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_weather, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.textView.setText(arr.get(position));
            holder.delete.setTag(position);
            holder.delete.setOnClickListener(this);

        }

        @Override
        public int getItemCount() {
            return arr.size();
        }

        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            arr.remove(pos);

            recyclerView.getAdapter().notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.textView)
            TextView textView;
            @BindView(R.id.delete)
            ImageButton delete;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
