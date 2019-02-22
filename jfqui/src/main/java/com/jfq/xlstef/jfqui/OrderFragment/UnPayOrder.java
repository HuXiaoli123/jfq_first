package com.jfq.xlstef.jfqui.OrderFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.R;

/*
未支付订单
 */
public class UnPayOrder extends  BaseFragment {

    public UnPayOrder()
    {
        pageLayout= R.layout.unplayorder6;
        path="unpaid";
        /*path="https://blog.csdn.net/qq_37140150/article/details/85287751";
        path="file:///D:/A/OrderPrj/allOrder.html";*/
        CategoryAdapter.mOrdername=CategoryAdapter.OrderName.UnpayOrder;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
