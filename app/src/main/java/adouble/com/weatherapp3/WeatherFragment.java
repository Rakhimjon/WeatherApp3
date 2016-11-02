package adouble.com.weatherapp3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by root on 10/14/16.
 */

public class WeatherFragment extends Fragment implements Callback {

    public static final String CITY_NAME = "CITY_NAME";
    private OkHttpClient okHttpClient;
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("HH:mm");

    @BindView(R.id.celcium)
    TextView textView;
    @BindView(R.id.tvWind)
    TextView textView3;

    @BindView(R.id.tvsunrise)
    TextView textView1;

    @BindView(R.id.tvsunset)
    TextView textView2;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        okHttpClient = new OkHttpClient();

        View view = inflater.inflate(R.layout.weather_fragment, container, false);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);


        String text = getArguments().getString(CITY_NAME);
        try {

            Request request = new Request.Builder()
                    .url("http://api.openweathermap.org/data/2.5/weather?q=" + text + "&APPID=0f04c4135e0946abf9ce0161f55a9ffb").build();
            okHttpClient.newCall(request).enqueue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String data = response.body().string();

            JSONObject jsonObject = new JSONObject(data);
            final JSONObject sys = jsonObject.getJSONObject("sys");
            JSONObject wind = jsonObject.getJSONObject("wind");
            final float speed1 = (float) wind.getDouble("speed");

            final long sunrise1 = sys.getLong("sunrise") * 1000;

            final Calendar c1 = Calendar.getInstance();
            c1.setTimeInMillis(sunrise1 * 1000);

            final int sunset1 = sys.getInt("sunset");

            final Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(sunset1 * 1000);

            JSONObject main = jsonObject.getJSONObject("main");
            double tempKelvin = main.getDouble("temp");
            double tempCelsium = tempKelvin - 273.15;
            final long tempratura = Math.round(tempCelsium);

            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView3.setText(speed1 + " mps ");
                    textView1.setText(DATE_FORMATTER.format(c1.getTime()));
                    textView2.setText(DATE_FORMATTER.format(c2.getTime()));
                    textView.setText(Html.fromHtml(tempratura + "&deg;C"));
                    progressBar.setVisibility(View.GONE);
               }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}


