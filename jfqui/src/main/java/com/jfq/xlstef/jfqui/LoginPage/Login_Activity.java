package com.jfq.xlstef.jfqui.LoginPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoIntentService;
import com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService;
import com.jfq.xlstef.jfqui.MainActivity;
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.utils.ParserJsonUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Login_Activity extends AppCompatActivity   implements View.OnClickListener {
        boolean postResult=false;
        EditText usernameTXT,passwordTXT;
        private ImageView iv_see_password;
        Button login;
        Handler myhander;
        private  String url;
        // "appId="+appid+"&clientId="+clientid;
        String access_token;
        private  String appid;
        private String clientid;
        private  String cookiehead;

        private CheckBox autoLogin;
        private CheckBox remeberUser;
        private boolean isAutoLogin;
        private boolean isRemeberUser;

        KqwSpeechCompound kqwSpeechCompound;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            kqwSpeechCompound=new KqwSpeechCompound(getApplicationContext());
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            initView();

            InitialSaveData();
            myhander=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.e("msg_0",msg.obj.toString());
                    if(msg.what==1){
                   /* usernameTXT.setText("登录成功！欢迎您");
                    passwordTXT.setText(msg.obj.toString());*/
                    }else
                    {
                        Log.e("msg_1",msg.obj.toString());
                        Toast.makeText(Login_Activity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
                    }

                }
            };



        }

        //是否自动登录
        int HasHistoryRemeber()
        {
            boolean isautoLogin = getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("isAutoLogin", false);
            boolean isRemerber=getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("isRemeberUser", false);

            Log.e("my_test",""+isautoLogin);

            //自动登录
            if(isautoLogin)
            {
                String  username = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("UserName", "");
                String password=getSharedPreferences("settings", Context.MODE_PRIVATE).getString("Password", "");
                SetUserPassword(username,password);
                postNetWork(username,password);


                return 1;
            }
            //记住密码
            else if(isRemerber){
                String  username = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("UserName", "");
                String password=getSharedPreferences("settings", Context.MODE_PRIVATE).getString("Password", "");
                SetUserPassword(username,password);
                return 2;
            }

            return 0;
        }

        private void  initView()
        {
            usernameTXT= (EditText) findViewById(R.id.username);
            passwordTXT= (EditText) findViewById(R.id.password);
            autoLogin=findViewById(R.id.auto_login);
            remeberUser=findViewById(R.id.remeberUser);
            login= (Button) findViewById(R.id.login);
            iv_see_password=findViewById(R.id.iv_see_password);
            iv_see_password.setOnClickListener((View.OnClickListener) this);

            switch (HasHistoryRemeber()){
                case 1:
                    DisableAllView();
                    Toast.makeText(Login_Activity.this,"您已经是老用户，直接登录",Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    LoginAction();
                    break;
                default:
                    LoginAction();
                    break;
            }
        }





        void LoginAction()
        {
            //自动登录
            autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                //自动登录按钮是否被点击
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked)
                    {
                        isAutoLogin=true;
                        Log.e("check","autoLogin"+isChecked);
                        remeberUser.setEnabled(false);
                        remeberUser.setChecked(true);
                    }
                    else{
                        isAutoLogin=false;
                        Log.e("check","autoLogin"+isChecked);
                        remeberUser.setEnabled(true);
                        remeberUser.setChecked(false);
                    }
                }
            });
            //记录用户名是否被点击
            remeberUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked)
                    {
                        if(usernameTXT.getText()==null||passwordTXT.getText()==null)
                        {
                            Toast.makeText(Login_Activity.this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
                        }
                        isRemeberUser=true;
                        Log.e("check","remeberUser"+isChecked);
                    }
                    else{
                        isRemeberUser=false;
                        Log.e("check","remeberUser"+isChecked);
                    }
                }
            });
            //登录事件
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String UserName=usernameTXT.getText().toString();
                    String Password=passwordTXT.getText().toString();
                    boolean post_result=  postNetWork(UserName,Password);
                    Log.i("btn1",postResult+","+post_result);
                    /*if(postResult)
                    {
                        if(isAutoLogin&&isRemeberUser)
                        {
                            //保存用户名和密码（至xml）
                            SetData("UserName",UserName);
                            SetData("Password",Password);
                            //保存自动登录和记住密码是否被点击过
                            SetData("isAutoLogin",isAutoLogin);
                            SetData("isRemeberUser",isRemeberUser);
                            Log.i("btn_1",post_result+""+isAutoLogin+","+isRemeberUser+UserName+Password);
                        }
                    }*/


                    Log.i("btn_",isAutoLogin+","+isRemeberUser+UserName+Password);

                    Log.i("btn",""+getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("isAutoLogin", false));
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_see_password:
                    setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                    break;

            }
        }

        /**
         * 设置密码可见和不可见的相互转换
         */
        private void setPasswordVisibility() {
            if (iv_see_password.isSelected()) {
                iv_see_password.setSelected(false);
                iv_see_password.setImageResource(R.drawable.icon_nosee_pass);
                //密码不可见
                passwordTXT.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            } else {
                iv_see_password.setSelected(true);
                iv_see_password.setImageResource(R.drawable.icon_see_pass);
                //密码可见
                passwordTXT.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }

        }

        void SetUserPassword(String userName,String Password)
        {
        /*在activity中可以用setChecked(true);来设置为选中状态。
         ;语句为判断是否选中，返回一个boolean值。*/
            boolean isautoLogin = getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("isAutoLogin", false);
            boolean isRemerber=getSharedPreferences("settings", Context.MODE_PRIVATE).getBoolean("isRemeberUser", false);
            usernameTXT.setText(userName);
            passwordTXT.setText(Password);
            autoLogin.setChecked(isautoLogin);
            remeberUser.setChecked(isRemerber);

        }

        void  DisableAllView()
        {
            usernameTXT.setEnabled(false);
            passwordTXT.setEnabled(false);
            autoLogin.setEnabled(false);
            remeberUser.setEnabled(false);
            login.setEnabled(false);
        }




    /*
 post类型请求的网络操作方法
*/
        boolean  postNetWork(final String UserName, final String Password ){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //HttpURLConnection是专门用来创建web连接的
                    //HttpClient  android6.0----->不建议用了，过时了
                    HttpURLConnection connection=null;
                    InputStream input=null;
                    InputStreamReader inputreader=null;
                    BufferedReader buffer=null;
                    //创建连接
                    try {
                  /*  1. 设置Post参数：?grant_type=password&username=xxx$password=xxx
                    2. 设置http请求头中‘Authorization’,值为“Basic”+base64("webapp":"webapp")*/
                        //设置URL
                        // URL url=new URL("https://store.tuihs.com/oauth/token?");
                        //  URL url=new URL("http://192.168.0.109:8085/BJXT/PhoneServlet?");
                        URL url=new URL(getString(R.string.post_url));
                        //使用HttpURLConnection进行HTTP连接
                        connection= (HttpURLConnection) url.openConnection();
                        //设置连接时间和读取时间
                        connection.setConnectTimeout(10000);
                        connection.setReadTimeout(5000);
                        // 设置是否向connection输出，因为这个是post请求，参数要放在
                        // http正文内，因此需要设为true
                        connection.setDoOutput(true);
                        // Read from the connection. Default is true.
                        connection.setDoInput(true);
                        // 将请求方法设为post，默认是 GET方式,
                        connection.setRequestMethod("POST");
                        // Post 请求不能使用缓存
                        connection.setUseCaches(false);
                        //设置本次连接是否自动重定向
                        connection.setInstanceFollowRedirects(true);
                        //设置字符集
                        connection.setRequestProperty("Charset", "UTF-8");
                        //设置文件类型(千万别写"Content-Type", "text/html;charset=UTF-8")
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//或者不写 (不能)
                        //进行autho认证
                        //设置http请求头中‘Authorization’,值为“Basic”+base64("webapp":"webapp")

                        final String tokenStr = "Basic " + new String(Base64.encode(("webapp:webapp").getBytes(),Base64.DEFAULT));
                        connection.addRequestProperty("Authorization", tokenStr);

                        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                        // 要注意的是connection.getOutputStream会隐含的进行connect。
                        connection.connect();
                        OutputStream out = connection.getOutputStream();



                        Log.e("Test_result","tokenStr:"+tokenStr);


                        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串(也就是get请求中data变量中的内容)一致
                        String content = "grant_type=password&"+ "username=" + URLEncoder.encode(UserName, "UTF-8")
                                +"&password="+URLEncoder.encode(Password, "UTF-8");

                    /*String content = "username=" + URLEncoder.encode(usernameTXT.getText().toString(), "UTF-8")
                            +"&password="+URLEncoder.encode(passwordTXT.getText().toString(), "UTF-8");*/

                        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
                        Log.e("Test_result","content:"+content);
                        out.write(content.getBytes());
                        //流用完记得关
                        out.flush();

                        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){//网络请求状态码200表示正常

                            String cookie = connection.getHeaderField("set-cookie");// 获取服务器响应cookie
                            cookiehead=cookie;
                            Log.e("MysetCookies","cookie:"+cookie);
                            input=connection.getInputStream();
                            inputreader=new InputStreamReader(input,"UTF-8");
                            buffer=new BufferedReader(inputreader);
                            String line="";
                            String result = "";
                            while((line=buffer.readLine())!=null){
                                result+=line;
                            }

                            //解析json字符串  List bean
                            ResultClass  list= ParserJsonUtils.getPerson(result);

                            Log.e("MyTest",list.getAccess_token());

                            //修正
                            String json=result;
                            JSONObject obj=new JSONObject(json);
                            access_token=obj.getString("access_token");
                            Log.e("Myacess_tock",access_token+","+obj.toString());

                            SetData("cookie",access_token);
                            //  String access_token=result;

                            //使用handler将子线程中网络中读取的数据传到主线程中显示
                        /*Message msg= myhander.obtainMessage();
                        msg.what=1;
                        msg.obj=result;
                        myhander.sendMessage(msg);*/

                            messageHandle(1,result);
                            postResult=true;
                            // ActivityChange(access_token);
                            getNetWork();

                            Log.i("btn_1",""+isAutoLogin+","+isRemeberUser+UserName+Password+"remeberUser.isChecked()"+remeberUser.isChecked());
                            if(isAutoLogin)
                            {
                                //保存用户名和密码（至xml）
                                SetData("UserName",UserName);
                                SetData("Password",Password);
                                //保存自动登录和记住密码是否被点击过
                            /*SetData("isAutoLogin",isAutoLogin);
                            SetData("isRemeberUser",isRemeberUser);*/
                                SetData("isAutoLogin",autoLogin.isChecked());
                                SetData("isRemeberUser",remeberUser.isChecked());
                                Log.i("btn_1",""+isAutoLogin+","+isRemeberUser+UserName+Password+"remeberUser.isChecked()"+remeberUser.isChecked());
                            }
                            else if(isRemeberUser)
                            {
                                //保存用户名和密码（至xml）
                                SetData("UserName",UserName);
                                SetData("Password",Password);
                                //保存自动登录和记住密码是否被点击过
                                SetData("isAutoLogin",autoLogin.isChecked());
                                SetData("isRemeberUser",remeberUser.isChecked());
                                Log.i("btn_1",""+isAutoLogin+","+isRemeberUser+UserName+Password);
                            }else
                            {
                                //保存用户名和密码（至xml）
                                SetData("UserName","");
                                SetData("Password","");
                                //保存自动登录和记住密码是否被点击过
                                SetData("isAutoLogin",false);
                                SetData("isRemeberUser",false);
                            }



                            Log.i("btn",postResult+"postResult");

                        }else
                        {

                            Log.e("Test_result:","请求码"+connection.getResponseCode());

                            int code=connection.getResponseCode();
                            switch (code)
                            {
                                case 400 :
                                    messageHandle(0,"密码或用户名不正确");
                                    break;
                                case 401 :
                                    messageHandle(0,"请求无效，错误码"+code);
                                    break;
                                default:
                                    messageHandle(0,"错误码"+code+"请联系管理员");
                                    break;


                            }

                        }
                    } catch (IOException e) {
                        Log.e("Test_result","发生IOException异常！"); //网络连接不正确
                        messageHandle(0,"网络连接不正确");
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
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
                        }   catch (IOException e) {
                            Log.e("Test_result","发生异常！");
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
            return postResult;
        }

        //接收html网页传来消息，验证成功 result=1，acceptData=那边传过来的值
        private  void messageHandle(int result,String acceptData)
        {
            Message msg= myhander.obtainMessage();
            msg.what=result;
            msg.obj=acceptData;
            myhander.sendMessage(msg);
        }
    /*
        get类型请求的网络操作方法
     */
        void getNetWork(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //HttpURLConnection是专门用来创建web连接的
                    //HttpClient  android6.0----->不建议用了，过时了
                    HttpURLConnection connection=null;
                    InputStream input=null;
                    InputStreamReader inputreader=null;
                    BufferedReader buffer=null;

                    clientid=getSharedPreferences("settings", Context.MODE_PRIVATE).getString("clientid", "");// 从SharedPreferences中获取整个Cookie串

                    Log.e("my_clientid","my_clientid"+clientid);

                    //设置请求参数
                    String data="appId="+appid+"&clientId="+clientid;
                    //创建连接
                    try {
                        Log.e("my_test",access_token+","+data);
                        //  String path="http://store.tuihs.com/oauth/signin/app?"+"appId="+appid+"&clientId="+clientid;
                        //设置URL
                        //URL url=new URL("http://store.tuihs.com/oauth/signin/app?"+data);
                        //URL url=new URL("http://192.168.0.109:8085/BJXT/PhoneServlet?"+data);
                        URL url=new URL(getString(R.string.get_url)+data);
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
                        connection.setRequestProperty("Cookie", "JFCS_ACCESS_TOKEN="+ access_token);
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
                            //使用handler将子线程中网络中读取的数据传到主线程中显示
                            Message msg= myhander.obtainMessage();
                            msg.what=1;
                            msg.obj=result;
                            myhander.sendMessage(msg);

                            Log.e("my_test",access_token+","+connection.getResponseCode()+"result:"+result);

                            ActivityChange(access_token);

                        }else
                        {
                            Log.e("my_test_Err",access_token+","+connection.getResponseCode());

                            messageHandle(0,"请求无效，错误码"+connection.getResponseCode()+"请联系管理员");

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
        }

        //activity界面跳转
        private  void  ActivityChange(String intentValue)
        {

            /* 新建一个Intent对象 */
            Intent intent = new Intent();
            ArrayList<String> param=new ArrayList<String>();
            param.add(intentValue);
            param.add(appid);
            param.add(clientid);


            // url="http://store.tuihs.com/oauth/signin/app?"+"appId="+appid+"&clientId="+clientid;

            intent.putExtra("access_token",param);
            /* 指定intent要启动的类 */
            intent.setClass(Login_Activity.this, MainActivity.class);
            /* 启动一个新的Activity */
            Login_Activity.this.startActivity(intent);
            /* 关闭当前的Activity */
            Login_Activity.this.finish();
        }


        //保存数据
        void  InitialSaveData()
        {
            Log.i("mysend","DemoPushService");
            // com.getui.demo.DemoPushService 为第三方自定义推送服务
            PushManager.getInstance().initialize(this.getApplicationContext(),com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService.class);

            // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
            PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoIntentService.class);
            // appid="HKxjsmoNuh76VKMECvzsr3";
            appid=getString(R.string.GETUI_APP_ID);
            // clientid="fe078966f82648f8f1d1c9fbafcc9366";

            String cookie = getSharedPreferences("settings", Context.MODE_PRIVATE).getString("cookie", "");// 从SharedPreferences中获取整个Cookie串

            Log.e("MysetCookies:","cookieStr:"+cookie);



            //判断这个cookie是否为空
            if(!cookie.isEmpty()&&!cookie.equals(""))
            {
           /* usernameTXT.setVisibility(View.GONE);
            passwordTXT.setVisibility(View.GONE);
            login.setVisibility(View.GONE);*/
                access_token=getSharedPreferences("settings", Context.MODE_PRIVATE).getString("cookie", "");// 从SharedPreferences中获取整个Cookie串

            }


        }

        void SetData( String key ,String value)
        {
            //先建立数据库存储
            SharedPreferences preferences;
            if(key.equals("cookie"))
            {
                preferences = getSharedPreferences("cookies", Context.MODE_PRIVATE);
            }else
            {
                preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            }
            SharedPreferences.Editor editor = preferences.edit();
            //editor.putString("cookie", value);
            editor.putString(key, value);
            Log.e("MysetCookies","cookieStr:"+value);
            editor.commit();
        }
        void SetData(String key ,boolean value)
        {

            //先建立数据库存储
            SharedPreferences preferences;
            if(key.equals("isAutoLogin"))
            {
                preferences = getSharedPreferences("cookies", Context.MODE_PRIVATE);
            }else
            {
                preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            }
            SharedPreferences.Editor editor = preferences.edit();
            //editor.putString("cookie", value);
            editor.putBoolean(key, value);
            Log.e("MysetCookies","cookieStr:"+value);
            editor.commit();
        }


        /* *//**
         * 1.判断是否自动登录
         *//*
    private boolean autoLogin() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean autoLogin = helper.getBoolean("isRemeberUser", false);
        return autoLogin;
    }

    *//**
         * 2.判断是否记住密码
         *//*
    private boolean remenberPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean remenberPassword = helper.getBoolean("remenberPassword", false);
        return remenberPassword;
    }

    *//**
         * 3.保存用户账号和密码
         *//*
    public void loadUser() {
        if (!getAccount().equals("")&& !getPassword().equals("")) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
            helper.putValues(new SharedPreferencesUtils.ContentValue("name", getAccount()),new SharedPreferencesUtils.ContentValue("name",getPassword()));
        }

    }

    *//**
         * 获取账号
         *//*
    public String getAccount() {
        return usernameTXT.getText().toString().trim();//去掉空格
    }

    *//**
         * 获取密码
         *//*
    public String getPassword() {
        return passwordTXT.getText().toString().trim();//去掉空格
    }*/
}
