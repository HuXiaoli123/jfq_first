package com.hxl.test_moreload.OrderFragment.Util.ToolDataBase;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;

import com.hxl.test_moreload.OrderFragment.Goods.CategoryBean;
import com.hxl.test_moreload.OrderFragment.Goods.CompeleteOrder;
import com.hxl.test_moreload.OrderFragment.Goods.SweepCodeOrder;
import com.hxl.test_moreload.OrderFragment.Util.Tooljson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoadAsyncTask extends AsyncTask<String,Void,String> {

    private Context mContext;
    private  List<CategoryBean> mCompeleteOrder=new ArrayList<>();
    private List<SweepCodeOrder> mSweepCodeOrder= new ArrayList<>();

    public List<CategoryBean> getCompeleteOrder() {
        return mCompeleteOrder;
    }



    public DownLoadAsyncTask(Context mContext  ){
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
            //设置字符集
            connection.setRequestProperty("Charset", "UTF-8");
            //设置文件类型
            connection.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
            /*不同用户需要要修改的Cookie值*/
            connection.addRequestProperty("Cookie", "JFCS_ACCESS_TOKEN="+"c349e82e-1c13-4ef1-96ab-a94c767b2b95");
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
            Log.i("codeErro","codeErro"+e);
            e.printStackTrace();
        }
        Log.i("codeErro","codeErro"+outputStream.toString());
        return outputStream.toString();
    }

    @Override
    protected void onPostExecute(String s) {

        Log.i("mytest1",s);
        if(!"".equals(s)&&s!=null)
         {
             Log.i("sleep",s);
             /*s=parseData(s);*/
             List<CategoryBean> myCompeleteOrder=Tooljson.getjfqdata("content",s,true);
             mCompeleteOrder=Tooljson.getjfqdata("content",s,true);
             int len=myCompeleteOrder.size();
             Log.i("sleep_asy",""+mCompeleteOrder.size());
             //如果数据数量没有变化，不需要插入数据
             CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(mContext) );
             Log.i("mypath",len+":"+dao.allCaseNum());
             if(dao.allCaseNum()>=len)return;

             for(int i=0;i<len;i++)
             {
                 InsertData(mContext,myCompeleteOrder.get(i),DBHelper.COMPELETE_ORDER_TABLE_NAME);
             }
         }
    }

    //因为是我自己在sdn博客做的测试，所进行以下解析
    public  String parseData(String s)
    {
        s=Html.fromHtml(s).toString();
        int begin=s.indexOf("{\"request\":");
        int last=s.length();
        s=s.substring(begin,last);
        Log.i("path",s);
        return s;
    }

    //插入数据
    public static void InsertData(Context context, CategoryBean info, String tableName)
    {
        Log.i("Inserttable","Inserttable");
        //调用DAO辅助操作数据库
        CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(context) );
        dao.insertDB(info,tableName);
    }

  public static String DownLoadData(final String orderUrl)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //HttpURLConnection是专门用来创建web连接的
                //HttpClient  android6.0----->不建议用了，过时了
                HttpURLConnection connection=null;
                InputStream input=null;
                InputStreamReader inputreader=null;
                BufferedReader buffer=null;
                try {

                    URL url=new URL(orderUrl );
                    //使用HttpURLConnection进行HTTP连接
                    connection= (HttpURLConnection) url.openConnection();
                    //设置请求方法为get类型
                    connection.setRequestMethod("GET");
                    //设置连接时间和读取时间
                    //connection.setConnectTimeout(10000);
                    //connection.setReadTimeout(5000);
                    //设置字符集
                    connection.setRequestProperty("Charset", "UTF-8");
                    //设置文件类型
                    connection.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
                    //设置Cookie请求参数，可以通过Servlet中getHeader()获取
                    // connection.setRequestProperty("Cookie", "AppName="+ URLEncoder.encode("你好", "UTF-8"));
                    connection.addRequestProperty("Cookie", "JFCS_ACCESS_TOKEN="+"c349e82e-1c13-4ef1-96ab-a94c767b2b95");
                    //connection.setRequestProperty("Cookie", "JFCS_ACCESS_TOKEN="+ access_token);
                    if(connection.getResponseCode()==200){//网络请求状态码200表示正常
                        input=connection.getInputStream();
                        inputreader=new InputStreamReader(input,"UTF-8");
                        buffer=new BufferedReader(inputreader);
                        String line="";
                        String result = "";
                        while((line=buffer.readLine())!=null){
                            result+=line;
                        }


                    }else
                    {
                        Log.e("my_test_Err", ","+connection.getResponseCode());

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    //关闭流
                    try {
                        if(buffer!=null){
                            buffer.close();}
                        if(inputreader!=null){
                            inputreader.close();
                        }
                        if(input!=null){
                            input.close();
                        }
                        if(connection!=null){
                            connection.disconnect();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        return "";
    }
}
