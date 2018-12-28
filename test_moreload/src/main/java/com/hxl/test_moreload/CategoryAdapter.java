package com.hxl.test_moreload;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxl.test_moreload.OrderFragment.Goods.CategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renren on 2016/9/20.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryBean> mCategoryBeen = new ArrayList<>();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private LayoutInflater mLayoutInflater;


    private View mHeaderView;

    protected static boolean mSweepCode;
    protected static boolean mDailyOrder;

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

    @Override
    public int getItemViewType(int position) {

        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        if (mHeaderView != null && position + 1 == getItemCount() ) return TYPE_FOOTER;
        if (mHeaderView == null && position == getItemCount()) return TYPE_FOOTER;

        Log.i("mytype", position+","+mHeaderView+","+getItemCount());
        return TYPE_NORMAL;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public CategoryAdapter(List<CategoryBean> categoryBeen) {
        this.mCategoryBeen = categoryBeen;
    }


    int count;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ++count;
      //  Log.i("mytypt----------", ++count+","+mCategoryBeen.size());
        mLayoutInflater=LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new ViewHolder(mHeaderView);
            case TYPE_NORMAL:
                if(count>mCategoryBeen.size())  { Log.i("mycount","erro");  return new ViewHolder(mLayoutInflater.inflate(R.layout.errorlayout,parent,false));}
                return new ViewHolder(mLayoutInflater.inflate(R.layout.category_item_layout,parent,false));
            case TYPE_FOOTER:
                return new FooterViewHolder(mLayoutInflater.inflate(R.layout.item_foot,parent,false));
            default:
                return null;
        }

        /*if (mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
            Log.i("mytypt",  "TYPE_FOOTER)");
            return new FooterViewHolder(view);

        }else if(viewType==TYPE_HEADER)
        {
            Log.i("mytypt", ""+"TYPE_NORMAL");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_header, parent, false);
            return new FooterViewHolder(view);
        }
        Log.i("mytypt----------", (viewType)+"");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new ViewHolder(view);*/
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        if (holder instanceof ViewHolder) {
            ViewHolder holder1 = (ViewHolder) holder;
            if (pos == mCategoryBeen.size()) {
                return;
            }
            CategoryBean categoryBean = mCategoryBeen.get(pos);
            /*holder1.mCategoryDes.setText(categoryBean.getCategory_des());
            holder1.mCategoryTitle.setText(categoryBean.getCategory_title());
            holder1.mCategoryImg.setBackgroundResource(categoryBean.getImgUrl());*/

          if(mSweepCode)
          {
              Log.i("MySweepCode",mSweepCode+"");
          }

            if(mSweepCode){
                holder1.mMoreDetail.setVisibility(View.VISIBLE);

                ((ViewHolder) holder).addCountpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("MySweepCode","我是加价购");
                    }
                });

            }

            holder1.orderNumber.setText(categoryBean.getOrderNumber());
            holder1.oderType.setText(categoryBean.getOderType());
            holder1.itemPrice.setText(categoryBean.getItemPrice());
            holder1.platformDeduction.setText(categoryBean.getPlatformDeduction());
            holder1.userPlay.setText(categoryBean.getUserPlay());
            holder1.storeEntry.setText(categoryBean.getStoreEntry());
            if(!mDailyOrder)
            holder1.playTime.setText(categoryBean.getPlayTime());
            else
                holder1.playTime.setVisibility(View.GONE);

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

    //    @Override
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
//    }
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
