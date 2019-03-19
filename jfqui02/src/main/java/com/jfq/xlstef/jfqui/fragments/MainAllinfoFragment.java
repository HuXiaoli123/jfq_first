package com.jfq.xlstef.jfqui.fragments;

import android.app.Activity;
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


public class MainAllinfoFragment extends Fragment {
    private RecyclerView allinfo_list;
    private MainAllInfoAdapter mainAllInfoAdapter;
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private Activity activity = null;
    private List<CategoryBean> mDataList=new ArrayList<>();
    private List<CategoryBean>mDataTemp=new ArrayList<>();

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
    private  ProgressBar mProgressBar;

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
        return inflater.inflate(R.layout.fragment_main_allinfo, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("test","onstart");
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
                        Log.i("mProgressBar1",emptymessage.getVisibility()+"all");
                        break;
                    default:
                        mProgressBar.setVisibility(View.GONE);
                        Log.i("mProgressBar",mProgressBar.getVisibility()+"all");
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
               mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataTemp,1);//adapter
                Log.i("mypath_basefrag--:-----",mDataList.size()+""+mDataTemp.size());

            }else
            {

                mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataList,1);//adapter
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
        mProgressBar=activity.findViewById(R.id.myAllprogressbar);
        emptymessage=activity.findViewById(R.id.allinfo_item_emptymessage);
        allinfo_list = activity.findViewById(R.id.allinfo_list);//RecyclerView
        linearLayoutManager = new LinearLayoutManager(activity);//LayoutManager
        allinfo_list.setLayoutManager(linearLayoutManager);
        allinfo_list.setItemAnimator(new DefaultItemAnimator());
        /*mainAllInfoAdapter = new MainAllInfoAdapter(activity, mDataList);//adapter
        allinfo_list.setAdapter(mainAllInfoAdapter);*/
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
    private  boolean isFirstTime=true;
    private  String path="http://store.tuihs.com/store/orders?page=0&size=10";
    private void initItemData() {
        mDataList.clear();
        mDataTemp.clear();
        isfinish=false;
        mFirstCount=getActivity().getResources().getInteger(R.integer.first_load_count);
        CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(getActivity()) );
        Log.i("mypath_",":"+dao.allCaseNum()+" test");
        final DownLoadAsyncTask myAsyTask=  new DownLoadAsyncTask(getActivity());

        /*mCategoryBean=Tooljson.JsonParse(getContext());*/
        new Thread(new Runnable() {
            @Override
            public void run() {

                //进行数据库查询
                CategoryBeanDAO dao = new CategoryBeanDAO(new DBHelper(getActivity()));
//---------------------------------
               /* 判断数据库是否有数据*/
                if(isFirstTime) {
                    isFirstTime=false;
                 myAsyTask.execute(path);
                }else
                {

                }
//---------------------------------
                //当数据库中数据<新加载的数据

                 long lastTime=System.currentTimeMillis();
                while(dao.allCaseNum()<=0|| dao.allCaseNum()<DownLoadAsyncTask.mCompeleteOrderList||!DownLoadAsyncTask.mFinishLoad)
                {
                    Log.i("mypathtest1112",dao.allCaseNum()+"，"+myAsyTask.mCompeleteOrderList+";"+DownLoadAsyncTask.mFinishLoad);


                    //超时了还没有数据显示

                    if ((System.currentTimeMillis() - lastTime) > 1000)
                    {
                        Log.i("mypathtest1112",System.currentTimeMillis()+"OverTime11"+"，"+(System.currentTimeMillis() - lastTime)+","+lastTime);
                        break;
                    }
                   /* if(OverTime(500,exitTime))
                    {

                    }*/
                }

                //  mCategoryBean=myAsyTask.getCompeleteOrder();
                //从数据库中获取
                mDataList=dao.findOrderByAll(Data.VIEW_ALL_ORDER);;


                handler.sendEmptyMessage(mDataList.size());

            }
        }).start();

    }


    /**
     * 超时
     * @param time  :超过多久时间
     * @param lastTime  ：触发开始时间
     */
    private  boolean  OverTime(float time ,float lastTime)
    {
        Log.i("mypathtest1112",System.currentTimeMillis()+"OverTime11"+"，"+(System.currentTimeMillis() - lastTime)+","+lastTime);
        if ((System.currentTimeMillis() - lastTime) > time) {

            return true;
        }
        return  false;
    }

    /*
        刷新item数据(假设）
        真实设定需要刷新数据库
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
    boolean isfinish;
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
            Toast.makeText(getContext(),"上拉刷新",Toast.LENGTH_SHORT).show();
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
        intent.putExtra("orderName", 1);

       intent.putExtra("lstBean", (Serializable) mDataList);

        startActivity(intent);
    }
}


/*public class MainAllinfoFragment extends BaseFragment {
	// TODO: Rename parameter arguments, choose names that match

    String TAG="path";
    public  void myresearch()
    {
        Log.i("AAA_","MainAllinfoFragment");

        Intent intent = new Intent();
        intent.setClass(getActivity(), SerachActivity.class);
        intent.putExtra("orderName", 1);

        intent.putExtra("lstBean", (Serializable) mCategoryBean);

        startActivity(intent);
    }
    //初始化特有数据
    public  MainAllinfoFragment()
    {
        Log.i("myfragment","MainAllinfoFragment1");
       // pageLayout= R.layout.completeorder1_main;
        path="http://store.tuihs.com/store/orders?page=0&size=10";
       // CategoryAdapter.mOrdername=CategoryAdapter.OrderName.CompleteOrder;
        //path="https://blog.csdn.net/qq_37140150/article/details/86289071";
        *//*path="https://blog.csdn.net/qq_37140150/article/details/85287751";
        path="file:///D:/A/OrderPrj/allOrder.html";*//*


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=View.inflate(getActivity(),R.layout.completeorder1_main, null);
        CategoryAdapter.mOrdername=CategoryAdapter.OrderName.CompleteOrder;
        lineChart = (LineChartView)view.findViewById(R.id.line_chart);
        super.initial(view);
        return view;
    }

    //------------------

    @Override
    public void initRecyclerView() {
        super.initRecyclerView();


        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
    }
    *//*
        下面进行画图
     *//*

    private LineChartView lineChart;

    //一个是横坐标，一个是数据点数组。
    String[] date = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};//X轴的标注
    int[] score= { 74,22,18,79,20,74,20,74,42,90,74,42,90,50,42,150,1000,10,74,22,18,79,20,74,22,18,79,20,0,89};//图表的数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();//数据源值
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();//X轴的值

    *//**
     * 设置X 轴的显示-----显示一个月的数据
     *//*
    private  boolean setX=true;
    private  int count=1;
    private void getAxisXLables(){
        Log.i("date.length",date.length+","+score.length);
        int len=date.length;
        *//*
        进行奇偶数判断，为了让最后一个点有数据
         *//*
        if(len%2==0) mAxisXValues.add(new AxisValue(0).setLabel(date[0]));
        else {count=0 ;}
        for (int i = count; i < len; i++) {

            if(setX){
                mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
                setX=false;
            }else
            {
                setX=true;
            }
        }
    }
    *//**
     * 图表的每个点的显示
     *//*
    private void getAxisPoints(){
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
            Log.i("mydate",date[i]+","+score[i]);
        }
    }

    *//**
     * 初始化LineChart的一些设置
     *//*
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
       // Toast.makeText(this,"radius"+line.getPointRadius(),Toast.LENGTH_LONG).show();
        line.setPointRadius(4);//默认半径为6
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(2);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        *//*line.setHasLabels(true);//曲线的数据坐标是否加上备注*//*
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色

//	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(15);//设置字体大小
        axisX.setMaxLabelChars(0); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称

        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(11);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        axisY.setName("yName");
        axisY.setHasLines(true);//设置y轴分割线

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        lineChart.setMaxZoom((float) 1);//---------缩放比例
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        *//**注：下面的7，10只是代表一个数字去类比而已
     * 这个弯路比较多！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
     * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
     * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
     * 若设置axisX.setMaxLabelChars(int count)这句话,
     * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
     刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
     若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
     * 并且Y轴是根据数据的大小自动设置Y轴上限
     * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
     *//*
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }


}*/
