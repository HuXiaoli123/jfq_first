package com.hxl.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author justin on 2016/08/28 01:34
 *         justin@magicare.me
 * @version V1.0
 */
public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.BaseViewHolder> {


    List<Object> list;
    private int createTimes = 0;
    public MyAdapter2(List<Object> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder vh = null;
        createTimes++;
        Log.d("MyAdapter2", "createTimes:" + createTimes);
        switch (viewType){
            case 0:
                return new TopHolder(inflate(parent,R.layout.item_order_top));
            case 1:
                return new BottomHolder(inflate(parent,R.layout.item_order_bottom));
            case 2:
                return new GoodsHolder(inflate(parent,R.layout.item_order_in));
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {

        Object content = list.get(position);
        if(content instanceof TopBean){
            return 0;
        }
        if(content instanceof BottomBean){
            return 1;
        }
        return 2;
    }


    private View inflate(ViewGroup parent,int layout){

       return LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
    }

    public abstract  class BaseViewHolder<DATA> extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindData(DATA data);
    }


    /**
     * top部分
     */
    public class TopHolder extends BaseViewHolder<TopBean>{

        public TopHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(TopBean itemOrderTop) {
            //TODO 处理数据
        }
    }

    /**
     * Bottom部分
     */
    public class BottomHolder extends BaseViewHolder<BottomBean>{

        public BottomHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(BottomBean itemOrderTop) {
            //TODO 处理数据
        }
    }


    /**
     * 商品部分
     */
    public class GoodsHolder extends BaseViewHolder<OrderBean>{
        TextView nameTv;
        TextView snTv;
        public GoodsHolder(View itemView) {
            super(itemView);
            nameTv= (TextView) itemView.findViewById(R.id.good_name);
            snTv = (TextView) itemView.findViewById(R.id.good_sn);
        }

        @Override
        public void bindData(OrderBean orderBean) {

            //TODO 处理数据
            nameTv.setText(orderBean.getGoodName());
            snTv.setText(orderBean.getGoodSn());
        }
    }

}
