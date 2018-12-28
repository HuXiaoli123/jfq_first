package com.hxl.mytabledata;

import android.content.Context;
import android.support.v7.app.ActionBarDrawerToggle;
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


    TextView  orderNumber;
    TextView  oderType;
    TextView  itemPrice;
    TextView  platformDeduction;
    TextView  userPlay;
    TextView storeEntry;
    TextView playTime;

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
        orderNumber = (TextView) convertView.findViewById(R.id.orderNumber);
        orderNumber.setText(orderGoods.getOrderNumber());

        oderType= (TextView) convertView.findViewById(R.id.oderType);
        oderType.setText(orderGoods.getOderType());

        itemPrice= (TextView) convertView.findViewById(R.id.itemPrice);
        itemPrice.setText(orderGoods.getItemPrice());

        platformDeduction= (TextView) convertView.findViewById(R.id.platformDeduction);
        platformDeduction.setText(orderGoods.getPlatformDeduction());

        userPlay = (TextView) convertView.findViewById(R.id.userPlay);
        userPlay.setText(orderGoods.getUserPlay());

        storeEntry= (TextView) convertView.findViewById(R.id.storeEntry);
        storeEntry.setText(orderGoods.getStoreEntry());

        playTime = (TextView) convertView.findViewById(R.id.playTime);
        playTime.setText(orderGoods.getPlayTime());




        return convertView;
    }

    /*ActionBarDrawerToggle.Delegate func;
    void  SetText(View convertView,TextView tv, int id ,func)
    {
        tv=convertView.findViewById(id);
        tv.setText(tv.get);
    }*/
}
