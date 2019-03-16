package com.jfq.xl_stef.jfq_android_store.gt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.igexin.sdk.GTServiceManager;

public class PushService extends Service {
    public PushService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GTServiceManager.getInstance().onCreate(this);
        Log.e("pushService","oncreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        Log.e("pushService","onstartcommand");
        return GTServiceManager.getInstance().onStartCommand(this,intent,flags,startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GTServiceManager.getInstance().onDestroy();
        Log.e("pushService","ondestroy");

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.e("pushService","onbind");
        return GTServiceManager.getInstance().onBind(intent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GTServiceManager.getInstance().onLowMemory();
    }
}
