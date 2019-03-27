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
import android.widget.Toast;

import com.iflytek.cloud.Setting;
import com.igexin.sdk.PushService;
import com.jfq.xlstef.jfqui.R;

import static com.iflytek.speech.UtilityConfig.CHANNEL_ID;

/**
        * 监听锁屏消息的广播接收器
        */
public class LockScreenMsgReceiver extends BroadcastReceiver {
    private String action = null;
   // private static final String TAG = ScreenBroadcastReceiver.class.getSimpleName();
    private static final String TAG = "LockScreenMsgReceiver";
    private boolean isRegisterReceiver = false;

    private  DemoPushService mContext;
    private  DemoPushService mDemoPushService;
    public  LockScreenMsgReceiver(DemoPushService context)
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
            mContext.stopForeground(true);
            Log.i("context_on",mContext+"11");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {

            // 锁屏  ---将services放到前台
            Intent forgroundService = new Intent(context,com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService.class);

            context.startService(forgroundService);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              mContext.startForeground(1,Getnotification(mContext));
                Log.i("context",context+"11");
            }else
            {
                Log.i("context","当前SDK版本较低");
                Toast.makeText(mContext,"当前SDK版本:"+Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
            }

           Log.i("onResume",ServiceUtils.isAppRunning(context,"com.jfq.xlstef.jfqui02")+"app"+
           ServiceUtils.isServiceRunning(context,"com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService")
           +ServiceUtils.isBackground(context));

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
            if(mContext!=null)
            {
                mContext.stopForeground(true);
            }else {
                initMyService(context);

            }

            Log.i("mytesteee",mContext+","+mDemoPushService);



        }else if (Intent.ACTION_TIME_TICK.equals(action))
        {
            if (!ServiceUtils.isServiceRunning(context,"com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService")) {

                Log.d(TAG, "MyService没有运行");
            } else {
                Log.d(TAG, "MyService正在运行");


            }
        }

        /*else if(Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action))
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


    //初始化系统消息推送服务
    private void  initMyService(Context context) {
        if (!ServiceUtils.isServiceRunning(context,"com.jfq.xlstef.jfqui.LoginPage.VoiceParse.DemoPushService")) {

            Log.d(TAG, "MyService没有运行");
        } else {
            Log.d(TAG, "MyService正在运行");


        }

    }

    public void registerScreenBroadcastReceiver(Context mContext) {
        if (!isRegisterReceiver) {
            isRegisterReceiver = true;
            mDemoPushService=(DemoPushService) mContext;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_USER_PRESENT);
           // filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            filter.addAction(Intent.ACTION_TIME_TICK); // 这个广播每分钟发送一次，我们可以每分钟检查一次Service的运行状态，如果已经被结束了，就重新启动Service。
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

     //   Notification notification = new Notification.Builder(context,"id").build();
        Notification notification = new Notification.Builder(context)
                .setContentTitle("") .setContentText("积分圈在运行")
                .setSmallIcon(R.drawable.jfq_login_logo)
                .setChannelId(CHANNEL_ID)
                .build();
       return  notification;
    }


}

