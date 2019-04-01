package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.DailyOrder;

import java.util.ArrayList;
import java.util.List;

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
        cv.put(Data.itemQuantity,listInfo.getItemQuantity());

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
    public static final String ORDER_BY_ID="_id desc";
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
        Cursor cursor=null;
        if(orderType.equals("扫码订单"))
        {
            cursor=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
                    new String[]{ }, "oderType=? or oderType=? and payStatus=? ", new String[]{orderType,"加价购订单","paid"},
                    null, null, ORDER_BY);
        }
       else {
            cursor = readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
                    new String[]{}, "oderType=? and  payStatus=? ", new String[]{orderType, "paid"},
                    null, null, ORDER_BY);
        }


        Log.i("myadapter","findByOrderType"+cursor.getCount());
        return GetData(cursor);
    }


    /*
   查询商城订单并且不是未支付的订单
    */
    public  ArrayList findByOrderPay( )
    {
        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase(); //USER_NAME + "='" + userName+"'"


        /* 将数据库中数据倒序的取出*/
        Cursor  results=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
                    new String[]{ }, "oderType=? or oderType=? and payStatus=? ", new String[]{"扫码订单","扫码+加价购","paid" },
                    null, null, ORDER_BY_ID);
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
            listInfo.setAddpriceAmount(results.getString(8));//加价购价格·
            listInfo.setAddpriceName(results.getString(9));
            listInfo.setNameOfCommodity(results.getString(10));
            listInfo.setItemQuantity(results.getString(11));

            arrayList.add(listInfo);
        }
        results.close();
        return arrayList;
    }

    public  ArrayList findByOrderComdity()
    {
        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase(); //USER_NAME + "='" + userName+"'"


        /* 将数据库中数据倒序的取出*/
        Cursor results= readDB.query(Data.VIEW_COMODITYORDER,
                    new String[]{}, null, null,
                    null, null, ORDER_BY_ID);
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
            /*listInfo.setAddpriceAmount(results.getString(8));
            listInfo.setAddpriceName(results.getString(9));
            listInfo.setNameOfCommodity(results.getString(10));*/
            // Log.i("myadapter",results.getString(10));
            arrayList.add(listInfo);
        }
        results.close();
        return arrayList;
    }

    public  ArrayList findByOrderAll(String orderType)
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
                    new String[]{ }, "oderType=? or oderType=? and payStatus=? ", new String[]{orderType,"扫码+加价购","paid"},
                    null, null, ORDER_BY);
        }
        else {
            cursor = readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
                    new String[]{}, "oderType=? and  payStatus=? ", new String[]{orderType, "paid"},
                    null, null, ORDER_BY);
        }

        if (cursor != null) {

        }else
        {

        }
        Log.i("myadapter","findByOrderType"+cursor.getCount());
        return GetData(cursor);
    }

    public  ArrayList findByOrderComdity(String orderType)
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
                    new String[]{ }, "oderType=? or oderType=? and payStatus=? ", new String[]{orderType,"扫码+加价购","paid"},
                    null, null, ORDER_BY);
        }
        else {
            cursor = readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
                    new String[]{}, "oderType=? and  payStatus=? ", new String[]{orderType, "paid"},
                    null, null, ORDER_BY);
        }

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

    public ArrayList findOrderByAll( String tableName ) {
        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase(); //USER_NAME + "='" + userName+"'"
        Cursor results = readDB.query(tableName, null,
                null, null, null, null, ORDER_BY_ID);
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
            arrayList.add(listInfo);
        }
        results.close();
        return arrayList;

    }

    public ArrayList findOrderByComdity( String tableName ) {
        ArrayList<CategoryBean> arrayList=new ArrayList<>();
        SQLiteDatabase readDB=dbHelper.getReadableDatabase(); //USER_NAME + "='" + userName+"'"
        Cursor results = readDB.query(tableName, null,
                null, null, null, null, ORDER_BY);

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
            Log.i("mydatas",results.getString(results.getColumnIndex(Data.itemQuantity)));
            listInfo.setItemQuantity(results.getString(results.getColumnIndex(Data.itemQuantity)));
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
            Log.i("mydatas",results.getColumnName(results.getColumnIndex(Data.itemQuantity)));
            listInfo.setItemQuantity(results.getColumnName(results.getColumnIndex(Data.itemQuantity)));
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

    public ArrayList queryByTimer(String table,String StartTimer,String endTimer){

        String  addpriceName="";
        SQLiteDatabase readDB=dbHelper.getReadableDatabase();

      /*  Cursor cursor=readDB.query(table,new String[]{},Data.COLUMN_playTime+">=? and "+ "DATEDIFF(day,"+Data.COLUMN_playTime+","+endTimer+")"+"<=?",
                new String[]{StartTimer,"0"},null,null,null);*/
        Cursor cursor=readDB.query(table,new String[]{},Data.COLUMN_playTime+">=? and "+ "strftime('%Y-%m-%d',"+Data.COLUMN_playTime+")"+"<=?",
                new String[]{StartTimer,endTimer},null,null,"date("+COLUMN_DATE+") desc");
        return GetAllInfoData(cursor);
    }
    private  ArrayList GetAllInfoData(Cursor results)
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
            // Log.i("myadapter",results.getString(10));
            arrayList.add(listInfo);
        }
        results.close();
        return arrayList;
    }


    /**
     * 查询数据库中的总条数.:这里应该选择ViewAll
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

    public String LastCateanTimer(String table)
    {
        String sql = "select playTime from " +table+" order by _id desc LIMIT 1" ;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst())
        {
            Log.i("lllllll",cursor.getString(0)+"");
            return cursor.getString(0);
        }else
        {
            return  "0";
        }




    }


    /**
     * 查询数据库中的总条数.
     * @return
     */
    public long allCaseNumView(String View){
        String sql = "select count(*)from "+View;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }


    String mLastTime="";
    String mCurrentDate="";
    /**
     * 测试：统计每一天的总销量
     */
    public  List<DailyOrder> DailySales(String lastTime,String currentDate)
    {
        mLastTime=lastTime;
        mCurrentDate=currentDate;
        // mCurrentDate="2019-03-21";
        SQLiteDatabase Db=dbHelper.getReadableDatabase();
       // Cursor compelteCusor=resultCursor(Db,Data.VIEW_ALL_ORDER,new String[]{"date("+Data.playTime+")"},null);
        Cursor sweepcodeCursor=resultCursor(Db,Data.VIEW_SWEEPCODE,Data.storeEntry);
        Cursor addCountCursor=resultCursor(Db,Data.VIEW_AddCount,Data.addpriceAmount);
        Cursor comodityOrderCursor=resultCursor(Db,Data.VIEW_COMODITYORDER,Data.storeEntry);
        Cursor commissionCursor=resultCursor(Db,Data.DETAILS_OF_COMMISSION,Data.storeEntry);

       /* Map<String,List<String>>mdic=new HashMap<>();
        Cursor allOrderCusor=resultCursor(Db,Data.VIEW_ALL_ORDER,new String[]{"date("+Data.playTime+")"},null);*/



        String dropTemp="drop table if exists "+Data.TEMPORDERDAILY_TABLE_NAME;
        Db.execSQL(dropTemp);

        Db.execSQL(DBHelper.CreatTempTable());  //创建一张临时表



        if(sweepcodeCursor.moveToFirst())
        {
            do{
                final String swwepcodepay=sweepcodeCursor.getString(0);
                Log.i("mycursor1----","扫码" +sweepcodeCursor.getString(1) +":"+sweepcodeCursor.getString(0));
                Db.insert(Data.TEMPORDERDAILY_TABLE_NAME,null,ValuesTransform.inserIntoTemp(
                        sweepcodeCursor.getString(1),Data.sweepPay,sweepcodeCursor.getString(0)));

            }while (sweepcodeCursor.moveToNext());
        }

        if(addCountCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=addCountCursor.getString(0);
                Log.i("mycursor1----","扫码+加价购" +addCountCursor.getString(1) +":"+addCountCursor.getString(0));
                Db.insert(Data.TEMPORDERDAILY_TABLE_NAME,null,ValuesTransform.inserIntoTemp(
                        addCountCursor.getString(1),Data.addpriceAmount,addCountCursor.getString(0)));
            }while (addCountCursor.moveToNext());

        }

        if(comodityOrderCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String comidity=comodityOrderCursor.getString(0);
                Log.i("mycursor1----","商城订单" +comodityOrderCursor.getString(1) +":"+comidity);
                Db.insert(Data.TEMPORDERDAILY_TABLE_NAME,null,ValuesTransform.inserIntoTemp(
                        comodityOrderCursor.getString(1),Data.comdityOrder,comodityOrderCursor.getString(0)));

            }while (comodityOrderCursor.moveToNext());
        }
        if(commissionCursor.moveToFirst())
        {
            List<String>values=new ArrayList<>();
            do{
                final String swwepcodepay=commissionCursor.getString(0);
                Log.i("mycursor1----","佣金" +commissionCursor.getString(1) +":"+commissionCursor.getString(0));
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

                Log.i("my-----------",tempSummary.getString(0)+";"+tempSummary.getString(1)+","
                +tempSummary.getString(2)+";"+tempSummary.getString(3)+
                        tempSummary.getString(4)+";"+tempSummary.getString(5));
            }while (tempSummary.moveToNext());
        }
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


    /**Data.playTime+" < "+"date('now')"
     * selection:
     * @param readDB
     * @param table
     * @param storeEntry :因为在扫码订单中门店入账包括了扫码支付+加价购
     * @return
     */
    public  Cursor resultCursor(SQLiteDatabase readDB,String table,String storeEntry )
    {
      /*  Cursor cursor1=readDB.query(table,new String[]{},Data.COLUMN_playTime+">=? and "+ "strftime('%Y-%m-%d',"+Data.COLUMN_playTime+")"+"<=?",
                new String[]{StartTimer,endTimer},null,null,"date("+COLUMN_DATE+") desc");*/

      //也需要将它存储起来，不然的话，这个只是在有新数据进来的时候才进行执行，如果想统计除了当天的数据可以在日定单表中进行（Data.playTime+" < "+"date('now')"）查询
    /*    Cursor cursor=readDB.query(table,new String[]{ "SUM(storeEntry)","date("+Data.playTime+")" },
                "date(playTime)<? and date(playTime)>?",new String[]{"'now'",mLastTime},
                "date(playTime)",null,null);*/
      /*  Cursor cursor=readDB.query(table,new String[]{ "SUM(storeEntry)","date("+Data.playTime+")" },
                "date(playTime)"+"<?",new String[]{"date('now')"},
                "date(playTime)",null,null);*/

      Log.i("mycursor",mLastTime);

        Cursor cursor=readDB.query(table,new String[]{ "SUM("+storeEntry+")","date("+Data.playTime+")" },
                "date(playTime)>?"+" and "+"date(playTime)<?",
                new String[]{ mLastTime,mCurrentDate},
                "date(playTime)",null,null);

        return cursor;
    }
  /*  cursor=readDB.query(Data.COMPELETE_ORDER_TABLE_NAME,
            new String[]{ }, "oderType=? or oderType=? and payStatus=? ", new String[]{orderType,"加价购订单","paid"},
            null, null, ORDER_BY);*/
    /*
    获取最后的总结结果    selection :Data.playTime+" < "+"date('now')"  之前设置统计前天
     */

    //使用date("now") 进行多条件查询出错

    public  Cursor resultEndCursor(SQLiteDatabase readDB,String table)
    {
        Cursor cursor=readDB.query(table,new String[]{ "date("+Data.playTime+")","SUM("+Data.sweepPay+")", "SUM("+Data.addpriceAmount+")",
                        "SUM("+Data.comdityOrder+")","SUM("+Data.comissionOrder+")",
                       "SUM(sweepPay)+SUM(addpriceAmount)+SUM(comdityOrder)+SUM(comissionOrder)" },"date(playTime)<? ",new String[]{mCurrentDate},
                "date(playTime)",null,Data.ORDER_BY_TIME_ASC);
        return cursor;
    }



}
