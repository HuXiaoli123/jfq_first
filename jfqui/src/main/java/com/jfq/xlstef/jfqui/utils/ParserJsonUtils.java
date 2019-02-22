package com.jfq.xlstef.jfqui.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.jfq.xlstef.jfqui.LoginPage.ResultClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json解析的工具类
 */
public class ParserJsonUtils {
    /**
     * 根据json字符串解析封装成实体类并且存储到集合中
     * @param jsonString json字符串
     * @return   集合
     */
    public static List<ResultClass> parserJsonToList(String jsonString){
        List<ResultClass> list=new ArrayList<>();
        try {
            //1.将json字符串封装成jsonObject对象
            JSONObject jsonObject=new JSONObject(jsonString);
            //2.根据key获取value值  数组
            JSONArray jsonArray=jsonObject.getJSONArray("");

            Log.e("myresult__length", "length"+jsonArray.length() );
            //3.循环数组将数据封装到list集合中
            for(int i=0;i<jsonArray.length();i++){
                JSONObject obj=jsonArray.getJSONObject(i);
                //将jsonobject中的value值封装成属性值
                ResultClass info=new ResultClass();
                info.setAccess_token(obj.getString("access_token"));
                info.setToken_type(obj.getString("token_type"));
                info.setScope(obj.getString("scope"));
                info.setUserName(obj.getString("userName"));
                info.setAuthorities(obj.getString("authorities"));
                list.add(info);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("myresult","抛出异常" );
        }
        return list;
    }

    public static ResultClass getPerson(String jsonString) {
        ResultClass info = new ResultClass();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
           // JSONObject personObject = jsonObject.getJSONObject("person");

            info.setAccess_token(jsonObject.getString("access_token"));
            info.setToken_type(jsonObject.getString("token_type"));
            info.setScope(jsonObject.getString("scope"));
            info.setUserName(jsonObject.getString("userName"));
            info.setAuthorities(jsonObject.getString("authorities"));
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("myresult","抛出异常" );
        }
        return info;
    }

    public static String createJsonString(String key, Object value) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 得到json文件中的内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static boolean SaveUserInfo(Context context, String number,
                                       String psw) {

        try {
            File SDCardFile = Environment.getExternalStorageDirectory();
            File file = new File(SDCardFile, "data.txt");
            FileOutputStream fos;

            fos = new FileOutputStream(file);

            String data = number + "##" + psw;
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }
    /**
     * 从SD卡中读取数据
     * @param context
     * @return
     */
    public static Map<String, String> GetUserInfo(Context context) {

        try {
            File sDCardFile = Environment.getExternalStorageDirectory();
            File file = new File(sDCardFile, "data.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            String readLine = br.readLine();
            if (!TextUtils.isEmpty(readLine)) {
                Map<String, String> userInfoMap = new HashMap<String, String>();
                String split[] = readLine.split("##");
                userInfoMap.put("number", split[0]);
                userInfoMap.put("psw", split[1]);
                return userInfoMap;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }
}
