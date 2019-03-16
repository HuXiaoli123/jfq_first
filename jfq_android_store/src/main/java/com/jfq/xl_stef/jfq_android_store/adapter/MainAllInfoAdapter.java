package com.jfq.xl_stef.jfq_android_store.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jfq.xl_stef.jfq_android_store.R;
import com.jfq.xl_stef.jfq_android_store.data.AllInfoData;
import com.jfq.xl_stef.jfq_android_store.viewholder.AllInfoViewHolder;
import com.jfq.xl_stef.jfq_android_store.viewholder.BaseViewHolder;
import com.jfq.xl_stef.jfq_android_store.viewholder.RefreshFooterViewHolder;

import java.text.DateFormat;
import java.util.List;

public class MainAllInfoAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final static int TYPE_CONTENT=0;//正常内容
    private final static int TYPE_FOOTER=1;//加载View
    private Context mContext;
    private List<AllInfoData> mDataSet;

    public MainAllInfoAdapter(Context context,List<AllInfoData> DataSet){
        mContext=context;
        mDataSet=DataSet;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TYPE_FOOTER){
            View view=LayoutInflater.from(mContext).inflate(R.layout.layout_refresh_footer,parent,false);
            return new RefreshFooterViewHolder(view);
        }else {
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_main_allinfo,parent,false);
            return  new AllInfoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_FOOTER){

        }else{
            AllInfoViewHolder allInfoViewHolder=(AllInfoViewHolder)holder;
            AllInfoData allInfoData=mDataSet.get(position);
            allInfoViewHolder.code_text.setText(allInfoData.getCode());
            allInfoViewHolder.orderType_text.setText(allInfoData.getOrderType());
            allInfoViewHolder.totalFee_text.setText(String.valueOf(allInfoData.getTotalFee())+"元");
            allInfoViewHolder.salesAmount_text.setText("实付：  "+String.valueOf(allInfoData.getSalesAmount())+"元");
            allInfoViewHolder.totalReduction_text.setText("抵扣：  "+String.valueOf(allInfoData.getTotalReduction())+"元");
            allInfoViewHolder.createTime_text.setText(DateFormat.getDateTimeInstance().format(allInfoData.getCreateTime()));
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==mDataSet.size()){
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }
}
