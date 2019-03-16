package com.jfq.xlstef.jfqui.OrderFragment.Goods;

import java.io.Serializable;

public class DailyOrder  extends CategoryBean implements Serializable {
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public DailyOrder(String playTime) {
        this.playTime = playTime;
    }

    public DailyOrder() {
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getSweepPay() {
        return sweepPay;
    }

    public void setSweepPay(String sweepPay) {
        this.sweepPay = sweepPay;
    }

    public String getAddpriceAmount() {
        return addpriceAmount;
    }

    public void setAddpriceAmount(String addpriceAmount) {
        this.addpriceAmount = addpriceAmount;
    }

    public String getComdityOrder() {
        return comdityOrder;
    }

    public void setComdityOrder(String comdityOrder) {
        this.comdityOrder = comdityOrder;
    }

    public String getComissionOrder() {
        return comissionOrder;
    }

    public void setComissionOrder(String comissionOrder) {
        this.comissionOrder = comissionOrder;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    private  int _id;

    private  String playTime; //日期
    private String sweepPay;     //扫码支付
    private String addpriceAmount;   //加价购订单
    private String comdityOrder;//商城订单
    private String comissionOrder;      //佣金入账
    private String entryValue;       //入账总额

}
