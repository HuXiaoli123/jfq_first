package com.jfq.xlstef.jfqui.OrderFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.R;


public class CompleteOrder  extends BaseFragment {

    String TAG="path";
    //初始化特有数据
    public  CompleteOrder()
    {
        pageLayout= R.layout.completeorder1_main;
        path="http://store.tuihs.com/store/orders?page=0&size=10";
        //path="https://blog.csdn.net/qq_37140150/article/details/86289071";
        /*path="https://blog.csdn.net/qq_37140150/article/details/85287751";
        path="file:///D:/A/OrderPrj/allOrder.html";*/
        CategoryAdapter.mOrdername=CategoryAdapter.OrderName.CompleteOrder;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       Log.i("path","onCreateView");
       View view=super.onCreateView(inflater, container, savedInstanceState);
       // lineChart = (LineChartView)view.findViewById(R.id.line_chart);
        return view;
    }
    //---------------------------






    //------------------

    @Override
    public void initRecyclerView() {
        super.initRecyclerView();


       /* getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化*/
    }
    /*
        下面进行画图
     */

    /*private LineChartView lineChart;

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
         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
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
    }*/



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
