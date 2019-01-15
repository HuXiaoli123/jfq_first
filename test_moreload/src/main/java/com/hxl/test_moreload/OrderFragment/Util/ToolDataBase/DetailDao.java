package com.hxl.test_moreload.OrderFragment.Util.ToolDataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hxl.test_moreload.OrderFragment.Goods.CommissionDetailOrder;

import java.util.ArrayList;
import java.util.List;

/*
佣金明细辅助查询类
 */
public class DetailDao  implements DAO<CommissionDetailOrder> {

    private Context context;
    private DBHelper dbHelper;

    public DetailDao(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(this.context);
    }


    @Override
    public List<CommissionDetailOrder> queryAll() {

        return queryAction(null,null);
    }

    @Override
    public List<CommissionDetailOrder> queryAction(String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<CommissionDetailOrder> list = null;
        try {
            sqLiteDatabase = this.dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(Data.DETAILS_OF_COMMISSION, null, selection, selectionArgs, null, null, Data.ORDER_BY);
            list = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(ValuesTransform.transformMessage(cursor));
                } while (cursor.moveToNext());
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

    /**
     * 插入数据到佣金明细表中
     * @param commissionDetailOrder
     */
    @Override
    public void insert(CommissionDetailOrder commissionDetailOrder) {

        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.dbHelper.getWritableDatabase();
            sqLiteDatabase.insert(Data.DETAILS_OF_COMMISSION,null,ValuesTransform.transformContentValues(commissionDetailOrder));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }

    }

    @Override
    public void update(CommissionDetailOrder commissionDetailOrder) {

    }
}
