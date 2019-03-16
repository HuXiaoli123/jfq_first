package com.jfq.xlstef.jfqui.OrderFragment.Goods;

/**
 * Created by renren on 2016/9/20.
 */
public class SweepCodeOrder {



    private  int _id;

    public SweepCodeOrder(String orderNumber, String goodName, String addCountshopping, String platformDeduction, String userPlay, String storeEntry, String playTime) {
        this.orderNumber = orderNumber;
        GoodName = goodName;
        this.addCountshopping = addCountshopping;
        this.platformDeduction = platformDeduction;
        this.userPlay = userPlay;
        this.storeEntry = storeEntry;
        this.playTime = playTime;
    }

    private  String orderNumber;

    private String GoodName;
    private String addCountshopping;

    private String platformDeduction;
    private String userPlay;
    private String storeEntry;
    private String playTime;

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

    public String getGoodName() {
        return GoodName;
    }

    public void setGoodName(String goodName) {
        GoodName = goodName;
    }

    public String getAddCountshopping() {
        return addCountshopping;
    }

    public void setAddCountshopping(String addCountshopping) {
        this.addCountshopping = addCountshopping;
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

    public SweepCodeOrder() {

    }

}
