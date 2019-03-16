package com.jfq.xlstef.jfqui.LoginPage;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;

/**
 * Created by Malik J on 2017/6/5.
 */

/**
 * 语音合成的类 发音人明细http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=367
 *
 * @author kongqw
 */
public class KqwSpeechCompound {
    // Log标签
    private static final String TAG = "KqwSpeechCompound";
    // 上下文
    private Context mContext;
    // 语音合成对象
    private static SpeechSynthesizer mTts;

    public  static  boolean isComplete;

    public static ArrayList<String> infoQue;

    static
    {
        infoQue=new ArrayList<String>();
    }


    /**
     * 发音人
     */
    public final static String[] COLOUD_VOICERS_ENTRIES = {"小燕", "小宇", "凯瑟琳", "亨利", "玛丽", "小研", "小琪", "小峰", "小梅", "小莉", "小蓉", "小芸", "小坤", "小强 ", "小莹",
            "小新", "楠楠", "老孙",};
    public final static String[] COLOUD_VOICERS_VALUE = {"xiaoyan", "xiaoyu", "catherine", "henry", "vimary", "vixy", "xiaoqi", "vixf", "xiaomei",
            "xiaolin", "xiaorong", "xiaoqian", "xiaokun", "xiaoqiang", "vixying", "xiaoxin", "nannan", "vils",};


    /**
     * 构造方法
     *
     * @param context
     */
    public KqwSpeechCompound(Context context) {
        // 上下文
        mContext = context;
        Log.d("tag54", "" +"KqwSpeechCompound" );
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(mContext, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    Log.d("tag54", "初始化失败,错误码：" + code);
                }
                else
                Log.d("tag54", "初始化失败,初始化成功：" + code);
            }
        });
        Log.d("tag54", "" +"mTts"+mContext+mTts );
    }

    /**
     * 开始合成
     *
     * @param text
     */
    public void speaking(String text) {
        Log.d("tag54","-----"+text+"++++++++++");
        // 非空判断
        if (TextUtils.isEmpty(text)) {
            return;
        }else
        {
            Log.d("tag54","-----"+text+"");
        }
        Log.d("tag54","-----"+mTts+"");
        int code = mTts.startSpeaking(text, mTtsListener);
        Log.d("tag54","-----"+code+"++++++++++");

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                Toast.makeText(mContext, "没有安装语音+ code = " + code, Toast.LENGTH_SHORT).show();
                Log.d("tag54","没有安装语音+ code = " + code);
            } else {
                Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
                Log.d("tag54","语音合成失败,错误码: " + code);
            }
        }


    }

    /*
     * 停止语音播报
     */
    public static void stopSpeaking() {
        // 对象非空并且正在说话
        if (null != mTts && mTts.isSpeaking()) {
            // 停止说话
            mTts.stopSpeaking();
        }
    }

    /**
     * 判断当前有没有说话
     *
     * @return
     */
    public static boolean isSpeaking() {
        if (null != mTts) {
            return mTts.isSpeaking();
        } else {
            return false;
        }
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin()
        {
            Log.i(TAG, "开始播放");
        }

        @Override
        public void onSpeakPaused() {
            Log.i(TAG, "暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            Log.i(TAG, "继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            // TODO 缓冲的进度
            Log.i(TAG, "缓冲 : " + percent);
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // TODO 说话的进度
            Log.i(TAG, "合成 : " + percent);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Log.i(TAG, "播放完成");
                isComplete=true;

            } else if (error != null) {
                Log.i(TAG, error.getPlainDescription(true));
            }

        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 引擎类型 网络
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, COLOUD_VOICERS_VALUE[0]);
        // 设置语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        // 设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        // 设置音量
        mTts.setParameter(SpeechConstant.VOLUME, "100");
        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");

        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/KRobot/wavaudio.pcm");
        // 背景音乐  1有 0 无
        // mTts.setParameter("bgs", "1");
    }
    public static boolean IsStart=true;
    public static boolean finishPlay=true;
    //用于播放传过来的信息
    public  static    void  PlayInfo(String data){

        infoQue.add(data);

        if(!infoQue.isEmpty()){
            Log.e("test1","是否开始"+IsStart);
            if(IsStart){
                IsStart=false;
                new Thread() {
                    @Override
                    public void run() {
                        //这里写入子线程需要做的工作
                        //判断是否完成
                        //   if(KqwSpeechCompound.isComplete)

                        Log.e("testInfo","info大小"+infoQue.size());
                        while (!infoQue.isEmpty()){

                            if(finishPlay==true)
                            {
                                Log.e("test1","finishPlay"+finishPlay);
                            }
                            if(finishPlay){
                                finishPlay=false;
                                String dataString=infoQue.get(0);
                                Log.e("test1","传过来的数值是"+dataString);
                                //speaking(data);
                            }
                            if(KqwSpeechCompound.isComplete){
                                Log.e("test1","是否完成"+KqwSpeechCompound.isComplete);
                               infoQue.remove(0);
                                KqwSpeechCompound.isComplete=false;
                                finishPlay=true;

                            }

                        }
                        Log.e("test1","最后判断列表是否为空"+infoQue.isEmpty());
                        IsStart=true;


                    }
                }.start();

            }
        }

    }
}
