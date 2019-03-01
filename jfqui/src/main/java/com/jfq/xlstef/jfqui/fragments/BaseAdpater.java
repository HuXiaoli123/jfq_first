package com.jfq.xlstef.jfqui.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseAdpater<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {


    //定义  判断要传入的布局标记
    static final int TYPE_HODE = 0;
    static final int TYPE_NOMAL = 1;

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull T t, int i) {

    }

    @Override
    public int getItemCount() {
        // return number.size();
        return headView == null ? number.size() : number.size() + 1;
    }

    //该方法主要是对item 下标进行判断，当添加有头，position - 1 ，在以后调用就不用考虑position所在位置
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headView == null ? position : position - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView == null) return TYPE_NOMAL;
        if (position == 0) return TYPE_HODE;
        return TYPE_NOMAL;
    }



    View headView=null;
    public void setHeadView(View headView)
    {
        Log.i("headView",headView+"");
        this.headView = headView;
        notifyItemInserted(0);//在position位置插入数据的时候更新
    }
    boolean  hasMatch;
    String text;
    List <CategoryBean>number;
    List<CategoryBean>temp_number;
    TestFilter myFilter;

    public Filter getFilter() {

        if (myFilter == null) {
            myFilter = new BaseAdpater.TestFilter();
        }
        return myFilter;
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

    class TestFilter extends Filter {
        //constraint  ：过滤条件
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            hasMatch=false;
            text=constraint.toString();
            List<CategoryBean> new_number=new ArrayList();
            if (constraint != null && constraint.toString().trim().length() > 0) {
                for (int i = 0; i < temp_number.size(); i++) {

                    if( temp_number.get(i).getOrderNumber().contains(constraint)||temp_number.get(i).getOderType().contains(constraint))
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

    public   OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(  OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, CategoryBean categoryBean);
    }
}
