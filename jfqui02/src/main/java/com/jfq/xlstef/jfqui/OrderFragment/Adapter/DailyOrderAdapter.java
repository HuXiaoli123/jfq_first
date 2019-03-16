package com.jfq.xlstef.jfqui.OrderFragment.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.R;

import java.util.ArrayList;
import java.util.List;

public class DailyOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<DailyOrder> mCategoryBeen = new ArrayList<>();
    private Context mContext;
    CategoryBeanDAO dao;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private LayoutInflater mLayoutInflater;


    private View mHeaderView;

    public   static CategoryAdapter.OrderName mOrdername=CategoryAdapter.OrderName.CompleteOrder;

    public DailyOrderAdapter(){}

    public DailyOrderAdapter(List<DailyOrder> categoryBeen,Context context) {
        this.mCategoryBeen = categoryBeen;
        this.mContext=context;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);

        //headerView.setVisibility( View.VISIBLE);

//        notifyItemChanged(1,1);
    }

    /*
    在什么位置显示什么模块，比如说  第一行，设为头部  中间部分：普通的表格   最后一行设置为尾部
     */
    @Override
    public int getItemViewType(int position) {

        Log.i("mytype", position+","+getItemCount());
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        if (mHeaderView != null && position + 1 == getItemCount() ) return TYPE_FOOTER;
        if (mHeaderView == null && position == getItemCount()) return TYPE_FOOTER;


        return TYPE_NORMAL;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    int count;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        mLayoutInflater=LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new DailyOrderAdapter.ViewHolder(mHeaderView);
            case TYPE_NORMAL:
               /* if(count>mCategoryBeen.size())  {
                    Log.i("mycount","erro");
                    return new ViewHolder(mLayoutInflater.inflate(R.layout.errorlayout,parent,false));
                }*/
                return new DailyOrderAdapter.ViewHolder(mLayoutInflater.inflate(R.layout.dailyoder_item_layout,parent,false));
            case TYPE_FOOTER:
                return new DailyOrderAdapter.FooterViewHolder(mLayoutInflater.inflate(R.layout.item_foot,parent,false));
            default:
                return null;


        }


    }

    /**
     * 1.重写onBindViewHolder(VH holder, int position, List<Object> payloads)这个方法
     * <p>
     * 2.执行两个参数的notifyItemChanged，第二个参数随便什么都行，只要不让它为空就OK~，
     * 这样就可以实现只刷新item中某个控件了！！！
     * payload 的解释为：如果为null，则刷新item全部内容
     * 那言外之意就是不为空就可以局部刷新了~！
     *
     * @param holder  服用的holder
     * @param position  当前位置
     * @param payloads  如果为null，则刷新item全部内容  否则局部刷新
     */


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        if (holder instanceof DailyOrderAdapter.ViewHolder) {
            DailyOrderAdapter.ViewHolder holder1 = (DailyOrderAdapter.ViewHolder) holder;
            if (pos == mCategoryBeen.size()) {
                return;
            }
            DailyOrder categoryBean = mCategoryBeen.get(pos);

            //支付时间
            holder1.playTime.setText(categoryBean.getPlayTime());
            holder1.sweepPay.setText(categoryBean.getSweepPay());
            holder1.addpriceAmount.setText(categoryBean.getAddpriceAmount());
            holder1.comdityOrder.setText(categoryBean.getComdityOrder());
            holder1.comissionOrder.setText(categoryBean.getComissionOrder());
            holder1.entryValue.setText(categoryBean.getEntryValue());
        }

    }



    public  void DetailMsg( String []allPriceName)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(mContext).inflate(R.layout.addcountpage, null);
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


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


    @Override
    public int getItemCount() {
        if(mCategoryBeen.size()>17)
            return mHeaderView == null ? mCategoryBeen.size() + 1 : mCategoryBeen.size() + 2;
        return  mCategoryBeen.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView playTime; //日期
        private TextView sweepPay;     //扫码支付
        private TextView addpriceAmount;   //加价购订单
        private TextView comdityOrder;//商城订单
        private TextView comissionOrder;      //佣金入账
        private TextView entryValue;       //入账总额



        public ViewHolder(View itemView) {
            super(itemView);

            playTime = (TextView) itemView.findViewById(R.id.date);
            sweepPay= (TextView) itemView.findViewById(R.id.sweepCodepay);
            addpriceAmount= (TextView) itemView.findViewById(R.id.addCountOrder);

            comdityOrder=(TextView)itemView.findViewById(R.id.comodityOrder);
            comissionOrder=(TextView)itemView.findViewById(R.id.commissionEntry);
            entryValue= (TextView) itemView.findViewById(R.id.entryValue);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


    private CategoryAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, CategoryBean categoryBean);
    }
}
