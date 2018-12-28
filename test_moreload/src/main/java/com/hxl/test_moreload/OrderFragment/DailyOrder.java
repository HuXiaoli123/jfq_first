package com.hxl.test_moreload.OrderFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxl.test_moreload.CategoryAdapter;
import com.hxl.test_moreload.R;

public class DailyOrder  extends BaseFragment {

    //初始化特有数据
    public  DailyOrder()
    {
        pageLayout= R.layout.dailyoder5;


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCategoryAdapter=new CategoryAdapter(false,true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
