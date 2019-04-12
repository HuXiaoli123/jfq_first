package com.jfq.xlstef.jfqui.OrderFragment.Util;

import android.content.Context;
import android.util.Log;


import com.jfq.xlstef.jfqui.OrderFragment.Goods.CategoryBean;
import com.jfq.xlstef.jfqui.OrderFragment.Goods.SweepCodeOrder;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.DBHelper;
import com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase.Data;
import com.jfq.xlstef.jfqui.utils.SaveDifData.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Tooljson {
    public static    List<CategoryBean> JsonParse(Context context , String test)
    {
        /*
        1.请求数据
        2.拿服务器的数据进行事件比较，看是否有新数据过来
        3.将数据保存到本地的数据库（如果数据过多，看是否需要放在其他的table）
        myCategoryBean-----接收过来的json字符串----然后传递到 CategoryBean类中
         */
         List<CategoryBean> mCategoryBean = new ArrayList<>();
         List<SweepCodeOrder> mSweepCodeOrder= new ArrayList<>();
        CategoryBean myCategoryBean=new CategoryBean();

        SweepCodeOrder mySweepCodeOrder=new SweepCodeOrder("1234567","商城订单","29","2.6","26.4","29","12-11/14:27");


        List<CategoryBean> oderdatalist=new ArrayList<CategoryBean>() ;
        oderdatalist.add(myCategoryBean);

        //----------------------------------------
        List<SweepCodeOrder> sweepcodedatalist=new ArrayList<SweepCodeOrder>() ;
        sweepcodedatalist.add(mySweepCodeOrder);
        //-----------------------------------------
            for (int i = 0; i <10; i++) {

                CategoryBean orderGood = new CategoryBean();
                orderGood.setOrderNumber(oderdatalist.get(0).getOrderNumber());
                orderGood.setOderType(oderdatalist.get(0).getOderType());
                orderGood.setItemPrice(oderdatalist.get(0).getItemPrice());
                orderGood.setPlatformDeduction(oderdatalist.get(0).getPlatformDeduction());
                orderGood.setUserPlay(oderdatalist.get(0).getUserPlay());
                orderGood.setStoreEntry(oderdatalist.get(0).getStoreEntry());
                orderGood.setPlayTime(oderdatalist.get(0).getPlayTime());
                if (i == 99) orderGood.setPlayTime("2018--12--15");
                // orderGoodses.add(orderGood);
                mCategoryBean.add(orderGood);

                /*设置扫码订单*/
                SweepCodeOrder sweepCodeOrder = new SweepCodeOrder();
                sweepCodeOrder.setOrderNumber(sweepcodedatalist.get(0).getOrderNumber());
                sweepCodeOrder.setGoodName(sweepcodedatalist.get(0).getGoodName());
                sweepCodeOrder.setAddCountshopping(sweepcodedatalist.get(0).getAddCountshopping());
                sweepCodeOrder.setPlatformDeduction(sweepcodedatalist.get(0).getPlatformDeduction());
                sweepCodeOrder.setUserPlay(sweepcodedatalist.get(0).getUserPlay());
                sweepCodeOrder.setStoreEntry(sweepcodedatalist.get(0).getStoreEntry());
                sweepCodeOrder.setPlayTime(sweepcodedatalist.get(0).getPlayTime());
                if (i == 99) orderGood.setPlayTime("2018--12--15");
                // orderGoodses.add(orderGood);
                mSweepCodeOrder.add(sweepCodeOrder);
                //InsertData(context,sweepCodeOrder,DBHelper.SWEEP_CODE_ORDER_TABLE_NAME);
            }

        return mCategoryBean;

    }



/*
        key:content
 */
    /*public static List getjfqdata(String key, String jsonString) {
        List list = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i); 

                CategoryBean orderGood = new CategoryBean();
                String  orderNumber=jsonObject2.getString("code");
                orderGood.setOrderNumber(orderNumber); //订单编号orderType
                orderGood.setOderType(jsonObject2.getString("orderType")); //订单类型
                orderGood.setItemPrice(String.valueOf(jsonObject2.getDouble("totalFee"))); //商品原价

                orderGood.setPlatformDeduction(String.valueOf(jsonObject2.getJSONObject("loyaltyPromotions")
                        .getDouble("totalReduction")));//平台抵扣
                orderGood.setUserPlay(String.valueOf(jsonObject2.getDouble("salesAmount")));//用户实际支付金额
                orderGood.setStoreEntry(String.valueOf(jsonObject2.getDouble("totalFee")));//门店入账

                String date = orderNumber.subSequence(3, orderNumber.length()-7).toString();

                orderGood.setPlayTime( StringToDate(date));//支付时间入账


                list.add(orderGood);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("my_test",e.toString());
            Log.i("my_test","handle exception");
        }
        return list;
    }*/

    /**
     * 解析数据库---------------------------------yes
     * @param key
     * @param jsonString
     * @param timer
     * @return
     */
    public  static boolean isTest=false;
   public  static boolean  isFinishAllSerach=false;
    public static List<CategoryBean> getjfqdata(String key, String jsonString,String  timer,Context context) {
        List list = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组   1.获取json对象数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);

            String storeName=jsonArray.getJSONObject(0).getJSONObject("store").getString("name");
            Log.i("store_name",storeName);//门店名称

            SharedPreferencesUtils helper = new SharedPreferencesUtils(context, "settings");
            helper.putValues(new SharedPreferencesUtils.ContentValue("store_name", storeName));



            CategoryBean myCategoryBean8=new CategoryBean("1234567","扫码订单","29","2.6",
            "26.4","29","2019-03-22 22:59:39","12",
            "桃子","香蕉","paid");

          /* if(isTest)
           {
               list.add(myCategoryBean8);
           }*/

            //--------------test
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                CategoryBean orderGood = new CategoryBean();

                /*String date = orderNumber.subSequence(3, orderNumber.length()-7).toString();

                String payTimer=StringToDate(date);*/
                String  date=jsonObject2.getString("createTime");
                String payTimer=StringsToDate(date);

                   if(  timer.compareTo(payTimer) >=0)
                   {
                       Log.i("overTimer",timer);
                       isFinishAllSerach=true;
                       break;
                   }

                String  orderNumber=jsonObject2.getString("code");


                orderGood.setPlayTime( payTimer);//支付时间入账------------



               /* Log.i("timers--",EngToChinese(jsonObject2.getString("orderType"))+","+StringToDate(date)+","+jsonObject2.getJSONObject("status").getString("code"));*/


                orderGood.setOrderNumber(orderNumber); //订单编号orderType
                orderGood.setOderType(EngToChinese(jsonObject2.getString("orderType")) ); //订单类型----

                Double allitemprice=jsonObject2.getDouble("totalFee");//总商品原价

                orderGood.setItemPrice(String.valueOf(jsonObject2.getDouble("totalFee"))); //商品原价 ----0316暂时更改为门店入账    ----下面需要修改 因为它包括了原商品价格+加价购商品价格

                orderGood.setPlatformDeduction(String.valueOf(jsonObject2.getJSONObject("loyaltyPromotions")
                        .getDouble("totalReduction")));//平台抵扣--------
                orderGood.setUserPlay(String.valueOf(jsonObject2.getDouble("salesAmount")));//用户实际支付金额----------

                orderGood.setStoreEntry(String.valueOf(jsonObject2.getDouble("totalFee")));//门店入账-----



                String addpriceName="";
                StringBuilder builder = new StringBuilder();
                Double addpriceAmount=0.0;
                Double totalBargain=0.0;

                Double seepCode=0.0;

                Double sumItem=0.0;


                JSONArray addPricejsonArray = jsonObject2.getJSONArray("items");

                //求得扫码支付订单
               /* for(int j=0 ,len=addPricejsonArray.length();j<len;j++)
                {
                    JSONObject jsonObject3=addPricejsonArray.getJSONObject(j);
                    JSONObject addPriceObject =jsonObject3 .getJSONObject("sku");
                    if(jsonObject3.optJSONObject("listPrice")!=null) {

                        //将每个offerPrice*quantity
                        sumItem=add(sumItem, (jsonObject3.optJSONObject("offer").optDouble("price"))*(jsonObject3.optDouble("quantity")));
                       // totalBargain= add(totalBargain, jsonObject3.optJSONObject("bargainActivity").optDouble("totalBargain"));//商家最后获取的价格-加价购商品

                    }else
                    {
                        Log.i("ordertypr",    "元");
                    }

                    if(j!=0)
                        builder.append("-").append(addPriceObject.getString("name"));
                    else builder.append(addPriceObject.getString("name"));
                    addpriceAmount= add(addpriceAmount,addPriceObject.getJSONObject("offerPrice").getDouble("price"));
                    *//*addpriceAmount+=addPriceObject.getJSONObject("offerPrice").getDouble("price");*//*
                    Log.i("addpriceName",","+addPriceObject.getJSONObject("offerPrice").getDouble("price")+"len:"+len);

                }*/


                for(int j=0 ,len=addPricejsonArray.length();j<len;j++)
                {
                    JSONObject jsonObject3=addPricejsonArray.getJSONObject(j);
                    JSONObject addPriceObject =jsonObject3 .getJSONObject("sku");
                    if(jsonObject3.optJSONObject("bargainActivity")!=null) {

                        Log.i("ordertypr",   jsonObject3.optJSONObject("bargainActivity").optDouble("totalBargain")+"元");
                        //将每个加价购的砍价的价格相加起来
                        totalBargain= add(totalBargain, jsonObject3.optJSONObject("bargainActivity").optDouble("totalBargain"));//商家最后获取的价格-加价购商品

                    }
                    if(j!=0)
                    builder.append("-").append(addPriceObject.getString("name"));
                    else builder.append(addPriceObject.getString("name"));




                    if((jsonObject2.getString("orderType").equals("commodity"))) //判断是否为商城订单
                    {

                    }else if((jsonObject2.getString("orderType").equals("combined"))||(jsonObject2.getString("orderType").equals("pay"))) //为加价购
                    {
                        Double myoffice = 0.0;
                        myoffice=(addPriceObject.getJSONObject("offerPrice").getDouble("price")) *jsonObject3.getDouble("quantity");
                        addpriceAmount= add(addpriceAmount,myoffice);
                        Log.i("Mytest--------",addpriceAmount+"");

                    }

                }

                if((jsonObject2.getString("orderType").equals("commodity"))) //判断是否为商城订单
                {
                    orderGood.setNameOfCommodity(builder.toString());
                }else   //为加价购
                {
                    orderGood.setAddpriceName(builder.toString());
                    orderGood.setAddpriceAmount(String.valueOf(addpriceAmount));
                }

                //加价购+扫码支付=门店


                if(!(jsonObject2.getString("orderType").equals("commodity"))) //判断是否为商城订单
                {
                    orderGood.setSweepPay( String.valueOf(sub(allitemprice,addpriceAmount) ));
                    Log.i("Mytesttt",allitemprice+","+addpriceAmount+","+String.valueOf( allitemprice -addpriceAmount));
                    orderGood.setItemPrice(String.valueOf(sub(allitemprice,addpriceAmount) ));

                }
                Log.i("totalQuantity1","totalQuantity"+jsonObject2.getString("totalQuantity"));
                orderGood.setItemQuantity(jsonObject2.getString("totalQuantity"));//商品总数量

               // Double temp=sub(jsonObject2.getDouble("totalFee"),addpriceAmount);////商家最后获取的价格-加价购商品 （未去掉bargin的价格）
               // orderGood.setItemPrice(String.valueOf(add(temp,totalBargain)));

                orderGood.setPayStatus(jsonObject2.getJSONObject("status").getString("code")); //支付状态

                list.add(orderGood);

            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.i("my_test",e.toString());
            Log.i("my_test","handle exception");
        }
        return list;
    }



    //插入数据
  /*  public static void InsertData(Context context, CategoryBean info, String tableName)
    {
        Log.i("Inserttable","Inserttable");
        //调用DAO辅助操作数据库
        CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(context) );
        dao.insertDB(info,tableName);
    }*/

    public   static String StringsToDate(String str)throws ParseException
    {


        long unixstamp=Long.parseLong(str);
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date dt=new Date(unixstamp);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String s = sdf.format(dt);




       /* Log.i("Mytier", "Test:"+ sdf.format(new Date( Long.parseLong("1553220069141")))
                +sdf.format(new Date( Long.parseLong("1553994027427")))) ;*/
        return s;

    }


   private  static String StringToDate(String str)
   {
   /*    String raw = "hello";
       String str1 = String.format("%1$7s", raw);
      Log.i("newStr",str1);*/

       StringBuilder sb = new StringBuilder();
       for(int i = 0; i <str.length();  i++) {

           switch (i)
           {
               case 4 :
               case 6:
                   sb.append("-");
                   break;
               case 8:
                   sb.append(" ");
                   break;
               case 10:
               case 12:
                   sb.append(":");
                   break;
                   default:
                       break;

           }
           sb.append(str.charAt(i));
       }
       Log.i("newStr1",sb.toString());

       return sb.toString();

   }


   private  static  String EngToChinese(String word)
   {
       if(word.equals("combined"))return "扫码+加价购";
       else if(word.equals("pay")) return "扫码订单";
       else if(word.equals("commodity")) return "商城订单";
       return  word;

   }

   /* private static String StringToDate(String time) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        try {
            date = format.parse(time);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }finally {
            date=new Date();
        }

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format1.format(date);
        Log.i("s",s+"");
        return time;

    }*/

    /**
     * 两个Double相加
     * @param v1
     * @param v2
     * @return
     */
    public  static Double add (Double v1,Double v2)
    {
        BigDecimal b1=new BigDecimal((v1.toString()));
        BigDecimal b2=new BigDecimal(v2.toString());
        return  b1.add(b2).doubleValue();
    }







    public  static  Double addthree(String v1,String v2 )
    {
        BigDecimal b1=new BigDecimal(v1);
        BigDecimal b2=new BigDecimal(v2);
       return   b1.add(b2) .doubleValue();


    }

    /**
     * 两个double相减
     * @param v1
     * @param v2
     * @return
     */
    public  static Double sub(Double v1,Double v2)
    {
        BigDecimal b1=new BigDecimal((v1.toString()));
        BigDecimal b2=new BigDecimal(v2.toString());
        return  b1.subtract(b2).doubleValue();
    }




}
