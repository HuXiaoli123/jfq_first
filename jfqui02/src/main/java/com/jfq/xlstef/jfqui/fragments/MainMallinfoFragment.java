package com.jfq.xlstef.jfqui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DownLoadAsyncTask;
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.SerachDetail.MoreDeail_Activity;
import com.jfq.xlstef.jfqui.SerachDetail.SerachActivity;
import com.jfq.xlstef.jfqui.adapter.MainAllInfoAdapter;
import com.jfq.xlstef.jfqui.interfaces.OnItemClickListener;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

//商城订单
public class MainMallinfoFragment  extends Fragment {
    private RecyclerView allinfo_list;
    private MainAllInfoAdapter mainAllInfoAdapter;
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private Activity activity = null;
    private List<CategoryBean> mDataList=new ArrayList<>();
    // Header View
    private ProgressBar headProgressBar;
    private TextView headTextView;
    private ImageView headImageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private TextView emptymessage;
    private  int mFirstCount;
    int selectfragment; //当前处于哪个fragment
    String selectType="";
    private List<CategoryBean>mDataTemp=new ArrayList<>();
    boolean isfinish=false;//是否完成所有加载内容
    private  ProgressBar mProgressBar;
    //处理子线层传过来的信息
    private Handler handler =null;
    boolean mFirstCreate ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_mallinfo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("MyActivity","onActivityCreated");
        mFirstCreate=true;
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.i("MyActivity","onStart");
        initView();
        initItemData();
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        mProgressBar.setVisibility(View.GONE);
                        emptymessage.setVisibility(View.VISIBLE);
                        Log.i("mProgressBar1",emptymessage.getVisibility()+"");
                        break;
                    default:
                        mFirstCreate=false;
                        mProgressBar.setVisibility(View.GONE);
                        Log.i("mProgressBar",mProgressBar.getVisibility()+"");
                        emptymessage.setVisibility(View.GONE);
                        initParentThread();
                        break;
                }
            }
        };
    }
    void initParentThread()
    {

              /*
        控制第一次刷新的条数
         */

            if(mDataList.size()>mFirstCount)
            {
                for(int i=0;i<mFirstCount+1;i++)
                {
                    mDataTemp.add(mDataList.get(i));
                }
                mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataTemp,selectfragment,selectType);//adapter
                Log.i("mypath_basefrag--:-----",mDataList.size()+""+mDataTemp.size());

            }else
            {

                mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataList,selectfragment,selectType);//adapter
            }
            Log.i("mypath_basefrag--:-----",mDataList.size()+""+mDataTemp.size()+mFirstCount);
            allinfo_list.setAdapter(mainAllInfoAdapter);
            mainAllInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                }
            });
            mainAllInfoAdapter.notifyDataSetChanged();

    }


    void initView() {
        activity = getActivity();
        mProgressBar=activity.findViewById(R.id.myMallprogressbar);
        selectfragment=2;
        selectType="商品名:";
        emptymessage=activity.findViewById(R.id.mallinfo_item_emptymessage);
        allinfo_list = activity.findViewById(R.id.mallinfo_list);//RecyclerView
        linearLayoutManager = new LinearLayoutManager(activity);//LayoutManager
        allinfo_list.setLayoutManager(linearLayoutManager);
        allinfo_list.setItemAnimator(new DefaultItemAnimator());
        /*mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataList);//adapter
        allinfo_list.setAdapter(mainAllInfoAdapter);*/
        superSwipeRefreshLayout = activity.findViewById(R.id.main_malllinfo_swiperefresh);//superswiperefresh
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
        mDataTemp.clear();
        isfinish=false;
        mFirstCount=getActivity().getResources().getInteger(R.integer.first_load_count);
        CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(getActivity()) );
        Log.i("mypath_",":"+dao.allCaseNum()+" test");

        /*mCategoryBean=Tooljson.JsonParse(getContext());*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                long exitTime=System.currentTimeMillis();
                //进行数据库查询
                CategoryBeanDAO dao = new CategoryBeanDAO(new DBHelper(getActivity()));
                /*判断数据库是否有数据*/
                Log.i("MyActivity",mFirstCreate+"");
                if(mFirstCreate)
                {
                    Log.i("MyActivity",mFirstCreate+"intosleep");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mFirstCreate=false;
                }



                while(dao.allCaseNumView(Data.VIEW_COMODITYORDER)<=0)
                {


                    if ((System.currentTimeMillis() - exitTime) > 1000)
                    {
                        break;
                    }
                }
               // mDataList=dao.findByOrderType("商城订单");//找到订单类型为商城订单

                 mDataList=dao.findOrderByComdity(Data.VIEW_COMODITYORDER);
                Log.i("daoooooooo",  mDataList.size()+"."+dao.allCaseNumView(Data.VIEW_COMODITYORDER));

                handler.sendEmptyMessage(mDataList.size());

            }
        }).start();
    }



    //查询数据
    public ArrayList QueryData(DBHelper dbHelper,String status) {
        CategoryBeanDAO dao = new CategoryBeanDAO(dbHelper);
        return  dao.findOrderByName(Data.COMPELETE_ORDER_TABLE_NAME,status);
    }

    /**
     * 超时
     * @param time  :超过多久时间
     * @param lastTime  ：触发开始时间
     */
    private  boolean  OverTime(float time ,float lastTime)
    {
        if ((System.currentTimeMillis() - lastTime) > time)   return  true;
        return  false;
    }

    /**
     * 请求更多的数据
     */
    private void LoadMoreRecycleViewclass()
    {

        int count=mDataTemp.size();
          /*  Log.i("InsertData6", mCategoryTemp.size()+","+mCategoryBean.size()+"count"+count);
            Log.i("InsertData6-------", mCategoryTemp.size()+","+mCategoryBean.size());*/
        if(mDataList.size()- mDataTemp.size()>mFirstCount)
        {
            for(int i=mDataTemp.size();i<count+mFirstCount+1;i++)
            {
                Log.i("InsertData6——temp", mDataTemp.size()+","+mDataTemp.size());
                mDataTemp.add(mDataList.get(i));
            }

        }else
        {
            Log.i("InsertData6", mDataTemp.size()+","+mDataList.size());
            for(int i=count;i<mDataList.size();i++)
            {
                Log.i("InsertData6——temp", mDataTemp.size()+","+mDataList.size());
                mDataTemp.add(mDataList.get(i));
            }
        }
    }
    /*
        刷新item数据(假设）
        真实设定需要刷新数据库
     */
    private void refreshItemData(String freshType) {
        if (freshType.equals("loadmore")) {
            if(!isfinish)
            {

                LoadMoreRecycleViewclass();
                // mCategoryAdapter.notifyItemChanged(1,1);
                if(mDataTemp.size()==mDataList.size() )isfinish=true;

            }else
            {
                Toast.makeText(getContext(), "没有更多的数据了", Toast.LENGTH_SHORT).show();
                // mCategoryAdapter.notifyItemRemoved(mCategoryAdapter.getItemCount());
            }
            mainAllInfoAdapter.notifyDataSetChanged();
        } else if (freshType.equals("refresh")) {
            mFirstCreate=true;
            Toast.makeText(getContext(),"已刷新订单信息",Toast.LENGTH_SHORT).show();
            new DownLoadAsyncTask(getActivity()).execute(Data.loadPath);
            initItemData();
            mainAllInfoAdapter.notifyDataSetChanged();
        }
    }
    //查询
    public  void myresearch()
    {
        Log.i("AAA_","MainAllinfoFragment");

        Intent intent = new Intent();
        intent.setClass(getActivity(), SerachActivity.class);
        intent.putExtra("orderName", 2);

        intent.putExtra("lstBean", (Serializable) mDataList);

        startActivity(intent);
    }
}
