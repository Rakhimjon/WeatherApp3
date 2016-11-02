package adouble.com.weatherapp3;

/**
 * Created by root on 10/16/16.
 */

public class Weather {
    private  String textView;


    public Weather(String textView) {
        this.textView = textView;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }
}
