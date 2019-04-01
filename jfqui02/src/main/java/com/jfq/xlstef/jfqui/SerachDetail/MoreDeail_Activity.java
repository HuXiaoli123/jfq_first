package com.jfq.xlstef.jfqui.SerachDetail;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.MainActivity;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.fragments.MainAllinfoFragment;
import com.jfq.xlstef.jfqui.fragments.MainCommissioninfoFragment;
import com.jfq.xlstef.jfqui.fragments.MainMallinfoFragment;
import com.jfq.xlstef.jfqui.fragments.MainPayinfoFragment;
import com.jfq.xlstef.jfqui.fragments.MainSummaryinfoFragment;

import org.w3c.dom.Text;

public class MoreDeail_Activity extends AppCompatActivity {

    private TextView orderNumber;
    private TextView oderType;
    private TextView itemPrice;
    private TextView platformDeduction;
    private TextView userPlay;
    private TextView storeEntry;
    private TextView payTimer;

    private  TextView seepcodedetail;
    private RelativeLayout layout_addcount;
   private   Class<?> lastCls;
   private int mSelectFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_moredetail_layout);
        Intent intentGet = getIntent();
        CategoryBean mCategoryBaen=(CategoryBean)intentGet.getSerializableExtra("beanData");
        initView();
        mSelectFrag=intentGet.getExtras().getInt("selectfrag");
        initData(mCategoryBaen,mSelectFrag);
    }

    private  void initView()
    {
        orderNumber=(TextView)findViewById(R.id.orderNumber);
        oderType=(TextView)findViewById(R.id.oderType);
        itemPrice=(TextView)findViewById(R.id.itemPrice);
        platformDeduction=(TextView)findViewById(R.id.platformDeduction);
        userPlay=(TextView)findViewById(R.id.userPlay);
        storeEntry=(TextView)findViewById(R.id.storeEntry);
        payTimer=(TextView)findViewById(R.id.payTimer);
        seepcodedetail=(TextView)findViewById(R.id.txt_arrow);
        layout_addcount=(RelativeLayout) findViewById(R.id.layout_addCount);

      /*  SpannableString spanText = new SpannableString("请输入需要查询内容");


    // 设置字体大小
        spanText.setSpan(new AbsoluteSizeSpan(20, true), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // 设置字体颜色
        spanText.setSpan(new ForegroundColorSpan(Color.WHITE), 0,
                spanText.length(),     Spannable.SPAN_INCLUSIVE_EXCLUSIVE);*/



    }

    private void initData(final CategoryBean mCategoryBaen ,int selectfrag)
    {
        orderNumber.setText("订单编号："+mCategoryBaen.getOrderNumber());
        Log.i("selectfrag",selectfrag+"");

        switch (selectfrag)
       {
           case 1:
               oderType.setText("订单类型："+mCategoryBaen.getOderType());

               platformDeduction.setText("平台抵扣： "+mCategoryBaen.getPlatformDeduction()+"元");
               userPlay.setText("用户支付："+mCategoryBaen.getUserPlay()+"元");
               storeEntry.setText("门店入账： "+mCategoryBaen.getStoreEntry()+"元");
               payTimer.setText("支付时间： "+mCategoryBaen.getPlayTime());

               if(mCategoryBaen.getOderType().equals("扫码+加价购"))
               {
                   itemPrice.setText("加价购："+mCategoryBaen.getAddpriceAmount()+"元");
                   seepcodedetail.setVisibility(View.VISIBLE);
                   Log.i("seepcodedetail",seepcodedetail.getVisibility()+";;;");
                   layout_addcount.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           CategoryBeanDAO  dao=new CategoryBeanDAO(new DBHelper(getApplicationContext()));
                           String addPriceName=dao.queryById(mCategoryBaen.get_id());//因为数据库中的id是从1开始的

                           String[] allPriceName=addPriceName.split("-");


                           //弹出加价购的详细信息
                           DetailMsg(allPriceName);
                       }
                   });
               }else
               {
                   itemPrice.setText("商品原价："+mCategoryBaen.getItemPrice()+"元");
               }

               lastCls=MainAllinfoFragment.class;
               break;
           case 2:
               oderType.setText("产品名称："+mCategoryBaen.getOderType() );
               itemPrice.setText("商品原价："+mCategoryBaen.getItemPrice()+"元");
               platformDeduction.setText("平台抵扣： "+mCategoryBaen.getPlatformDeduction()+"元");
               userPlay.setText("用户支付："+mCategoryBaen.getUserPlay()+"元");
               storeEntry.setText("门店入账： "+mCategoryBaen.getStoreEntry()+"元");
               payTimer.setText("支付时间： "+mCategoryBaen.getPlayTime());
               lastCls=MainMallinfoFragment.class;
               break;
           case 3:
               oderType.setText("扫码支付："+mCategoryBaen.getItemPrice()+"元");
               itemPrice.setText("加价购："+mCategoryBaen.getAddpriceAmount()+"元");
               platformDeduction.setText("平台抵扣： "+mCategoryBaen.getPlatformDeduction()+"元");
               userPlay.setText("用户支付："+mCategoryBaen.getUserPlay()+"元");
               storeEntry.setText("门店入账： "+mCategoryBaen.getStoreEntry()+"元");
               payTimer.setText("支付时间： "+mCategoryBaen.getPlayTime());

               if(!mCategoryBaen.getAddpriceAmount().equals("0.0")&&mCategoryBaen.getAddpriceAmount()!=null)
               {
                   seepcodedetail.setVisibility(View.VISIBLE);
                   layout_addcount.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           CategoryBeanDAO  dao=new CategoryBeanDAO(new DBHelper(getApplicationContext()));
                           String addPriceName=dao.queryById(mCategoryBaen.get_id());//因为数据库中的id是从1开始的

                           String[] allPriceName=addPriceName.split("-");


                           //弹出加价购的详细信息
                           DetailMsg(allPriceName);
                       }
                   });
               }

               lastCls=MainPayinfoFragment.class;

               break;
           case 5:
               lastCls=MainSummaryinfoFragment.class;
               break;


       }



    }
    /* 加价购细节对话框*/
    public  void DetailMsg( String []allPriceName)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(this).inflate(R.layout.addcountpage, null);
// 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
//这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final TextView et_Threshold = view.findViewById(R.id.edThreshold);

        Log.i("path",allPriceName.length+"");
        StringBuilder s=new StringBuilder();
        if(allPriceName!=null)
        {
            for(int i=0;i<allPriceName.length;i++)
            {
                Log.i("path",allPriceName[i]);
                s.append(allPriceName[i]).append("\n");
            }
        }

        et_Threshold.setText(s.toString());

        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认

                //写相关的服务代码

                //关闭对话框
                dialog.dismiss();
            }
        });


    }

    public void backActivity(View v)
    {
        // 这两句是放在点击返回控件触发的事件里面
        MoreDeail_Activity.this.setResult(mSelectFrag);
        finish();

    }


}
