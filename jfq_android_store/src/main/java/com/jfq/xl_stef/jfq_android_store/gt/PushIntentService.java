package com.jfq.xl_stef.jfq_android_store.gt;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息
 * onReceiveMessageData 处理透传消息
 * onReceiveClientId 接收 cid
 * onReceiveOnlineState cid 离线上线通知
 * onReceiveCommandResult 各种事件处理回执
 */
public class PushIntentService extends GTIntentService {
    public PushIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    @Override
    public void onReceiveClientId(Context context, String clientId) {
        Log.e(TAG,"onReceiveClientId->"+"clientid="+clientId);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        //Log.e(TAG,"onReceiveMessageData->"+"message="+new String(gtTransmitMessage.getPayload()));
        String appId=gtTransmitMessage.getAppid();
        String taskId=gtTransmitMessage.getTaskId();
        String messageId=gtTransmitMessage.getMessageId();
        byte[] payLoad=gtTransmitMessage.getPayload();
        String pkg=gtTransmitMessage.getPkgName();
        Log.d("透传消息：" ,context.toString()+" appID="+appId+"payLoad="+new String(payLoad));

    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }


}