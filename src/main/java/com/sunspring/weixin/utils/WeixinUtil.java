package com.sunspring.weixin.utils;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WeixinUtil {

    public static final String TOKEN = "wx_service_sh";
    public static final String APP_ID = "wx132b689efeb0f7d7";
    public static final String APP_SECRET = "c346c42697194237ba92cf203658b58a";
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static String accessToken;
    private static Long expireTime;
    /**
     * 获取Accesstoken凭据
     * @return accessToken
     */
    public static String getAccessToken() {
        //accessToken为空 或 已经失效时进行重新获取
        if(StringUtils.isEmpty(WeixinUtil.accessToken) || System.currentTimeMillis() > WeixinUtil.expireTime){
            JSONObject jsonObject = HttpUtil.get(GET_ACCESSTOKEN_URL.replace("APPID",APP_ID).replace("APPSECRET",APP_SECRET));
            //凭据
            WeixinUtil.accessToken = jsonObject.getString("access_token");
            //有效期
            Long expires_in = jsonObject.getLong("expires_in");
            WeixinUtil.expireTime = System.currentTimeMillis() + ((expires_in - 60) * 1000);
        }
        return WeixinUtil.accessToken;
    }

    private static String json = "{\n" +
            "     \"button\":[\n" +
            "     {    \n" +
            "          \"type\":\"click\",\n" +
            "          \"name\":\"当前时间\",\n" +
            "          \"key\":\"V1001_CURRENT_TIME\"\n" +
            "      },\n" +
            "      {\n" +
            "           \"name\":\"菜单\",\n" +
            "           \"sub_button\":[\n" +
            "           {    \n" +
            "               \"type\":\"view\",\n" +
            "               \"name\":\"搜索\",\n" +
            "               \"url\":\"http://www.soso.com/\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"type\":\"click\",\n" +
            "               \"name\":\"赞一下我们\",\n" +
            "               \"key\":\"V1001_GOOD\"\n" +
            "            }]\n" +
            "       }]\n" +
            " }";
    public static void createMenu(String jsonParam) {
        JSONObject resultJson =  HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()),jsonParam);
        System.out.println(">>>>>>>>>>>>>>>>>>>>"+resultJson);
    }

    public static void main(String[] args) {
        createMenu(json);
//        System.out.println(WeixinUtil.accessToken);
//        System.out.println(getAccessToken());
//        System.out.println(getAccessToken());
//        System.out.println(getAccessToken());
    }
}
