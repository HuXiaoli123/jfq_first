package com.hxl.test_moreload.OrderFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hxl.test_moreload.CategoryAdapter;
import com.hxl.test_moreload.OrderFragment.Goods.CategoryBean;
import com.hxl.test_moreload.DividerItemDecoration;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.DBHelper;
import com.hxl.test_moreload.R;

import java.util.ArrayList;
import java.util.List;


public class CompleteOrder  extends BaseFragment {

    String TAG="path";
    //初始化特有数据
    public  CompleteOrder()
    {
        pageLayout= R.layout.completeorder1_main;
        path="http://store.tuihs.com/store/orders?page=0&size=10";
        /*path="https://blog.csdn.net/qq_37140150/article/details/85287751";
        path="file:///D:/A/OrderPrj/allOrder.html";*/
        CategoryAdapter.mOrdername=CategoryAdapter.OrderName.CompleteOrder;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       Log.i("path","onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    //---------------------------
    @Override
    public void onAttach(Context context) {
        //由于 onCreate 是在 onAttach 后执行，故此时 mTitle 为空
        Log.i(TAG, "Fragment id = "+ " is onAttach.");
        super.onAttach(context);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, " is onActivityCreated.");
        super.onActivityCreated(savedInstanceState);
        //测试 onCreate() 是 Activity 的 UI 是否初始化完成

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, " is onStart.");
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("messageFrament",hidden+"");
        if(!hidden){}
           // QueryData(dbHelper);

    }

    //---------------------------
    @Override
    public void LoadRecycleViewclass() {
        Log.i("mytest","InserDatabase");
        super.InserDatabase();
        super.LoadRecycleViewclass();
    }

}


