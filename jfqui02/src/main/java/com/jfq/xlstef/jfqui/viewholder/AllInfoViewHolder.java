package com.jfq.xlstef.jfqui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.R;


public class AllInfoViewHolder extends BaseViewHolder {
    public TextView code_text;//订单编号
    public TextView orderType_text;//订单类型
    public TextView totalFee_text;//支付金额（商品原价）
    public TextView salesAmount_text;//用户实付
    public TextView totalReduction_text;//优惠减免（减免合计）
    public TextView createTime_text;//订单支付时间
    public AllInfoViewHolder(View itemView) {
        super(itemView);
        code_text=itemView.findViewById(R.id.item_name);
        orderType_text=itemView.findViewById(R.id.item_baseinfo);
        totalFee_text=itemView.findViewById(R.id.item_info_amount);
        salesAmount_text=itemView.findViewById(R.id.item_userspay);
        totalReduction_text=itemView.findViewById(R.id.item_addcount);
        createTime_text=itemView.findViewById(R.id.item_time);
    }
}
