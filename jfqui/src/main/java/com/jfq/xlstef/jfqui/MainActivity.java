package com.jfq.xlstef.jfqui;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.jfq.xlstef.jfqui.utils.MoveBg;

public class MainActivity extends TabActivity {
	TabHost tabHost;
	TabHost.TabSpec tabSpec;
	RadioGroup radioGroup;
	//RelativeLayout bottom_layout;
	ImageView img;
	//int startLeft;

	//监听器
	private RadioGroup.OnCheckedChangeListener checkedChangeListener=new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			int currentTab = tabHost.getCurrentTab();
			switch (checkedId){
				case R.id.radio_main:
					tabHost.setCurrentTabByTag("main");
					/*MoveBg.moveFrontBg(img, startLeft, 0, 0, 0);
					startLeft = 0;*/
					//如果需要动画效果就使用
					//setCurrentTabWithAnim(currentTab, 0, "main");
					//getSupportActionBar().setTitle("主页");
					break;
				case R.id.radio_message:
					tabHost.setCurrentTabByTag("message");
					/*MoveBg.moveFrontBg(img, startLeft, img.getWidth(), 0, 0);
					startLeft = img.getWidth();*/
					//如果需要动画效果就使用
					//setCurrentTabWithAnim(currentTab, 1, "message");
					//getSupportActionBar().setTitle("消息");
					break;
				case R.id.radio_search:
					tabHost.setCurrentTabByTag("search");
				/*	MoveBg.moveFrontBg(img, startLeft, img.getWidth()*2, 0, 0);
					startLeft = img.getWidth()*2;*/
					//如果需要动画效果就使用
					//setCurrentTabWithAnim(currentTab, 2, "search");
					//getSupportActionBar().setTitle("查询");
					break;
				case R.id.radio_mycenter:
					tabHost.setCurrentTabByTag("mycenter");
					/*MoveBg.moveFrontBg(img, startLeft, img.getWidth()*3, 0, 0);
					startLeft = img.getWidth()*3;*/
					//如果需要动画效果就使用
					//setCurrentTabWithAnim(currentTab, 3, "mycenter");
					//getSupportActionBar().setTitle("个人中心");
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabInit();

	}
	/*
	初始化tab
	 */
	 void tabInit(){
		//bottom_layout = (RelativeLayout) findViewById(R.id.layout_bottom);
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		/*tabHost=(TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();*/
		tabHost=getTabHost();
		//tabHost.addTab(tabHost.newTabSpec("main").setIndicator("main").setContent(R.id.frag1));
		tabHost.addTab(tabHost.newTabSpec("main").setIndicator("main").setContent(new
				Intent(this,TabMainActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("message").setIndicator("message").setContent(new
				Intent(this,TabMessageActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("search").setIndicator("search").setContent(new
				Intent(this,TabSearchActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("mycenter").setIndicator("mycenter").setContent(new
				Intent(this,TabMycenterActivity.class)));

		radioGroup.setOnCheckedChangeListener(checkedChangeListener);

	/*	img = new ImageView(this);
		img.setImageResource(R.drawable.tab_front_bg);
		bottom_layout.addView(img);*/
	}

	// 判断动画滑动的方向
	private void setCurrentTabWithAnim(int now, int next, String tag) {
		if (now > next) {
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
			tabHost.setCurrentTabByTag(tag);
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
		} else {
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
			tabHost.setCurrentTabByTag(tag);
			tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
		}
	}
}
