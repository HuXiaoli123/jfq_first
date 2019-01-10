package com.hxl.test_moreload;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxl.test_moreload.OrderFragment.Goods.CategoryBean;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.DBHelper;

import java.util.ArrayList;
import java.util.List;

/*
划线的路径;E:\Picture\Pic\appcollections\MPAndroidChart-master
 */


/**
 * Created by renren on 2016/9/20.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


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

    protected static boolean mSweepCode;
    protected static boolean mDailyOrder;

    public   static OrderName mOrdername=OrderName.CompleteOrder;


    public CategoryAdapter(boolean sweepCode,boolean dailyOrder){
        mSweepCode=sweepCode;
        mDailyOrder=dailyOrder;
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

        Log.i("mytype", position+","+mHeaderView+","+getItemCount());
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        if (mHeaderView != null && position + 1 == getItemCount() ) return TYPE_FOOTER;
        if (mHeaderView == null && position == getItemCount()) return TYPE_FOOTER;


        return TYPE_NORMAL;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public CategoryAdapter(List<CategoryBean> categoryBeen,Context context) {
        this.mCategoryBeen = categoryBeen;
        this.mContext=context;
    }


    int count;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        mLayoutInflater=LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new ViewHolder(mHeaderView);
            case TYPE_NORMAL:
               /* if(count>mCategoryBeen.size())  {
                    Log.i("mycount","erro");
                    return new ViewHolder(mLayoutInflater.inflate(R.layout.errorlayout,parent,false));
                }*/
                return new ViewHolder(mLayoutInflater.inflate(R.layout.category_item_layout,parent,false));
            case TYPE_FOOTER:
                return new FooterViewHolder(mLayoutInflater.inflate(R.layout.item_foot,parent,false));
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
        if (holder instanceof ViewHolder) {
            ViewHolder holder1 = (ViewHolder) holder;
            if (pos == mCategoryBeen.size()) {
                return;
            }
            CategoryBean categoryBean = mCategoryBeen.get(pos);
            holder1.orderNumber.setText(categoryBean.getOrderNumber());
          //支付时间
            holder1.playTime.setText(categoryBean.getPlayTime());

            switch (mOrdername)
            {
                case  CompleteOrder:
                    holder1.oderType.setText(categoryBean.getOderType());
                    holder1.itemPrice.setText(categoryBean.getItemPrice());
                    break;
                case ShopMallOrder: //商城订单暂时不写
                    holder1.oderType.setText(categoryBean.getNameOfCommodity());
                    holder1.itemPrice.setText(categoryBean.getItemPrice());
                    break;
                case SweepCode:  //扫码订单
                    holder1.oderType.setText(categoryBean.getItemPrice());
                    if(!"0.0".equals(categoryBean.getAddpriceAmount()))
                    {
                        holder1.itemPrice.setText(categoryBean.getAddpriceAmount());
                        holder1.mMoreDetail.setVisibility(View.VISIBLE);
                        dao=new CategoryBeanDAO(new DBHelper(mContext));


                        //给加价购提供点击事件
                        ((ViewHolder) holder).addCountpage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Log.i("MySweepCode","我是加价购");
                                if (mOnItemClickListener != null) {
                                    mOnItemClickListener.OnItemClick(v, pos, mCategoryBeen.get(pos));
                                    Log.i("MySweepCode","我是加价购"+pos+","+position);
                                    String addPriceName=dao.queryById(pos+1);//因为数据库中的id是从1开始的
                                    Log.i("path","我是加价购"+pos+","+position);
                                    String[] allPriceName=addPriceName.split("-");

                                    //弹出加价购的详细信息
                                    DetailMsg(allPriceName);


                                }
                            }
                        });
                    }else
                    {
                        holder1.itemPrice.setText("无");
                    }

                    break;
                case DetailCommission:
                    holder1.oderType.setText(categoryBean.getOderType());
                    holder1.itemPrice.setText(categoryBean.getItemPrice());
                case DailyOrder:
                    holder1.playTime.setVisibility(View.GONE);
                    break;
                case UnpayOrder:
                    holder1.oderType.setText(categoryBean.getOderType());
                    holder1.itemPrice.setText(categoryBean.getItemPrice());
                    default:
                        break;

            }

            holder1.platformDeduction.setText(categoryBean.getPlatformDeduction());
            holder1.userPlay.setText(categoryBean.getUserPlay());
            holder1.storeEntry.setText(categoryBean.getStoreEntry());
           /* if(!mDailyOrder)
            holder1.playTime.setText(categoryBean.getPlayTime());
            else
                holder1.playTime.setVisibility(View.GONE);*/

           //这一行可以不需要：这是为所有的Item添加点击事件
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemClick(view, pos, mCategoryBeen.get(pos));
                    }
                }
            });
        }

    }

    //暂时不需要
    protected void study2(AlertDialog.Builder builder) {
        builder.setTitle("标题");
        builder.setView(new EditText(mContext));
        builder.setPositiveButton("确定", null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("简单消息框");
        builder.show();
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
//        if (getItemViewType(position) == TYPE_HEADER) return;
//        final int pos = getRealPosition(holder);
//        if (holder instanceof ViewHolder) {
//            ViewHolder holder1 = (ViewHolder) holder;
//            if (pos == mCategoryBeen.size()) {
//                return;
//            }
//            CategoryBean categoryBean = mCategoryBeen.get(pos);
//            holder1.mCategoryDes.setText(categoryBean.getCategory_des());
//            holder1.mCategoryTitle.setText(categoryBean.getCategory_title());
//            holder1.mCategoryImg.setBackgroundResource(categoryBean.getImgUrl());
//            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.OnItemClick(view, pos, mCategoryBeen.get(pos));
//
//                    }
//                }
//            });
//        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

   /* //    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        if (getItemViewType(position) == TYPE_HEADER) return;
//        final int pos = getRealPosition(holder);
//        if (holder instanceof ViewHolder) {
//            CategoryBean categoryBean = mCategoryBeen.get(pos);
//            holder.mCategoryDes.setText(categoryBean.getCategory_des());
//            holder.mCategoryTitle.setText(categoryBean.getCategory_title());
//            holder.mCategoryImg.setBackgroundResource(categoryBean.getImgUrl());
//            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mOnItemClickListener != null){
//                        mOnItemClickListener.OnItemClick(view, pos, mCategoryBeen.get(pos));
//
//                    }
//                }
//            });
//        }
//
//    }*/
    @Override
    public int getItemCount() {
        if(mCategoryBeen.size()>17)
        return mHeaderView == null ? mCategoryBeen.size() + 1 : mCategoryBeen.size() + 2;
        return  mCategoryBeen.size() ;
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


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, CategoryBean categoryBean);
    }
}