/*public class CompleteOrder  extends Fragment {


    private RecyclerView mRecyclerView;
    private List<CategoryBean> mCategoryBean = new ArrayList<>();
    private List<CategoryBean>mCategoryTemp=new ArrayList<>();


    private CategoryAdapter mCategoryAdapter;

    private View headerView;
    private Handler handler = new Handler();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.completeorder1_main, null);

        Log.i("我的页面","completeorder1_main");
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(getActivity());


        LoadRecycleViewclass();
        initRecyclerView();

        return view;
    }

    boolean isLoading;
      LinearLayoutManager manager=null;
    private void initRecyclerView() {

       *//*  manager = new LinearLayoutManager(getActivity());*//*
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //下方注释的代码用来解决headerview和footerview加载到头一个或者最后一个item  而不是占据一行的bug
        *//*final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        // gridLayoutManager  布局管理器
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是第一个(添加HeaderView)   还有就是最后一个(FooterView)
                return position == mCategoryBean.size() + 1 || position == 0 ? gridLayoutManager.getSpanCount() : 1;
            }
        });*//*


        *//*
        控制第一次刷新的条数
         *//*



        if(mCategoryBean.size()>17)
        {
            for(int i=0;i<18;i++)
            {
                mCategoryTemp.add(mCategoryBean.get(i));
            }

            Log.i("InsertData6", mCategoryTemp.size()+","+mCategoryBean.size());
            mCategoryAdapter = new CategoryAdapter(mCategoryTemp);
          //  loadLisVIew(arrayListInfos);
        }else
        {
            Log.i("InsertData6", mCategoryTemp.size()+","+mCategoryBean.size());
          //  loadLisVIew(listInfos);
            mCategoryAdapter = new CategoryAdapter(mCategoryBean);
        }


        mRecyclerView.setAdapter(mCategoryAdapter);



        //setHeader(mRecyclerView);
        mCategoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, CategoryBean categoryBean) {
                Toast.makeText(getContext(), "我是第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisiableItemPosition = manager.findLastVisibleItemPosition();
                if( manager.findFirstCompletelyVisibleItemPosition()==0)
                {
                    Log.i("compl","到头了"+lastVisiableItemPosition+":"+mCategoryAdapter.getItemCount());
                    setHeader(mRecyclerView);
                }
                if (lastVisiableItemPosition + 1 == mCategoryAdapter.getItemCount()){
                    if (!isLoading){
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //requestData();
                                requestLoadMoreData();
                                //    Toast.makeText(MainActivity.this, "已经没有新的了", Toast.LENGTH_SHORT).show();
                                isLoading = false;
                                // adapter.notifyItemRemoved(adapter.getItemCount());
                            }
                        },2000);
                    }
                }
            }
        });


    }

    private void setHeader(RecyclerView view) {
        final View header = LayoutInflater.from(getContext()).inflate(R.layout.category_item_header, view, false);


        mCategoryAdapter.setHeaderView(header);


    }



    List<CategoryBean> oderdatalist=new ArrayList<CategoryBean>() ;
    private void LoadRecycleViewclass() {
        CategoryBean myCategoryBean=new CategoryBean("1234567","商城订单","29","2.6","26.4","29","12-11/14:27");

        oderdatalist.add(myCategoryBean);

        for (int i = 0; i < 3; i++) {

            CategoryBean orderGood = new CategoryBean();

            orderGood.setOrderNumber(oderdatalist.get(0).getOrderNumber());
            orderGood.setOderType(oderdatalist.get(0).getOderType());
            orderGood.setItemPrice(oderdatalist.get(0).getItemPrice());
            orderGood.setPlatformDeduction(oderdatalist.get(0).getPlatformDeduction());
            orderGood.setUserPlay(oderdatalist.get(0).getUserPlay());
            orderGood.setStoreEntry(oderdatalist.get(0).getStoreEntry());
            orderGood.setPlayTime(oderdatalist.get(0).getPlayTime());
            if(i==99)  orderGood.setPlayTime("2018--12--15");
            // orderGoodses.add(orderGood);

            mCategoryBean.add(orderGood);
        }


    }

    private void LoadMoreRecycleViewclass() {

        int count=mCategoryTemp.size();
        Log.i("InsertData6", mCategoryTemp.size()+","+mCategoryBean.size()+"count"+count);
        Log.i("InsertData6-------", mCategoryTemp.size()+","+mCategoryBean.size());
        if(mCategoryBean.size()- mCategoryTemp.size()>17)
        {
            for(int i=mCategoryTemp.size();i<count+18;i++)
            {
                Log.i("InsertData6——temp", mCategoryTemp.size()+","+mCategoryBean.size());
                mCategoryTemp.add(mCategoryBean.get(i));
            }

        }else
        {
            Log.i("InsertData6", mCategoryTemp.size()+","+mCategoryBean.size());
            for(int i=count;i<mCategoryBean.size();i++)
            {
                Log.i("InsertData6——temp", mCategoryTemp.size()+","+mCategoryBean.size());
                mCategoryTemp.add(mCategoryBean.get(i));
            }
        }


    }



    boolean isfinish=false;
    private void requestLoadMoreData(){


        if(!isfinish)
        {
            LoadMoreRecycleViewclass();
            mCategoryAdapter.notifyItemChanged(1,1);
            if(mCategoryTemp.size()==mCategoryBean.size() )isfinish=true;
            Log.i("isfinish",isfinish+"");
        }else
        {
            Toast.makeText(getContext(), "已经没有新的了", Toast.LENGTH_SHORT).show();
            mCategoryAdapter.notifyItemRemoved(mCategoryAdapter.getItemCount());
        }


       *//* if (mCategoryTemp.size()<=mCategoryBean.size()) {
            LoadMoreRecycleViewclass();
            mCategoryAdapter.notifyItemChanged(1,1);
            if(mCategoryTemp.size()==mCategoryBean.size() )isfinish=true;
        } else {
            Log.i("InsertData6----","已经没有新的了");
            Toast.makeText(getContext(), "已经没有新的了", Toast.LENGTH_SHORT).show();
            mCategoryAdapter.notifyItemRemoved(mCategoryAdapter.getItemCount());

        } *//*

       *//* index++;

        if (index <= 3) {
            initData();
            mCategoryAdapter.notifyItemChanged(1,1);
        } else {
            Toast.makeText(getContext(), "已经没有新的了", Toast.LENGTH_SHORT).show();
            mCategoryAdapter.notifyItemRemoved(mCategoryAdapter.getItemCount());

        }*//*


        // swipeRefreshLayout.setRefreshing(false);
        // mCategoryAdapter.notire


    }


    private void initView() {

    }

    *//* private void LoadRecycleView() {
        CategoryBean myCategoryBean=new CategoryBean("1234567","商城订单","29","2.6","26.4","29","12-11/14:27");

        List<CategoryBean> oderdatalist=new ArrayList<CategoryBean>() ;

        oderdatalist.add(myCategoryBean);

        for (int i = 0; i < 100; i++) {

            CategoryBean orderGood = new CategoryBean();

            orderGood.setOrderNumber(oderdatalist.get(0).getOrderNumber());
            orderGood.setOderType(oderdatalist.get(0).getOderType());
            orderGood.setItemPrice(oderdatalist.get(0).getItemPrice());
            orderGood.setPlatformDeduction(oderdatalist.get(0).getPlatformDeduction());
            orderGood.setUserPlay(oderdatalist.get(0).getUserPlay());
            orderGood.setStoreEntry(oderdatalist.get(0).getStoreEntry());
            orderGood.setPlayTime(oderdatalist.get(0).getPlayTime());
            // orderGoodses.add(orderGood);

            mCategoryBean.add(orderGood);
        }

    }*//*

}*/
