package com.jfq.xlstef.jfqui.LoginPage.VoiceParse;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.jfq.xlstef.jfqui.LoginPage.KqwSpeechCompound;
import com.jfq.xlstef.jfqui.LoginPage.Login_Activity;
import com.jfq.xlstef.jfqui.MainActivity;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.R;
import com.jfq.xlstef.jfqui.Tools.DataBaseUtil.ListInfo;
import com.jfq.xlstef.jfqui.Tools.DataBaseUtil.ListInfoDAO;
import com.jfq.xlstef.jfqui.utils.SaveDifData.SharedPreferencesUtils;
import com.jfq.xlstef.jfqui.widget.BadgeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.jfq.xlstef.jfqui.MainActivity.mInstance;

/**
 * （一旦有消息过来，就会启动这个Service   全局变量进行了重新的初始化）
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class DemoIntentService extends GTIntentService {

    DBHelper dbHelper;
    boolean isSend=false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyIntent","onCreate");

    }
//--------------------------------------------------

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e("MyIntent","onTaskRemoved");
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        Log.e("MyIntent","setIntentRedelivery");
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("MyIntent","onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MyIntent","onUnbind");
        return super.onUnbind(intent);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("MyIntent_test","onStart");
    }
    @Override
    public void onLowMemory() {
        Log.e("MyIntent","onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
        Log.e("MyIntent","registerComponentCallbacks");
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.e("MyIntent","unbindService");
    }

    @Override
    public boolean stopService(Intent name) {
        Log.e("MyIntent","stopService"+name);
        return super.stopService(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyIntent","onBind");
        return super.onBind(intent);
    }
    //--------------------------------------------------

    private  KqwSpeechCompound kqwSpeechCompound;


    //构造函数
    public DemoIntentService() {

        Log.e(TAG, "DemoIntentService---------------------------------");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);
        Log.i("myintent","onHandleIntent");
       /*if(isSend)
       {
           // 发送广播通知Activity
           Intent sendIntent = new Intent("StartQueryData");
           getApplicationContext().sendBroadcast(sendIntent);
           isSend=false;
       }*/
    }

    //重启了应用，重新接收了
    @Override
    public void onReceiveServicePid(Context context, int i) {


        Log.e("MyIntent---","Pid_i"+i);
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {

        Log.e(TAG, "MyIntent " + "clientid = " + clientid);
        //先建立数据库存储
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("clientid", clientid);

        Log.e("MysetCookies","cookieStr:"+clientid);
        editor.commit();
    }
    Boolean IsStart=true;
    Boolean finishPlay=true;
    int infoLength=0;
    private  int dataCount;
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        kqwSpeechCompound=new KqwSpeechCompound(getApplicationContext());
        Log.e(TAG, "MyIntent onReceiveMessageData-test");

        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        Log.e(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        Log.e(TAG, "MyIntent onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
                + "\ncid = " + cid);

        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);

            Log.e(TAG, "MyIntent receiver payload = " + data);
            //data="积分圈到账"+data+"元";

           Test(data);

            kqwSpeechCompound.infoQue.add(data);
            Log.e("test1","几条数据："+kqwSpeechCompound.infoQue.size());

            Log.e("test1","是否开始"+KqwSpeechCompound.IsStart);
          //  kqwSpeechCompound.speaking(data);
            Log.e("test1","新来第几条信息："+(++dataCount));
            if(!kqwSpeechCompound.infoQue.isEmpty()){
                Log.e("test1","是否开始"+KqwSpeechCompound.IsStart);
                if(KqwSpeechCompound.IsStart){
                    KqwSpeechCompound.IsStart=false;


                    Log.e("testInfo","info大小"+kqwSpeechCompound.infoQue.size());
                    while (!kqwSpeechCompound.infoQue.isEmpty()){

                        if(KqwSpeechCompound.finishPlay==true)
                        {
                            Log.e("test1","finishPlay"+KqwSpeechCompound.finishPlay);
                        }
                        if(KqwSpeechCompound.finishPlay){
                            KqwSpeechCompound.finishPlay=false;
                            String dataString=kqwSpeechCompound.infoQue.get(0);
                            Log.e("test1","传过来的数值是"+dataString);
                            kqwSpeechCompound.speaking(dataString);
                            Log.e("test1","kqwSpeechCompound 语音播报"+dataString);
                        }
                        if(KqwSpeechCompound.isComplete){
                            Log.e("test1","是否完成"+KqwSpeechCompound.isComplete);
                            kqwSpeechCompound.infoQue.remove(0);
                            KqwSpeechCompound.isComplete=false;
                            KqwSpeechCompound.finishPlay=true;

                        }

                    }
                    Log.e("test1","最后判断列表是否为空"+kqwSpeechCompound.infoQue.isEmpty());
                    KqwSpeechCompound.IsStart=true;


                    new Thread() {
                        @Override
                        public void run() {
                            //这里写入子线程需要做的工作
                            //判断是否完成
                            //   if(KqwSpeechCompound.isComplete)




                        }
                    }.start();

                }

            }

            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String curtimer = formatter.format(curDate);

            InsertData(curtimer,data);

/*
            try {

                sendMessage(data, 0);//这里对透传消息进行发送 通过App中的方法进行处理
            } catch (Exception e) {
                e.printStackTrace();
            }*/



        }

        Log.e(TAG, "----------------------------------------------------------------------------------------------");

    }



    public   Notification Test(String data )
    {
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(DemoIntentService.this);
        builder.setSmallIcon(R.drawable.message);
        builder.setTicker("显示第一个通知");
        builder.setContentTitle("第一个通知");
        builder.setContentText(data);
        builder.setWhen(System.currentTimeMillis()); //发送时间
        builder.setDefaults(Notification.DEFAULT_ALL);
        Notification notification = builder.build();
        notificationManager.notify( (int) (Math.random() * 10000), notification);

        return  notification;
       // startForeground(1, notification);


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyIntent","onDestroy");
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b)
    {

        Log.e("MyIntent","clientid是否在线"+b);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage)
    {
        Log.e("MyIntent","onReceiveCommandResult" +gtCmdMessage.toString());
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    


    //插入数据
    public  void InsertData(String timer,String content)
    {


        if(dbHelper==null)
        {
            dbHelper=new DBHelper(getApplication());
        }
        ListInfo listInfo=new ListInfo();
        Log.i("InsertData1", timer+content);
        listInfo.setTimer(timer);
        Log.i("InsertData2", timer+content);
        listInfo.setContent(content);
        Log.i("InsertData3", timer+content);
        //调用DAO辅助操作数据库
        ListInfoDAO dao=new ListInfoDAO(dbHelper);
        Log.i("InsertData4", timer+content);
        dao.insertDB(listInfo);
        Log.i("dao",""+dao);
        //Toast.makeText(mContext,"已插入一条数据",Toast.LENGTH_SHORT).show();
        Log.i("InsertData5", timer+content);

        isSend=true;
        int count=0;

        SharedPreferencesUtils utils=new SharedPreferencesUtils(getApplicationContext(),"msgs");
        Log.i("InsertData5", utils.getInt("msg_count")+"");
        count= utils.getInt("msg_count")<0 ? 1:utils.getInt("msg_count")+1;

        utils.putValues(new SharedPreferencesUtils.ContentValue("msg_count", count));

        // 发送广播通知Activity
        Intent sendIntent = new Intent("StartQueryData");

        sendIntent.putExtra("badge_count",count);
        getApplicationContext().sendBroadcast(sendIntent);
        //sendToMainActivity(getApplicationContext(),MainActivity.msgCount++);


       /* Intent intent=new Intent("com.hxl.mygetuitest01");
        intent.putExtra("123","123");
        Log.i("My_onReceive","sendBroadcast");
        sendBroadcast(intent);
        Log.i("My_onReceive","sendBroadcast");*/



    }
    private static void sendToMainActivity(Context context ,int count){

        Intent intent = new Intent("BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        context.sendBroadcast(intent);
    }


}
