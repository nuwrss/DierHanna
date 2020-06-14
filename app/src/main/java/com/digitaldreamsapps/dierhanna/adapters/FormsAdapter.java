package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnAppointmentClicked;
import com.digitaldreamsapps.dierhanna.interfaces.OnFormClickListener;
import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.digitaldreamsapps.dierhanna.models.Form;

import java.util.List;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.ViewHolder> {

    private OnFormClickListener onAppointmentClicked;



    public void setOnAppointmentClicked(OnFormClickListener onAppointmentClicked) {
        this.onAppointmentClicked = onAppointmentClicked;
    }

    @NonNull
    @Override
    public FormsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.apointmentcell, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Form appointment = appointments.get(position);
        holder.itemDetail.setText(appointment.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAppointmentClicked.onClick(appointment);
            }
        });

    }
    List<Form> appointments;
    public FormsAdapter(List<Form> appointments){
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
