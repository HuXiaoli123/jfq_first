package com.jfq.xlstef.jfqui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.LoginPage.Login_Activity;
import com.jfq.xlstef.jfqui.Message.Mycenter_MsgActivity;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.utils.SaveDifData.SharedPreferencesUtils;


public class TabMycenterActivity extends AppCompatActivity {

    private  TextView number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_mycenter);
		//设置标题文字
		TextView layout_top_text=(TextView)findViewById(R.id.layout_top_text);
		layout_top_text.setText(R.string.tab_message_title_text);

		initView();
		initButton();
	}

	private void initView()
    {
        number=(TextView) findViewById(R.id.numberName);
        number.setText(Data.USER_NUMBER);
    }
	Dialog	dialog;
	private void initButton()
	{
		LinearLayout quitButton = (LinearLayout)findViewById(R.id.withdraw_1);
		//LinearLayout messageButton = (LinearLayout)findViewById(R.id.message);
		quitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("mygetuitest","注销");

				/*// 创建退出对话框
				AlertDialog isExit = new AlertDialog.Builder(getApplicationContext()).create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("确定要退出吗");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener);
				isExit.setButton2("取消", listener);

				// 显示对话框
				isExit.show();*/
				//此处直接new一个Dialog对象出来，在实例化的时候传入主题
				dialog = new Dialog(TabMycenterActivity.this, R.style.addNoteType_error_Dialog);

				//设置它的ContentView
				dialog.setContentView(R.layout.quitdilog);
				dialog.show();


			}
		});
		/*messageButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				TransformToMsg();
			}
		});*/
	}

	public void  SystemTip(View view)
	{
		switch (view.getId())
		{
			case R.id.btn_quit:
				ActivityBack();
				break;
			case  R.id.btn_cancle:
				dialog.cancel();

		}
	}


	 

	private  void TransformToMsg()
	{
		Intent intent = new Intent(TabMycenterActivity.this,Mycenter_MsgActivity.class);

		/* 启动一个新的Activity */
		 startActivity(intent);
		/* 关闭当前的Activity */
		//finish();
	}



	//注销账号返回到主界面
	private  void  ActivityBack()
	{
		/* 新建一个Intent对象 */
		Intent intent = new Intent();
		/*SharedPreferencesUtils sharedPreferencesUtils=new SharedPreferencesUtils(getApplication(),"cookies");

		sharedPreferencesUtils.clear();*/

        SharedPreferencesUtils  Utils_set=new SharedPreferencesUtils(getApplication(),"settings");

        if(Utils_set.getBoolean("isAutoLogin",false))
		{
			Utils_set.clear();
		} else if(!Utils_set.getBoolean("isRemeberUser",false))
		{
			Utils_set.clear();
		}
		Log.i("test",Utils_set.getBoolean("isRemeberUser",false)+"");
		intent.setClass(getApplicationContext(), Login_Activity.class);
		destroyDBHelp();
		/* 启动一个新的Activity */
		 this.startActivity(intent);


		/* 关闭当前的Activity */
		// finish();
	}

	//销毁DBHelp的实例
	public void destroyDBHelp()
	{
		DBHelper.getInstance(getApplicationContext()).closeDBHelper();
	}
}
