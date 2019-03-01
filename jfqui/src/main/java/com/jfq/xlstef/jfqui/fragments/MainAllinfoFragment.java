package com.jfq.xlstef.jfqui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.BaseFragment;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class MainAllinfoFragment extends BaseFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

	/*@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_main_allinfo, container, false);
	}*/
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
        /*path="https://blog.csdn.net/qq_37140150/article/details/85287751";
        path="file:///D:/A/OrderPrj/allOrder.html";*/


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
    /*
        下面进行画图
     */

    private LineChartView lineChart;

    //一个是横坐标，一个是数据点数组。
    String[] date = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};//X轴的标注
    int[] score= { 74,22,18,79,20,74,20,74,42,90,74,42,90,50,42,150,1000,10,74,22,18,79,20,74,22,18,79,20,0,89};//图表的数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();//数据源值
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();//X轴的值

    /**
     * 设置X 轴的显示-----显示一个月的数据
     */
    private  boolean setX=true;
    private  int count=1;
    private void getAxisXLables(){
        Log.i("date.length",date.length+","+score.length);
        int len=date.length;
        /*
        进行奇偶数判断，为了让最后一个点有数据
         */
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
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(){
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
            Log.i("mydate",date[i]+","+score[i]);
        }
    }

    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
       // Toast.makeText(this,"radius"+line.getPointRadius(),Toast.LENGTH_LONG).show();
        line.setPointRadius(4);//默认半径为6
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(2);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        /*line.setHasLabels(true);//曲线的数据坐标是否加上备注*/
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

        /**注：下面的7，10只是代表一个数字去类比而已
     * 这个弯路比较多！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
     * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
     * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
     * 若设置axisX.setMaxLabelChars(int count)这句话,
     * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
     刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
     若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
     * 并且Y轴是根据数据的大小自动设置Y轴上限
     * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
     */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }


}
