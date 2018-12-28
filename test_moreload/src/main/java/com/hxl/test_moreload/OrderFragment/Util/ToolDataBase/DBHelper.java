package com.hxl.test_moreload.OrderFragment.Util.ToolDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="mygetuitest01_DB";


    public static final String COMPELETE_ORDER_TABLE_NAME ="compelete_order_table";//商城订单----------------

    public static final String SWEEP_CODE_ORDER_TABLE_NAME ="sweepcode_order_table";//扫码订单----------------

    public static final String ITEM_DRTAIL_TABLE_NAME="itemdetail_table";//细节商品----------------

    public static final int VERSION=1;
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context){
        this(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("sql__onCreate","-----------创建表："+ SWEEP_CODE_ORDER_TABLE_NAME);


        String shopmallsql="create table "+ COMPELETE_ORDER_TABLE_NAME +"(_id integer primary key AUTOINCREMENT,"
                +"orderNumber text not null,"+"oderType text not null,"+"itemPrice text not null,"
                +"platformDeduction text not null,"+"userPlay text not null,"+"storeEntry text not null,"+
                "playTime date not null,"+"addpriceName text)" ;

        String sweepcode_sql="create table "+ SWEEP_CODE_ORDER_TABLE_NAME +"(_id integer primary key AUTOINCREMENT,"
                +"orderNumber text not null,"+"GoodName text not null,"+"addCountshopping text not null,"
                +"platformDeduction text not null,"+"userPlay text not null,"+"storeEntry text not null,"+
                "playTime date not null)" ;

        String itemdetail="create table "+ ITEM_DRTAIL_TABLE_NAME +"(_id integer primary key AUTOINCREMENT,"+"GoodName text not null)"  ;


        db.execSQL(shopmallsql);
        db.execSQL(sweepcode_sql);
        db.execSQL(itemdetail);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists "+DATABASE_NAME;
        db.execSQL(sql);
        Log.d("sql__","-----------更新数据库："+ SWEEP_CODE_ORDER_TABLE_NAME);
        this.onCreate(db);
    }
}
