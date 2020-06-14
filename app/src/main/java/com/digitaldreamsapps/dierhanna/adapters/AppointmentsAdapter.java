package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnAppointmentClicked;
import com.digitaldreamsapps.dierhanna.models.Appointment;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    private OnAppointmentClicked onAppointmentClicked;



    public void setOnAppointmentClicked(OnAppointmentClicked onAppointmentClicked) {
        this.onAppointmentClicked = onAppointmentClicked;
    }

    @NonNull
    @Override
    public AppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.apointmentcell, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Appointment appointment = appointments.get(position);
        holder.itemDetail.setText(appointment.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAppointmentClicked.onClick(appointment);
            }
        });

    }
    List<Appointment> appointments;
    public AppointmentsAdapter(List<Appointment> appointments){
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
