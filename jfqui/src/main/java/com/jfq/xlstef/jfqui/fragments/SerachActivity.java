package com.jfq.xlstef.jfqui.fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.OrderFragment.Enum_Order.OrderName;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Util.CustomDatePicker;
import com.jfq.xlstef.jfqui.OrderFragment.Util.DateFormatUtils;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.R;

import java.util.List;

public class SerachActivity extends AppCompatActivity implements SearchView.OnQueryTextListener ,View.OnClickListener {

    SearchView searchView;
    MyAdpater    adpter;
    ImageView   timerPick;

    /**
     * 搜索框
     */
    private EditText mEdtSearch;
    /**
     * 删除按钮
     */
    private ImageView mImgvDelete;
    /**
     * recyclerview
     */
    private RecyclerView mRcSearch;
    /**
     * 所有数据 可以是从上个activity中传来的，也可以是数据库中的
     */
    List<CategoryBean> lstBean;

    int mOrderName=0;
    String table;
    /**
     * 此list用来保存符合我们规则的数据
     */
    private List<CategoryBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        Log.i("mydata","test");
        Intent intentGet = getIntent();
        mOrderName=(int)intentGet.getExtras().getInt("orderName");
        lstBean= (List<CategoryBean>) intentGet.getSerializableExtra("lstBean");
        Log.i("mydata","lstBean"+lstBean.size());
        initView();
        adpter=new MyAdpater(lstBean);
        switch (mOrderName)
        {
            case 0:
                table=Data.VIEW_ALL_ORDER;
                setHeader(R.layout.all_head);
                break;
            case 1:
                table=Data.VIEW_COMODITYORDER;
                setHeader(R.layout.mall_head);
                break;
        }
        mRcSearch.setAdapter(adpter);


    }

    //头部添加
    private void setHeader(int headLayout) {
        View header = LayoutInflater.from(this).inflate(headLayout, null);
        adpter.setHeadView(header);

        // 这里可以获得的头部布局具体控件，并进行操作
    }

    private CustomDatePicker mDatePicker, mTimerPicker;
    private TextView pickertext;
    private void initView() {
      //  mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mImgvDelete = (ImageView) findViewById(R.id.imgv_delete);
        mRcSearch = (RecyclerView) findViewById(R.id.rc_search);
        //Recyclerview的配置
        mRcSearch.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search_view);

        pickertext=(TextView) findViewById(R.id.pickertext);
        timerPick=(ImageView) findViewById(R.id.timerPicker);


        searchView.setOnQueryTextListener(this);
        timerPick.setOnClickListener(this);

        initDatePicker();
        initTimerPicker();
    }

    //当完成输入的内容点击搜索按钮后该方法会回调,
    // 参数String query返回当前文本框可见的文字
    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }
    //每次当文本框的内容发生改变该方法会回调,
// 参数String newText返回当前文本框可见的文字
    @Override
    public boolean onQueryTextChange(String newText){

        adpter.getFilter().filter(newText);

        return false;
    }



    boolean isDay=true;//是否为日查询
    //时间选择器进行时间查询
    @Override
    public void onClick(View v) {

        mDatePicker.show("12");


       /* if(isDay)
        {
            pickertext.setText("按月");
            // 日期格式为yyyy-MM-dd
            mDatePicker.show("12");
        }else
        {
            pickertext.setText("按日");
           mTimerPicker.show("13");
        }*/
      /*  switch (v.getId()) {
            case R.id.timerPicker:

                break;

            case R.id.ll_time:
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker.show(mTvSelectedTime.getText().toString());
                break;
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();

       // mTvStartDate.setText("12");

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            /*public void onTimeSelected(long timestamp) {
               // mTvStartDate.setText(DateFormatUtils.long2Str(timestamp, false));
                Log.i("timestamp",DateFormatUtils.long2Str(timestamp, false));
            }*/
            public void onTimeSelected(String timer) {
                // mTvStartDate.setText(DateFormatUtils.long2Str(timestamp, false));
                ResearchByT(timer);

            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    public void ResearchByT(String timer)
    {
        Log.i("teswt",  "test"+"-----");
        String[]timers=timer.split("#");
        String startTimer=timers[0];
        String endTimer=timers[1];
        CategoryBeanDAO dao = new CategoryBeanDAO(new DBHelper(getApplicationContext()));
       List<CategoryBean>quearydata=  dao.queryByTimer(table,startTimer,endTimer);
        Log.i("teswt",  "test"+"-----"+quearydata.size());
        adpter=new MyAdpater(quearydata);
        mRcSearch.setAdapter(adpter);

    }

    private void initTimerPicker() {
        String beginTime = "2018-10-17 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

       // mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(String timestamp) {
                //mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, true));
               /* Log.i("timestamp",DateFormatUtils.long2Str(timestamp, true));*/
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }
}
