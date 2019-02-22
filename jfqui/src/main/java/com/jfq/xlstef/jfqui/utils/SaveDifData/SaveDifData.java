package com.jfq.xlstef.jfqui.utils.SaveDifData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SaveDifData <T> {



    void SetData(Context context , String key ,boolean value)
    {
        //先建立数据库存储
        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("cookie", value);
        editor.putBoolean(key, value);
        Log.e("MysetCookies","cookieStr:"+value);
        editor.commit();
    }
    /*void SetData(Context context , String key , <T>value  )
    {
        //先建立数据库存储
        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("cookie", value);

        editor.putStringSet(key,Set<value>);

        editor.putBoolean(key, value);
        Log.e("MysetCookies","cookieStr:"+value);
        editor.commit();
    }*/
}
