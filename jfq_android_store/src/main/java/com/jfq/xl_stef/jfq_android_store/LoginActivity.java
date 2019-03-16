package com.jfq.xl_stef.jfq_android_store;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.igexin.sdk.*;
import com.jfq.xl_stef.jfq_android_store.gt.PushIntentService;

public class LoginActivity extends AppCompatActivity {
    private  String configFileName;//配置信息文件名
    private Button userInfo_submit;
    private EditText password,username;
    private CheckBox autologin_check,saveinfo_check;
    //isAutoLogin：是否自动登录
    //isSaveInfo：是否保存账号名密码
    private boolean isAutoLogin,isSaveInfo;
    SharedPreferences sp=null;
    private String savedUsername,savedPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_login);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.login_titlebar);
        //初始化界面
        initView();
        //读取配置
        readConfig();
        //初始化个推
        initgt();

    }
    private void initgt(){
        PushManager.getInstance().initialize(getApplicationContext(), com.igexin.sdk.PushService.class);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(),PushIntentService.class);
    }
    private void readConfig(){
        //获取保存文件名
        configFileName=getString(R.string.SP_Config);
        //初始化SharedPreferences
         sp=getSharedPreferences(configFileName, Context.MODE_PRIVATE);
        //获取保存的参数
        isAutoLogin=sp.getBoolean("isautologin",false);
        isSaveInfo=sp.getBoolean("issaveinfo",false);
        savedUsername=sp.getString("savedusername","");
        savedPassword=sp.getString("savedpassword","");
    }
    private void initView(){
        userInfo_submit= (Button) findViewById(R.id.userInfo_submit);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        userInfo_submit.setOnClickListener(new SubmitOnClick());
        autologin_check= (CheckBox) findViewById(R.id.autologin_checkbox);
        saveinfo_check= (CheckBox) findViewById(R.id.saveinfo_checkbox);

    }
    //登录按钮点击
   private class SubmitOnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {

        }
    }
    //登录
    private void login(){

    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    /*
        初始化配置
     */
    private void initConfig(){
        //判断一开始自动保存账号名密码是否打开
        if(isSaveInfo==true && !savedUsername.equals("")&&!savedPassword.equals("")){
            saveinfo_check.setChecked(true);
            username.setText(savedUsername);
            password.setText(savedPassword);
        }else{
            saveinfo_check.setChecked(false);
            username.setText("");
            password.setText("");
        }
        //判断一开始自动保存登录是否打开
        if(isAutoLogin==true){
            autologin_check.setChecked(true);

        }else{
            autologin_check.setChecked(false);
        }
        
    }
}
