package com.jfq.xl_stef.jfq_android_store.fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.jfq.xl_stef.jfq_android_store.R;
import com.jfq.xl_stef.jfq_android_store.adapter.MainAllInfoAdapter;
import com.jfq.xl_stef.jfq_android_store.data.AllInfoData;

import java.util.ArrayList;
import java.util.Date;
import java.text.DecimalFormat;
import java.util.List;

public class MainAllinfoFragment extends Fragment {
    private RecyclerView allinfo_list;
    private MainAllInfoAdapter mainAllInfoAdapter;
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private Activity activity = null;
    private List<AllInfoData> mDataList=new ArrayList<>();
    // Header View
    private ProgressBar headProgressBar;
    private TextView headTextView;
    private ImageView headImageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_allinfo, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initItemData();
    }

    void initView() {
        activity = getActivity();
        allinfo_list = activity.findViewById(R.id.allinfo_list);//RecyclerView
        linearLayoutManager = new LinearLayoutManager(activity);//LayoutManager
        allinfo_list.setLayoutManager(linearLayoutManager);
        allinfo_list.setItemAnimator(new DefaultItemAnimator());
        mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataList);//adapter
        allinfo_list.setAdapter(mainAllInfoAdapter);
        superSwipeRefreshLayout = activity.findViewById(R.id.main_allinfo_swiperefresh);//superswiperefresh
        //设定下拉刷新栏的背景色
        superSwipeRefreshLayout.setHeaderViewBackgroundColor(0xff888888);
        //加上自定义的下拉头部刷新栏
        superSwipeRefreshLayout.setHeaderView(createHeaderView());
        //加上自定义的上拉尾部刷新栏
        superSwipeRefreshLayout.setFooterView(createFooterView());
        //设置子View是否跟随手指的滑动而滑动
        superSwipeRefreshLayout.setTargetScrollWithLayout(true);
        //设置下拉刷新监听
        superSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                headTextView.setText("正在刷新...");
                headImageView.setVisibility(View.GONE);
                headProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshItemData("refresh");
                        headProgressBar.setVisibility(View.GONE);
                        superSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            //是否下拉到执行刷新的位置
            @Override
            public void onPullEnable(boolean enable) {
                headTextView.setText(enable ? "松开刷新" : "下拉刷新");
                headImageView.setVisibility(View.VISIBLE);
                headImageView.setRotation(enable ? 180 : 0);
            }
        });
        //设置上拉加载监听
        superSwipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshItemData("loadmore");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerProgressBar.setVisibility(View.GONE);
                        superSwipeRefreshLayout.setLoadMore(false);
                    }
                }, 3000);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            //是否上拉到执行加载的位置
            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });
    }

    /*
       下拉头部刷新栏可以自定义
    */
    private View createHeaderView() {
        View headerView = LayoutInflater.from(superSwipeRefreshLayout.getContext())
                .inflate(R.layout.layout_refresh_head, null);
        headProgressBar = (ProgressBar) headerView.findViewById(R.id.head_pb_view);
        headTextView = (TextView) headerView.findViewById(R.id.head_text_view);
        headTextView.setText("下拉刷新");
        headImageView = (ImageView) headerView.findViewById(R.id.head_image_view);
        headImageView.setVisibility(View.VISIBLE);
        headImageView.setImageResource(R.mipmap.down_arrow);
        headProgressBar.setVisibility(View.GONE);
        return headerView;
    }

    /*
        上拉尾部刷新栏也可以自定义
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(superSwipeRefreshLayout.getContext())
                .inflate(R.layout.layout_refresh_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.mipmap.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    /*
        设置初始item数据(假设）
        真实设定需要访问数据库
     */
    private void initItemData() {
        mDataList.clear();
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 0; i < 10; i++) {
            AllInfoData allInfoData=new AllInfoData();
            allInfoData.setCode("ord2018122416281205715" + df.format((long) i));
            float totalFee = 1000f + i * 5f;
            float totalReduction = 1f + i * 0.1f;
            allInfoData.setTotalFee(totalFee);
            allInfoData.setCreateTime(new Date());
            allInfoData.setTotalReduction(totalReduction);
            allInfoData.setSalesAmount(totalFee - totalReduction);
            mDataList.add(allInfoData);
        }
        mainAllInfoAdapter.notifyDataSetChanged();
    }

    /*
        刷新item数据(假设）
        真实设定需要刷新数据库
     */
    private void refreshItemData(String freshType) {
        if (freshType.equals("loadmore")) {
            DecimalFormat df = new DecimalFormat("00");
            int d1 = mDataList.size();
            for (int i = d1; i < d1 + 6; i++) {
                AllInfoData allInfoData=new AllInfoData();
                allInfoData.setCode("ord2018122416281205715" + df.format((long) i));
                float totalFee = 1000 + i * 5;
                float totalReduction = 1 + i * 0.1f;
                allInfoData.setTotalFee(totalFee);
                allInfoData.setCreateTime(new Date());
                allInfoData.setTotalReduction(totalReduction);
                allInfoData.setSalesAmount(totalFee - totalReduction);
                mDataList.add(allInfoData);
            }
            mainAllInfoAdapter.notifyDataSetChanged();
        } else if (freshType.equals("refresh")) {
            int d2 = mDataList.size();
            mDataList.clear();
            DecimalFormat df = new DecimalFormat("00");
            for (int i = d2; i < d2 + 10; i++) {
                AllInfoData allInfoData=new AllInfoData();
                allInfoData.setCode("ord2018122416281205715" + df.format((long) i));
                float totalFee = 1000 + i * 5;
                float totalReduction = 1 + i * 0.1f;
                allInfoData.setTotalFee(totalFee);
                allInfoData.setCreateTime(new Date());
                allInfoData.setTotalReduction(totalReduction);
                allInfoData.setSalesAmount(totalFee - totalReduction);
                mDataList.add(allInfoData);
            }
            mainAllInfoAdapter.notifyDataSetChanged();
        }
    }
}
