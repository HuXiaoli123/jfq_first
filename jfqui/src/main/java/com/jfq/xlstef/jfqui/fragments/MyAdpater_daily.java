package com.jfq.xlstef.jfqui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAdpater_daily extends  BaseAdpater<MyAdpater_daily.ViewHolder> implements Filterable {
    List <DailyOrder>number;
    List<DailyOrder>temp_number;

    TestFilter myFilter;

    /**
     * 需要改变颜色的text
     */
    private String text;

    /**
     * View  布局分配
     */
    View headView=null;
    //定义  判断要传入的布局标记
    static final int TYPE_HODE = 0;
    static final int TYPE_NOMAL = 1;

    Context mContext;
   // ProgressView progress_loading_main; // 加载数据时显示的进度圆圈


    public MyAdpater_daily(List<DailyOrder> number, Context context) {

        this.number=number;
        this.mContext=context;
        temp_number = number;
    }


     // 生成为每个Item inflater出一个View，  法返回的是一个ViewHolder
    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailyoder_item_layout, null);
        //ViewHolder viewHolder = new ViewHolder(view);

        if (headView != null && viewType == TYPE_HODE) {
            return new ViewHolder(headView);}
        else {
            View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailyoder_item_layout, parent, false);
            return new ViewHolder(holder);}

       // return viewHolder;
    }

    //该方法主要是对item 下标进行判断，当添加有头，position - 1 ，在以后调用就不用考虑position所在位置
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headView == null ? position : position - 1;
    }
    CategoryBeanDAO dao;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HODE) {
            return;
        }
        final int pos = getRealPosition(holder);
        DailyOrder categoryBean = number.get(pos);
        /*//设置span
        SpannableString string = matcherSearchText(Color.rgb(255, 0, 0), list.get(position), text);
        holder.mTvText.setText(string);*/
        Log.i("text",text +hasMatch);
       if(text!=null&&hasMatch)
       {
           holder.playTime.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getPlayTime(), text));
           //支付时间
           holder.sweepPay.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getSweepPay(), text));

           holder.addpriceAmount.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getAddpriceAmount(), text));
           holder.comdityOrder.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getComdityOrder(), text));
           holder.comissionOrder.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getComissionOrder(), text));
           holder.entryValue.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getEntryValue(), text));
       }else
       {
           holder.playTime.setText(categoryBean.getPlayTime());
           holder.sweepPay.setText(categoryBean.getSweepPay());
           holder.addpriceAmount.setText(categoryBean.getAddpriceAmount());
           holder.comdityOrder.setText(categoryBean.getComdityOrder());
           holder.comissionOrder.setText(categoryBean.getComissionOrder());
           holder.entryValue.setText(categoryBean.getEntryValue());
       }


    }

    /* 加价购细节对话框*/
    public  void DetailMsg( String []allPriceName)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(mContext).inflate(R.layout.addcountpage, null);
// 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
//这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final TextView et_Threshold = view.findViewById(R.id.edThreshold);

        Log.i("path",allPriceName.length+"");
        StringBuilder s=new StringBuilder();
        if(allPriceName!=null)
        {
            for(int i=0;i<allPriceName.length;i++)
            {
                Log.i("path",allPriceName[i]);
                s.append(allPriceName[i]).append("\n");
            }
        }

        et_Threshold.setText(s.toString());

        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认

                //写相关的服务代码

                //关闭对话框
                dialog.dismiss();
            }
        });


    }

    @Override
    public int getItemCount() {
       // return number.size();
        return headView == null ? number.size() : number.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView == null) return TYPE_NOMAL;
        if (position == 0) return TYPE_HODE;
        return TYPE_NOMAL;
    }

    @Override
    public Filter getFilter() {

        if (myFilter == null) {
            myFilter = new TestFilter();
        }
        return myFilter;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView playTime; //日期
        private TextView sweepPay;     //扫码支付
        private TextView addpriceAmount;   //加价购订单
        private TextView comdityOrder;//商城订单
        private TextView comissionOrder;      //佣金入账
        private TextView entryValue;       //入账总额

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == headView) return;
            playTime = (TextView) itemView.findViewById(R.id.date);
            sweepPay= (TextView) itemView.findViewById(R.id.sweepCodepay);
            addpriceAmount= (TextView) itemView.findViewById(R.id.addCountOrder);

            comdityOrder=(TextView)itemView.findViewById(R.id.comodityOrder);
            comissionOrder=(TextView)itemView.findViewById(R.id.commissionEntry);
            entryValue= (TextView) itemView.findViewById(R.id.entryValue);
        }
    }


    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);//在position位置插入数据的时候更新
    }


    /**
     * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
     */
    private SpannableString matcherSearchText(int color, String text, String keyword) {


        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }
    boolean hasMatch=true;
    class TestFilter extends Filter {
        //constraint  ：过滤条件
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            hasMatch=false;
            text=constraint.toString();
            List<DailyOrder> new_number=new ArrayList();
            if (constraint != null && constraint.toString().trim().length() > 0) {
                for (int i = 0; i < temp_number.size(); i++) {

                    if( temp_number.get(i).getPlayTime().contains(constraint)||temp_number.get(i).getSweepPay().contains(constraint)||temp_number.get(i).getAddpriceAmount().contains(constraint)
                            ||temp_number.get(i).getComdityOrder().contains(constraint)||temp_number.get(i).getComissionOrder().contains(constraint)
                            ||temp_number.get(i).getEntryValue().contains(constraint) )
                    {
                        new_number.add( temp_number.get(i));
                        hasMatch=true;
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
            number = (List<DailyOrder>)(results.values);
            //如果过滤后的返回的值的个数大于等于0的话,对Adpater的界面进行刷新
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                //否则说明没有任何过滤的结果,直接提示用户"没有符合条件的结果"
                number = new ArrayList<DailyOrder>(){};
                number.add(new DailyOrder(" 没有符合条件的结果"));
                notifyDataSetChanged();
            }

        }
    }

}
