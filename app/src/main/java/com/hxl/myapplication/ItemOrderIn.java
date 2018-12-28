package com.hxl.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单布局-in
 * 作者：fly on 2016/8/24 0024 23:45
 * 邮箱：cugb_feiyang@163.com
 */
public class ItemOrderIn implements OrderContent {

    private List<OrderGoods> list;
    private OrderGoods orderGoods;

    public ItemOrderIn(OrderGoods orderGoods) {
        this.orderGoods = orderGoods;
        list = new ArrayList<>();
        list.add(orderGoods);
    }

    @Override
    public int getLayout() {
        return R.layout.item_order_in;
    }

    @Override
    public boolean isClickAble() {
        return true;
    }

    @Override
    public View getView(Context mContext, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(mContext);
        convertView =  inflater.inflate(getLayout(),null);
        //TODO 数据展示-订单内容
        TextView nameTv = (TextView) convertView.findViewById(R.id.good_name);
        nameTv.setText(orderGoods.getGoodName());
        TextView snTv = (TextView) convertView.findViewById(R.id.good_sn);
        snTv.setText(orderGoods.getGoodSn());
        return convertView;
    }
}
