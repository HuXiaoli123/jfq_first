package com.hxl.test_moreload.OrderFragment.Util;

import android.content.Context;
import android.net.ParseException;
import android.support.v4.app.Person;
import android.text.format.DateFormat;
import android.util.Log;

import com.hxl.test_moreload.OrderFragment.Goods.CategoryBean;
import com.hxl.test_moreload.OrderFragment.Goods.CompeleteOrder;
import com.hxl.test_moreload.OrderFragment.Goods.SweepCodeOrder;
import com.hxl.test_moreload.OrderFragment.SweepCode;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.CategoryBeanDAO;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.DBHelper;
import com.hxl.test_moreload.OrderFragment.Util.ToolDataBase.DownLoadAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

public class Tooljson {




    public static    List<CategoryBean> JsonParse(Context context ,String test)
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


    public static    List<CategoryBean> JsonParse(Context context )
    {

        return getjfqdata("content",dataSource);
    }
/*
        key:content
 */
    public static List getjfqdata(String key, String jsonString) {
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
    }


    public static List<CategoryBean> getjfqdata(String key, String jsonString,boolean is) {
        List list = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组   1.获取json对象数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                CategoryBean orderGood = new CategoryBean();
                String  orderNumber=jsonObject2.getString("code");
                orderGood.setOrderNumber(orderNumber); //订单编号orderType
                orderGood.setOderType(jsonObject2.getString("orderType")); //订单类型

                 Double allitemprice=jsonObject2.getDouble("totalFee");//总商品原价

                orderGood.setItemPrice(String.valueOf(jsonObject2.getDouble("totalFee"))); //商品原价  ----下面需要修改 因为它包括了原商品价格+加价购商品价格

                orderGood.setPlatformDeduction(String.valueOf(jsonObject2.getJSONObject("loyaltyPromotions")
                        .getDouble("totalReduction")));//平台抵扣
                orderGood.setUserPlay(String.valueOf(jsonObject2.getDouble("salesAmount")));//用户实际支付金额
                orderGood.setStoreEntry(String.valueOf(jsonObject2.getDouble("totalFee")));//门店入账

                String date = orderNumber.subSequence(3, orderNumber.length()-7).toString();

                orderGood.setPlayTime( StringToDate(date));//支付时间入账
                String addpriceName="";
                StringBuilder builder = new StringBuilder();
                Double addpriceAmount=0.0;

                JSONArray addPricejsonArray = jsonObject2.getJSONArray("items");
                for(int j=0 ,len=addPricejsonArray.length();j<len;j++)
                {
                    JSONObject addPriceObject = addPricejsonArray.getJSONObject(j).getJSONObject("sku");

                    builder.append(addPriceObject.getString("name")).append("-");

                    addpriceAmount+=addPriceObject.getJSONObject("offerPrice").getDouble("price");
                }
                orderGood.setAddpriceName(builder.toString());
                orderGood.setAddpriceAmount(String.valueOf(addpriceAmount));
                orderGood.setItemPrice(String.valueOf(jsonObject2.getDouble("totalFee")-addpriceAmount));
                Log.i("path",builder.toString()+","+addpriceAmount);

                list.add(orderGood);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("my_test",e.toString());
            Log.i("my_test","handle exception");
        }
        return list;
    }

   /* //插入数据
    public static void InsertData(Context context, SweepCodeOrder info, String tableName)
    {
        Log.i("Inserttable","Inserttable");
        //调用DAO辅助操作数据库
        CategoryBeanDAO dao=new CategoryBeanDAO(new DBHelper(context) );
        dao.insertDB(info,tableName);
    }*/

    private static String StringToDate(String time) throws ParseException {

        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
      *//*  try {
            date = format.parse(time);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }finally {
            date=new Date();
        }*//*

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format1.format(date);
        Log.i("s",s+"");*/
        return time;

    }


    public static  String dataSource= "{\n" +
            "\t\"request\": {\n" +
            "\t\t\"page\": 0,\n" +
            "\t\t\"size\": 10,\n" +
            "\t\t\"orders\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"direction\": \"DESC\",\n" +
            "\t\t\t\t\"property\": \"createTime\",\n" +
            "\t\t\t\t\"ignoreCase\": false\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t\"totalPages\": 1,\n" +
            "\t\"totalElements\": 3,\n" +
            "\t\"number\": 3,\n" +
            "\t\"content\": [\n" +
            "    {\n" +
            "      \"application\": {\n" +
            "        \"id\": \"5acfdb7262c712621cf7963a\",\n" +
            "        \"code\": \"app201804130619300284603\",\n" +
            "        \"name\": \"积分圈扫码支付\", // -------------------------------------支付方式\n" +
            "        \"partner\": {\n" +
            "          \"id\": \"5aceacbd587f188fe85cb438\",\n" +
            "          \"code\": \"jfc-pay\",\n" +
            "          \"name\": \"积分圈付款\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"store\": {\n" +
            "        \"id\": \"5bf8fa0f86fed5617b5d7793\",\n" +
            "        \"code\": \"str201811241513190330412\",\n" +
            "        \"name\": \"积分圈技术内测门店\",\n" +
            "        \"partner\": {\n" +
            "          \"id\": \"5aceacbd587f188fe85cb438\",\n" +
            "          \"code\": \"jfc\",\n" +
            "          \"name\": \"积分圈\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"totalFee\": 0.56, // -----------------扫码支付支付的总金额\n" +
            "      \"otherFee\": 0.0,\n" +
            "      \"virtual\": null,\n" +
            "      \"delivery\": null,\n" +
            "      \"invoice\": null,\n" +
            "      \"tradeType\": \"NATIVE\",\n" +
            "      \"orderType\": \"pay\",\n" +
            "      \"items\": [\n" +
            "      ],\n" +
            "      \"paymentMethod\": \"wepay\",\n" +
            "      \"customerIp\": \"172.31.149.124\",\n" +
            "      \"member\": {\n" +
            "        \"id\": \"5ae282bc62c7127ee66a3af7\",\n" +
            "        \"code\": \"mmb201804270954040136971\",\n" +
            "        \"name\": \"18655362962\",\n" +
            "        \"realName\": null,\n" +
            "        \"icon\": \"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epGhcTibyH7n2QEuHqJqKia8kugpT9icqUZRhX756f9MMRRubXhlw7Ymhfs9CeC0K2UgUvN1KELR0hRQ/132\",\n" +
            "        \"openId\": \"oB-09xCk_VG6Ox_k3NVlk-fQFs0s\",\n" +
            "        \"status\": null\n" +
            "      },\n" +
            "      \"body\": null,\n" +
            "      \"detail\": null,\n" +
            "      \"id\": \"5c20991086fed52420b0a727\",\n" +
            "      \"code\": \"ord201812241630080286927\", // ----------------------订单编号\n" +
            "      \"startTime\": null,\n" +
            "      \"expireTime\": null,\n" +
            "      \"returnUrl\": null,\n" +
            "      \"salesAmount\": 0.29, // --------------------------实际支付金额\n" +
            "      \"createTime\": 1545640208286,\n" +
            "      \"status\": {\n" +
            "        \"code\": \"paid\",\n" +
            "        \"name\": \"已支付\" // -----------------------支付状态\n" +
            "      },\n" +
            "      \"memberPromotions\": null,\n" +
            "      \"couponPromotions\": null,\n" +
            "      \"loyaltyPromotions\": {\n" +
            "        \"totalReduction\": 0.27, //------------------------积分抵扣金额\n" +
            "        \"loyalties\": [\n" +
            "          {\n" +
            "            \"id\": \"5b57ca1a62c7120947f1fc3c\",\n" +
            "            \"loyaltyPartner\": {\n" +
            "              \"id\": \"5b57ca1a62c7120947f1fc3c\",\n" +
            "              \"code\": \"ltp201807250853460526641\",\n" +
            "              \"name\": \"积蛋消费随机减活动\",\n" +
            "              \"issue\": {\n" +
            "                \"id\": \"5aceb8df62c7120880dbca63\",\n" +
            "                \"code\": \"iss201804120939430471500\",\n" +
            "                \"name\": \"积蛋\",\n" +
            "                \"channel\": {\n" +
            "                  \"id\": \"5aceaa3f587f188fe85cace0\",\n" +
            "                  \"code\": \"jfc\",\n" +
            "                  \"name\": \"积分圈\"\n" +
            "                }\n" +
            "              }\n" +
            "            },\n" +
            "            \"account\": {\n" +
            "              \"id\": \"5ae282bc62c7127ee66a3af8\",\n" +
            "              \"code\": null,\n" +
            "              \"member\": null,\n" +
            "              \"balance\": 364.0\n" +
            "            },\n" +
            "            \"appliedAward\": {\n" +
            "              \"type\": \"randomReduce\",\n" +
            "              \"allowUserChange\": false\n" +
            "            },\n" +
            "            \"consumption\": 10.0, //-------------------------消耗10个积蛋\n" +
            "            \"reduction\": 0.27 //-------------------------积蛋抵扣金额\n" +
            "          },\n" +
            "          {\n" +
            "            \"id\": \"5b570c8d62c7120947f1fc3b\",\n" +
            "            \"loyaltyPartner\": {\n" +
            "              \"id\": \"5b570c8d62c7120947f1fc3b\",\n" +
            "              \"code\": \"ltp201807241925010420828\",\n" +
            "              \"name\": \"积蛋消费比例赠送活动\",\n" +
            "              \"issue\": {\n" +
            "                \"id\": \"5aceb8df62c7120880dbca63\",\n" +
            "                \"code\": \"iss201804120939430471500\",\n" +
            "                \"name\": \"积蛋\",\n" +
            "                \"channel\": {\n" +
            "                  \"id\": \"5aceaa3f587f188fe85cace0\",\n" +
            "                  \"code\": \"jfc\",\n" +
            "                  \"name\": \"积分圈\"\n" +
            "                }\n" +
            "              }\n" +
            "            },\n" +
            "            \"account\": {\n" +
            "              \"id\": \"5ae282bc62c7127ee66a3af8\",\n" +
            "              \"code\": null,\n" +
            "              \"member\": null,\n" +
            "              \"balance\": 364.0\n" +
            "            },\n" +
            "            \"appliedAward\": {\n" +
            "              \"type\": \"rateLoyaltyGift\",\n" +
            "              \"allowUserChange\": false\n" +
            "            },\n" +
            "            \"consumption\": null,\n" +
            "            \"reduction\": null\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"totalQuantity\": 0,\n" +
            "      \"totalCommission\": 0\n" +
            "    },      \n" +
            "    //上面从application开始是一单的支付节点\n" +
            "\n" +
            "\n" +
            "\n" +
            "    {\n" +
            "      \"application\": {\n" +
            "        \"id\": \"5acfdb7262c712621cf7963a\",\n" +
            "        \"code\": \"app201804130619300284603\",\n" +
            "        \"name\": \"积分圈扫码支付\", //-------------------支付方式(订单类型)\n" +
            "        \"partner\": {\n" +
            "          \"id\": \"5aceacbd587f188fe85cb438\",\n" +
            "          \"code\": \"jfc-pay\",\n" +
            "          \"name\": \"积分圈付款\"\n" +
            "        }\n" +
            "      }, //支付方式\n" +
            "      \"store\": {\n" +
            "        \"id\": \"5bf8fa0f86fed5617b5d7793\",\n" +
            "        \"code\": \"str201811241513190330412\",\n" +
            "        \"name\": \"积分圈技术内测门店\",\n" +
            "        \"partner\": {\n" +
            "          \"id\": \"5aceacbd587f188fe85cb438\",\n" +
            "          \"code\": \"jfc\",\n" +
            "          \"name\": \"积分圈\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"totalFee\": 6.2, //-------------------总金额\n" +
            "      \"otherFee\": 0.0,\n" +
            "      \"virtual\": null,\n" +
            "      \"delivery\": null,\n" +
            "      \"invoice\": null,\n" +
            "      \"tradeType\": \"NATIVE\",\n" +
            "      \"orderType\": \"combined\",\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"sku\": {\n" +
            "            \"id\": \"5c1c9f0c86fed57c55dd11bd\",\n" +
            "            \"code\": \"mku201812211606360886554\",\n" +
            "            \"name\": \"测试商品2\",\n" +
            "            \"listPrice\": {\n" +
            "              \"code\": \"list\",\n" +
            "              \"name\": \"列表价\",\n" +
            "              \"price\": 1.5\n" +
            "            },\n" +
            "            \"offerPrice\": {\n" +
            "              \"code\": \"offer\",\n" +
            "              \"name\": \"销售价\",\n" +
            "              \"price\": 1.2\n" +
            "            },\n" +
            "            \"merchandise\": {\n" +
            "              \"id\": \"5c1c9f0c86fed57c55dd11bc\",\n" +
            "              \"code\": \"mcd201812211606360883256\",\n" +
            "              \"name\": \"测试商品2\",\n" +
            "              \"title\": \"测试商品2\",\n" +
            "              \"imageUrls\": [\n" +
            "                \"http://images.tuihs.cn/images/mall/CS_17775474113/CS2.jpg\"\n" +
            "              ],\n" +
            "              \"subtitle\": \"测试商品2\",\n" +
            "              \"unit\": null\n" +
            "            },\n" +
            "            \"imageUrls\": [\n" +
            "              \"http://images.tuihs.cn/images/mall/CS_17775474113/CS2.jpg\"\n" +
            "            ],\n" +
            "            \"productSku\": {\n" +
            "              \"id\": \"5c1c9ed086fed57c55dd11bb\",\n" +
            "              \"code\": \"pku201812211605360254876\",\n" +
            "              \"name\": \"测试商品2\",\n" +
            "              \"specifications\": [\n" +
            "                {\n" +
            "                  \"@class\": \"com.jfc.store.vo.order.merchandise.attribute.SelectedValue\",\n" +
            "                  \"attribute\": {\n" +
            "                    \"@class\": \"com.jfc.store.vo.order.merchandise.attribute.SelectableAttribute\",\n" +
            "                    \"code\": \"product\",\n" +
            "                    \"name\": \"产品\",\n" +
            "                    \"valueType\": \"single\",\n" +
            "                    \"options\": null\n" +
            "                  },\n" +
            "                  \"value\": {\n" +
            "                    \"code\": \"CS2_CS_17775474113\",\n" +
            "                    \"name\": \"测试商品2\",\n" +
            "                    \"selectable\": null\n" +
            "                  }\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          },\n" +
            "          \"bargainActivity\": null,\n" +
            "          \"distribution\": null,\n" +
            "          \"commission\": null,\n" +
            "          \"quantity\": 1.0\n" +
            "        }\n" +
            "      ],\n" +
            "      \"paymentMethod\": \"wepay\",\n" +
            "      \"customerIp\": \"36.60.17.152\",\n" +
            "      \"member\": {\n" +
            "        \"id\": \"5b32dd3762c7120fd8eb698a\",\n" +
            "        \"code\": \"mmb201806270841270186349\",\n" +
            "        \"name\": \"18155336419\",\n" +
            "        \"realName\": null,\n" +
            "        \"icon\": \"http://thirdwx.qlogo.cn/mmopen/vi_32/T9etGe2KJFbVKL63GU30gWIA17Fy2f1Hib5QQ3G0DbVgs2Hb5olGmNn1k0fnib33zdA6X5ib8ylWmYjkdC3wNjdqQ/132\",\n" +
            "        \"openId\": \"oB-09xBMdQleZU0xIDvJnKOhsps0\",\n" +
            "        \"status\": null\n" +
            "      },\n" +
            "      \"body\": null,\n" +
            "      \"detail\": \"测试商品2\",\n" +
            "      \"id\": \"5c2097a286fed52420b0a71e\",\n" +
            "      \"code\": \"ord201812241624020030627\", //-------------------订单编号\n" +
            "      \"startTime\": null,\n" +
            "      \"expireTime\": null,\n" +
            "      \"returnUrl\": null,\n" +
            "      \"salesAmount\": 1.95, //-------------------用户实际支付金额\n" +
            "      \"createTime\": 1545639842030,\n" +
            "      \"status\": {\n" +
            "        \"code\": \"paid\",\n" +
            "        \"name\": \"已支付\" //-------------------支付支付状态\n" +
            "      }, //支付状态\n" +
            "      \"memberPromotions\": null,\n" +
            "      \"couponPromotions\": null,\n" +
            "      \"loyaltyPromotions\": {\n" +
            "        \"totalReduction\": 4.25, //-------------------总共抵扣金额\n" +
            "        \"loyalties\": [\n" +
            "          {\n" +
            "            \"id\": \"5af2970562c712671978717b\",\n" +
            "            \"loyaltyPartner\": {\n" +
            "              \"id\": \"5af2970562c712671978717b\",\n" +
            "              \"code\": \"ltp201805091436530892244\",\n" +
            "              \"name\": \"积分圈私房钱抵扣活动\",\n" +
            "              \"issue\": {\n" +
            "                \"id\": \"5aceb40d62c7120880dbca62\",\n" +
            "                \"code\": \"iss201804120919090273985\",\n" +
            "                \"name\": \"私房钱\", //------------------抵扣方式私房钱\n" +
            "                \"channel\": {\n" +
            "                  \"id\": \"5aceaa3f587f188fe85cace0\",\n" +
            "                  \"code\": \"jfc\",\n" +
            "                  \"name\": \"积分圈\"\n" +
            "                }\n" +
            "              }\n" +
            "            },\n" +
            "            \"account\": {\n" +
            "              \"id\": \"5b32dd3762c7120fd8eb698b\",\n" +
            "              \"code\": null,\n" +
            "              \"member\": null,\n" +
            "              \"balance\": 12.22\n" +
            "            },\n" +
            "            \"appliedAward\": {\n" +
            "              \"type\": \"rateReduce\",\n" +
            "              \"allowUserChange\": true\n" +
            "            },\n" +
            "            \"consumption\": 4.0, //消耗金额\n" +
            "            \"reduction\": 4.0\n" +
            "          }, //这是第一个抵扣方式  ：私房钱\n" +
            "          {\n" +
            "            \"id\": \"5b57ca1a62c7120947f1fc3c\",\n" +
            "            \"loyaltyPartner\": {\n" +
            "              \"id\": \"5b57ca1a62c7120947f1fc3c\",\n" +
            "              \"code\": \"ltp201807250853460526641\",\n" +
            "              \"name\": \"积蛋消费随机减活动\",\n" +
            "              \"issue\": {\n" +
            "                \"id\": \"5aceb8df62c7120880dbca63\",\n" +
            "                \"code\": \"iss201804120939430471500\",\n" +
            "                \"name\": \"积蛋\",\n" +
            "                \"channel\": {\n" +
            "                  \"id\": \"5aceaa3f587f188fe85cace0\",\n" +
            "                  \"code\": \"jfc\",\n" +
            "                  \"name\": \"积分圈\"\n" +
            "                }\n" +
            "              }\n" +
            "            },\n" +
            "            \"account\": {\n" +
            "              \"id\": \"5b32dd3762c7120fd8eb698c\",\n" +
            "              \"code\": null,\n" +
            "              \"member\": null,\n" +
            "              \"balance\": 17.0\n" +
            "            },\n" +
            "            \"appliedAward\": {\n" +
            "              \"type\": \"randomReduce\",\n" +
            "              \"allowUserChange\": false\n" +
            "            },\n" +
            "            \"consumption\": 10.0, //---消耗10个积蛋\n" +
            "            \"reduction\": 0.25 //---积蛋总抵扣金额0.25\n" +
            "          } //这是第二个抵扣方式   ：积蛋\n" +
            "        ] //各种不同抵扣金额和方式\n" +
            "      }, //总抵扣金额、各种不同抵扣金额和方式\n" +
            "      \"totalQuantity\": 1.0,\n" +
            "      \"totalCommission\": 0 //总佣金0？？？？？？\n" +
            "    },\n" +
            "\n" +
            "\n" +
            "\n" +
            "     \n" +
            "\n" +
            "   //第三个节点 \n" +
            "\t\t{\n" +
            "\t\t\t\"application\": {\n" +
            "\t\t\t\t\"id\": \"5acfdb7262c712621cf7963a\",\n" +
            "\t\t\t\t\"code\": \"app201804130619300284603\",\n" +
            "\t\t\t\t\"name\": \"积分圈扫码支付\",\n" +
            "\t\t\t\t\"partner\": {\n" +
            "\t\t\t\t\t\"id\": \"5aceacbd587f188fe85cb438\",\n" +
            "\t\t\t\t\t\"code\": \"jfc-pay\",\n" +
            "\t\t\t\t\t\"name\": \"积分圈付款\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t\"store\": {\n" +
            "\t\t\t\t\"id\": \"5bf8fa0f86fed5617b5d7793\",\n" +
            "\t\t\t\t\"code\": \"str201811241513190330412\",\n" +
            "\t\t\t\t\"name\": \"积分圈技术内测门店\",\n" +
            "\t\t\t\t\"partner\": {\n" +
            "\t\t\t\t\t\"id\": \"5aceacbd587f188fe85cb438\",\n" +
            "\t\t\t\t\t\"code\": \"jfc\",\n" +
            "\t\t\t\t\t\"name\": \"积分圈\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t\"totalFee\": 0.1,\n" +
            "\t\t\t\"otherFee\": 0.0,\n" +
            "\t\t\t\"virtual\": null,\n" +
            "\t\t\t\"delivery\": null,\n" +
            "\t\t\t\"invoice\": null,\n" +
            "\t\t\t\"tradeType\": \"NATIVE\",\n" +
            "\t\t\t\"orderType\": \"pay\",\n" +
            "\t\t\t\"items\": [\n" +
            "\t\t\t],\n" +
            "\t\t\t\"paymentMethod\": \"wepay\",\n" +
            "\t\t\t\"customerIp\": \"172.31.149.124\",\n" +
            "\t\t\t\"member\": {\n" +
            "\t\t\t\t\"id\": \"5ae282bc62c7127ee66a3af7\",\n" +
            "\t\t\t\t\"code\": \"mmb201804270954040136971\",\n" +
            "\t\t\t\t\"name\": \"18655362962\",\n" +
            "\t\t\t\t\"realName\": null,\n" +
            "\t\t\t\t\"icon\": \"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epGhcTibyH7n2QEuHqJqKia8kugpT9icqUZRhX756f9MMRRubXhlw7Ymhfs9CeC0K2UgUvN1KELR0hRQ/132\",\n" +
            "\t\t\t\t\"openId\": \"oB-09xCk_VG6Ox_k3NVlk-fQFs0s\",\n" +
            "\t\t\t\t\"status\": null\n" +
            "\t\t\t},\n" +
            "\t\t\t\"body\": null,\n" +
            "\t\t\t\"detail\": null,\n" +
            "\t\t\t\"id\": \"5bfe8a3386fed5668b1fa152\",\n" +
            "\t\t\t\"code\": \"ord201811282029390166217\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"expireTime\": null,\n" +
            "\t\t\t\"returnUrl\": null,\n" +
            "\t\t\t\"salesAmount\": 0.0,\n" +
            "\t\t\t\"createTime\": 1543408179166,\n" +
            "\t\t\t\"status\": {\n" +
            "\t\t\t\t\"code\": \"paid\",\n" +
            "\t\t\t\t\"name\": \"已支付\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"memberPromotions\": null,\n" +
            "\t\t\t\"couponPromotions\": null,\n" +
            "\t\t\t\"loyaltyPromotions\": {\n" +
            "\t\t\t\t\"totalReduction\": 0.1,\n" +
            "\t\t\t\t\"loyalties\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"id\": \"5b57ca1a62c7120947f1fc3c\",\n" +
            "\t\t\t\t\t\t\"loyaltyPartner\": {\n" +
            "\t\t\t\t\t\t\t\"id\": \"5b57ca1a62c7120947f1fc3c\",\n" +
            "\t\t\t\t\t\t\t\"code\": \"ltp201807250853460526641\",\n" +
            "\t\t\t\t\t\t\t\"name\": \"积蛋消费随机减活动\",\n" +
            "\t\t\t\t\t\t\t\"issue\": {\n" +
            "\t\t\t\t\t\t\t\t\"id\": \"5aceb8df62c7120880dbca63\",\n" +
            "\t\t\t\t\t\t\t\t\"code\": \"iss201804120939430471500\",\n" +
            "\t\t\t\t\t\t\t\t\"name\": \"积蛋\",\n" +
            "\t\t\t\t\t\t\t\t\"channel\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"id\": \"5aceaa3f587f188fe85cace0\",\n" +
            "\t\t\t\t\t\t\t\t\t\"code\": \"jfc\",\n" +
            "\t\t\t\t\t\t\t\t\t\"name\": \"积分圈\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"account\": {\n" +
            "\t\t\t\t\t\t\t\"id\": \"5ae282bc62c7127ee66a3af8\",\n" +
            "\t\t\t\t\t\t\t\"code\": null,\n" +
            "\t\t\t\t\t\t\t\"member\": null,\n" +
            "\t\t\t\t\t\t\t\"balance\": 378.0\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"appliedAward\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"randomReduce\",\n" +
            "\t\t\t\t\t\t\t\"allowUserChange\": false\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"consumption\": 10.0,\n" +
            "\t\t\t\t\t\t\"reduction\": 0.1\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"id\": \"5b570c8d62c7120947f1fc3b\",\n" +
            "\t\t\t\t\t\t\"loyaltyPartner\": {\n" +
            "\t\t\t\t\t\t\t\"id\": \"5b570c8d62c7120947f1fc3b\",\n" +
            "\t\t\t\t\t\t\t\"code\": \"ltp201807241925010420828\",\n" +
            "\t\t\t\t\t\t\t\"name\": \"积蛋消费比例赠送活动\",\n" +
            "\t\t\t\t\t\t\t\"issue\": {\n" +
            "\t\t\t\t\t\t\t\t\"id\": \"5aceb8df62c7120880dbca63\",\n" +
            "\t\t\t\t\t\t\t\t\"code\": \"iss201804120939430471500\",\n" +
            "\t\t\t\t\t\t\t\t\"name\": \"积蛋\",\n" +
            "\t\t\t\t\t\t\t\t\"channel\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"id\": \"5aceaa3f587f188fe85cace0\",\n" +
            "\t\t\t\t\t\t\t\t\t\"code\": \"jfc\",\n" +
            "\t\t\t\t\t\t\t\t\t\"name\": \"积分圈\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"account\": {\n" +
            "\t\t\t\t\t\t\t\"id\": \"5ae282bc62c7127ee66a3af8\",\n" +
            "\t\t\t\t\t\t\t\"code\": null,\n" +
            "\t\t\t\t\t\t\t\"member\": null,\n" +
            "\t\t\t\t\t\t\t\"balance\": 378.0\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"appliedAward\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"rateLoyaltyGift\",\n" +
            "\t\t\t\t\t\t\t\"allowUserChange\": false\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"consumption\": null,\n" +
            "\t\t\t\t\t\t\"reduction\": null\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t\t},\n" +
            "\t\t\t\"totalQuantity\": 0,\n" +
            "\t\t\t\"totalCommission\": 0\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}\n";

}
