package adouble.com.weatherapp3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 10/15/16.
 */

public class AddCityActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.editText)
    EditText editText;

    SharedPreferences sharedPreferences;
    List<String> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_city);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cities.addAll(Arrays.asList(getResources().getStringArray(R.array.cities)));
        setTitle(getString(R.string.add_city));

    }

    @OnClick(R.id.button)
    void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                saveText();
                break;
        }

    }

    private void saveText() {

        Set<String> set = new HashSet<>();

        for (String s : cities)
            set.add(s);

        set.add(editText.getText().toString());

        Log.d("SIZE" , set.size() + "");

        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putStringSet("cities", set);

        ed.apply();

        String text = editText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("text", text);
        setResult(RESULT_OK, intent);
        finish();

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
