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

import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAdpater_pay extends  BaseAdpater<MyAdpater_pay.ViewHolder> implements Filterable {
    List <CategoryBean>number;
    List<CategoryBean>temp_number;

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


    public MyAdpater_pay(List<CategoryBean> number,Context context) {

        this.number=number;
        this.mContext=context;
        temp_number = number;
    }


     // 生成为每个Item inflater出一个View，  法返回的是一个ViewHolder
    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, null);
        //ViewHolder viewHolder = new ViewHolder(view);

        if (headView != null && viewType == TYPE_HODE) {
            return new ViewHolder(headView);}
        else {
            View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
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
        CategoryBean categoryBean = number.get(pos); 
        /*//设置span
        SpannableString string = matcherSearchText(Color.rgb(255, 0, 0), list.get(position), text);
        holder.mTvText.setText(string);*/
        Log.i("text",text +hasMatch);
       if(text!=null&&hasMatch)
       {
           holder.orderNumber.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getOrderNumber(), text));
           //支付时间
           holder.playTime.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getPlayTime(), text));

           holder.platformDeduction.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getPlatformDeduction(), text));
           holder.userPlay.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getUserPlay(), text));
           holder.storeEntry.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getStoreEntry(), text));
           holder.oderType.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getOderType(), text));
           holder.itemPrice.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getItemPrice(), text));
       }else
       {
            holder.orderNumber.setText(categoryBean.getOrderNumber() );
        //支付时间
        holder.playTime.setText(categoryBean.getPlayTime());

        holder.platformDeduction.setText(categoryBean.getPlatformDeduction());
        holder.userPlay.setText(categoryBean.getUserPlay());
        holder.storeEntry.setText(categoryBean.getStoreEntry());
           holder.oderType.setText(categoryBean.getItemPrice());
           Log.i("MySweepCode","SweepCode");
           if(!"0.0".equals(categoryBean.getAddpriceAmount()))
           {
               holder.itemPrice.setText(categoryBean.getAddpriceAmount());
               holder.mMoreDetail.setVisibility(View.VISIBLE);
               dao=new CategoryBeanDAO(new DBHelper(mContext));


               //给加价购提供点击事件
               ((MyAdpater_pay.ViewHolder) holder).addCountpage.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       Log.i("MySweepCode","我是加价购");
                       if (mOnItemClickListener != null) {
                           mOnItemClickListener.OnItemClick(v, pos, number.get(pos));
                           String addPriceName=dao.queryById(number.get(pos).get_id());//因为数据库中的id是从1开始的
                           Log.i("path","我是加价购"+pos+","+number);
                           String[] allPriceName=addPriceName.split("-");

                           Log.i("mytest_", number.get(pos).getOrderNumber()+";"+ number.get(pos).get_id());
                           //弹出加价购的详细信息
                           DetailMsg(allPriceName);
                           number.get(pos).getOrderNumber();

                       }
                   }
               });
           }else
           {
               holder.itemPrice.setText("无");
           }

       }
       /* holder.orderNumber.setText(matcherSearchText(Color.rgb(255, 0, 0), categoryBean.getOrderNumber(), text));
        //支付时间
        holder.playTime.setText(categoryBean.getPlayTime());

        holder.platformDeduction.setText(categoryBean.getPlatformDeduction());
        holder.userPlay.setText(categoryBean.getUserPlay());
        holder.storeEntry.setText(categoryBean.getStoreEntry());
        holder.oderType.setText(categoryBean.getOderType());
        holder.itemPrice.setText(categoryBean.getItemPrice());*/

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
        private TextView  orderNumber;
        private TextView  oderType;
        private TextView  itemPrice;
        private TextView  platformDeduction;
        private TextView  userPlay;
        private TextView storeEntry;
        private TextView playTime;
        private TextView  payStatus;

        private ImageView mMoreDetail;
        private LinearLayout addCountpage;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == headView) return;
            orderNumber = (TextView) itemView.findViewById(R.id.orderNumber);
            oderType= (TextView) itemView.findViewById(R.id.oderType);
            itemPrice= (TextView) itemView.findViewById(R.id.itemPrice);

            addCountpage=(LinearLayout)itemView.findViewById(R.id.addCountpage);
            mMoreDetail=(ImageView)itemView.findViewById(R.id.addCount);

            platformDeduction= (TextView) itemView.findViewById(R.id.platformDeduction);
            userPlay = (TextView) itemView.findViewById(R.id.userPlay);
            storeEntry= (TextView) itemView.findViewById(R.id.storeEntry);
            playTime = (TextView) itemView.findViewById(R.id.playTime);
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
            List<CategoryBean> new_number=new ArrayList();
            if (constraint != null && constraint.toString().trim().length() > 0) {
                for (int i = 0; i < temp_number.size(); i++) {

                    if( temp_number.get(i).getOrderNumber().contains(constraint)||temp_number.get(i).getItemPrice().contains(constraint)||temp_number.get(i).getAddpriceAmount().contains(constraint)
                            ||temp_number.get(i).getStoreEntry().contains(constraint)||temp_number.get(i).getUserPlay().contains(constraint)
                            ||temp_number.get(i).getPlatformDeduction().contains(constraint)||temp_number.get(i).getPlayTime().contains(constraint))
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
            number = (List<CategoryBean>)(results.values);
            //如果过滤后的返回的值的个数大于等于0的话,对Adpater的界面进行刷新
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                //否则说明没有任何过滤的结果,直接提示用户"没有符合条件的结果"
                number = new ArrayList<CategoryBean>(){};
                number.add(new CategoryBean(" 没有符合条件的结果"));
                notifyDataSetChanged();
            }

        }
    }

}
