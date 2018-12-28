package com.hxl.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：fly on 2016/8/24 0024 23:34
 * 邮箱：cugb_feiyang@163.com
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<OrderContent> list;
    private LayoutInflater mIflater;


    public MyAdapter(Context mContext, List<OrderContent> list) {
        this.mContext = mContext;
        this.list = list;
    }
    int times =0;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder =
                new MyViewHolder(list.get(viewType).getView(mContext, parent, mIflater));
        times++;
        Log.d("MyAdapter", "times:" + times);
        return holder;
    }

    /**
     * 每一个位置的item都作为单独一项来设置
     * viewType 设置为position
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

//        OrderContent content = list.get(position);
//        if(content instanceof ItemOrderTop){
//            return 0;
//        }
//        if(content instanceof ItemOrderBottom){
//            return 1;
//        }
//        return 2;
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 更新数据
     * @param orderContents
     */
    public void updateList(List<OrderContent> orderContents){
        this.list = orderContents;
        this.notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clearList(){
        this.list.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
