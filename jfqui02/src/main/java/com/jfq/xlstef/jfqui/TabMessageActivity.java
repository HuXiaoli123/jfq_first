package com.jfq.xlstef.jfqui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.Message.Adapter;
import com.jfq.xlstef.jfqui.Message.LoadListView;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.Tools.DataBaseUtil.ListInfo;
import com.jfq.xlstef.jfqui.Tools.DataBaseUtil.ListInfoDAO;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class TabMessageActivity extends AppCompatActivity implements LoadListView.ILoadListener{

	List<ListInfo> listInfos=new ArrayList<>();
	private  LoadListView lv;
	Adapter adapter;

	List<ListInfo> messages=new ArrayList<ListInfo>();//加载10页
	private int totalSize;
	private int retainSize;
	private int pageSize;//加载页数
	List<ListInfo> arrayListInfos;

	static Context mContext;
	static DBHelper dbHelper;//创建数据库辅助类
	RelativeLayout linearLayout ;
	View tipview=null;

	int msgCount=0;

   /* private     String timer="";
    private   String content;*/

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_message);

		TextView layout_top_text=(TextView)findViewById(R.id.layout_top_text);
		layout_top_text.setText(R.string.tab_message_title_text);

		IntentFilter intentFilter=new IntentFilter("StartQueryData");
		getApplication().registerReceiver(new TabMessageActivity.InsertReceiver(),intentFilter);
		mContext=getApplicationContext();
		//生成DBHelper的对象
		dbHelper=new DBHelper(mContext);
		Log.i("dbHelper1",mContext+""+dbHelper+"");
		lv=(LoadListView)findViewById(R.id.lv);
		lv.Setinterface(this);

	}




	public class InsertReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			QueryData(new DBHelper(getApplicationContext()));
		}
	}



	@Override
	public void onResume() {
		Log.i("messageFrament","onResume");
		super.onResume();
		QueryData(dbHelper);
	}
	public void onStart() {
		Log.i("messageFrament","onResume");
		super.onStart();

	}

	//插入数据
	public  void InsertData(String timer,String content)
	{

		ListInfo listInfo=new ListInfo();
		Log.i("InsertData1", timer+content);
		listInfo.setTimer(timer);
		Log.i("InsertData2", timer+content);
		listInfo.setContent(content);
		Log.i("InsertData3", timer+content);
		//调用DAO辅助操作数据库
		ListInfoDAO dao=new ListInfoDAO(dbHelper);
		Log.i("dbHelper",mContext+""+dbHelper+"");
		Log.i("InsertData4", timer+content);
		dao.insertDB(listInfo);
		Log.i("dao",""+dao);
		//Toast.makeText(mContext,"已插入一条数据",Toast.LENGTH_SHORT).show();
		Log.i("InsertData5", timer+content);


	}

	//查询数据
	public void QueryData(DBHelper dbHelper)
	{

		// mainlayout.removeView(lv);//一开始的时候先将listview清除出mainlayout中
		ListInfoDAO dao=new ListInfoDAO(dbHelper);

		// ListInfo listInfo=new ListInfo();
		/* listInfo.setTimer();*/
		//将查询的数据放入ArrayList中
		//ArrayList<Map<String ,Object>> arrayList= dao2.queryDB(_ID);

		listInfos=dao.queryDB();
		//不要重新定义一个，不然就对全局变量起不到作用
		Log.i("arrayList.size()",""+listInfos.size());
		//创建一个ListView（列表控件）
		// lv=new ListView(getApplicationContext());
		//ListView必须和ListAdapter关联


		if(listInfos.size()<=0)
		{

			linearLayout = (RelativeLayout)findViewById(R.id.myrelative);
			LayoutInflater inflater = LayoutInflater.from(getApplication());
			tipview = inflater.inflate(R.layout.nodatatip, null);
			linearLayout.addView(tipview);
		}else
		{
			if(tipview!=null)
			{
				linearLayout.removeView(tipview);
			}
		}

		arrayListInfos=new ArrayList<>();
		if(listInfos.size()>12)
		{
			for(int i=0;i<11;i++)
			{
				arrayListInfos.add(listInfos.get(i));
			}
			Log.i("InsertData6", listInfos.size()+","+arrayListInfos.size());
			loadLisVIew(arrayListInfos);
		}else
		{
			Log.i("InsertData6", listInfos.size()+","+arrayListInfos.size());
			loadLisVIew(listInfos);
		}


		//把listView添加进主界面中
		// mainlayout.addView(lv);//加载listview进mainlayout中



	}



	void loadLisVIew(List<ListInfo> listInfos)
	{
		if(adapter==null)
		{
			if(listInfos!=null && listInfos.size()!=0){
				Log.i("Tag","adapter-null");
				adapter=new Adapter(listInfos,mContext);//自动为id是list的ListView设置适配器
				lv.setAdapter(adapter);
			}else{
				Log.i("Tag","没有记录");
			}
		}else
		{
			Log.i("Tag","adapter"+listInfos.size());
			adapter.onDateChange(listInfos);//刷新view
		}
	}

	boolean isOverride;
	/*模拟加载数据*/
	private void loadData() {
		int count = adapter.getCount();
		Log.i("adapter_count",listInfos.size()+","+count);
		pageSize++;
		for (int i = count; i < count+10; i++) {
			if(i<listInfos.size())
			{
				arrayListInfos.add(listInfos.get(i));
				loadLisVIew(arrayListInfos);

			} else
			{
				loadLisVIew(listInfos);
				return;
			}
		}


		retainSize=totalSize-11;
        /*//*listInfos=new ArrayList<>();
        ListInfo lv=new ListInfo("13-37","你好");
        listInfos.add(lv);*//*
        listInfos.add(new ListInfo("13-39","你好a"));*/


	}



	@Override
	public void onLoad() {

		//增加延时效果
		Handler handler=new Handler();
		handler.postDelayed(new Runnable(){
			@Override
			public void run() {

				//获取更多数据
				loadData();
				//更新listview显示
				Log.i("listInfo",""+listInfos.size());
				// loadLisVIew(listInfos);
				//通知listView加载完毕
				lv.LoadComplete();

			}
		},2000);


	}
}




