package com.jfq.xlstef.jfqui.LoginPage.VoiceParse;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

//初始化语音
public class MyAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(MyAPP.this, SpeechConstant.APPID + "=5bee6329"); //初始化
    }
}
