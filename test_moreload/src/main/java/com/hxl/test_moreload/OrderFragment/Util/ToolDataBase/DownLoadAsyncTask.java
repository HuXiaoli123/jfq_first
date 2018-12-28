package com.hxl.test_moreload.OrderFragment.Util.ToolDataBase;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.hxl.test_moreload.OrderFragment.Goods.CompeleteOrder;
import com.hxl.test_moreload.OrderFragment.Goods.SweepCodeOrder;
import com.hxl.test_moreload.OrderFragment.Util.Tooljson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoadAsyncTask extends AsyncTask<String,Void,String> {

    private Context mContext;
    private List<SweepCodeOrder> mSweepCodeOrder= new ArrayList<>();

    public DownLoadAsyncTask(Context mContext){
        this.mContext=mContext;
    }
    @Override
    protected String doInBackground(String... params) {
        Log.i("path","test");
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        try{
            URL url=new URL(params[0]);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            int code=connection.getResponseCode();//响应码
            if(code==200){
                InputStream inputStream=connection.getInputStream();//通过字节流的形式得到对象转换成流的操作
                int temp=0;
                byte[]buff=new byte[1024];
                while ((temp=inputStream.read(buff))!=-1){
                    outputStream.write(buff,0,temp);
                }
            } else
            {
                Log.i("codeErro","codeErro"+code);
            }

        }catch (Exception e){

            e.printStackTrace();
        }

        return outputStream.toString();
    }

    @Override
    protected void onPostExecute(String s) {

        s=Html.fromHtml(s).toString();
        int begin=s.indexOf("{\"request\":");
        int last=s.length();
        s=s.substring(begin,last);
        Log.i("path",s);
        if(!"".equals(s)&&s!=null)
         {
             List<CompeleteOrder> mCompeleteOrder=Tooljson.getjfqdata("content",s,true);
             int len=mCompeleteOrder.size();

             //如果数据数量没有变化，不需要插入数据
             CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(mContext) );
             Log.i("mypath",len+":"+dao.allCaseNum());
             if(dao.allCaseNum()>=len)return;
             for(int i=0;i<len;i++)
             {
                 InsertData(mContext,mCompeleteOrder.get(i),DBHelper.COMPELETE_ORDER_TABLE_NAME);
             }
         }
    }

    //插入数据
    public static void InsertData(Context context, CompeleteOrder info, String tableName)
    {
        Log.i("Inserttable","Inserttable");
        //调用DAO辅助操作数据库
        CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(context) );
        dao.insertDB(info,tableName);
    }
}
