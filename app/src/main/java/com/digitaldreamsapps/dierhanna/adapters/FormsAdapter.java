package com.digitaldreamsapps.dierhanna.adapters;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.Form;
import java.io.File;
import java.util.List;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.ViewHolder> {

    private List<Form> forms;
    private OnItemClickedListener onItemClickedListener;

    public FormsAdapter(List<Form> forms){
        this.forms = forms;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public FormsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.formcell, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Form form = forms.get(position);
        holder.itemDetail.setText(form.getTitle());
        isFileDownloaded(form,holder.download);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClicked(form);
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClicked(form);

            }
        });

    }



    @Override
    public int getItemCount() {
        return forms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView itemDetail;
        public ImageView download;

        public ViewHolder(View itemView) {
            super(itemView);

            itemDetail = itemView.findViewById(R.id.appontmenttitle);
            download=  itemView.findViewById(R.id.downloadimg);

        }
    }

    private boolean isFileDownloaded(Form form , ImageView downloadImg){
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/DierHanna/" + form.getTitle()+".pdf");
        if (!pdfFile.exists()){
           downloadImg.setVisibility(View.VISIBLE);
           return false;
        }else{
            downloadImg.setVisibility(View.GONE);
            return true;
        }

    }
}
