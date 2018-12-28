package com.hxl.test_moreload.OrderFragment;
import android.os.Bundle;
import android.os.Handler;
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

import com.hxl.test_moreload.CategoryAdapter;
import com.hxl.test_moreload.OrderFragment.Goods.CategoryBean;
import com.hxl.test_moreload.DividerItemDecoration;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.DBHelper;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.DownLoadAsyncTask;
import com.hxl.test_moreload.OrderFragment.Util.Tooljson;
import com.hxl.test_moreload.R;
import com.hxl.test_moreload.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

//定义抽象---不确定
    public abstract class   BaseFragment  extends Fragment {

        //公共属性
        protected RecyclerView mRecyclerView;
        protected List<CategoryBean> mCategoryBean = new ArrayList<>();
        protected List<CategoryBean>mCategoryTemp=new ArrayList<>();
        protected CategoryAdapter mCategoryAdapter;
        protected View headerView;
        protected Handler handler = new Handler();

        protected  String path;

        int  posindex;

        //修改属性----构造函数进行复制
        public int pageLayout;


        public  View  onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = View.inflate(getActivity(),pageLayout, null);

            Log.i("我的页面","completeorder1_main");
            mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
            manager = new LinearLayoutManager(getActivity());


            LoadRecycleViewclass();
            initRecyclerView();
            return view;
        }

        boolean isLoading;
        LinearLayoutManager manager=null;


    private void LoadRecycleViewclass() {
       /*   1.请求数据
        2.拿服务器的数据进行事件比较，看是否有新数据过来
        3.将数据保存到本地的数据库（如果数据过多，看是否需要放在其他的table） */

       /*
       异步加载网页数据
        */
       Log.i("path",path);
        new DownLoadAsyncTask(getActivity()).execute(path);

        mCategoryBean=Tooljson.JsonParse(getContext());
        QueryData(new DBHelper(getActivity()));
    }
    //查询数据
    public void QueryData(DBHelper dbHelper) {


        CategoryBeanDAO dao = new CategoryBeanDAO(dbHelper);

       Log.i("path",  dao.queryDB(DBHelper.COMPELETE_ORDER_TABLE_NAME).size()+"数据库数据长度");
    }

        public void initRecyclerView() {

            //出现bughttps://blog.csdn.net/lovexieyuan520/article/details/50537846
            /*  manager = new LinearLayoutManager(getActivity());*/


            manager=new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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

                mCategoryAdapter = new CategoryAdapter(mCategoryTemp);
            }else
            {

                mCategoryAdapter = new CategoryAdapter(mCategoryBean);
            }
            mRecyclerView.setAdapter(mCategoryAdapter);

            /*
            点击判断是哪一列
             */
            mCategoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position, CategoryBean categoryBean) {
                    Toast.makeText(getContext(), "我是第" + position + "项", Toast.LENGTH_SHORT).show();
                    posindex=position;
                    /*CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(getContext()) );
                    Toast.makeText(getContext(), "公有数据" + dao.queryDB(DBHelper.SWEEP_CODE_ORDER_TABLE_NAME).size() + "条", Toast.LENGTH_SHORT).show();  ;*/

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

    }

