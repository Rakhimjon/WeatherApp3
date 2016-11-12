package adouble.com.weatherapp3;

/**
 * Created by root on 11/11/16.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void  onItemDismiss(int position);
}
