package com.jfq.xlstef.jfqui.OrderFragment.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.R;

import java.util.ArrayList;
import java.util.List;

public class MallinfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public   enum OrderName
    {
        CompleteOrder("CompleteOrder"),
        ShopMallOrder("2"),
        SweepCode("3"),
        DetailCommission("4"),
        DailyOrder("5"),
        UnpayOrder("6");

        private String type;

        OrderName(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

    }
    private List<CategoryBean> mCategoryBeen = new ArrayList<>();
    private Context mContext;
    CategoryBeanDAO dao;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private LayoutInflater mLayoutInflater;


    private View mHeaderView;



    public   static CategoryAdapter.OrderName mOrdername= CategoryAdapter.OrderName.CompleteOrder;
    public   static boolean isMall;


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);

        //headerView.setVisibility( View.VISIBLE);

//        notifyItemChanged(1,1);
    }

    /*
    在什么位置显示什么模块，比如说  第一行，设为头部  中间部分：普通的表格   最后一行设置为尾部
     */
    //用来决定每一个Item对应的布局
    @Override
    public int getItemViewType(int position) {


        if (mHeaderView == null)
        {
            Log.i("mytest_type", position+","+getItemCount());
            Log.i("mytest_type1", "null");
            return TYPE_NORMAL;
        }
        if (position == 0) {
            Log.i("mytest_type1", "TYPE_HEADER");
            return TYPE_HEADER;
        }
        if (mHeaderView != null && position + 1 == getItemCount() ) {
            Log.i("mytest_type1", "TYPE_FOOTER");
            return TYPE_FOOTER;
        }
        if (mHeaderView == null && position == getItemCount()){
            Log.i("mytest_type1", "TYPE_FOOTER");
            return TYPE_FOOTER;
        }

        Log.i("mytest_type11", "TYPE_NORMAL");
        return TYPE_NORMAL;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public MallinfoAdapter(List<CategoryBean> categoryBeen, Context context) {
        this.mCategoryBeen = categoryBeen;
        this.mContext=context;
    }


    //用来创建ViewHolder，用来避免错位问题和提高加载效率
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i("viewType", viewType+"");
        mLayoutInflater=LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new MallinfoAdapter.ViewHolder(mHeaderView);
            case TYPE_NORMAL:
                return new MallinfoAdapter.ViewHolder(mLayoutInflater.inflate(R.layout.category_item_layout,parent,false));
            case TYPE_FOOTER:
                return new MallinfoAdapter.FooterViewHolder(mLayoutInflater.inflate(R.layout.item_foot,parent,false));
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

    //用来添加相关逻辑处理和UI数据界面的显示
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position, List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        if (holder instanceof MallinfoAdapter.ViewHolder) {
            MallinfoAdapter.ViewHolder holder1 = (MallinfoAdapter.ViewHolder) holder;
          /*  if (pos == mCategoryBeen.size()) {
                return;
            }*/
            CategoryBean categoryBean = mCategoryBeen.get(pos);

            holder1.orderNumber.setText(categoryBean.getOrderNumber());
            //支付时间
            holder1.playTime.setText(categoryBean.getPlayTime());
            holder1.platformDeduction.setText(categoryBean.getPlatformDeduction());
            holder1.userPlay.setText(categoryBean.getUserPlay());
            holder1.storeEntry.setText(categoryBean.getStoreEntry());

            holder1.oderType.setText(categoryBean.getNameOfCommodity());
            holder1.itemPrice.setText(categoryBean.getItemPrice());
            Log.i("order_pag","ShopMallOrder");

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }



    //动态决定视图中显示的item个数
    @Override
    public int getItemCount() {

        return mHeaderView == null ? mCategoryBeen.size() + 1 : mCategoryBeen.size() + 2;

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView  orderNumber;
        private TextView  oderType;
        private TextView  itemPrice;
        private TextView  platformDeduction;
        private TextView  userPlay;
        private TextView storeEntry;
        private TextView playTime;
        private TextView  payStatus;

        private ImageView mMoreDetail;
        private LinearLayout addCountpage;
        public ViewHolder(View itemView) {
            super(itemView);

            orderNumber = (TextView) itemView.findViewById(R.id.orderNumber);
            oderType= (TextView) itemView.findViewById(R.id.oderType);
            itemPrice= (TextView) itemView.findViewById(R.id.itemPrice);

            addCountpage=(LinearLayout)itemView.findViewById(R.id.addCountpage);
            mMoreDetail=(ImageView)itemView.findViewById(R.id.addCount);

            platformDeduction= (TextView) itemView.findViewById(R.id.platformDeduction);
            userPlay = (TextView) itemView.findViewById(R.id.userPlay);
            storeEntry= (TextView) itemView.findViewById(R.id.storeEntry);
            playTime = (TextView) itemView.findViewById(R.id.playTime);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


    private MallinfoAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(MallinfoAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, CategoryBean categoryBean);
    }
}
