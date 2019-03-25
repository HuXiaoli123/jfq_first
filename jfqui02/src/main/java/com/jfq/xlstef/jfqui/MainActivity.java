package com.jfq.xlstef.jfqui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.utils.SaveDifData.SharedPreferencesUtils;
import com.readystatesoftware.viewbadger.BadgeView;


public class MainActivity extends TabActivity {
	TabHost tabHost;
	TabHost.TabSpec tabSpec;
	RadioGroup radioGroup;
	//RelativeLayout bottom_layout;
	ImageView img;
	//int startLeft;
	public static int msgCount;

	public static Activity mInstance = null;
	private BadgeView badgeView=null;
	private Button button=null;

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
					Log.i("MyCOunt","radio_message");
					badgeView.hide();
					tabHost.setCurrentTabByTag("message");
					clearData();
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
		initView();
		tabInit();

	}
	/**
	 * 进行个推的实例化
	 */
	@Override
	protected void onResume() {
		super.onResume();
		PushManager.getInstance().initialize(this.getApplicationContext(),com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService.class);

		// com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
		PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoIntentService.class);

		Log.i("onResume","onResume");
		/*boolean isBins= PushManager.getInstance().bindAlias(getApplicationContext(),Data.USER_NUMBER);

		*//*messageHandle(4,"绑定成功?"+ isBins+"   meber:"+member+"\n cid: "+clientid);*//*
		Toast.makeText(this,Data.USER_NUMBER+",\n"+isBins,Toast.LENGTH_LONG).show();
		Log.i("onResume","onResume");*/

	}

	float exitTime=0;
	//双击退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(event.getRepeatCount() == 0)
			{
				// 判断间隔时间 大于2秒就退出应用
				Log.i("quitTIme", ","+System.currentTimeMillis()+","+exitTime);
				Log.i("quitTIme", (System.currentTimeMillis() - exitTime)+"," );
				Log.i("quitTIme", ","+exitTime);
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					// 计算两次返回键按下的时间差
					exitTime = System.currentTimeMillis();
					Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
					Log.i("quitTIme", ","+exitTime);
					return  true;
				} else {
					Log.i("quitTIme","quit");
					// 关闭应用程序
					Intent home = new Intent(Intent.ACTION_MAIN);
					home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					home.addCategory(Intent.CATEGORY_HOME);
					startActivity(home);
					return true;
					// Intent home = new Intent(Intent.ACTION_MAIN);
					// home.addCategory(Intent.CATEGORY_HOME);
					// startActivity(home);
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}


	void clearData()
	{
		SharedPreferencesUtils utils=new SharedPreferencesUtils(getApplicationContext(),"msgs");
		utils.putValues(new SharedPreferencesUtils.ContentValue("msg_count", 0));
	}
	void initView()
	{
		mInstance = this;




        button=findViewById(R.id.btn_msg);
		badgeView=new BadgeView(this,button);
		IntentFilter intentFilter=new IntentFilter("StartQueryData");
		getApplication().registerReceiver(new InsertReceiver(),intentFilter);


		/*badgeView.setBadgeMargin(8); //各边间隔
        badgeView.setBadgeMargin(10, 3); // 水平和竖直方向的间距
		badgeView.setText(String.valueOf(2)); // 需要显示的提醒类容
		//badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
		badgeView.setTextColor(Color.WHITE); // 文本颜色
		badgeView.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
		badgeView.setTextSize(12); // 文本大小

		// badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
		badgeView.show();// 只有显示*/


	}

	private class InsertReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.i("currTab",tabHost.getCurrentTab()+"");

			  if(tabHost.getCurrentTab()!=1) {
				  badgeView.setText(String.valueOf(intent.getExtras().getInt("badge_count"))); // 需要显示的提醒类容
				  badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
				  badgeView.setTextColor(Color.WHITE); // 文本颜色
				  badgeView.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
				  badgeView.setTextSize(12); // 文本大小
				  badgeView.setBadgeMargin(10, 3); // 水平和竖直方向的间距
				  badgeView.setBadgeMargin(5); //各边间隔
				  // badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
				  badgeView.show();// 只有显示
			  }

		}


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


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}




}
