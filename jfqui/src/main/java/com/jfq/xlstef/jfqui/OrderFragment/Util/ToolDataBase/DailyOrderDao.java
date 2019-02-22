package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


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
