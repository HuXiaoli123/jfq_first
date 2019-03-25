package com.jfq.xlstef.jfqui.LoginPage.VoiceParse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.igexin.sdk.GTServiceManager;

public class DemoPushService extends Service {
    public DemoPushService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GTServiceManager.getInstance().onCreate(this);
        Log.e("push","onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e("push","onBind");
        return GTServiceManager.getInstance().onBind(intent);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
         Log.e("push","onStartCommand");
         return GTServiceManager.getInstance().onStartCommand(this,intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("PushService_onDestroy","yes");
        GTServiceManager.getInstance().onDestroy();
    }

    /*在系统内存不足，所有后台程序（优先级为background的进程，不是指后台运行的进程）都被杀死时，
    系统会调用OnLowMemory。系统提供的回调有：Application/Activity/Fragementice/Service/ContentProvider*/
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("push","onLowMemory");
            GTServiceManager.getInstance().onLowMemory();
    }



}
