package com.jfq.xlstef.jfqui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jfq.xlstef.jfqui.OrderFragment.Adapter.CategoryAdapter;
import com.jfq.xlstef.jfqui.OrderFragment.BaseFragment;
import com.jfq.xlstef.jfqui.R;

//扫码加价购
public class MainPayinfoFragment extends BaseFragment {
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
		return inflater.inflate(R.layout.fragment_main_payinfo, container, false);
	}*/

	/*//初始化特有数据
	public  MainPayinfoFragment()
	{
		pageLayout= R.layout.sweep_code_layout3;
		//path="http://store.tuihs.com/store/orders/paid?page=0&size=10";
		CategoryAdapter.mOrdername=CategoryAdapter.OrderName.SweepCode;
	}*/



	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=View.inflate(getActivity(),R.layout.sweep_code_layout3, null);
		CategoryAdapter.mOrdername=CategoryAdapter.OrderName.SweepCode;
		super.initial(view);
		return view;
	}


}
