package com.jfq.xlstef.jfqui.Tools.DataBaseUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;

import java.util.ArrayList;

public class ListInfoDAO {
    DBHelper dbHelper=null;
    public ListInfoDAO(DBHelper dbHelper){
        this.dbHelper=dbHelper;
    }
    /*
        插入一条数据
     */
    public void insertDB(ListInfo listInfo){
        ContentValues cv=new ContentValues();
       // cv.put("_id",null);
        cv.put("timer",listInfo.getTimer());
        cv.put("content",listInfo.getContent());
        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        writeDB.insert(DBHelper.LISTINFO_TABLE_NAME,null,cv);
        Log.i("insertDB","insertDB"+writeDB);
        writeDB.close();
    }
    /*
        删除一条数据
     */
    public void deletDB(ListInfo listInfo){
        String whereCaluse="where _id=";
        String whereArgs[]=new String[]{String.valueOf(listInfo.get_id())};
        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        writeDB.delete(DBHelper.LISTINFO_TABLE_NAME,whereCaluse,whereArgs);
        writeDB.close();
    }
    /*
        修改一条数据
     */
    public void updateDB(ListInfo listInfo){
        ContentValues cv =new ContentValues();
        cv.put("timer",listInfo.getTimer());
        cv.put("content",listInfo.getContent());
        String whereCaluse="where _id=";
        String whereArgs[]=new String[]{String.valueOf(listInfo.get_id())};
        SQLiteDatabase writeDB=dbHelper.getWritableDatabase();
        writeDB.update(DBHelper.DATABASE_NAME,cv,whereCaluse,whereArgs);
        writeDB.close();
    }
    /*
        查询
     */
    public   final String ORDER_BY="_id desc";
    public ArrayList queryDB(){
        Log.i("My_queryDB","My_queryDB");
        ArrayList<ListInfo> arrayList=new ArrayList<>();
        Log.i("queryDB1","queryDB1");
        SQLiteDatabase readDB=dbHelper.getReadableDatabase();
        Cursor results=readDB.query(DBHelper.LISTINFO_TABLE_NAME,new String[]{"_id","timer","content"},null,null,null,null,null);
        Log.i("queryDB2","queryDB2");
        for(results.moveToFirst();!results.isAfterLast();results.moveToNext()){
            ListInfo listInfo=new ListInfo();
            listInfo.set_id(results.getInt(0));
            listInfo.setTimer(results.getString(1));
            listInfo.setContent(results.getString(2));
            arrayList.add(listInfo);
        }
        return arrayList;
    }
}
