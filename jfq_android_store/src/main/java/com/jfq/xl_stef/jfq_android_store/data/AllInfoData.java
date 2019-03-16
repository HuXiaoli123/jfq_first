package com.jfq.xl_stef.jfq_android_store.data;


import java.util.Date;

public class AllInfoData {
    private String orderType="";//订单类型
    private String code="";//订单编号
    private float totalFee;//支付金额（商品原价）
    private float totalReduction;//优惠减免（减免合计）
    private float totalBargain;//砍价减免
    private float jd_consumption;//花费积蛋
    private float jd_consumption_reduction;//积蛋减免
    private float sfq_consumption_reduction;//私房钱减免
    private float ttp_consumption_reduction;//第三方减免（=totalBargain+jd_consumption_reduction+sfq_consumption_reduction等）
    private float salesAmount;//用户实付
    private Date createTime;//订单支付时间

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
    }

    public float getTotalReduction() {
        return totalReduction;
    }

    public void setTotalReduction(float totalReduction) {
        this.totalReduction = totalReduction;
    }

    public float getTotalBargain() {
        return totalBargain;
    }

    public void setTotalBargain(float totalBargain) {
        this.totalBargain = totalBargain;
    }

    public float getJd_consumption() {
        return jd_consumption;
    }

    public void setJd_consumption(float jd_consumption) {
        this.jd_consumption = jd_consumption;
    }

    public float getJd_consumption_reduction() {
        return jd_consumption_reduction;
    }

    public void setJd_consumption_reduction(float jd_consumption_reduction) {
        this.jd_consumption_reduction = jd_consumption_reduction;
    }

    public float getSfq_consumption_reduction() {
        return sfq_consumption_reduction;
    }

    public void setSfq_consumption_reduction(float sfq_consumption_reduction) {
        this.sfq_consumption_reduction = sfq_consumption_reduction;
    }

    public float getTtp_consumption_reduction() {
        ttp_consumption_reduction=totalBargain+jd_consumption_reduction+sfq_consumption_reduction;
        return ttp_consumption_reduction;
    }



    public float getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(float salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
