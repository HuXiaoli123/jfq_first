package com.jfq.xlstef.jfqui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.SerachDetail.MoreDeail_Activity;
import com.jfq.xlstef.jfqui.SerachDetail.SerachActivity;
import com.jfq.xlstef.jfqui.interfaces.OnItemClickListener;
import com.jfq.xlstef.jfqui.utils.SaveDifData.SharedPreferencesUtils;
import com.jfq.xlstef.jfqui.viewholder.AllInfoViewHolder;
import com.jfq.xlstef.jfqui.viewholder.BaseViewHolder;
import com.jfq.xlstef.jfqui.viewholder.RefreshFooterViewHolder;
import com.jfq.xlstef.jfqui.viewholder.SummaryInfoViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainSummaryInfoAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private final static int TYPE_CONTENT = 0;//正常内容
    private final static int TYPE_FOOTER = 1;//加载View
    private Context mContext;
    private List<DailyOrder> mDataSet=new ArrayList<>();//在使用它之前，给他分配默认值0是没有必要的

    private String storeName;

    SerachActivity serachActivity;

    public MainSummaryInfoAdapter(Context context, List<DailyOrder> DataSet) {
        mContext = context;
        mDataSet = DataSet;
        SharedPreferencesUtils helper = new SharedPreferencesUtils(mContext, "settings");
        storeName = helper.getString("store_name");

    }

    public MainSummaryInfoAdapter(SerachActivity context, List<DailyOrder> DataSet) {
        mContext = context;
        mDataSet=DataSet;
        serachActivity = context;
        SharedPreferencesUtils helper = new SharedPreferencesUtils(mContext, "settings");
        storeName = helper.getString("store_name");

    }
   /* public MainSummaryInfoAdapter(SerachActivity context, List<DailyOrder> DataSet,List<DailyOrder>temp_numberlist) {
        mContext = context;
        *//* temp_number=DataSet;*//*
        temp_number=temp_numberlist;
        mDataSet=DataSet;
        serachActivity = context;
        SharedPreferencesUtils helper = new SharedPreferencesUtils(mContext, "settings");
        storeName = helper.getString("store_name");

    }*/


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Log.i("store_name","onCreateViewHolder");//门店名称
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_refresh_footer, parent, false);
            return new RefreshFooterViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_summaryinfo, parent, false);
            return new SummaryInfoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {

        } else {
            SummaryInfoViewHolder summaryInfoViewHolder = (SummaryInfoViewHolder) holder;
            DailyOrder dailyOrder = mDataSet.get(position);


            summaryInfoViewHolder.storeName.setText(storeName);

            if (text != null && hasMatch) {
                summaryInfoViewHolder.playTime.setText(CorlorChangeText(dailyOrder.getPlayTime()));
                summaryInfoViewHolder.sweepPay.setText(CorlorChangeText("扫码支付: " + String.valueOf(dailyOrder.getSweepPay()) + "元"));
                summaryInfoViewHolder.addpriceAmount.setText(CorlorChangeText("加价购订单: " + dailyOrder.getAddpriceAmount() + "元"));
                summaryInfoViewHolder.comdityOrder.setText(CorlorChangeText("商城订单: " + dailyOrder.getComdityOrder() + "元"));
                summaryInfoViewHolder.comissionOrder.setText(CorlorChangeText("佣金入账: " + dailyOrder.getComissionOrder() + "元"));
                summaryInfoViewHolder.entryValue.setText(CorlorChangeText("总额: " + dailyOrder.getEntryValue() + "元"));
            } else {
                summaryInfoViewHolder.playTime.setText(dailyOrder.getPlayTime());
                summaryInfoViewHolder.sweepPay.setText("扫码支付: " + String.valueOf(dailyOrder.getSweepPay()) + "元");
                summaryInfoViewHolder.addpriceAmount.setText("加价购订单: " + dailyOrder.getAddpriceAmount() + "元");
                summaryInfoViewHolder.comdityOrder.setText("商城订单: " + dailyOrder.getComdityOrder() + "元");
                summaryInfoViewHolder.comissionOrder.setText("佣金入账: " + dailyOrder.getComissionOrder() + "元");
                summaryInfoViewHolder.entryValue.setText("总额: " + dailyOrder.getEntryValue() + "元");
            }


           /* holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.OnItemClick(position);
                    Log.i("mydata",mDataSet.get(position).get_id()+";"+mDataSet.get(position).getOrderNumber());

                    Intent intent = new Intent();
                    intent.setClass(mContext, MoreDeail_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("beanData",mDataSet.get(position));
                    mContext.startActivity(intent);

                }
            });*/

        }
    }

    private CharSequence CorlorChangeText(String categorText) {
        return matcherSearchText(Color.rgb(255, 0, 0), categorText, text);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDataSet.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 需要改变颜色的text
     */
    private String text;

    public void setTemp_number(List<DailyOrder> temp_number) {

        this.temp_number = temp_number;


    }



    private List<DailyOrder> temp_number;

    TestFilter myFilter;

    /**
     * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
     */
    private SpannableString matcherSearchText(int color, String categorText, String keyword) {


        SpannableString spannableString = new SpannableString(categorText);
        if (keyword.indexOf(".") >= 0)
            keyword = keyword.replace(".", "\\.");
        //条件 keyword
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            Log.i("MyWord---", keyword + "," + categorText + ";" + start + ";" + end);
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        //返回变色处理的结果
        return spannableString;
    }

    boolean hasMatch = true;

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new TestFilter();
        }
        return myFilter;
    }

    class TestFilter extends Filter {
        //constraint  ：过滤条件
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            hasMatch = false;
            text = constraint.toString();


           /* Log.i("mycounts",  "setTemp_number"+temp_number.get(0).getEntryValue());
            Log.i("mycounts", "text:"+text+","+temp_number.size());*/
            List<DailyOrder> new_number = new ArrayList();
            if (constraint != null && constraint.toString().trim().length() > 0) {
                for (int i = 0; i < temp_number.size(); i++) {

                    if (temp_number.get(i).getPlayTime().contains(constraint) || temp_number.get(i).getSweepPay().contains(constraint) ||
                            temp_number.get(i).getAddpriceAmount().contains(constraint)
                            || temp_number.get(i).getComdityOrder().contains(constraint) || temp_number.get(i).getComissionOrder().contains(constraint)
                            || temp_number.get(i).getEntryValue().contains(constraint)) {
                        new_number.add(temp_number.get(i));
                        hasMatch = true;
                    }
                }
                Tiptext="没有符合条件的结果";
            } else {

                Tiptext="没有输入关键字";
              /*  new_number = temp_number;*/
            }
            FilterResults filterResults = new FilterResults();
            filterResults.count = new_number.size();
            filterResults.values = new_number;
            return filterResults;
        }

        //参数CharSequence,results第一个参数这里不需要用到,第二个参数是上面的performFiltering返回的值.
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {


            //这里对number进行过滤后重新赋值
            List<DailyOrder> myquaryData = (List<DailyOrder>) (results.values);

          //  mDataSet.clear();  //使用mDataSet  怎么就把temp_number 清除了呢
            mDataSet=new ArrayList<>();

            //如果过滤后的返回的值的个数大于等于0的话,对Adpater的界面进行刷新

                if (results.count > 0) {

                    if (myquaryData.size() >= 10) {
                        for (int i = 0; i < 10; i++) {
                            mDataSet.add(myquaryData.get(i));
                        }
                    } else {
                        mDataSet = myquaryData;
                    }

                    notifyDataSetChanged();
                    serachActivity.setmDailyqueryData(myquaryData,mDataSet);


                } else {
                    //否则说明没有任何过滤的结果,直接提示用户"没有符合条件的结果"
                    mDataSet = new ArrayList<DailyOrder>() {
                    };
                    Toast.makeText(mContext,Tiptext,Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }

        }


    }
    String Tiptext;
}
