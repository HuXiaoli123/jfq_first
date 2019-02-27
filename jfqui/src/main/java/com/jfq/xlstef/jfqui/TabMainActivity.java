package com.jfq.xlstef.jfqui;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.OrderFragment.CompleteOrder;
import com.jfq.xlstef.jfqui.fragments.*;
import com.jfq.xlstef.jfqui.utils.MoveBg;

import java.util.ArrayList;
import java.util.List;

public class TabMainActivity extends AppCompatActivity  {
	RelativeLayout titlebar_Layout;
	TextView titlebar_front;

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

	public enum  OrderName {

		CompleteOrder("CompleteOrder"),
		ShopMallOrder("2"),
		SweepCode("3"),
		DetailCommission("4"),
		DailyOrder("5"),
		UnpayOrder("6");

		private String type;

		OrderName(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

	}
	private View.OnClickListener onClickListener=new View.OnClickListener() {
		int startX;
		@Override
		public void onClick(View v) {
			title_width=findViewById(R.id.titlebar_text).getWidth();
			switch (v.getId()){
				case R.id.titlebar_main_allinfo:
					layout_body.setCurrentItem(0);
					MoveBg.moveFrontBg(titlebar_front,startX,0,0,0);
					startX=0;
					titlebar_front.setText(R.string.title_main_allinfo);
					mcurrentName=OrderName.CompleteOrder;
					break;
				case R.id.titlebar_main_mallinfo:
					layout_body.setCurrentItem(1);
					MoveBg.moveFrontBg(titlebar_front,startX,title_width,0,0);
					startX=title_width;
					titlebar_front.setText(R.string.title_main_mallinfo);
					mcurrentName=OrderName.ShopMallOrder;
					break;
				case R.id.titlebar_main_payinfo:
					layout_body.setCurrentItem(2);
					MoveBg.moveFrontBg(titlebar_front,startX,title_width*2,0,0);
					startX=title_width*2;
					titlebar_front.setText(R.string.title_main_payinfo);
					break;
				case R.id.titlebar_main_commissioninfo:
					layout_body.setCurrentItem(3);
					MoveBg.moveFrontBg(titlebar_front,startX,title_width*3,0,0);
					startX=title_width*3;
					titlebar_front.setText(R.string.title_main_commissioninfo);
					break;
				case R.id.titlebar_main_summaryinfo:
					layout_body.setCurrentItem(4);
					MoveBg.moveFrontBg(titlebar_front,startX,title_width*4,0,0);
					startX=title_width*4;
					titlebar_front.setText(R.string.title_main_summaryinfo);
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

			list.add(mainAllinfoFragment);
			list.add(mallinfoFragment);
			list.add(new MainPayinfoFragment());
			list.add(new MainCommissioninfoFragment());
			list.add(new MainSummaryinfoFragment());
			tabFragAdapter=new TabFragmentPagerAdapter(getSupportFragmentManager(),list);
			layout_body.setAdapter(tabFragAdapter);
			layout_body.setCurrentItem(0);
			//设置显示栏背景
			titlebar_front=new TextView(this);
			titlebar_front.setBackgroundResource(R.drawable.slibar);
			titlebar_front.setTextColor(Color.WHITE);
			titlebar_front.setText(R.string.title_main_allinfo);
			titlebar_front.setGravity(Gravity.CENTER);
			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
			titlebar_Layout.addView(titlebar_front,params);

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
					MoveBg.moveFrontBg(titlebar_front,startX,0,0,0);
					startX=0;
					titlebar_front.setText(R.string.title_main_allinfo);
					break;
				case 1:
					MoveBg.moveFrontBg(titlebar_front,startX,title_width,0,0);
					startX=title_width;
					titlebar_front.setText(R.string.title_main_mallinfo);
					break;
				case 2:
					MoveBg.moveFrontBg(titlebar_front,startX,title_width*2,0,0);
					startX=title_width*2;
					titlebar_front.setText(R.string.title_main_payinfo);
					break;
				case 3:
					MoveBg.moveFrontBg(titlebar_front,startX,title_width*3,0,0);
					startX=title_width*3;
					titlebar_front.setText(R.string.title_main_commissioninfo);
					break;
				case 4:
					MoveBg.moveFrontBg(titlebar_front,startX,title_width*4,0,0);
					startX=title_width*4;
					titlebar_front.setText(R.string.title_main_summaryinfo);
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
		  }
		  Log.i("AAA",mcurrentName+","+mainAllinfoFragment);
	}

}
