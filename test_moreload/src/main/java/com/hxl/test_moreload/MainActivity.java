package com.hxl.test_moreload;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hxl.test_moreload.OrderFragment.CompleteOrder;
import com.hxl.test_moreload.OrderFragment.DailyOrder;
import com.hxl.test_moreload.OrderFragment.DetailsOfCommission;
import com.hxl.test_moreload.OrderFragment.ShopMallOrder;
import com.hxl.test_moreload.OrderFragment.SweepCode;
import com.hxl.test_moreload.OrderFragment.UnPayOrder;

public class MainActivity extends AppCompatActivity   {

    private  String[] oderType;
    private TextView view ;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    LinearLayout linearLayout;

    FragmentManager fm;
    FragmentTransaction ft;
    CompleteOrder mCompleteOrder;
    ShopMallOrder mShopMallOrder;
    SweepCode mSweepCode;
    DetailsOfCommission mDetailCommission;
    DailyOrder mDailyOrder;
    UnPayOrder mUnPayOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm=getSupportFragmentManager();
        oderType=getResources().getStringArray(R.array.spinnername);
       /* view = (TextView) findViewById(R.id.spinnerText);*/
        spinner = (Spinner) findViewById(R.id.Spinner01);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,oderType);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);

    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            /*view.setText("你的血型是："+oderType[arg2]);*/
            Log.i("你选择的是：",arg2+"");


            /*int order[]={OrderType.compeleteOder.ordinal(),OrderType.compeleteOder.ordinal()};*/
            switch (arg2)
            {
                case 0 :
                    setTabSelection(R.layout.complete_layout1);
                    break;
                case 1:
                    setTabSelection(R.layout.shopmalloder2);
                    break;
                case 2:
                    setTabSelection(R.layout.sweep_code_layout3);
                    break;
                case 3:
                    setTabSelection(R.layout.detailsofcommission4);
                    break;
                case  4:
                    setTabSelection(R.layout.dailyoder5);
                case 5:setTabSelection(R.layout.unplayorder6);
                    break;

            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    void  FlashView(int layout) {

       /* linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(layout, null);
        *//*TextView tv= view.findViewById(R.id.mTextView);
        tv.setText("my layout"+layout);*//*
        linearLayout.addView(view);*/


    }

    public void getFragment() {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        // 将原有的Activity替换成Fragment。也可以添加到原有之上不替换 如：ft.add(R.id.rel, new
        // Fragment()) ;如果添加的多了会比较耗资源。
        ft.add(R.id.fl,new CompleteOrder());
        //提交
        ft.commit();

    }

    private void setTabSelection(int index){

        ft = fm.beginTransaction();
        hideFragment(ft);
        Log.i("messageFrament","hideall");
        //R.id.fl在activity_initial.xml的fragment中------------------------------如何使用公有父类抽象类---增加扩展-----减少case的添加呢
        switch (index) {
            case R.layout.complete_layout1:
                if(mCompleteOrder==null){
                    mCompleteOrder = new CompleteOrder();
                    ft.add(R.id.fl, mCompleteOrder);

                }else{
                    ft.show(mCompleteOrder);
                }
                break;
            case R.layout.shopmalloder2:
                if(mShopMallOrder==null){
                    mShopMallOrder = new ShopMallOrder();
                    ft.add(R.id.fl, mShopMallOrder);
                }else
                    ft.show(mShopMallOrder);
                break;

            case R.layout.sweep_code_layout3:
                if(mSweepCode==null){
                    mSweepCode = new SweepCode();
                    ft.add(R.id.fl, mSweepCode);
                }else
                ft.show(mSweepCode);
                break;
            case R.layout.detailsofcommission4:
                if(mDetailCommission==null){
                    mDetailCommission = new DetailsOfCommission();
                    ft.add(R.id.fl, mDetailCommission);
                }else
                    ft.show(mDetailCommission);
                break;
            case R.layout.dailyoder5:
                if(mDailyOrder==null){
                    mDailyOrder = new DailyOrder();
                    ft.add(R.id.fl, mDailyOrder);
                }else
                    ft.show(mDailyOrder);
                break;
            case R.layout.unplayorder6:
                {
                    if(mUnPayOrder==null){
                        mUnPayOrder = new UnPayOrder();
                        ft.add(R.id.fl, mUnPayOrder);
                    }else
                        ft.show(mUnPayOrder);
                }
                break;
        }
        ft.commit();
    }
    //用于隐藏fragment
    private void hideFragment(FragmentTransaction ft){
        Log.i("messageFrament",mCompleteOrder+","+mShopMallOrder);
        if(mCompleteOrder!=null){
            ft.hide(mCompleteOrder);
            Log.i("messageFrament","hide");
        }if(mShopMallOrder!=null){
            ft.hide(mShopMallOrder);
        }if (mSweepCode!=null)
            ft.hide(mSweepCode) ;
        if (mDetailCommission!=null)
            ft.hide(mDetailCommission) ;
        if (mDailyOrder!=null)
            ft.hide(mDailyOrder) ;
        if(mUnPayOrder!=null)
            ft.hide(mUnPayOrder);
    }




}
