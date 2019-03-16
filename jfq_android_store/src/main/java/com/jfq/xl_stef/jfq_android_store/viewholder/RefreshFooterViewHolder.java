package com.jfq.xl_stef.jfq_android_store.viewholder;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jfq.xl_stef.jfq_android_store.R;

public class RefreshFooterViewHolder extends BaseViewHolder {
   public ProgressBar footer_pb_view;
   public TextView refresh_footer_text;
   public ImageView footer_image_view;
    public RefreshFooterViewHolder(View itemView) {
        super(itemView);
        footer_pb_view=itemView.findViewById(R.id.footer_pb_view);
        refresh_footer_text=itemView.findViewById(R.id.footer_text_view);
        footer_image_view=itemView.findViewById(R.id.footer_image_view);
    }
}
