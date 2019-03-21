package com.jfq.xlstef.jfqui.SerachDetail;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.CustomDatePicker;
import com.jfq.xlstef.jfqui.OrderFragment.Util.DateFormatUtils;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DailyOrderDao;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.adapter.MainAllInfoAdapter;
import com.jfq.xlstef.jfqui.adapter.MainSummaryInfoAdapter;
import com.jfq.xlstef.jfqui.fragments.BaseAdpater;
import com.jfq.xlstef.jfqui.fragments.MyAdpater;
import com.jfq.xlstef.jfqui.fragments.MyAdpater_daily;
import com.jfq.xlstef.jfqui.fragments.MyAdpater_mall;
import com.jfq.xlstef.jfqui.fragments.MyAdpater_pay;

import java.util.ArrayList;
import java.util.List;

public class SerachActivity extends AppCompatActivity implements SearchView.OnQueryTextListener ,View.OnClickListener {

    SearchView searchView;
    BaseAdpater adpter;
    ImageView   timerPick;
    ImageView  monthPick;
    ImageView  backToMainfrag;//退出

    MainAllInfoAdapter myadpter;
    MainSummaryInfoAdapter mysumAdpter;

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
    List<CategoryBean> lstBean=new ArrayList<>();
    List<DailyOrder> dailysBean=new ArrayList<>();

    int mOrderName=0;
    String table;
    TextView unFoundData;


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
        if(mOrderName==5)
        {
            dailysBean= (List<DailyOrder>) intentGet.getSerializableExtra("lstBean");
            Log.i("mydata",dailysBean.size()+"");
        }else
        {
            lstBean= (List<CategoryBean>) intentGet.getSerializableExtra("lstBean");
        }


        initView();

      //  adpter=new MyAdpater(lstBean);


        switch (mOrderName)
        {
            case 1:
                SetGone();
                table=Data.VIEW_ALL_ORDER;
                myadpter=new MainAllInfoAdapter(this, lstBean,mOrderName);
                mRcSearch.setAdapter(myadpter);
                break;
            case 2:
                SetGone();
                table=Data.VIEW_COMODITYORDER;
                myadpter=new MainAllInfoAdapter(this, lstBean,mOrderName);
                mRcSearch.setAdapter(myadpter);
                break;
            case 3:
                SetGone();
                table=Data.VIEW_SWEEPCODE;
                myadpter=new MainAllInfoAdapter(this, lstBean,mOrderName);
                mRcSearch.setAdapter(myadpter);
                break;
            case 5:
                SetVisible();
                table=Data.ORDERDAILY_TABLE_NAME;
                mysumAdpter=new MainSummaryInfoAdapter(getApplicationContext(), dailysBean);
                mRcSearch.setAdapter(mysumAdpter);
                break;
        }


    }
   /* void Listtest(List<? extends CategoryBean> t){

    }*/
    void SetGone()
    {
        if(monthLayout.getVisibility()==View.VISIBLE)
            monthLayout.setVisibility(View.GONE);
    }
    void SetVisible()
    {
        if(monthLayout.getVisibility()==View.GONE)
            monthLayout.setVisibility(View.VISIBLE);
    }


    private CustomDatePicker mDatePicker,mDayPicker, mTimerPicker;
    private TextView pickertext;

    //按月查询
    RelativeLayout monthLayout;
    private void initView() {

        monthLayout=findViewById(R.id.month);

      //  mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mImgvDelete = (ImageView) findViewById(R.id.imgv_delete);
        mRcSearch = (RecyclerView) findViewById(R.id.rc_search);
        //Recyclerview的配置
        mRcSearch.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search_view);
        unFoundData=(TextView)findViewById(R.id.unfond);


        pickertext=(TextView) findViewById(R.id.pickertext);
        timerPick=(ImageView) findViewById(R.id.timerPicker);
        monthPick=(ImageView) findViewById(R.id.img_month);
        backToMainfrag=(ImageView)findViewById(R.id.backToMainfrag);


        searchView.setOnQueryTextListener(this);
        timerPick.setOnClickListener(this);
        monthPick.setOnClickListener(this);
        backToMainfrag.setOnClickListener(this);

        initDatePicker();
        initMonthPicker();
        initTimerPicker();


    }
    int  posindex;
    //当完成输入的内容点击搜索按钮后该方法会回调,
    // 参数String query返回当前文本框可见的文字
    @Override
    public boolean onQueryTextSubmit(String query){

      /*  // 设置SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(true);
        // 显示搜索按钮
        searchView.setSubmitButtonEnabled(true);*/
        return false;
    }
    //每次当文本框的内容发生改变该方法会回调,
