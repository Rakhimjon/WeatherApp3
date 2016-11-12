package adouble.com.weatherapp3.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import adouble.com.weatherapp3.Constants;
import adouble.com.weatherapp3.ItemTouchHelperAdapter;
import adouble.com.weatherapp3.OnCustomerListChangedListener;
import adouble.com.weatherapp3.OnStartDragListener;
import adouble.com.weatherapp3.R;
import adouble.com.weatherapp3.SettingsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static adouble.com.weatherapp3.R.id.recyclerView;

/**
 * Created by root on 11/12/16.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>
        implements View.OnClickListener   , ItemTouchHelperAdapter {

    private OnStartDragListener onStartDragListener;
    private OnCustomerListChangedListener mListChangedListener;
    ArrayList<String> arr;

    private SharedPreferences prefs;

    Activity activity;


    public WeatherAdapter(SettingsActivity onStartDragListener, SettingsActivity mListChangedListener, SettingsActivity settingsActivity) {
        this.onStartDragListener = onStartDragListener;
        this.mListChangedListener = mListChangedListener;
        this.activity = settingsActivity;


    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.textView.setText(arr.get(position));
        holder.delete.setTag(position);
        holder.delete.setOnClickListener(this);
        holder.handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                    onStartDragListener.onStartDrag(holder);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        arr = new ArrayList<>();

        arr.addAll(prefs.getStringSet(Constants.CITIES ,new HashSet<String>()));

        System.out.println(arr.size());
        return arr.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(arr, fromPosition, toPosition);
        mListChangedListener.onNoteListChanged(arr);
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        arr.remove(pos);



    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView textView;

        @BindView(R.id.delete)
        ImageView delete;

        @BindView(R.id.handle)
        ImageView handle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

