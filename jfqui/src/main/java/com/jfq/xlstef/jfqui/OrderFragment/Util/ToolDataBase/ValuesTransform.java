package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;


import android.content.ContentValues;
import android.database.Cursor;

import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.CommissionDetailOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;


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

    /**
     * 从DailyOrder生成ContentValues
     * @param daily
     * @return
     */
    public static ContentValues transformDailyValues(DailyOrder daily){
        ContentValues contentValues=new ContentValues();
        // contentValues.put(Data.COLUMN_id,detail.get_id());
        contentValues.put(Data.COLUMN_playTime,daily.getPlayTime());
        contentValues.put(Data.sweepPay,daily.getSweepPay());
        contentValues.put(Data.addpriceAmount,daily.getAddpriceAmount());
        contentValues.put(Data.comdityOrder,daily.getComdityOrder());
        contentValues.put(Data.comissionOrder,daily.getComissionOrder());
        contentValues.put(Data.entryValue,daily.getEntryValue());
        return  contentValues;
    }

    public  static  ContentValues inserIntoTemp(String date,String key, String value)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(Data.playTime,date);
        contentValues.put(key,value);
        return  contentValues;
    }

    /**
     * 从Cursor生成CommissionDetailOrder对象
     * @param cursor
     * @return
     */
    public static DailyOrder transformDailyOrder(Cursor cursor){
        DailyOrder detailOrder=new DailyOrder();
        detailOrder.set_id(cursor.getInt(cursor.getColumnIndex(Data.COLUMN_id)));
        detailOrder.setPlayTime(cursor.getString(cursor.getColumnIndex(Data.COLUMN_playTime)));
        detailOrder.setSweepPay(cursor.getString(cursor.getColumnIndex(Data.sweepPay)));
        detailOrder.setAddpriceAmount(cursor.getString(cursor.getColumnIndex(Data.addpriceAmount)));
        detailOrder.setComdityOrder(cursor.getString(cursor.getColumnIndex(Data.comdityOrder)));
        detailOrder.setComissionOrder(cursor.getString(cursor.getColumnIndex(Data.comissionOrder)));
        detailOrder.setEntryValue(cursor.getString(cursor.getColumnIndex(Data.entryValue)));
        return  detailOrder;
    }


}
