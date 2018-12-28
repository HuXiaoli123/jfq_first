package com.hxl.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyAdapter adapter;

    private Context mContext;

    List<OrderContent> orderContents;
    List<Object> orderContents2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        orderContents = new ArrayList<OrderContent>();

        initData();
//        initData2();
        initRecyclerView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode==9)
        {

            initData();
            initRecyclerView();
        }
        Log.i("myapplication",keyCode+"");
        return false;
    }

    /**
     * 设置数据
     */
    private void initData( ) {
        //TODO 最外层数据-一般是从接口获取
        List<Order> orderList = new ArrayList<>();

        for (int i = 0; i <3; i++) {
            Order order = new Order();
            orderList.add(order);
        }

        //外部第一个循环，将数据循环读取后存到订单顶部
        for (int k = 0; k < orderList.size(); k++) {
            List<OrderGoods> orderGoodses = new ArrayList<>();
            for (int i = 0; i < k + 1; i++) {
                OrderGoods orderGood = new OrderGoods();
                orderGood.setGoodName("商品" + i);
                orderGood.setGoodSn("商品SN" + i);
                orderGoodses.add(orderGood);
            }

            //TODO 设置顶部数据-需要的数据直接传
            ItemOrderTop itemOrderTop = new ItemOrderTop();
            orderContents.add(itemOrderTop);

            if (orderGoodses == null) {
                //没有订单
            } else {
                //中间for循环，将数据循环读取后存到订单中间部分
                //TODO 设置中间数据
                for (int j = 0; j < orderGoodses.size(); j++) {
                    OrderGoods goods = new OrderGoods();
                    goods.setGoodName(orderGoodses.get(j).getGoodName());
                    goods.setGoodSn(orderGoodses.get(j).getGoodSn());

                    //需要的数据直接传
                    ItemOrderIn orderIMiddle = new ItemOrderIn(goods);
                    orderContents.add(orderIMiddle);
                    Log.i("myLog", "orderContents =" + orderContents);
                }
            }

            //外部第二个循环，将数据循环读取后存到订单底部
            //TODO 设置底部数据-需要的数据直接传
            ItemOrderBottom orderBottom = new ItemOrderBottom();
            orderContents.add(orderBottom);

        }
    }

    /**
     * 初始化View
     */
    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        Log.i("myadapters","footerView"+recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("myadapters","footerView"+recyclerView);
            }
           boolean isLoadMore;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("myadapters","footerView"+newState);
              /*  if (isRefresh) {
                    return;
                }
                if (mState != STATE_NORMAL) {
                    return;
                }*/
                //判断是否最后一item个显示出来
               RecyclerView.LayoutManager layoutManager =recyclerView.getLayoutManager();

                //可见的item个数
                int visibleChildCount = layoutManager.getChildCount();
                Log.i("myadapters","footerView"+visibleChildCount);
                if (visibleChildCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoadMore) {
                    View lastVisibleView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int lastVisiblePosition = recyclerView.getChildLayoutPosition(lastVisibleView);
                    if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                       // footerView.setVisibility(VISIBLE);
                        Log.i("myadapter","footerView");
                        isLoadMore = true;
                      /*  if (myRecyclerViewListener != null) {
                            myRecyclerViewListener.onLoadMore();
                        }*/
                    } else {
                        /*footerView.setVisibility(GONE);*/
                        Log.i("myadapter","footerView_no");
                    }
                }

            }
        });

        adapter = new MyAdapter(mContext, orderContents);
        //MyAdapter2 adapter2 = new MyAdapter2(orderContents2);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }


    /*private void initData2() {
        //TODO 最外层数据-一般是从接口获取 模拟分组数据
        List<List<OrderBean>> orderList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            List<OrderBean> orderGroup = new ArrayList<>();
            for(int k = 0;k < i+1;k++){
                OrderBean orderBean = new OrderBean();
                orderBean.setGoodName(String.format("group%d", i));
                orderBean.setGoodSn(String.format("item%d", k));
                orderGroup.add(orderBean);
            }
            orderList.add(orderGroup);
        }

        orderContents2 = new ArrayList<>();

        //拆分数据

        if(orderList != null){
            for(List<OrderBean> group : orderList){
                orderContents2.add(new TopBean());
                orderContents2.addAll(group);
                orderContents2.add(new BottomBean());
            }

        }


    }*/

}




/*public class MainActivity extends AppCompatActivity {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyAdapter adapter;

    private Context mContext;

    List<OrderContent> orderContents;
    List<Object> orderContents2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        orderContents = new ArrayList<OrderContent>();

        initData();
//        initData2();
        initRecyclerView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if(keyCode==9)
        {

            initData();
            initRecyclerView();
        }
        Log.i("myapplication",keyCode+"");
        return false;
    }

    *//**
     * 设置数据
     *//*
    private void initData( ) {
        //TODO 最外层数据-一般是从接口获取
        List<Order> orderList = new ArrayList<>();

        for (int i = 0; i <3; i++) {
            Order order = new Order();
            orderList.add(order);
        }

        //外部第一个循环，将数据循环读取后存到订单顶部
        for (int k = 0; k < orderList.size(); k++) {
            List<OrderGoods> orderGoodses = new ArrayList<>();
            for (int i = 0; i < k + 1; i++) {
                OrderGoods orderGood = new OrderGoods();
                orderGood.setGoodName("商品" + i);
                orderGood.setGoodSn("商品SN" + i);
                orderGoodses.add(orderGood);
            }

            //TODO 设置顶部数据-需要的数据直接传
            ItemOrderTop itemOrderTop = new ItemOrderTop();
            orderContents.add(itemOrderTop);

            if (orderGoodses == null) {
                //没有订单
            } else {
                //中间for循环，将数据循环读取后存到订单中间部分
                //TODO 设置中间数据
                for (int j = 0; j < orderGoodses.size(); j++) {
                    OrderGoods goods = new OrderGoods();
                    goods.setGoodName(orderGoodses.get(j).getGoodName());
                    goods.setGoodSn(orderGoodses.get(j).getGoodSn());

                    //需要的数据直接传
                    ItemOrderIn orderIMiddle = new ItemOrderIn(goods);
                    orderContents.add(orderIMiddle);
                    Log.i("myLog", "orderContents =" + orderContents);
                }
            }

            //外部第二个循环，将数据循环读取后存到订单底部
            //TODO 设置底部数据-需要的数据直接传
            ItemOrderBottom orderBottom = new ItemOrderBottom();
            orderContents.add(orderBottom);

        }
    }

    *//**
     * 初始化View
     *//*
    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new MyAdapter(mContext, orderContents);
        //MyAdapter2 adapter2 = new MyAdapter2(orderContents2);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }


    *//*private void initData2() {
        //TODO 最外层数据-一般是从接口获取 模拟分组数据
        List<List<OrderBean>> orderList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            List<OrderBean> orderGroup = new ArrayList<>();
            for(int k = 0;k < i+1;k++){
                OrderBean orderBean = new OrderBean();
                orderBean.setGoodName(String.format("group%d", i));
                orderBean.setGoodSn(String.format("item%d", k));
                orderGroup.add(orderBean);
            }
            orderList.add(orderGroup);
        }

        orderContents2 = new ArrayList<>();

        //拆分数据

        if(orderList != null){
            for(List<OrderBean> group : orderList){
                orderContents2.add(new TopBean());
                orderContents2.addAll(group);
                orderContents2.add(new BottomBean());
            }

        }


    }*//*

}*/

