package com.digitaldreamsapps.dierhanna.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.models.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class DierInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity act;
    public DierInfoWindowAdapter(Activity activity) {
        this.act=activity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = (act.getLayoutInflater()
                .inflate(R.layout.infowindow_marker, null));

        TextView name_tv = view.findViewById(R.id.name);
        TextView details_tv = view.findViewById(R.id.details);
        ImageView img = view.findViewById(R.id.pic);



        name_tv.setText(marker.getTitle());


        Place infoWindowData = (Place) marker.getTag();

        details_tv.setText(infoWindowData.getDescription());

        if (infoWindowData.getPic()==null || !infoWindowData.istShow_image_inside_marker_window()){
            img.setVisibility(View.GONE);
        }else {
            img.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(infoWindowData.getPic())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(img);
        }



        return view;
    }
}
