package com.hxl.test_moreload.OrderFragment.Goods;

/**
 * Created by renren on 2016/9/20.
 */
public class CategoryBean {



    private  int _id;

    private  String orderNumber;
    private String oderType;
    private String itemPrice;
    private String platformDeduction;
    private String userPlay;
    private String storeEntry;
    private String playTime;

    public CategoryBean(String orderNumber, String oderType, String itemPrice, String platformDeduction, String userPlay, String storeEntry, String playTime) {
        this.orderNumber = orderNumber;
        this.oderType = oderType;
        this.itemPrice = itemPrice;
        this.platformDeduction = platformDeduction;
        this.userPlay = userPlay;
        this.storeEntry = storeEntry;
        this.playTime = playTime;
    }

    public CategoryBean() {

    }

    public int get_id() {   return _id; }

    public void set_id(int _id) {  this._id = _id;  }

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
}