// 参数String newText返回当前文本框可见的文字
    @Override
    public boolean onQueryTextChange(String newText){

        if(mOrderName<5)
        {
            myadpter.getFilter().filter(newText);
        } else
            mysumAdpter.getFilter().filter(newText);

        return false;
    }
    boolean isDay=true;//是否为日查询
    //时间选择器进行时间查询
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.timerPicker:
                searchView.setQuery("",false);
                mDatePicker.show("12");
                break;
            case R.id.img_month:
                Log.i("dateStr","img_month");
                searchView.setQuery("",false);
                mDayPicker.show("12");
                break;
            case R.id.backToMainfrag:
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
        mDayPicker.onDestroy();
        unFoundData.setVisibility(View.GONE);
    }
    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long(getResources().getString(R.string.original_time), false);
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

    /**
     * 初始化日选择按钮
     */
    private void initMonthPicker() {
        long beginTimestamp = DateFormatUtils.str2Long(getResources().getString(R.string.original_time_unday));
        long endTimestamp = System.currentTimeMillis();

        // mTvStartDate.setText("12");

        // 通过时间戳初始化日期，毫秒级别
        mDayPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            /*public void onTimeSelected(long timestamp) {
               // mTvStartDate.setText(DateFormatUtils.long2Str(timestamp, false));
                Log.i("timestamp",DateFormatUtils.long2Str(timestamp, false));
            }*/
            public void onTimeSelected(String timer) {
                // mTvStartDate.setText(DateFormatUtils.long2Str(timestamp, false));
                SearchDailyOrder(timer);

            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDayPicker.setCancelable(false);
        // 不显示时和分
        mDayPicker.setCanShowPreciseTime(false);
        //不显示日
        mDayPicker.setCanShowDaily(false);
        // 不允许循环滚动
        mDayPicker.setScrollLoop(false);
        // 不允许滚动动画
        mDayPicker.setCanShowAnim(false);
    }

    public void ResearchByT(String timer)
    {

        String[]timers=timer.split("#");
        String startTimer=timers[0];
        String endTimer=timers[1];
        if(mOrderName!=5)
        {
            CategoryBeanDAO dao = new CategoryBeanDAO(new DBHelper(getApplicationContext()));
           List<CategoryBean> quearydata=  dao.queryByTimer(table,startTimer,endTimer);
            myadpter=new MainAllInfoAdapter(this,quearydata,mOrderName);
            if(quearydata.size()<=0)
                unFoundData.setVisibility(View.VISIBLE);
            else
            {
                unFoundData.setVisibility(View.GONE);
            } unFoundData.setVisibility(View.VISIBLE);
            mRcSearch.setAdapter(myadpter);
        }else
        {
            DailyOrderDao dao = new DailyOrderDao(getApplicationContext());
            List<DailyOrder> quearydata=  dao.queryByTimer(startTimer,endTimer);
            mysumAdpter=new MainSummaryInfoAdapter(this,quearydata);
            if(quearydata.size()<=0)
                unFoundData.setVisibility(View.VISIBLE);
            else
            {
                unFoundData.setVisibility(View.GONE);
            } unFoundData.setVisibility(View.VISIBLE);
            mRcSearch.setAdapter(mysumAdpter);
        }




    }



    //按照月份查询
    void SearchDailyOrder(String timer)
    {

        String[]timers=timer.split("#");
        String sTimer=timers[0];
        String eTimer=timers[1];
        DailyOrderDao dao = new DailyOrderDao(getApplicationContext());
        List<DailyOrder>quearydata=dao.querByMonth(sTimer,eTimer);
        if(quearydata.size()<=0)
            unFoundData.setVisibility(View.VISIBLE);
        else
        {
            unFoundData.setVisibility(View.GONE);
        }

        mysumAdpter=new MainSummaryInfoAdapter(getApplicationContext(),quearydata);
        mRcSearch.setAdapter(mysumAdpter);
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
                Log.i("timestamp",timestamp);
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
