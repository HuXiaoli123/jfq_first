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

//商城订单
public class MainMallinfoFragment extends BaseFragment {
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



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=View.inflate(getActivity(),R.layout.shopmalloder2, null);
         //CategoryAdapter.mOrdername=CategoryAdapter.OrderName.ShopMallOrder;
        super.initial(view);
        return view;
    }
    @Override
    public View initpage() {
        View v=  View.inflate(getActivity(),R.layout.shopmalloder2, null);
        CategoryAdapter.mOrdername=CategoryAdapter.OrderName.ShopMallOrder;

        return v;
    }


}
