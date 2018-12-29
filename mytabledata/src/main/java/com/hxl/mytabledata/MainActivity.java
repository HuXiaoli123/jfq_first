package com.hxl.mytabledata;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyAdapter adapter;

    private Context mContext;

    List<OrderContent> orderContents;
    List<Object> orderContents2;

    View footerview;//底部布局

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

         List<String> oderdatalist=new ArrayList<String>(){
            {
                add("1234567");
                add("商城订单");
                add("29");
                add("2.6");
                add("26.4");
                add("29");
                add("12-11/14:27");
            }
        };



        for (int i = 0; i <2; i++) {
            Order order = new Order();
            orderList.add(order);
        }

        /*orderNumber oderType  itemPrice platformDeduction userPlay storeEntry playTime*/
        //外部第一个循环，将数据循环读取后存到订单顶部
        for (int k = 0; k < orderList.size(); k++) {
            List<OrderGoods> orderGoodses = new ArrayList<>();


            for (int i = 0; i < k + 1; i++) {
                OrderGoods orderGood = new OrderGoods();
                orderGood.setOrderNumber(  oderdatalist.get(0));
                orderGood.setOderType(oderdatalist.get(1));
                orderGood.setItemPrice(oderdatalist.get(2));
                orderGood.setPlatformDeduction(oderdatalist.get(3));
                orderGood.setUserPlay(oderdatalist.get(4));
                orderGood.setStoreEntry(oderdatalist.get(5));
                orderGood.setPlayTime(oderdatalist.get(6));
                orderGoodses.add(orderGood);
            }

            //TODO 设置顶部数据-需要的数据直接传--------------------暂不需要头部
            /*ItemOrderTop itemOrderTop = new ItemOrderTop();
            orderContents.add(itemOrderTop);*/

            if (orderGoodses == null) {
                //没有订单
            } else {
                //中间for循环，将数据循环读取后存到订单中间部分
                //TODO 设置中间数据
                for (int j = 0; j < orderGoodses.size(); j++) {
                    OrderGoods goods = new OrderGoods();
                    goods.setOrderNumber(orderGoodses.get(j).getOrderNumber());
                    goods.setOderType(orderGoodses.get(j).getOderType());
                    goods.setItemPrice(orderGoodses.get(j).getItemPrice());
                    goods.setPlatformDeduction(orderGoodses.get(j).getPlatformDeduction());
                    goods.setUserPlay(orderGoodses.get(j).getUserPlay());
                    goods.setStoreEntry(orderGoodses.get(j).getStoreEntry());
                    goods.setPlayTime(orderGoodses.get(j).getPlayTime());

                    //需要的数据直接传
                    ItemOrderIn orderIMiddle = new ItemOrderIn(goods);
                    orderContents.add(orderIMiddle);
                    Log.i("myLog", "orderContents =" + orderContents);
                }
            }

            //外部第二个循环，将数据循环读取后存到订单底部
            //TODO 设置底部数据-需要的数据直接传-------------------------暂不需要底部
            /*ItemOrderBottom orderBottom = new ItemOrderBottom();
            orderContents.add(orderBottom);*/

        }
    }

    /*
   添加底部加载提示布局到listview
    */
    private void addView( )
    {
        //LayoutInflater 将底部布局添加进来
        LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
        footerview=inflater.inflate(R.layout.footer_layout,null);
        footerview.findViewById(R.id.load_layout).setVisibility(View.GONE);
    }

    /**
     * 初始化View
     */
    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
            boolean isLoadMore;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("myadapters","newState:"+newState);
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
                Log.i("myadapters","count"+visibleChildCount+","+layoutManager.getItemCount());
                if (visibleChildCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoadMore) {
                    View lastVisibleView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int lastVisiblePosition = recyclerView.getChildLayoutPosition(lastVisibleView);
                    Log.i("myadapters","lastcount"+lastVisiblePosition+","+layoutManager.getItemCount());
                    if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                       //footerview.setVisibility(VISIBLE);
                        Log.i("myadapters","footerView");
                        isLoadMore = true;
                      /*  if (myRecyclerViewListener != null) {
                            myRecyclerViewListener.onLoadMore();
                        }*/
                    } else {
                        /*footerView.setVisibility(GONE);*/
                        Log.i("myadapters","footerView_no");
                    }
                }

            }
        });

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //MyAdapter2 adapter2 = new MyAdapter2(orderContents2);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(mContext, orderContents);
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

