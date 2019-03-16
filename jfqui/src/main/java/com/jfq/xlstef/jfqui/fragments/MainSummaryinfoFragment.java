package com.jfq.xlstef.jfqui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jfq.xlstef.jfqui.OrderFragment.Adapter.DailyOrderAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.BasicOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DailyOrderDao;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.OrderFragment.WrapContentLinearLayoutManager;
import com.jfq.xlstef.jfqui.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//每日汇总
public class MainSummaryinfoFragment extends Fragment {
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
		return inflater.inflate(R.layout.fragment_main_summaryinfo, container, false);
	}*/
	//公共属性
	protected RecyclerView mRecyclerView;

	protected List<DailyOrder> mCategoryBean = new ArrayList<>();
	/*List<? extends Number> num = new ArrayList<Double>();*/
	BasicOrder b=new CategoryBean();
	protected List<DailyOrder>mCategoryTemp=new ArrayList<>();
	protected DailyOrderAdapter mCategoryAdapter;
	protected View headerView;
	protected Handler handler =null;

	public  View  onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.i("path--","onCreateView34");
		View view = View.inflate(getActivity(),R.layout.dailyoder5, null);

		handler=new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				Log.i("message",""+msg);
				initRecyclerView();
			}
		};

		Log.i("我的页面","dailyoder5");
		mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
		manager = new LinearLayoutManager(getActivity());

		LoadRecycleViewclass();
		//initRecyclerView();
		return view;
	}

	boolean isLoading;
	LinearLayoutManager manager=null;

	private  boolean isFirstTime=true;

	public void LoadRecycleViewclass()  {
       /*   1.请求数据
        2.拿服务器的数据进行事件比较，看是否有新数据过来
        3.将数据保存到本地的数据库（如果数据过多，看是否需要放在其他的table） */

		DailyOrderDao dao=new DailyOrderDao(getActivity());


		new Thread(new Runnable() {
			@Override
			public void run() {
				long exitTime=System.currentTimeMillis();
				//进行数据库查询
				DailyOrderDao dao = new DailyOrderDao(getActivity());

				/*判断数据库是否有数据*/
				while(dao.queryAll().size()<=0)
				{
					//超时了还没有数据显示
					if(OverTime(3,exitTime)) break;
				}
				mCategoryBean=dao.queryAll();

				handler.sendEmptyMessage(0);

			}
		}).start();

	}

	/**
	 * 超时
	 * @param time  :超过多久时间
	 * @param lastTime  ：触发开始时间
	 */
	private  boolean  OverTime(float time ,float lastTime)
	{
		if ((System.currentTimeMillis() - lastTime) > 3000)   return  true;
		return  false;
	}
	private  boolean OverTime(DailyOrderDao dao,float time ,float lastTime)
	{
		while (dao.queryAll().size()<=0)
		{
			if ((System.currentTimeMillis() - lastTime) > 3000)   return  true;
		}
		return  false;
	}

	public  void   InserDatabase()
	{
          /*
       异步加载网页数据
        */
        /*new DownLoadAsyncTask(getActivity(),mCategoryBean).execute(path);
        QueryData(new DBHelper(getActivity()));*/

	}
	//查询数据
	public ArrayList QueryData(DBHelper dbHelper, String status) {
		CategoryBeanDAO dao = new CategoryBeanDAO(dbHelper);
		return  dao.findOrderByName(Data.COMPELETE_ORDER_TABLE_NAME,status);
	}

	public   void initRecyclerView() {

		//出现bughttps://blog.csdn.net/lovexieyuan520/article/details/50537846
		/*  manager = new LinearLayoutManager(getActivity());*/
		manager=new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

		mRecyclerView.setLayoutManager(manager);
		// mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST)); //添加水平划线
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		Log.i("initrecy","initrecy"+mCategoryBean.size());
        /*
        控制第一次刷新的条数
         */
		if(mCategoryBean.size()>17)
		{
			for(int i=0;i<18;i++)
			{
				mCategoryTemp.add(mCategoryBean.get(i));
			}

			mCategoryAdapter = new DailyOrderAdapter(mCategoryTemp,getActivity());
		}else
		{

			mCategoryAdapter = new DailyOrderAdapter(mCategoryBean,getActivity());
		}
		mRecyclerView.setAdapter(mCategoryAdapter);



		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				int lastVisiableItemPosition = manager.findLastVisibleItemPosition();
				if( manager.findFirstCompletelyVisibleItemPosition()==0)
				{
					Log.i("compl","到头了"+lastVisiableItemPosition+":"+mCategoryAdapter.getItemCount());

					if(mCategoryBean.size()>17)
						setHeader(mRecyclerView);
				} else if (lastVisiableItemPosition + 1 == mCategoryAdapter.getItemCount()){
					if (!isLoading){
						isLoading = true;
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								//requestData();
								requestLoadMoreData();
								//    Toast.makeText(MainActivity.this, "已经没有新的了", Toast.LENGTH_SHORT).show();
								isLoading = false;
								// adapter.notifyItemRemoved(adapter.getItemCount());
							}
						},1000);
					}
				}
			}
		});


	}

	private void setHeader(RecyclerView view) {
		final View header = LayoutInflater.from(getContext()).inflate(R.layout.category_item_header, view, false);

		mCategoryAdapter.setHeaderView(header);


	}
	private void LoadMoreRecycleViewclass() {

		int count=mCategoryTemp.size();
		Log.i("InsertData6", mCategoryTemp.size()+","+mCategoryBean.size()+"count"+count);
		Log.i("InsertData6-------", mCategoryTemp.size()+","+mCategoryBean.size());
		if(mCategoryBean.size()- mCategoryTemp.size()>17)
		{
			for(int i=mCategoryTemp.size();i<count+18;i++)
			{
				Log.i("InsertData6——temp", mCategoryTemp.size()+","+mCategoryBean.size());
				mCategoryTemp.add(mCategoryBean.get(i));
			}

		}else
		{
			Log.i("InsertData6", mCategoryTemp.size()+","+mCategoryBean.size());
			for(int i=count;i<mCategoryBean.size();i++)
			{
				Log.i("InsertData6——temp", mCategoryTemp.size()+","+mCategoryBean.size());
				mCategoryTemp.add(mCategoryBean.get(i));
			}
		}
	}

	boolean isfinish=false;
	private void requestLoadMoreData(){
		if(!isfinish)
		{
			LoadMoreRecycleViewclass();
			mCategoryAdapter.notifyItemChanged(1,1);
			if(mCategoryTemp.size()==mCategoryBean.size() )isfinish=true;
			Log.i("isfinish",isfinish+"");
		}else
		{
			Toast.makeText(getContext(), "已经没有新的了", Toast.LENGTH_SHORT).show();
			mCategoryAdapter.notifyItemRemoved(mCategoryAdapter.getItemCount());
		}

	}

	public  void getState(int position){
		RecyclerView.ViewHolder viewHolder =
				(RecyclerView.ViewHolder)mRecyclerView
						.getChildViewHolder(mRecyclerView.getChildAt(position));

	}

	public  void myresearch()
	{
		Log.i("AAA_","MainSummaryinfoFragment");

		Intent intent = new Intent();
		intent.setClass(getActivity(), SerachActivity.class);
		intent.putExtra("orderName", 5);

		intent.putExtra("lstBean", (Serializable) mCategoryBean);

		startActivity(intent);
	}


}
