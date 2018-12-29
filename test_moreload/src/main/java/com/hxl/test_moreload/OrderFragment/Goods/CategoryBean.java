package com.hxl.test_moreload.OrderFragment.Goods;

/**
 * Created by renren on 2016/9/20.
 */
public class CategoryBean {


    private  int _id;

    private  String orderNumber; //订单编号
    private String oderType;     //订单类型
    private String itemPrice;   //商品原价
    private String platformDeduction;//平台抵扣
    private String userPlay;      //用户实际支付
    private String storeEntry;    //门店入账
    private String playTime;       //支付时间
    private String addpriceAmount;
    private String addpriceName;   //加价购商品名称


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
