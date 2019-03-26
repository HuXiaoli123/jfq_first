package com.jfq.xlstef.jfqui.LoginPage.VoiceParse;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.igexin.sdk.PushService;

/**
        * 监听锁屏消息的广播接收器
        */
public class LockScreenMsgReceiver extends BroadcastReceiver {
    private String action = null;
   // private static final String TAG = ScreenBroadcastReceiver.class.getSimpleName();
    private static final String TAG = "LockScreenMsgReceiver";
    private boolean isRegisterReceiver = false;

    private  Service mContext;
    public  LockScreenMsgReceiver(Service context)
    {
        this.mContext=context;
    }

    public  LockScreenMsgReceiver()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            // 开屏
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.i("context",context+"11");
            // 锁屏  ---将services放到前台
            Intent forgroundService = new Intent(context,com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService.class);

            context.startService(forgroundService);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForeground(1,Getnotification(mContext));
                Log.i("context",context+"11");
            }else
            {

            }

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(forgroundService,Getnotification);

                mContext.startForeground(1.);

                forgroundService.startForeground(1, Getnotification(context));
               // startForeground(id, notification);
            }else
            {
                context.startService(forgroundService);
            }*/


        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            // 解锁
            mContext.stopForeground(true);

        }/*else if(Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action))
        {
            Log.i("context",context+"11");
            // 锁屏  ---将services放到前台
            Intent forgroundService = new Intent(context,com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService.class);

            context.startService(forgroundService);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForeground(1,Getnotification(mContext));
                Log.i("context",context+"11");
            }else
            {

            }
        }*/
        Log.i(TAG,action);
    }

    public void registerScreenBroadcastReceiver(Context mContext) {
        if (!isRegisterReceiver) {
            isRegisterReceiver = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_USER_PRESENT);
           // filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            mContext.registerReceiver(LockScreenMsgReceiver.this, filter);
        }
    }

    public void unRegisterScreenBroadcastReceiver(Context mContext) {
        if (isRegisterReceiver) {
            isRegisterReceiver = false;
            mContext.unregisterReceiver(LockScreenMsgReceiver.this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification Getnotification(Context context)
    {
        NotificationChannel channel = new NotificationChannel("id","name", NotificationManager.IMPORTANCE_LOW);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel);
        }

        Notification notification = new Notification.Builder(context,"id").build();

       return  notification;
    }
}

