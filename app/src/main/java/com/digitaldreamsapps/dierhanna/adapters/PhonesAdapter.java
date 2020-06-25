package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnPhoneClickListener;
import com.digitaldreamsapps.dierhanna.models.Phones;

import java.util.List;

public class PhonesAdapter extends RecyclerView.Adapter<PhonesAdapter.ViewHolder> {


    public OnPhoneClickListener getOnPhoneClickListener() {
        return onPhoneClickListener;
    }

    public void setOnPhoneClickListener(OnPhoneClickListener onPhoneClickListener) {
        this.onPhoneClickListener = onPhoneClickListener;
    }

    private OnPhoneClickListener onPhoneClickListener;
    @NonNull
    @Override
    public PhonesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.importantphones_cell, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Phones phone = phones.get(position);

        holder.title.setText(phone.getTitle());

        holder.phonetext1.setText(phone.getPhones().get(0));
        holder.phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhoneClickListener.onPhoneNumberClick(phone.getPhones().get(0));
            }
        });

        if (phone.getPhones().size()>1){
            holder.phonetext2.setText(phone.getPhones().get(1));
            holder.phonetext2.setVisibility(View.VISIBLE);
            holder.phone2.setVisibility(View.VISIBLE);
            holder.phone2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPhoneClickListener.onPhoneNumberClick(phone.getPhones().get(1));
                }
            });
        }else{
            holder.phonetext2.setText("");
            holder.phonetext2.setVisibility(View.GONE);
            holder.phone2.setVisibility(View.GONE);
            holder.phone2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        if (phone.getEmail()==null || phone.getEmail().trim().equals("")){
            holder.emailtext.setVisibility(View.GONE);
            holder.emailtext.setText("");
            holder.email.setVisibility(View.GONE);
            holder.email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else{
            holder.emailtext.setVisibility(View.VISIBLE);
            holder.emailtext.setText(phone.getEmail());
            holder.email.setVisibility(View.VISIBLE);
            holder.email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPhoneClickListener.onEmailClick(phone.getEmail());
                }
            });
        }

    }
    List<Phones> phones;
    public PhonesAdapter(List<Phones> phones){
        this.phones = phones;
    }



    @Override
    public int getItemCount() {
        return phones.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView title,phonetext1,phonetext2,emailtext;
        public ImageView phone1,phone2,email;

        public ViewHolder(View itemView) {
            super(itemView);

            title =
                    (TextView)itemView.findViewById(R.id.ttitle);
            phonetext1 =
                    (TextView)itemView.findViewById(R.id.phonetext1);
            phonetext2 =
                    (TextView)itemView.findViewById(R.id.phonetext2);

            emailtext =
                    (TextView)itemView.findViewById(R.id.emailtext);

            phone1 =
                    itemView.findViewById(R.id.phone1);

            phone2 =
                    itemView.findViewById(R.id.phone2);

            email =
                    itemView.findViewById(R.id.email);
        }
    }
}
