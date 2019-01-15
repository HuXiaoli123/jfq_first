package com.hxl.test_moreload.OrderFragment.Util.ToolDataBase;


import android.content.ContentValues;
import android.database.Cursor;

import com.hxl.test_moreload.OrderFragment.Goods.CommissionDetailOrder;
import com.hxl.test_moreload.OrderFragment.Goods.CompeleteOrder;

/**
 *  Create by${胡小丽} on 2019/1/12
 *   用途：
 *  各种对象转换的工具类
 */
public class ValuesTransform {


    /**
     * 从Cursor生成CommissionDetailOrder对象
     * @param cursor
     * @return
     */
    public static CommissionDetailOrder transformMessage(Cursor cursor){
        CommissionDetailOrder detailOrder=new CommissionDetailOrder();
        detailOrder.set_id(cursor.getInt(cursor.getColumnIndex(Data.COLUMN_id)));
        detailOrder.setOrderNumber(cursor.getString(cursor.getColumnIndex(Data.COLUMN_Number)));
        detailOrder.setCommissionType(cursor.getString(cursor.getColumnIndex(Data.COLUMN_Type)));
        detailOrder.setItemPrice(cursor.getString(cursor.getColumnIndex(Data.COLUMN_Price)));
        detailOrder.setPlatformDeduction(cursor.getString(cursor.getColumnIndex(Data.COLUMN_platformDeduction)));
        detailOrder.setUserPlay(cursor.getString(cursor.getColumnIndex(Data.COLUMN_userPlay)));
        detailOrder.setStoreEntry(cursor.getString(cursor.getColumnIndex(Data.COLUMN_Entry)));
        detailOrder.setPlayTime(cursor.getString(cursor.getColumnIndex(Data.COLUMN_playTime)));
        return  detailOrder;
    }


    /**
     * 从CommissionDetailOrder生成ContentValues
     * @param detail
     * @return
     */
    public static ContentValues transformContentValues(CommissionDetailOrder detail){
        ContentValues contentValues=new ContentValues();
       // contentValues.put(Data.COLUMN_id,detail.get_id());
        contentValues.put(Data.COLUMN_Number,detail.getOrderNumber());
        contentValues.put(Data.COLUMN_Type,detail.getCommissionType());
        contentValues.put(Data.COLUMN_Price,detail.getItemPrice());
        contentValues.put(Data.COLUMN_platformDeduction,detail.getPlatformDeduction());
        contentValues.put(Data.COLUMN_userPlay,detail.getUserPlay());
        contentValues.put(Data.COLUMN_Entry,detail.getStoreEntry());
        contentValues.put(Data.COLUMN_playTime,detail.getPlayTime());
        return  contentValues;
    }



}
