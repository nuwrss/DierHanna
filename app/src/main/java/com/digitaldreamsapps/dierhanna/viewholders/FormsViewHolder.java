package com.digitaldreamsapps.dierhanna.viewholders;

import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.Form;
import java.io.File;

public class FormsViewHolder<T> extends BaseViewHolder {
   private ImageView downloadImg;
    public FormsViewHolder(@NonNull View itemView) {
        super(itemView);
        downloadImg=  itemView.findViewById(R.id.downloadimg);

    }
    public void setDetails(final Object item , final OnItemClickedListener onItemClickedListener){
        super.setDetails(item,onItemClickedListener);

        isFileDownloaded((Form)item,downloadImg);

        downloadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClicked(item);
            }
        });

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
