package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    /**
     * 数据库信息
     */
    public static final String DATABASE_NAME="mygetuitest01_DB";
    public static final int VERSION=1;

    public static final String LISTINFO_TABLE_NAME="listinfo_table";//语音表

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context){
        this(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       /* String compelteOrserSql="create table "+ Data.COMPELETE_ORDER_TABLE_NAME +"(_id integer primary key AUTOINCREMENT,"
                +"orderNumber text not null,"+"oderType text not null,"+"itemPrice text not null,"
                +"platformDeduction text not null,"+"userPlay text not null,"+"storeEntry text not null,"+
                "playTime date not null,"+"addpriceAmount text,"+"addpriceName text,"+"nameOfCommodity text," +"payStatus text)" ;*/
       //创建所有订单的表
        String compelteOrserSql="create table "+ Data.COMPELETE_ORDER_TABLE_NAME +"("+Data.COLUMN_id+" integer primary key AUTOINCREMENT,"+
                Data. orderNumber+" text,"+
                Data.  oderType+" text,"+
                Data. itemPrice+" text,"+
                Data.  platformDeduction+" text,"+
                Data. userPlay+" text,"+
                Data.  storeEntry+" text,"+
                Data. playTime+" time,"+
                Data. addpriceAmount+" text,"+
                Data. addpriceName+" text,"+
                Data. nameOfCommodity+" text,"+
                Data. payStatus+" text,"+
                Data. sweepPay+" text"
                +")";

        //创建佣金明细表
        String CREATE_DETAILOFCOMMISSION="create table "+ Data.DETAILS_OF_COMMISSION +"("+
                Data.COLUMN_id+" integer primary key autoincrement,"+
                Data.COLUMN_Number+" text,"+
                Data.COLUMN_Type+" text,"+
                Data.COLUMN_Price+" text,"+
                Data.COLUMN_platformDeduction+" text,"+
                Data.COLUMN_userPlay+" text,"+
                Data.COLUMN_Entry+" text,"+
                Data.COLUMN_playTime+" time"  +")";

       //创建每日订单表
        String CREATE_DailyOrder="create table "+ Data.ORDERDAILY_TABLE_NAME +"("+
                Data.COLUMN_id+" integer primary key autoincrement,"+
                Data.COLUMN_playTime+" text,"+
                Data.sweepPay+" text,"+
                Data.addpriceAmount+" text,"+
                Data.comdityOrder+" text,"+
                Data.comissionOrder+" text,"+
                Data.entryValue+" time"  +")";
        /*String CREATE_DailyOrder="create table "+ Data.ORDERDAILY_TABLE_NAME +" as select SUM(storeEntry) "+
                Data.sweepPay +" from "+Data.VIEW_SWEEPCODE +" GROUP BY date(playTime) ";*/

        //创建所有订单视图
        String CreateView_all=" create view "+Data.VIEW_ALL_ORDER+" as select "+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.COLUMN_id+","+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.oderType+","+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.itemPrice+","+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.platformDeduction+","+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.userPlay+","+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.storeEntry+","+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.playTime+" from "+
                Data.COMPELETE_ORDER_TABLE_NAME +" where "+
                Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.payStatus+" ='"+"paid"+"'";

        String yuyinsql="create table "+LISTINFO_TABLE_NAME+"(_id integer primary key AUTOINCREMENT,"+"timer date not null,"+
                "content text not null)" ;
        db.execSQL(compelteOrserSql);
        db.execSQL(CREATE_DETAILOFCOMMISSION);
       /* db.execSQL(DATA_VIEW_COMMISSION_DETAIL); //佣金明细View
        db.execSQL(DATA_VIEW_DAILY_ORDER); //每日订单*/
        db.execSQL(CreateView_all);
        db.execSQL(CreateViewSql(Data.VIEW_COMODITYORDER,"商城订单",Data.nameOfCommodity,Data.itemPrice));
        db.execSQL(CreateViewSql(Data.VIEW_SWEEPCODE,"扫码订单",Data.userPlay,Data.addpriceAmount));
        db.execSQL(CreateViewSql(Data.VIEW_AddCount,"加价购订单",Data.userPlay,Data.addpriceAmount));
        db.execSQL(CREATE_DailyOrder);//每日订单表
        db.execSQL(yuyinsql);
        /*db.execSQL(sweepcode_sql);
        db.execSQL(itemdetail);*/
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists "+DATABASE_NAME;
        db.execSQL(sql);
        this.onCreate(db);
    }

    public  static  String CreatTempTable()
    {
 /*
        创建一张临时表
         */
        //创建每日订单表
        String CREATE_DailyOrder="create table "+ Data.TEMPORDERDAILY_TABLE_NAME +"("+
                Data.COLUMN_id+" integer primary key autoincrement,"+
                Data.COLUMN_playTime+" text,"+
                Data.sweepPay+" text default '0',"+
                Data.addpriceAmount+" text default '0',"+
                Data.comdityOrder+" text default '0',"+
                Data.comissionOrder+" text default '0',"+
                Data.entryValue+" time default '0'"  +")";
        return  CREATE_DailyOrder;
    }
    /*
    打开数据库执行的操作
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    /**
     * 创建视图的同一方式
     * @param view_Name :订单的类型 商城订单 扫码/加价购
     * @param orderType :订单的类型 商城订单 扫码/加价购
     * @param item  商品名称 /扫码支付  ----视图的第三个参数
     * @param price  ----视图的第四个参数
     * @return
     */
     public  String CreateViewSql(String view_Name,String orderType,String item,String price)
     {
         String CreateView_Mode=" create view "+view_Name+" as select "+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.COLUMN_id+","+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.orderNumber+","+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+item+","+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+price+","+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.platformDeduction+","+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.userPlay+","+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.storeEntry+","+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.playTime+" from "+
                 Data.COMPELETE_ORDER_TABLE_NAME +" where "+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.oderType+" ='"+orderType+"'"+" and "+
                 Data.COMPELETE_ORDER_TABLE_NAME+"."+Data.payStatus+" ='"+"paid"+"'";
         return  CreateView_Mode;
     }


   /* *//** 创建视图: View_DailyOrder *//*
    private void createViewTransponders() {
        Data.DATA_VIEW_DAILY_ORDER = "create view "
                + Data.VIEW_NAME + " as select "
                + Data.COMPELETE_ORDER_TABLE_NAME + ".*" + ", "
                + Data.DETAILS_OF_COMMISSION + ".*" + " from "
                + Data.COMPELETE_ORDER_TABLE_NAME + ", "
                + Data.DETAILS_OF_COMMISSION + " where "

                + Data.DETAILS_OF_COMMISSION+ "."
                + Data.COLUMN_id+ " = "
                + Data.DETAILS_OF_COMMISSION + "._id";
    }*/

}
