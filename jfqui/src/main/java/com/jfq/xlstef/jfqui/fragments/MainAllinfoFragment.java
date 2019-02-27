package com.jfq.xlstef.jfqui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.BaseFragment;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.R;

import java.io.Serializable;
import java.util.List;


public class MainAllinfoFragment extends BaseFragment {
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
		return inflater.inflate(R.layout.fragment_main_allinfo, container, false);
	}*/
    String TAG="path";
    public  void myresearch()
    {
        Log.i("AAA_","MainAllinfoFragment");

        Intent intent = new Intent();
        intent.setClass(getActivity(), SerachActivity.class);
        intent.putExtra("orderName", 0);

        intent.putExtra("lstBean", (Serializable) mCategoryBean);

        startActivity(intent);
    }
    //初始化特有数据
    public  MainAllinfoFragment()
    {
        Log.i("myfragment","MainAllinfoFragment1");
       // pageLayout= R.layout.completeorder1_main;
        path="http://store.tuihs.com/store/orders?page=0&size=10";
       // CategoryAdapter.mOrdername=CategoryAdapter.OrderName.CompleteOrder;
        //path="https://blog.csdn.net/qq_37140150/article/details/86289071";
        /*path="https://blog.csdn.net/qq_37140150/article/details/85287751";
        path="file:///D:/A/OrderPrj/allOrder.html";*/


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=View.inflate(getActivity(),R.layout.completeorder1_main, null);
        CategoryAdapter.mOrdername=CategoryAdapter.OrderName.CompleteOrder;

        super.initial(view);
        return view;
    }


}
