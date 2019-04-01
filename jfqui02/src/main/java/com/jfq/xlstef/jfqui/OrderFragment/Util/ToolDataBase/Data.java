package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;

public class Data {
    /**
     * 信息表，及其字段
     */

    public static  String USER_NUMBER="";
    public static final String COMPELETE_ORDER_TABLE_NAME="completeOrder"; //所有订单总表
    public static final String DETAILS_OF_COMMISSION ="DetailsOfCommission"; //佣金明细表
    public static  final String ORDERDAILY_TABLE_NAME="orderdaily";//每日订单表
    public static  final  String TEMPORDERDAILY_TABLE_NAME="temptable";



    public static final String COLUMN_id="_id";
    public static final String COLUMN_Number="oderNumber";
    public static final String COLUMN_Type="commissionType"; //佣金类型
    public static final String COLUMN_Price="itemPrice";
    public static final String COLUMN_platformDeduction="platformDeduction";//平台抵扣
    public static final String COLUMN_userPlay="userPlay";
    public static final String COLUMN_Entry="storeEntry";
    public static final String COLUMN_playTime="playTime";

    /*
    每日订单的字段
     */
    public static final String COLUMN_DAY="day";
    public static final String COLUMN_SWEEPCODE="sweepCode";
    public static final String COLUMN_ADDCOUNT="addCount";
    public static final String COLUMN_COMODITYORDER="comodityOrder";
    public static final String COLUMN_COMMISSIONENTRY="commissionEntry";
    public static final String COLUMN_SUMENTRY="sumEntry";

    /*
    所有订单的字段
     */
    public static final String orderNumber="orderNumber";
    public static final String oderType="oderType";
    public static final String itemPrice="itemPrice";
    public static final String platformDeduction="platformDeduction";
    public static final String userPlay="userPlay";
    public static final String storeEntry="storeEntry";
    public static final String playTime="playTime";
    public static final String addpriceAmount="addpriceAmount";
    public static final String addpriceName="addpriceName";
    public static final String nameOfCommodity="nameOfCommodity";
    public static final String payStatus="payStatus";
    public static final String sweepPay="sweepPay";
    public static  final  String itemQuantity="itemQuantity";

    //商城订单总值
    public static final String comdityOrder="comdityOrder";
    //佣金订单总值
    public static final String comissionOrder="comissionOrder";
    //入账总金额
    public static final String entryValue="entryValue";

    /*
    创建数据库视图
     */
    public  static String VIEW_NAME="DailyView";//数据库视图的名称
    public  static String VIEW_ALL_ORDER="allOrder";//数据库全部订单视图
    public  static String VIEW_COMODITYORDER="comodityOrder";//商城订单视图的名称
    public  static String VIEW_SWEEPCODE="SweepCodeView";//扫码订单视图的名称
    public  static String VIEW_AddCount="addCountView";//加价购视图的名称
    /*public  static String DATA_VIEW_DAILY_ORDER="create view "
            + Data.VIEW_NAME + " as select "
            + Data.COMPELETE_ORDER_TABLE_NAME + ".*" + ", "
            + Data.DETAILS_OF_COMMISSION + ".*" + " from "
            + Data.COMPELETE_ORDER_TABLE_NAME + ", "
            + Data.DETAILS_OF_COMMISSION + " where "

            + Data.DETAILS_OF_COMMISSION+ "."
            + Data.COLUMN_id+ " = "
            + Data.DETAILS_OF_COMMISSION + "._id";*/ //创建数据库视图的语句



           /* + " where "
            + Data.DETAILS_OF_COMMISSION+ "."
            + Data.COLUMN_id+ " = "
            + Data.DETAILS_OF_COMMISSION + "._id";*/



    /**
     * 时间字段的降序，采用date函数比较
     */
    public static final String ORDER_BY="date("+COLUMN_id+") desc";
    public static final String ORDER_BY_TIME_ASC ="date("+Data.playTime+") ASC";
    public static final String ORDER_BY_TIME_DESC ="date("+Data.playTime+") ASC";

    public  static  int page=0;
    public  static  String loadPath="http://store.tuihs.com/store/orders?page="+page+"&size=200";

}
