package com.jfq.xlstef.jfqui.Message;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.jfq.xlstef.jfqui.R;

public class LoadListView extends ListView  implements AbsListView.OnScrollListener {

    View footer;//底部布局
    int totalitemCount ;//总数量
    int lastVisibleItem;//最后一个课件的item
    boolean isLoading;//正在加载；
    private Adapter adapter;

    ILoadListener iLoadListener;

    public LoadListView(Context context) {
        super(context);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
    /*
    添加底部加载提示布局到listview
     */
    private void initView(Context context)
    {
        //LayoutInflater 将底部布局添加进来
        LayoutInflater inflater=LayoutInflater.from(context);
        footer=inflater.inflate(R.layout.footer_layout,null);
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
        //初始化设置滚动监听
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem=firstVisibleItem+visibleItemCount;
        this.totalitemCount=totalItemCount;

        Log.i("totalitemCount",totalitemCount+","+firstVisibleItem+","+visibleItemCount);

    }
    //判断加载到界面最低端
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
           if(totalitemCount==lastVisibleItem&&scrollState==SCROLL_STATE_IDLE)
           {
               if(!isLoading) {
                   isLoading=true;
                   footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                   //加载更多数据
                   Log.i("iLoadListener",""+iLoadListener);
                     iLoadListener.onLoad();//因为MainActivy实现了这个接口，在mainActivity下面的重载的方法做剩余的事

               }
           }
    }

    /*
    加载完毕
     */
    public void  LoadComplete()
    {
            isLoading=false;
            footer.findViewById(R.id.load_layout).setVisibility(View.GONE);

    }


    //初始化接口
    public  void Setinterface(ILoadListener iLoadListener){

        this.iLoadListener=iLoadListener;
    }

    //加载更多数据的回调接口
    public interface ILoadListener{
        public void onLoad();
    }


}
