package com.hxl.mytabledata;

/**
 * 商品信息
 * 作者：fly on 2016/8/24 0024 23:47
 * 邮箱：cugb_feiyang@163.com
 */
public class OrderGoods {

   private  String orderNumber;
    private String oderType;
    private String itemPrice;
    private String platformDeduction;
    private String userPlay;
    private String storeEntry;
    private String playTime;

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

    private String goodName;
    private String goodSn;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodSn() {
        return goodSn;
    }

    public void setGoodSn(String goodSn) {
        this.goodSn = goodSn;
    }
}


