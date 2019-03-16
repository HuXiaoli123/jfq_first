package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;

import java.util.ArrayList;
import java.util.List;

public class DailyOrderDao implements DAO<DailyOrder> {

    private Context context;
    private DBHelper dbHelper;

    public DailyOrderDao(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(this.context);
    }
    @Override
    public List<DailyOrder> queryAll() {

        return queryAction(null,null);
    }

    public  List<DailyOrder> queryByTimer( String StartTimer,String endTimer){

        String  addpriceName="";
        SQLiteDatabase readDB=dbHelper.getReadableDatabase();
       return  queryAction(Data.COLUMN_playTime+">=? and "+ "strftime('%Y-%m-%d',"+Data.COLUMN_playTime+")"+"<=?",new String[]{StartTimer,endTimer});

    }

    public List<DailyOrder>querByMonth(String StartTimer,String endTimer)
    {
        /*return queryAction("strftime('%Y-%m',"+Data.COLUMN_playTime+")"+">=? and "+ "strftime('%Y-%m',"+Data.COLUMN_playTime+")"+"<=?",
                new String[]{StartTimer,endTimer});*/
        Log.i("MyCorsor1",StartTimer+","+endTimer);
        return queryAction("strftime('%Y-%m',"+Data.COLUMN_playTime+")"+">=? and "+ "strftime('%Y-%m',"+Data.COLUMN_playTime+")"+"<=?",
                new String[]{StartTimer,endTimer},null);
    }
    public List <DailyOrder>  queryAction(String selection, String[] selectionArgs,String[]groupBy) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<DailyOrder> list = new ArrayList<>();
        try {
            sqLiteDatabase = dbHelper.getReadableDatabase();
           /* cursor = sqLiteDatabase.query(Data.ORDERDAILY_TABLE_NAME, new String[]{ "month("+Data.playTime+")","SUM("+Data.sweepPay+")", "SUM("+Data.addpriceAmount+")",
                    "SUM("+Data.comdityOrder+")","SUM("+Data.comissionOrder+")",
                    "SUM(sweepPay)+SUM(addpriceAmount)+SUM(comdityOrder)+SUM(comissionOrder)" }, selection, selectionArgs,
                    "month(playTime)", null, Data.ORDER_BY);*/

            cursor = sqLiteDatabase.query(Data.ORDERDAILY_TABLE_NAME, new String[]{ Data.COLUMN_id ,"strftime('%Y-%m',"+Data.COLUMN_playTime+")"+" as "+Data.COLUMN_playTime,"SUM("+Data.sweepPay+")", "SUM("+Data.addpriceAmount+")",
                            "SUM("+Data.comdityOrder+")","SUM("+Data.comissionOrder+")",
                            "SUM(entryValue)" }, selection, selectionArgs,
                    "strftime('%Y-%m',"+Data.COLUMN_playTime+")", null, Data.ORDER_BY);
            Log.i("MyCorsor",selection+","+selectionArgs);

            list=new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Log.i("MyCorsor",cursor.getString(0)+cursor.getString(1)+0);
                    list.add(ValuesTransform.transformDailyOrder(cursor));
                    Log.i("MyCorsor",cursor+"");
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }

        return list;
    }


    @Override
    public List <DailyOrder>  queryAction(String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<DailyOrder> list = new ArrayList<>();
        try {
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(Data.ORDERDAILY_TABLE_NAME, null, selection, selectionArgs, null, null, Data.ORDER_BY);
            list=new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(ValuesTransform.transformDailyOrder(cursor));
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }

        return list;
    }

    @Override
    public void delite() {

    }

    @Override
    public void insert(DailyOrder dailyOrder) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.dbHelper.getWritableDatabase();
            sqLiteDatabase.insert(Data.ORDERDAILY_TABLE_NAME,null,ValuesTransform.transformDailyValues(dailyOrder));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    @Override
    public void update(DailyOrder dailyOrder) {

    }


}
