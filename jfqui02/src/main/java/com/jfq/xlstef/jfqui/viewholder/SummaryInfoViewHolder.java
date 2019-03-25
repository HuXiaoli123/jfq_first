package com.jfq.xlstef.jfqui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.jfq.xlstef.jfqui.R;


public class SummaryInfoViewHolder extends BaseViewHolder {



    public TextView playTime; //日期
    public TextView sweepPay;     //扫码支付
    public TextView addpriceAmount;   //加价购订单
    public TextView comdityOrder;//商城订单
    public TextView comissionOrder;      //佣金入账
    public TextView entryValue;       //入账总额
    public TextView storeName;      //门店名称

    public SummaryInfoViewHolder(View itemView) {
        super(itemView);
        playTime = (TextView) itemView.findViewById(R.id.item_time);
        sweepPay= (TextView) itemView.findViewById(R.id.item_sweepcodepay);
        addpriceAmount= (TextView) itemView.findViewById(R.id.item_addcount);

        comdityOrder=(TextView)itemView.findViewById(R.id.item_comdity);
        comissionOrder=(TextView)itemView.findViewById(R.id.item_commission);
        entryValue= (TextView) itemView.findViewById(R.id.item_info_amount);
        storeName=(TextView) itemView.findViewById(R.id.item_name);

    }
}
