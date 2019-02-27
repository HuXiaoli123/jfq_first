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
import android.widget.ImageView;
import android.widget.Toast;

import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.Adapter.DailyOrderAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.Adapter.MallinfoAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.BaseFragment;
import com.jfq.xlstef.jfqui.OrderFragment.Enum_Order.OrderName;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.BasicOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DailyOrderDao;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DownLoadAsyncTask;
import com.jfq.xlstef.jfqui.OrderFragment.WrapContentLinearLayoutManager;
import com.jfq.xlstef.jfqui.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//商城订单
public class MainMallinfoFragment extends  Fragment  {
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
        return inflater.inflate(R.layout.fragment_main_mallinfo, container, false);
    }*/
//初始化特有数据



    //公共属性
    protected RecyclerView mRecyclerView;

    protected  List<CategoryBean>  mCategoryBean = new ArrayList<CategoryBean>();
    /*List<? extends Number> num = new ArrayList<Double>();*/
    BasicOrder b=new CategoryBean();
    protected List<CategoryBean>mCategoryTemp=new ArrayList<>();
    protected MallinfoAdapter mCategoryAdapter;
    protected View headerView;
    protected Handler handler =null;

    protected  String path="";

    int  posindex;



    public  View  onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.shopmalloder2, null);

        initial(view);

        return view;
    }

    public  void initial(View view)
    {
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                Log.i("message",""+msg);
                initRecyclerView();
            }
        };

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(getActivity());

        LoadRecycleViewclass();

        //initRecyclerView();
    }


    public  void myresearch()
    {
        Log.i("AAA_","MallresearchFragment");

        Intent intent = new Intent();
        intent.setClass(getActivity(), SerachActivity.class);
        intent.putExtra("orderName", 1);

        intent.putExtra("lstBean", (Serializable) mCategoryBean);

        startActivity(intent);
    }

    boolean isLoading;
    boolean isFreshing;
    LinearLayoutManager manager=null;

    private  boolean isFirstTime=true;

    public void LoadRecycleViewclass()  {
       /*   1.请求数据
        2.拿服务器的数据进行事件比较，看是否有新数据过来
        3.将数据保存到本地的数据库（如果数据过多，看是否需要放在其他的table） */

        CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(getActivity()) );
        Log.i("mypath1",":"+dao.allCaseNum());
        final DownLoadAsyncTask myAsyTask=  new DownLoadAsyncTask(getActivity());
        /*mCategoryBean=Tooljson.JsonParse(getContext());*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                long exitTime=System.currentTimeMillis();
                //进行数据库查询
                CategoryBeanDAO dao = new CategoryBeanDAO(new DBHelper(getActivity()));

                /*判断数据库是否有数据*/
                while(dao.allCaseNum()<=0)
                {
                    //超时了还没有数据显示
                    if(OverTime(3,exitTime)) break;
                }
                mCategoryBean=dao.findByOrderType("商城订单");//找到订单类型为商城订单

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
    private  boolean OverTime(CategoryBeanDAO dao,float time ,float lastTime)
    {
        while (dao.allCaseNum()<=0)
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
    public ArrayList QueryData(DBHelper dbHelper,String status) {
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

        // mCategoryBean=QueryData(new DBHelper(getActivity()));


        Log.i("sleep","waiting+my"+mCategoryBean.size());

        //下方注释的代码用来解决headerview和footerview加载到头一个或者最后一个item  而不是占据一行的bug
        /*final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        // gridLayoutManager  布局管理器
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是第一个(添加HeaderView)   还有就是最后一个(FooterView)
                return position == mCategoryBean.size() + 1 || position == 0 ? gridLayoutManager.getSpanCount() : 1;
            }
        });*/


        /*
        控制第一次刷新的条数
         */
        if(mCategoryBean.size()>17)
        {
            for(int i=0;i<18;i++)
            {
                mCategoryTemp.add(mCategoryBean.get(i));
            }

            mCategoryAdapter = new MallinfoAdapter(mCategoryTemp,getActivity());
        }else
        {

            mCategoryAdapter = new MallinfoAdapter(mCategoryBean,getActivity());
        }
        mRecyclerView.setAdapter(mCategoryAdapter);

            /*
            点击判断是哪一列  :
             */
        mCategoryAdapter.setOnItemClickListener(new MallinfoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, CategoryBean categoryBean) {
                //  Toast.makeText(getContext(), "我是第" + position + "项", Toast.LENGTH_SHORT).show();
                posindex=position;

            }
        });

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
                    Log.i(" Sroller","到定了"+lastVisiableItemPosition+":"+mCategoryAdapter.getItemCount());

                    // if(mCategoryBean.size()>17)
                    setHeader(mRecyclerView);  //上拉刷新数据
                    if (!isFreshing){
                        isFreshing = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("Sroller ","上拉刷新");
                                //requestData();
                                requestLoadMoreData();
                                //    Toast.makeText(MainActivity.this, "已经没有新的了", Toast.LENGTH_SHORT).show();
                                isFreshing = false;
                                // adapter.notifyItemRemoved(adapter.getItemCount());
                            }
                        },1000);
                    }
                } else if (lastVisiableItemPosition + 1 == mCategoryAdapter.getItemCount()){
                    if (!isLoading){
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("Sroller ","到头了");
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

    public  void research(View v)
    {
        Log.i("v","mallinfo");
    }

}
