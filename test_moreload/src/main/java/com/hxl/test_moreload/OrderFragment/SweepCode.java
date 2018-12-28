package com.hxl.test_moreload.OrderFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxl.test_moreload.CategoryAdapter;
import com.hxl.test_moreload.R;

public class SweepCode  extends BaseFragment {

    //初始化特有数据
    public  SweepCode()
    {
         pageLayout= R.layout.sweep_code_layout3;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mCategoryAdapter=new CategoryAdapter(true,false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
