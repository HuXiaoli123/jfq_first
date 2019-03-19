package com.jfq.xlstef.jfqui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jfq.xlstef.jfqui.OrderFragment.CompleteOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Enum_Order.OrderName;
import com.jfq.xlstef.jfqui.fragments.*;
import com.jfq.xlstef.jfqui.utils.DisplayUtil;
import com.jfq.xlstef.jfqui.utils.MoveBg;

import java.util.ArrayList;
import java.util.List;

public class TabMainActivity extends AppCompatActivity  {
	RelativeLayout titlebar_Layout;
	ImageView titlebar_front;

	TextView titlebar_allinfo;//标题栏——全部订单
	TextView titlebar_mallinfo;//标题栏——商城订单
	TextView titlebar_payinfo;//标题栏——扫码加价
	TextView titlebar_commissioninfo;//标题栏——佣金明细
	TextView titlebar_summaryinfo;//标题栏——每日汇总
	ViewPager layout_body;
	//TextView tv_bar_more;
	List<Fragment> list;
	TabFragmentPagerAdapter tabFragAdapter;
	//一个title栏text的长度
	int title_width = 0;
	int screenWidth=0;//屏幕长度
	TextView lastMove_titlebar;
	private long exitTime = 0;// 退出时间



	private View.OnClickListener onClickListener=new View.OnClickListener() {
		int startX;
		@Override
		public void onClick(View v) {
			title_width=findViewById(R.id.titlebar_text).getWidth();
			switch (v.getId()){
				case R.id.titlebar_main_allinfo:
					layout_body.setCurrentItem(0);
					MoveBg.moveFrontBg(titlebar_front,titlebar_allinfo,startX,0,0,0);
					startX=0;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_allinfo;
					mcurrentName=OrderName.CompleteOrder;
					break;
				case R.id.titlebar_main_mallinfo:
					layout_body.setCurrentItem(1);
					MoveBg.moveFrontBg(titlebar_front,titlebar_mallinfo,startX,title_width,0,0);
					startX=title_width;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_mallinfo;
					mcurrentName=OrderName.ShopMallOrder;
					break;
				case R.id.titlebar_main_payinfo:
					layout_body.setCurrentItem(2);
					MoveBg.moveFrontBg(titlebar_front,titlebar_payinfo,startX,title_width*2,0,0);
					startX=title_width*2;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_payinfo;
					mcurrentName=OrderName.SweepCode;
					break;
				case R.id.titlebar_main_commissioninfo:
					layout_body.setCurrentItem(3);
					MoveBg.moveFrontBg(titlebar_front,titlebar_commissioninfo,startX,title_width*3,0,0);
					startX=title_width*3;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_commissioninfo;
					mcurrentName=OrderName.DetailCommission;
					break;
				case R.id.titlebar_main_summaryinfo:
					layout_body.setCurrentItem(4);
					MoveBg.moveFrontBg(titlebar_front,titlebar_summaryinfo,startX,title_width*4,0,0);
					startX=title_width*4;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_summaryinfo;
					mcurrentName=OrderName.DailyOrder;
					break;
				default:
						break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_main);
		initViews();
	}
	/*
		初始化各个控件
	 */
	private void initViews(){

			//获取控件
			titlebar_Layout=(RelativeLayout) findViewById(R.id.layout_titlebar);
			layout_body=(ViewPager)findViewById(R.id.layout_body);
			titlebar_allinfo=(TextView)findViewById(R.id.titlebar_main_allinfo);
			titlebar_mallinfo=(TextView)findViewById(R.id.titlebar_main_mallinfo);
			titlebar_payinfo=(TextView)findViewById(R.id.titlebar_main_payinfo);
			titlebar_commissioninfo=(TextView)findViewById(R.id.titlebar_main_commissioninfo);
			titlebar_summaryinfo=(TextView)findViewById(R.id.titlebar_main_summaryinfo);
			//设置标题文字
			TextView layout_top_text=(TextView)findViewById(R.id.layout_top_text);
			layout_top_text.setText(R.string.tab_main_title_text);
			//监听器
			titlebar_allinfo.setOnClickListener(onClickListener);
			titlebar_mallinfo.setOnClickListener(onClickListener);
			titlebar_payinfo.setOnClickListener(onClickListener);
			titlebar_commissioninfo.setOnClickListener(onClickListener);
			titlebar_summaryinfo.setOnClickListener(onClickListener);
			layout_body.addOnPageChangeListener(new MyPagerChangeListener());
			//设置viewpage
			list=new ArrayList<>();

			mainAllinfoFragment=new MainAllinfoFragment();
			mallinfoFragment=new MainMallinfoFragment();
			mainPayinfoFragment=new MainPayinfoFragment();
			mainSummaryinfoFragment=new MainSummaryinfoFragment();

			list.add(mainAllinfoFragment);
			list.add(mallinfoFragment);
			list.add(mainPayinfoFragment);
			list.add(new MainCommissioninfoFragment());
			list.add(mainSummaryinfoFragment);
			tabFragAdapter=new TabFragmentPagerAdapter(getSupportFragmentManager(),list);
			layout_body.setAdapter(tabFragAdapter);
			layout_body.setCurrentItem(0);
			//设置显示栏背景
		//设置显示栏背景（线条）
		//获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		int screenl_5 = (screenWidth-DisplayUtil.dp2px(this,10))/5;
		titlebar_front=new ImageView(this);

			/*titlebar_front=new TextView(this);*/
			/*titlebar_front.setBackgroundResource(R.drawable.slidebar);*/
		titlebar_front.setImageResource(R.drawable.slidebar);
			/*titlebar_front.setTextColor(Color.WHITE);
			titlebar_front.setText(R.string.title_main_allinfo);
			titlebar_front.setGravity(Gravity.CENTER);*/
			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(screenl_5,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
			titlebar_Layout.addView(titlebar_front,params);
		lastMove_titlebar=titlebar_allinfo;
		lastMove_titlebar.setTextColor(getResources().getColor(R.color.foot_text_select));

		mcurrentName=OrderName.CompleteOrder;

	}
	OrderName mcurrentName;
	/**
	 * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
	 *
	 */
	class MyPagerChangeListener implements ViewPager.OnPageChangeListener{
		int startX;
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			title_width=findViewById(R.id.titlebar_text).getWidth();
			switch (position){
				case 0:
					MoveBg.moveFrontBg(titlebar_front,titlebar_allinfo,startX,0,0,0);
					startX=0;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_allinfo;
					break;
				case 1:
					MoveBg.moveFrontBg(titlebar_front,titlebar_mallinfo,startX,title_width,0,0);
					startX=title_width;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_mallinfo;
					mcurrentName=OrderName.ShopMallOrder;
					break;
				case 2:
					MoveBg.moveFrontBg(titlebar_front,titlebar_payinfo,startX,title_width*2,0,0);
					startX=title_width*2;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_payinfo;
					mcurrentName=OrderName.SweepCode;
					break;
				case 3:
					MoveBg.moveFrontBg(titlebar_front,titlebar_commissioninfo,startX,title_width*3,0,0);
					startX=title_width*3;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_commissioninfo;
					mcurrentName=OrderName.DetailCommission;
					break;
				case 4:
					MoveBg.moveFrontBg(titlebar_front,titlebar_summaryinfo,startX,title_width*4,0,0);
					startX=title_width*4;
					lastMove_titlebar.setTextColor(getResources().getColor(R.color.top_title_fontcolor));
					lastMove_titlebar=titlebar_summaryinfo;
					mcurrentName=OrderName.DailyOrder;
					break;
				default:
					break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	}

	MainAllinfoFragment mainAllinfoFragment;
	MainMallinfoFragment mallinfoFragment;
	MainPayinfoFragment mainPayinfoFragment;
	MainSummaryinfoFragment mainSummaryinfoFragment;
	public void research(View v)
	{
		  switch (mcurrentName)
		  {
			  case CompleteOrder:
				  if(null != mainAllinfoFragment){
					  mainAllinfoFragment.myresearch();
				  }

			  	break;
			  case ShopMallOrder:
				  if(null != mallinfoFragment){
					  mallinfoFragment.myresearch();
				  }
				  break;
			  case SweepCode:
				  if(null != mainPayinfoFragment){
					  mainPayinfoFragment.myresearch();
				  }
				  break;
			  case DailyOrder:
			  	if(null!=mainSummaryinfoFragment)
				{
					mainSummaryinfoFragment.myresearch();
				}
		  }
		  Log.i("AAA",mcurrentName+"," );
	}
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**
		 *  判断requestCode, resultCode 来确定要执行的代码
		 */
		Log.i("MyCode",requestCode+","+resultCode);
		/*if(requestCode==1){
			// 在这设置选中你要显示的fragment
			switch (requestCode)
			{
				case 1:
					layout_body.setCurrentItem(0);
					break;
				case 2:
					layout_body.setCurrentItem(1);
					break;
				case 3:
					layout_body.setCurrentItem(2);
					break;
				case 4:
					layout_body.setCurrentItem(3);
					break;
				case 5:
					layout_body.setCurrentItem(4);
					break;
			}
		}*/

	}


}
