package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        cv.put(Data.sweepPay,listInfo.getSweepPay());

        Log.i("mypayStatus",listInfo.getPayStatus());
        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        /*writeDB.insert(DBHelper.SWEEP_CODE_ORDER_TABLE_NAME,null,cv);*/
        writeDB.insert(tableName,null,cv);
        writeDB.close();

    }
    void Insert()
    {
        ContentValues cv=new ContentValues();
        String sql="select SUM(storeEntry)  as sweepcodepay  from SweepCodeView GROUP BY date(playTime)";

        SQLiteDatabase db=dbHelper.getReadableDatabase();

    }

    /*
        删除一条数据
     */
    public void deletDB(CategoryBean listInfo){
        String whereCaluse="where _id=";
        String whereArgs[]=new String[]{String.valueOf(listInfo.get_id())};
        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        writeDB.delete(Data.COMPELETE_ORDER_TABLE_NAME,whereCaluse,whereArgs);
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
                    "payStatus"+ "="+status,null,null,null,ORDER_BY);
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
    /**
     * 时间字段的降序，采用date函数比较
     */
    public static final String COLUMN_DATE="playTime";
    public static final String ORDER_BY="date("+COLUMN_DATE+") desc";
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
        Cursor cursor;
        if(orderType.equals("扫码订单"))
        {
            cursor=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
                    new String[]{ }, "oderType=? or oderType=? and payStatus=? ", new String[]{orderType,"加价购订单","paid"},
                    null, null, ORDER_BY);
        }
        else   cursor = readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
                new String[]{ }, "oderType=? and  payStatus=? ", new String[]{orderType,"paid"},
                null, null, ORDER_BY);

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
        Cursor results = readDB.query(Data.COMPELETE_ORDER_TABLE_NAME, null,
                "payStatus" + "='" + payStatus+"'", null, null, null, ORDER_BY);
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
        Cursor results=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,new String[]{ "addpriceName"},"_id"+ "="+id,null,null,null,null);



        results=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME, new String[]{"addpriceName"}, "_id ="+id, null, null, null, null);
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
        String sql = "select count(*)from "+Data.COMPELETE_ORDER_TABLE_NAME;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }



    /**
     * 测试：统计每一天的总销量
     */
    public  List<DailyOrder> DailySales()
    {


      /* // String sql="select storeEntry from SweepCodeView GROUP BY date(playTime)";
        SQLiteDatabase readDB=dbHelper.getReadableDatabase();
        Cursor results=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,new String[]{ "SUM(storeEntry)"},Data.playTime+" < "+"date('now')",null,
                "date(playTime)",null,null);

        Cursor r2=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,new String[]{ "SUM(storeEntry)"},Data.playTime+" < "+"date('now')",null,
                "date(playTime)",null,null);
        Cursor r3=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,new String[]{ "SUM(storeEntry)"},Data.playTime+" < "+"date('now')",null,
                "date(playTime)",null,null);

        if(results.moveToFirst()){
            do{
                Log.i("cursor----","--------"+results.getString(0)+";"+
                        results.getString(results.getColumnIndex("SUM(storeEntry)")));
            }while(results.moveToNext());
        }*/
        SQLiteDatabase Db=dbHelper.getReadableDatabase();
        Cursor compelteCusor=resultCursor(Db,Data.VIEW_ALL_ORDER,new String[]{"date("+Data.playTime+")"},null);
        Cursor sweepcodeCursor=resultCursor(Db,Data.VIEW_SWEEPCODE);
        Cursor addCountCursor=resultCursor(Db,Data.VIEW_AddCount);
        Cursor comodityOrderCursor=resultCursor(Db,Data.VIEW_COMODITYORDER);
        Cursor commissionCursor=resultCursor(Db,Data.DETAILS_OF_COMMISSION);


        Map<String,List<String>>mdic=new HashMap<>();
        Cursor allOrderCusor=resultCursor(Db,Data.VIEW_ALL_ORDER,new String[]{"date("+Data.playTime+")"},null);

       Db.execSQL(DBHelper.CreatTempTable());  //创建一张临时表
        if(sweepcodeCursor.moveToFirst())
        {
            do{
                final String swwepcodepay=sweepcodeCursor.getString(0);
                Log.i("cursor1----","扫码" +sweepcodeCursor.getString(1) +":"+sweepcodeCursor.getString(0));
                Db.insert(Data.TEMPORDERDAILY_TABLE_NAME,null,ValuesTransform.inserIntoTemp(
                        sweepcodeCursor.getString(1),Data.sweepPay,sweepcodeCursor.getString(0)));

            }while (sweepcodeCursor.moveToNext());
        }

        if(addCountCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=addCountCursor.getString(0);
                Log.i("cursor1----","加价购" +addCountCursor.getString(1) +":"+addCountCursor.getString(0));
                Db.insert(Data.TEMPORDERDAILY_TABLE_NAME,null,ValuesTransform.inserIntoTemp(
                        addCountCursor.getString(1),Data.addpriceAmount,addCountCursor.getString(0)));
            }while (addCountCursor.moveToNext());

        }

        if(comodityOrderCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=comodityOrderCursor.getString(0);

                Db.insert(Data.TEMPORDERDAILY_TABLE_NAME,null,ValuesTransform.inserIntoTemp(
                        comodityOrderCursor.getString(1),Data.comdityOrder,comodityOrderCursor.getString(0)));

            }while (comodityOrderCursor.moveToNext());
        }
        if(commissionCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=commissionCursor.getString(0);
                Log.i("cursor1----","佣金" +commissionCursor.getString(1) +":"+commissionCursor.getString(0));
                Db.insert(Data.TEMPORDERDAILY_TABLE_NAME,null,ValuesTransform.inserIntoTemp(
                        commissionCursor.getString(1),Data.comissionOrder,commissionCursor.getString(0)));
            }while (commissionCursor.moveToNext());
        }
        List<DailyOrder> dailyOrderList=new ArrayList<>();

        /**
         * 对临时表进行整合
         */
        Cursor tempSummary=resultEndCursor(Db,Data.TEMPORDERDAILY_TABLE_NAME);
        if(tempSummary.moveToFirst())
        {
            do{
                DailyOrder dailyOrder=new DailyOrder();
                dailyOrder.setPlayTime(tempSummary.getString(0));
                dailyOrder.setSweepPay(tempSummary.getString(1));
                dailyOrder.setAddpriceAmount(tempSummary.getString(2));
                dailyOrder.setComdityOrder(tempSummary.getString(3));
                dailyOrder.setComissionOrder(tempSummary.getString(4));
                dailyOrder.setEntryValue(tempSummary.getString(5));
                dailyOrderList.add(dailyOrder);

                Log.i("my-----------",tempSummary.getString(0)+";"+tempSummary.getString(1)
                +tempSummary.getString(2)+";"+tempSummary.getString(3)+
                        tempSummary.getString(4)+";"+tempSummary.getString(5));
            }while (tempSummary.moveToNext());
        }
        Log.i("my-----------","nodata");

        /*if(sweepcodeCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=sweepcodeCursor.getString(0);
                Log.i("cursor1----","扫码" +sweepcodeCursor.getString(1) +":"+sweepcodeCursor.getString(0));
                values.add(sweepcodeCursor.getString(0)+"-"+OrderName.SweepCode);
                mdic.put(sweepcodeCursor.getString(1),values);

            }while (sweepcodeCursor.moveToNext());
        }

        if(addCountCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=addCountCursor.getString(0);
                Log.i("cursor1----","加价购" +addCountCursor.getString(1) +":"+addCountCursor.getString(0));
                values.add(addCountCursor.getString(0)+"-"+OrderName.AddCountOrder);
                mdic.put(addCountCursor.getString(1),values);
            }while (addCountCursor.moveToNext());

        }

        if(comodityOrderCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=comodityOrderCursor.getString(0);
                Log.i("cursor1----","商城" +comodityOrderCursor.getString(1) +":"+comodityOrderCursor.getString(0));
                values.add(comodityOrderCursor.getString(0)+"-"+OrderName.ComdityOrder);
                mdic.put(comodityOrderCursor.getString(1),values);
            }while (comodityOrderCursor.moveToNext());
        }
        if(commissionCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=commissionCursor.getString(0);
                Log.i("cursor1----","佣金" +commissionCursor.getString(1) +":"+commissionCursor.getString(0));
                values.add(commissionCursor.getString(0)+"-"+OrderName.CommissionEntry);
                mdic.put(commissionCursor.getString(1),values);
            }while (commissionCursor.moveToNext());
        }*/


        /*if(compelteCusor.moveToFirst()){
            do{
                Double sumEntry=0.0;
                DailyOrder dailyOrder=new DailyOrder();
                List<String>values=new ArrayList<>();
                Log.i("cursor1----","--------" +compelteCusor.getString(0));
                if(sweepcodeCursor.moveToNext())
                {
                    final String swwepcodepay=sweepcodeCursor.getString(0);
                    dailyOrder.setSweepPay(sweepcodeCursor.getString(0));
                    sumEntry=Tooljson.addthree(sumEntry.toString(),sweepcodeCursor.getString(0));

                    Log.i("cursor1----","扫码" +sweepcodeCursor.getString(1) +":"+sweepcodeCursor.getString(0));
                    values.add(sweepcodeCursor.getString(0)+"-扫码");
                 mdic.put(sweepcodeCursor.getString(1),values);

                }else
                    dailyOrder.setSweepPay("0");

                if(addCountCursor.moveToNext()) {
                    final String addcountpay=addCountCursor.getString(0);
                    dailyOrder.setAddpriceAmount(addCountCursor.getString(0));
                    sumEntry=Tooljson.addthree(sumEntry.toString(),addCountCursor.getString(0));
                    Log.i("cursor2----","-加价购" +addCountCursor.getString(1)  +":"+addCountCursor.getString(0));

                    values.add(addcountpay+"-加价购" );
                    mdic.put(addCountCursor.getString(1),values);

                }else
                    dailyOrder.setAddpriceAmount("0");
                if(comodityOrderCursor.moveToNext()) {
                    final String comoditypay=comodityOrderCursor.getString(0);
                    dailyOrder.setComdityOrder(comodityOrderCursor.getString(0));
                    sumEntry=Tooljson.addthree(sumEntry.toString(),comodityOrderCursor.getString(0)) ;
                    Log.i("cursor3----","商城" +comodityOrderCursor.getString(1)  +":"+comodityOrderCursor.getString(0));

                    values.add(comoditypay+"-商城");
                    mdic.put(comodityOrderCursor.getString(1),values);
                }else
                    dailyOrder.setComdityOrder("0");
                if(commissionCursor.moveToNext()) {
                    final String commissionpay=commissionCursor.getString(0);
                    dailyOrder.setComissionOrder(commissionCursor.getString(0));
                    sumEntry=Tooljson.addthree(sumEntry.toString(),commissionCursor.getString(0)) ;

                    values.add(commissionpay+"-佣金");
                  mdic.put(commissionCursor.getString(1)+"---",values);
                   Log.i("cursor4----","佣金明细" +commissionCursor.getString(1) +":"+commissionCursor.getString(0));
                }else
                    dailyOrder.setComissionOrder("0");

                dailyOrder.setEntryValue(sumEntry.toString());
                dailyOrderList.add(dailyOrder);

            }while(compelteCusor.moveToNext());
        }else
        {
            Log.i("cursor1----"," No data" );
        }*/

       /* Iterator<Map.Entry<String,List<String>>> iter = mdic.entrySet().iterator();
        while(iter.hasNext()){

            Map.Entry<String,List<String>> entry = iter.next();
            String key = entry.getKey();
            DailyOrder dailyOrder=new DailyOrder();
            Log.i("myOrder","-----"+key);
            for(int i=0;i<mdic.get(key).size();i++)
            {
               *//* String value = entry.getKey().get(i);*//*
                String value = mdic.get(key).get(i);
                dailyOrder.setPlayTime(key);
               // Parse(dailyOrder,value);
                *//*dailyOrder.setSweepPay("1");
                dailyOrder.setAddpriceAmount("2");
                dailyOrder.setComdityOrder("3");
                dailyOrder.setComissionOrder("4");*//*

                String[]detali=value.split("-");
                orderName=OrderName.valueOf(detali[1]);
                Log.i("myOrder",detali[0]+":"+detali[1]);
                switch(orderName )
                {
                    case SweepCode:
                        dailyOrder.setSweepPay(detali[0]);
                        break;
                    case AddCountOrder:
                        dailyOrder.setAddpriceAmount(detali[0]);
                        break;
                    case ComdityOrder:
                        dailyOrder.setComdityOrder(detali[0]);
                        break;
                    case CommissionEntry:
                        dailyOrder.setComissionOrder(detali[0]);
                        break;
                }

                System.out.println("----"+key+" "+value);
            }

            dailyOrderList.add(dailyOrder);
        }
     */
        return  dailyOrderList;

        //select  playTime>=datetime('now','start of day','-7 day','weekday 1') AND playTime<datetime('now','start of day','+0 day','weekday 1') from completeOrder
    }


    /**
     * 返回查询的结果集
     * @param table
     * @return
     */
    public  Cursor resultCursor(SQLiteDatabase readDB,String table,String[]columns, String selection)
    {

       /* Cursor cursor=readDB.query(table,new String[]{ "SUM(storeEntry)"},Data.playTime+" < "+"date('now')",null,
                "date(playTime)",null,null);*/
        Cursor cursor=readDB.query(table,columns,Data.playTime+" < "+"date('now')",null,
                "date(playTime)",null,null);
        return cursor;
    }
    public  Cursor resultCursor(SQLiteDatabase readDB,String table)
    {
        Cursor cursor=readDB.query(table,new String[]{ "SUM(storeEntry)","date("+Data.playTime+")" },Data.playTime+" < "+"date('now')",null,
                "date(playTime)",null,null);
        return cursor;
    }

    /*
    获取最后的总结结果
     */
    public  Cursor resultEndCursor(SQLiteDatabase readDB,String table)
    {
        Cursor cursor=readDB.query(table,new String[]{ "date("+Data.playTime+")","SUM("+Data.sweepPay+")", "SUM("+Data.addpriceAmount+")",
                        "SUM("+Data.comdityOrder+")","SUM("+Data.comissionOrder+")",
                       "SUM(sweepPay)+SUM(addpriceAmount)+SUM(comdityOrder)+SUM(comissionOrder)" },Data.playTime+" < "+"date('now')",null,
                "date(playTime)",null,null);
        return cursor;
    }

}
