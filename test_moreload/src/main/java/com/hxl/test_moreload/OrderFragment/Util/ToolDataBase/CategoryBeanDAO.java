package com.hxl.test_moreload.OrderFragment.Util.ToolDataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hxl.test_moreload.OrderFragment.Goods.CategoryBean;
import com.hxl.test_moreload.OrderFragment.Goods.CompeleteOrder;
import com.hxl.test_moreload.OrderFragment.Goods.SweepCodeOrder;
import com.hxl.test_moreload.OrderFragment.SweepCode;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryBeanDAO {
    DBHelper dbHelper=null;
    public CategoryBeanDAO(DBHelper dbHelper){
        this.dbHelper=dbHelper;
    }
    /*
        插入一条数据
     */
    public void insertDB(CategoryBean listInfo, String tableName){
        ContentValues cv=new ContentValues();

       // cv.put("_id",null);
        /*cv.put("orderNumber",listInfo.getOrderNumber());
        cv.put("GoodName",listInfo.getGoodName());
        cv.put("addCountshopping",listInfo.getPlatformDeduction());
        cv.put("platformDeduction",listInfo.getUserPlay());
        cv.put("userPlay",listInfo.getStoreEntry());
        cv.put("storeEntry",listInfo.getPlayTime());
        cv.put("playTime",listInfo.getPlayTime());*/


        cv.put("orderNumber",listInfo.getOrderNumber());
        cv.put("oderType",listInfo.getOderType());
        cv.put("itemPrice",listInfo.getItemPrice());
        cv.put("platformDeduction",listInfo.getPlatformDeduction());
        cv.put("userPlay",listInfo.getUserPlay());
        cv.put("storeEntry",listInfo.getStoreEntry());
        cv.put("playTime",listInfo.getPlayTime());
        cv.put("addpriceAmount",listInfo.getAddpriceAmount());
        cv.put("addpriceName",listInfo.getAddpriceName());
        cv.put("nameOfCommodity",listInfo.getNameOfCommodity());
        cv.put("payStatus",listInfo.getPayStatus());

        Log.i("mypayStatus",listInfo.getPayStatus());


        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        /*writeDB.insert(DBHelper.SWEEP_CODE_ORDER_TABLE_NAME,null,cv);*/
        writeDB.insert(tableName,null,cv);
        writeDB.close();
    }
    /*
        删除一条数据
     */
    public void deletDB(CategoryBean listInfo){
        String whereCaluse="where _id=";
        String whereArgs[]=new String[]{String.valueOf(listInfo.get_id())};
        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        writeDB.delete(DBHelper.COMPELETE_ORDER_TABLE_NAME,whereCaluse,whereArgs);
        writeDB.close();
    }
    /*
        修改一条数据
     */
    public void updateDB(CategoryBean listInfo){
        ContentValues cv =new ContentValues();
        cv.put("timer",listInfo.getOrderNumber());
        cv.put("content",listInfo.getOderType());
        String whereCaluse="where _id=";
        String whereArgs[]=new String[]{String.valueOf(listInfo.get_id())};
        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        writeDB.update(DBHelper.DATABASE_NAME,cv,whereCaluse,whereArgs);
        writeDB.close();
    }
    /*
        查询
     */
    public ArrayList queryDB(String tableName){
        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase();
        Cursor results=readDB.query(tableName,new String[]{"_id","orderNumber","oderType","itemPrice","platformDeduction","userPlay","storeEntry","playTime","addpriceAmount","addpriceName"},null,null,null,null,null);

        for(results.moveToFirst();!results.isAfterLast();results.moveToNext()){
            CategoryBean listInfo=new CategoryBean();
            listInfo.set_id(results.getInt(0));
            listInfo.setOrderNumber(results.getString(1));
            listInfo.setOderType(results.getString(2));
            listInfo.setItemPrice(results.getString(3));
            listInfo.setPlatformDeduction(results.getString(4));
            listInfo.setUserPlay(results.getString(5));
            listInfo.setStoreEntry(results.getString(6));
            listInfo.setPlayTime(results.getString(7));
            listInfo.setAddpriceAmount(results.getString(8));
            listInfo.setAddpriceName(results.getString(9));
            arrayList.add(listInfo);
        }
        return arrayList;
    }

    /*
       根据支付状态查询数据
    */
    public ArrayList queryDB(String tableName,String status){
        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase();
        Cursor results=null;

        try
        {
            results=readDB.query(tableName,new String[]{"_id","orderNumber","oderType","itemPrice","platformDeduction",
                    "userPlay","storeEntry","playTime","addpriceAmount","addpriceName","payStatus"},
                    "payStatus"+ "="+status,null,null,null,null);
        }catch (Exception e)
        {
            System.out.print(e.toString());
        }
        for(results.moveToFirst();!results.isAfterLast();results.moveToNext()){
            CategoryBean listInfo=new CategoryBean();
            listInfo.set_id(results.getInt(0));
            listInfo.setOrderNumber(results.getString(1));
            listInfo.setOderType(results.getString(2));
            listInfo.setItemPrice(results.getString(3));
            listInfo.setPlatformDeduction(results.getString(4));
            listInfo.setUserPlay(results.getString(5));
            listInfo.setStoreEntry(results.getString(6));
            listInfo.setPlayTime(results.getString(7));
            listInfo.setAddpriceAmount(results.getString(8));
            listInfo.setAddpriceName(results.getString(9));
            listInfo.setPayStatus(results.getString(10));
            arrayList.add(listInfo);
        }
        return arrayList;
    }

    /*
    查询商城订单并且不是未支付的订单
     */
    public  ArrayList findByOrderType(String orderType)
    {

        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase(); //USER_NAME + "='" + userName+"'"
       /* Cursor cursor = readDB.query(DBHelper.COMPELETE_ORDER_TABLE_NAME,
                null, "oderType" + "='" + ""+"'", null, null, null, null);*/

        /*Cursor cursor = rdb.query("user", new String[]{"name","phone"}, "name=?", new String[]{"zhangsan"}, null, null, "_id desc");*/
        /* 将数据库中数据倒序的取出*/
        Cursor cursor = readDB.query(DBHelper.COMPELETE_ORDER_TABLE_NAME,
                new String[]{ }, "oderType=? and  payStatus=?", new String[]{orderType,"paid"}, null, null, "_id desc");


        if (cursor != null) {

        }else
         {

         }
        Log.i("myadapter","findByOrderType"+cursor.getCount());
        return GetData(cursor);
    }

    private  ArrayList GetData(Cursor results)
    {
        ArrayList<CategoryBean>arrayList=new ArrayList<>();
        for(results.moveToFirst();!results.isAfterLast();results.moveToNext()){
            CategoryBean listInfo=new CategoryBean();
            listInfo.set_id(results.getInt(0));
            listInfo.setOrderNumber(results.getString(1));
            listInfo.setOderType(results.getString(2));
            listInfo.setItemPrice(results.getString(3));
            listInfo.setPlatformDeduction(results.getString(4));
            listInfo.setUserPlay(results.getString(5));
            listInfo.setStoreEntry(results.getString(6));
            listInfo.setPlayTime(results.getString(7));
            listInfo.setAddpriceAmount(results.getString(8));
            listInfo.setAddpriceName(results.getString(9));
            listInfo.setNameOfCommodity(results.getString(10));
           // Log.i("myadapter",results.getString(10));
            arrayList.add(listInfo);
        }
        results.close();
        return arrayList;
    }

    public ArrayList findOrderByName( String tableName,String payStatus) {
        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase(); //USER_NAME + "='" + userName+"'"
        Cursor results = readDB.query(DBHelper.COMPELETE_ORDER_TABLE_NAME, null, "payStatus" + "='" + payStatus+"'", null, null, null, null);
        /*if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }*/
        for(results.moveToFirst();!results.isAfterLast();results.moveToNext()){
            CategoryBean listInfo=new CategoryBean();
            listInfo.set_id(results.getInt(0));
            listInfo.setOrderNumber(results.getString(1));
            listInfo.setOderType(results.getString(2));
            listInfo.setItemPrice(results.getString(3));
            listInfo.setPlatformDeduction(results.getString(4));
            listInfo.setUserPlay(results.getString(5));
            listInfo.setStoreEntry(results.getString(6));
            listInfo.setPlayTime(results.getString(7));
            listInfo.setAddpriceAmount(results.getString(8));
            listInfo.setAddpriceName(results.getString(9));
            arrayList.add(listInfo);
        }
        results.close();
        return arrayList;

    }


    public String queryById(int  id){
        Log.d("name", "msg pid:"+id);
        String  addpriceName="";
        SQLiteDatabase readDB=dbHelper.getReadableDatabase();
        Cursor results=readDB.query(DBHelper.COMPELETE_ORDER_TABLE_NAME,new String[]{ "addpriceName"},"_id"+ "="+id,null,null,null,null);



        results=readDB.query(DBHelper.COMPELETE_ORDER_TABLE_NAME, new String[]{"addpriceName"}, "_id ="+id, null, null, null, null);
        if(results.moveToFirst()){
            do{
                addpriceName=results.getString(results.getColumnIndex("addpriceName"));
            }while(results.moveToNext());
        }
        Log.d("name", "msg pid:"+addpriceName);
        return addpriceName;
    }


    /**
     * 查询数据库中的总条数.
     * @return
     */
    public long allCaseNum(){
        String sql = "select count(*)from "+DBHelper.COMPELETE_ORDER_TABLE_NAME;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }

}
