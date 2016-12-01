package adouble.com.weatherapp3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import adouble.com.weatherapp3.adapter.WeatherAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements OnCustomerListChangedListener,OnStartDragListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.button)
    Button saveButton;

    private SharedPreferences sharedPreferences;
    private ItemTouchHelper itemTouchHelper;
    private WeatherAdapter mAdapter;

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
        recyclerView.setAdapter(new WeatherAdapter(this,this,this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> set = new HashSet<String>();
                set.addAll(arr);

                sharedPreferences.edit().putStringSet(Constants.CITIES, set).apply();
                setResult(RESULT_OK);

                finish();
            }
        });


    }

    @Override
    public void onNoteListChanged(ArrayList<String> weathers) {

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

        itemTouchHelper.startDrag(viewHolder);


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
