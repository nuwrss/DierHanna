package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnFormClickListener;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.models.Wedding;

import java.util.List;

public class WeddingsAdapter extends RecyclerView.Adapter<WeddingsAdapter.ViewHolder> {







    @NonNull
    @Override
    public WeddingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.apointmentcell, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Wedding appointment = appointments.get(position);
        holder.itemDetail.setText(appointment.getTitle() + "  " + appointment.getDate());


    }
    List<Wedding> appointments;
    public WeddingsAdapter(List<Wedding> appointments){
        this.appointments = appointments;
    }



    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            itemDetail =
                    (TextView)itemView.findViewById(R.id.appontmenttitle);
        }
    }
}
