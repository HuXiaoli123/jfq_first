package com.jfq.xlstef.jfqui.OrderFragment.Goods;

public abstract  class BasicOrder {

    public int _id;

    public String orderNumber; //订单编号
   // public String oderType;     //订单类型
    public String itemPrice;   //商品原价
    public String platformDeduction;//平台抵扣
    public String userPlay;      //用户实际支付
    public String storeEntry;    //门店入账
    public String playTime;       //支付时间
    //  public String addpriceName;   //加价购商品名称

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




}
