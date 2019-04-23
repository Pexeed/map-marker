package br.com.pexed.mapmarker.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.pexed.mapmarker.R;
import br.com.pexed.mapmarker.model.model.Location;

public class ListLocationsAdapter extends BaseAdapter {

    private List<Location> locations;
    private final Activity activity;

    public ListLocationsAdapter(Activity activity, List<Location> locations) {
        this.activity = activity;
        this.locations = locations;
    }

    @Override
    public int getCount() {
        if(locations.size()>1) {
            //Display All on Map
            return locations.size() + 1;
        }
        return locations.size();
    }

    @Override
    public Object getItem(int position) {
        if( position == locations.size() ){
            return "";
        }
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View reusableView, ViewGroup viewGroup) {
        View itemView;
        ViewHolder holder;

        //Better performance using ViewHolder pattern
        if( reusableView == null) {
            itemView = LayoutInflater.from(activity)
                    .inflate(R.layout.item_location, viewGroup, false);
            holder = new ViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            itemView = reusableView;
            holder = (ViewHolder) itemView.getTag();
        }

        if(position == locations.size()){
            //Display All
            holder.address.setText(activity.getString(R.string.display_all));
            holder.address.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            holder.address.setTextColor(Color.WHITE);
        }else {
            Location item = locations.get(position);
            holder.address.setText(item.getFormattedAddress());
            holder.address.setBackgroundColor(Color.parseColor("#FAFAFA"));
            holder.address.setTextColor(Color.parseColor("#808080"));
        }

        return itemView;
    }

    class ViewHolder {
        final TextView address;

        ViewHolder(View view) {
            address = view.findViewById(R.id.item_address);
        }
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
