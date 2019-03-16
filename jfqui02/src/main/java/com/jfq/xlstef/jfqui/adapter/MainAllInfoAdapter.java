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
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.SerachDetail.MoreDeail_Activity;
import com.jfq.xlstef.jfqui.interfaces.OnItemClickListener;
import com.jfq.xlstef.jfqui.viewholder.AllInfoViewHolder;
import com.jfq.xlstef.jfqui.viewholder.BaseViewHolder;
import com.jfq.xlstef.jfqui.viewholder.RefreshFooterViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAllInfoAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {
    private final static int TYPE_CONTENT=0;//正常内容
    private final static int TYPE_FOOTER=1;//加载View
    private Activity mContext;
    private List<CategoryBean> mDataSet;

    private  int mSelectfragment;

    public MainAllInfoAdapter(Activity context, List<CategoryBean> DataSet,int selectfragment ){
        mContext=context;
        mDataSet=DataSet;
        temp_number=DataSet;
        this.mSelectfragment=selectfragment;
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
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        if(getItemViewType(position)==TYPE_FOOTER){

        }else{
            AllInfoViewHolder allInfoViewHolder=(AllInfoViewHolder)holder;
            CategoryBean allInfoData=mDataSet.get(position);

            if(text!=null&&hasMatch) {
                allInfoViewHolder.code_text.setText(CorlorChangeText(allInfoData.getOrderNumber()));
                allInfoViewHolder.orderType_text.setText(CorlorChangeText(allInfoData.getOderType()));
                allInfoViewHolder.totalFee_text.setText(CorlorChangeText("总额："+String.valueOf(allInfoData.getStoreEntry())+"元"));
                allInfoViewHolder.salesAmount_text.setText(CorlorChangeText("实付：  "+String.valueOf(allInfoData.getUserPlay())+"元"));
                allInfoViewHolder.totalReduction_text.setText(CorlorChangeText("抵扣：  "+String.valueOf(allInfoData.getPlatformDeduction())+"元"));
                allInfoViewHolder.createTime_text.setText(CorlorChangeText( allInfoData.getPlayTime()));
            }else
            {
                allInfoViewHolder.code_text.setText(allInfoData.getOrderNumber());
                allInfoViewHolder.orderType_text.setText(allInfoData.getOderType());
                allInfoViewHolder.totalFee_text.setText("总额："+String.valueOf(allInfoData.getStoreEntry())+"元");
                allInfoViewHolder.salesAmount_text.setText("实付：  "+String.valueOf(allInfoData.getUserPlay())+"元");
                allInfoViewHolder.totalReduction_text.setText("抵扣：  "+String.valueOf(allInfoData.getPlatformDeduction())+"元");
                allInfoViewHolder.createTime_text.setText( allInfoData.getPlayTime());
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.OnItemClick(position);
                    Log.i("mydata",mDataSet.get(position).get_id()+";"+mDataSet.get(position).getOrderNumber());

                    Intent intent = new Intent();
                    intent.setClass(mContext, MoreDeail_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("beanData",mDataSet.get(position));
                    intent.putExtra("selectfrag",mSelectfragment);

                    mContext.startActivityForResult(intent,1);

                }
            });

        }
    }

    private  CharSequence CorlorChangeText(String categorText)
    {
        return matcherSearchText(Color.rgb(255, 0, 0),categorText, text);
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

    public OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(  OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 需要改变颜色的text
     */
    private String text;

    List<CategoryBean>temp_number;

   TestFilter myFilter;

    /**
     * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
     */
    private SpannableString matcherSearchText(int color, String text, String keyword) {


        SpannableString spannableString = new SpannableString(text);
        Log.i("MyWord---",keyword+keyword.indexOf(".") );
        //条件 keyword
        if(keyword.indexOf(".")>=0)
        {
            keyword=keyword.replace(".","\\.");
        }
        Log.i("MyWord------",keyword  );
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            Log.i("MyWord",text+","+keyword+","+start+","+end);
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }
    boolean hasMatch=true;

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
            hasMatch=false;
            text=constraint.toString();
            List<CategoryBean> new_number=new ArrayList();
            if (constraint != null && constraint.toString().trim().length() > 0) {
                for (int i = 0; i < temp_number.size(); i++) {

                    switch (mSelectfragment)
                    {
                        case 1:
                            if( temp_number.get(i).getOrderNumber().contains(constraint)||temp_number.get(i).getOderType().contains(constraint)||temp_number.get(i).getItemPrice().contains(constraint)
                                    ||temp_number.get(i).getStoreEntry().contains(constraint)||temp_number.get(i).getUserPlay().contains(constraint)
                                    ||temp_number.get(i).getPlatformDeduction().contains(constraint)||temp_number.get(i).getPlayTime().contains(constraint))
                            {
                                new_number.add( temp_number.get(i));
                                hasMatch=true;
                            }
                            break;
                        case 2:

                            if( temp_number.get(i).getOrderNumber().contains(constraint)||temp_number.get(i).getNameOfCommodity().contains(constraint)||temp_number.get(i).getItemPrice().contains(constraint)
                                    ||temp_number.get(i).getStoreEntry().contains(constraint)||temp_number.get(i).getUserPlay().contains(constraint)
                                    ||temp_number.get(i).getPlatformDeduction().contains(constraint)||temp_number.get(i).getPlayTime().contains(constraint))
                            {
                                new_number.add( temp_number.get(i));
                                hasMatch=true;
                            }
                            break;
                        case 3:
                            if( temp_number.get(i).getOrderNumber().contains(constraint)||temp_number.get(i).getItemPrice().contains(constraint)||temp_number.get(i).getAddpriceAmount().contains(constraint)
                                    ||temp_number.get(i).getStoreEntry().contains(constraint)||temp_number.get(i).getUserPlay().contains(constraint)
                                    ||temp_number.get(i).getPlatformDeduction().contains(constraint)||temp_number.get(i).getPlayTime().contains(constraint))
                            {
                                new_number.add( temp_number.get(i));
                                hasMatch=true;
                            }
                            break;
                        case 4:
                            break;

                    }

                }

            }else {

                new_number=temp_number;
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
            mDataSet = (List<CategoryBean>)(results.values);
            //如果过滤后的返回的值的个数大于等于0的话,对Adpater的界面进行刷新
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                //否则说明没有任何过滤的结果,直接提示用户"没有符合条件的结果"
                mDataSet = new ArrayList<CategoryBean>(){};
               // mDataSet.add(new CategoryBean(" 没有符合条件的结果"));
                Toast.makeText(mContext,"没有符合条件的结果",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }

        }
    }


}
