package com.hxl.mytabledata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 订单布局-top
 * 作者：fly on 2016/8/24 0024 23:45
 * 邮箱：cugb_feiyang@163.com
 */
public class ItemOrderTop implements OrderContent {

    public ItemOrderTop() {

    }


    @Override
    public int getLayout() {
        return R.layout.item_order_top;
    }

    @Override
    public boolean isClickAble() {
        return true;
    }

    @Override
    public View getView(Context mContext, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(getLayout(), null);
        //TODO 数据展示


        return convertView;
    }
}
