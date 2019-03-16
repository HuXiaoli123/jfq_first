package com.jfq.xlstef.jfqui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.jfq.xlstef.jfqui.OrderFragment.Adapter.DailyOrderAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.BasicOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DailyOrderDao;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.OrderFragment.WrapContentLinearLayoutManager;
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.SerachDetail.SerachActivity;
import com.jfq.xlstef.jfqui.adapter.MainAllInfoAdapter;
import com.jfq.xlstef.jfqui.adapter.MainSummaryInfoAdapter;
import com.jfq.xlstef.jfqui.interfaces.OnItemClickListener;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//每日汇总
public class MainSummaryinfoFragment extends Fragment {
    private RecyclerView allinfo_list;
    private MainSummaryInfoAdapter mainSummaryInfoAdapter;
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private Activity activity = null;
    private List<DailyOrder> mDataList=new ArrayList<>();
    // Header View
    private ProgressBar headProgressBar;
    private TextView headTextView;
    private ImageView headImageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    TextView  emptymessage;
    private  int mFirstCount;
    int selectfragment; //当前处于哪个fragment
    private List<DailyOrder>mDataTemp=new ArrayList<>();
    boolean isfinish=false;//是否完成所有加载内容

    //处理子线层传过来的信息
    protected Handler handler =null;
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
        return inflater.inflate(R.layout.fragment_main_summaryinfo, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initItemData();
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                initParentThread();
            }
        };
    }

    void initParentThread()
    {
        if(mDataList.size()>0)
        {
            emptymessage.setVisibility(View.GONE);

              /*
        控制第一次刷新的条数
         */

            if(mDataList.size()>mFirstCount)
            {
                for(int i=0;i<mFirstCount+1;i++)
                {
                    mDataTemp.add(mDataList.get(i));
                }
                mainSummaryInfoAdapter = new MainSummaryInfoAdapter(activity, mDataTemp);//adapter
                Log.i("mypath_basefrag--:-----",mDataList.size()+""+mDataTemp.size());

            }else
            {
                mainSummaryInfoAdapter = new MainSummaryInfoAdapter(activity, mDataList);//adapter
            }

            allinfo_list.setAdapter(mainSummaryInfoAdapter);
            mainSummaryInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                }
            });
            mainSummaryInfoAdapter.notifyDataSetChanged();
        }else
            emptymessage.setVisibility(View.VISIBLE);
    }
    void initView() {
        activity = getActivity();
        emptymessage=activity.findViewById(R.id.summaryinfo_item_emptymessage);
        allinfo_list = activity.findViewById(R.id.summaryinfo_list);//RecyclerView
        linearLayoutManager = new LinearLayoutManager(activity);//LayoutManager
        allinfo_list.setLayoutManager(linearLayoutManager);
        allinfo_list.setItemAnimator(new DefaultItemAnimator());
        /*mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataList);//adapter
        allinfo_list.setAdapter(mainAllInfoAdapter);*/
        superSwipeRefreshLayout = activity.findViewById(R.id.main_summaryinfo_swiperefresh);//superswiperefresh
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
    private  boolean isFirstTime=true;
    private  String path="http://store.tuihs.com/store/orders?page=0&size=10";
    private void initItemData() {
        mDataList.clear();
        selectfragment=5;
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
				DailyOrderDao dao = new DailyOrderDao(getActivity());

				/*判断数据库是否有数据*/
				while(dao.queryAll().size()<=0)
				{
					//超时了还没有数据显示
					if(OverTime(3,exitTime)) break;
				}
				mDataList=dao.queryAll();

				handler.sendEmptyMessage(0);



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
        if ((System.currentTimeMillis() - lastTime) > 3000)   return  true;
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
           /* DecimalFormat df = new DecimalFormat("00");
            int d1 = mDataList.size();
            for (int i = d1; i < d1 + 6; i++) {
                DailyOrder allInfoData=new DailyOrder();
                allInfoData.setOrderNumber("ord2018122416281205715" + df.format((long) i));
                float totalFee = 1000 + i * 5;
                float totalReduction = 1 + i * 0.1f;
                allInfoData.setStoreEntry("12");
                allInfoData.setPlayTime("2019");
                allInfoData.setPlatformDeduction(String.valueOf(totalReduction));
                allInfoData.setPlatformDeduction(String.valueOf(totalFee - totalReduction));
                mDataList.add(allInfoData);
            }*/

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

            mainSummaryInfoAdapter.notifyDataSetChanged();
        } else if (freshType.equals("refresh")) {
            Toast.makeText(getContext(),"上拉刷新",Toast.LENGTH_SHORT).show();
          /*  int d2 = mDataList.size();
            mDataList.clear();
            DecimalFormat df = new DecimalFormat("00");
            for (int i = d2; i < d2 + 10; i++) {
                CategoryBean allInfoData=new CategoryBean();
                allInfoData.setOrderNumber("ord2018122416281205715" + df.format((long) i));
                float totalFee = 1000 + i * 5;
                float totalReduction = 1 + i * 0.1f;
                allInfoData.setStoreEntry("12");
                allInfoData.setPlayTime("2019");
                allInfoData.setPlatformDeduction(String.valueOf(totalReduction));
                allInfoData.setPlatformDeduction(String.valueOf(totalFee - totalReduction));
                mDataList.add(allInfoData);
            }*/
			mainSummaryInfoAdapter.notifyDataSetChanged();
        }
    }
//查询
    public  void myresearch()
    {
        Log.i("AAA_","MainAllinfoFragment");

        Intent intent = new Intent();
        intent.setClass(getActivity(), SerachActivity.class);
        intent.putExtra("orderName", 5);

       intent.putExtra("lstBean", (Serializable) mDataList);

        startActivity(intent);
    }
}
