package com.jfq.xlstef.jfqui.OrderFragment.Goods;

import java.io.Serializable;

/**
 * Created by renren on 2016/9/20.
 */
public  class CategoryBean extends  BasicOrder implements Serializable {


    private  int _id;

    private  String orderNumber; //订单编号
    private String oderType;     //订单类型
    private String itemPrice;   //商品原价
    private String platformDeduction;//平台抵扣
    private String userPlay;      //用户实际支付
    private String storeEntry;    //门店入账
    private String playTime;       //支付时间
    private String addpriceAmount;  //加价购的价格
    private String addpriceName;   //加价购商品名称
    private  String nameOfCommodity; //商城订单物品名称
    private String payStatus;     //支付状态
    private String sweepPay;//扫码支付的价格

    //下面两行代码为了测试用的
    public  CategoryBean(){};
    public CategoryBean(String orderNumber, String oderType, String itemPrice, String platformDeduction, String userPlay, String storeEntry, String playTime, String addpriceAmount, String addpriceName, String nameOfCommodity, String payStatus) {
        this.orderNumber = orderNumber;
        this.oderType = oderType;
        this.itemPrice = itemPrice;
        this.platformDeduction = platformDeduction;
        this.userPlay = userPlay;
        this.storeEntry = storeEntry;
        this.playTime = playTime;
        this.addpriceAmount = addpriceAmount;
        this.addpriceName = addpriceName;
        this.nameOfCommodity = nameOfCommodity;
        this.payStatus = payStatus;
    }

    public CategoryBean(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public String getSweepPay() {
        return sweepPay;
    }

    public void setSweepPay(String sweepPay) {
        this.sweepPay = sweepPay;
    }

    public String getNameOfCommodity() {
        return nameOfCommodity;
    }

    public void setNameOfCommodity(String nameOfCommodity) {
        this.nameOfCommodity = nameOfCommodity;
    }


    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }




    public String getAddpriceAmount() {
        return addpriceAmount;
    }

    public void setAddpriceAmount(String addpriceAmount) {
        this.addpriceAmount = addpriceAmount;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOderType() {
        return oderType;
    }

    public void setOderType(String oderType) {
        this.oderType = oderType;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getPlatformDeduction() {
        return platformDeduction;
    }

    public void setPlatformDeduction(String platformDeduction) {
        this.platformDeduction = platformDeduction;
    }

    public String getUserPlay() {
        return userPlay;
    }

    public void setUserPlay(String userPlay) {
        this.userPlay = userPlay;
    }

    public String getStoreEntry() {
        return storeEntry;
    }

    public void setStoreEntry(String storeEntry) {
        this.storeEntry = storeEntry;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getAddpriceName() {
        return addpriceName;
    }

    public void setAddpriceName(String addpriceName) {
        this.addpriceName = addpriceName;
    }
}
