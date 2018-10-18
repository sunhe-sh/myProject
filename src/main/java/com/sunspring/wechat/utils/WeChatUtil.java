package com.sunspring.wechat.utils;

import com.sunspring.wechat.dto.CreateQrcodeParamDTO;
import com.sunspring.wechat.dto.LocationReportDTO;
import com.sunspring.wechat.dto.TemplateMessageDTO;
import com.sunspring.wechat.dto.TemplateMessageDataItem;
import com.sunspring.wechat.enums.AuthorizeScopeTypeEnum;
import com.sunspring.wechat.enums.QrcodeActionNameEnum;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunhe
 */
public class WeChatUtil {

    public static final String TOKEN = "wx_service_sh";
    public static final String APP_ID = "wx132b689efeb0f7d7";
    private static final String APP_SECRET = "c346c42697194237ba92cf203658b58a";
    /**
     * 存储用户位置信息，定时清空
     * key: 微信用户openId
     * value： LocationReportDTO
     */
    public static Map<String, LocationReportDTO> locationMap = new HashMap <>();
    /**
     * 创建自定义菜单接口
     */
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    /**
     * 删除自定义菜单接口
     */
    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    /**
     * 获取基本accessToke接口
     */
    private static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 获取网页授权的accessToke接口
     */
    private static final String GET_WEB_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * 发送模板消息接口
     */
    private static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    /**
     * 生成带参数的二维码接口
     */
    private static final String QRCODE_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
    /**
     * 通过ticket换取二维码接口
     */
    private static final String QRCODE_GET_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    /**
     * 获取网页授权的用户信息接口
     */
    private static final String GET_WEB_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取用户基本信息接口（已关注的用户）（包括UnionID机制）
     */
    private static final String GET_COMMON_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取用户授权，并返回code
     */
    public static final String REDIRECT_WITH_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";




    /**
     * webAccesstoken
     */
    private static Map<String, JSONObject> webAccessTokenInfo = new HashMap <>();
    /**
     * webAccesstoken 有效期
     */
    private static Map<String, Long> web_accesstoken_expireTime = new HashMap <>();
    /**
     * 获取网页授权的 accesstoken 凭据
     * @return webAccesstokenInfo
     */
    public static JSONObject getWebAccessToken(String code) {
        //accessToken为空 或 已经失效时进行重新获取
        if(webAccessTokenInfo.get(code) == null ||
                web_accesstoken_expireTime.get(code) == null ||
                System.currentTimeMillis() > web_accesstoken_expireTime.get(code)){
            JSONObject jsonObject = HttpUtil.get(
                    GET_WEB_ACCESSTOKEN_URL.replace("APPID", APP_ID)
                            .replace("SECRET", APP_SECRET)
                            .replace("CODE", code)
            );
            if(jsonObject.get("errcode") != null){
                System.out.println(">>>getWebAccessToken>>>>>>>>>>>>>>>>>>>>"+jsonObject);
                return jsonObject;
            }
            //凭据信息
            webAccessTokenInfo.put(code, jsonObject) ;
            //有效期
            Long expires_in = jsonObject.getLong("expires_in");
            web_accesstoken_expireTime.put(code, System.currentTimeMillis() + ((expires_in - 60) * 1000));
        }
        System.out.println(">>>getWebAccessToken>>>>>>>>>>>>>>>>>>>>"+webAccessTokenInfo.get(code));
        return webAccessTokenInfo.get(code);
    }

    /**
     * 基本accesstoken
     */
    private static String base_accessToken;
    /**
     * 基本accesstoken有效期
     */
    private static Long base_accesstoken_expireTime;
    /**
     * 获取基本Accesstoken凭据
     * @return accessToken
     */
    private static String getAccessToken() {
        //accessToken为空 或 已经失效时进行重新获取
        if(StringUtils.isEmpty(WeChatUtil.base_accessToken) || System.currentTimeMillis() > WeChatUtil.base_accesstoken_expireTime){
            JSONObject jsonObject = HttpUtil.get(GET_ACCESSTOKEN_URL.replace("APPID",APP_ID).replace("APPSECRET",APP_SECRET));
            //凭据
            WeChatUtil.base_accessToken = jsonObject.getString("access_token");
            //有效期
            Long expires_in = jsonObject.getLong("expires_in");
            WeChatUtil.base_accesstoken_expireTime = System.currentTimeMillis() + ((expires_in - 60) * 1000);
        }
        System.out.println(">>>getAccessToken>>>>>>>>>>>>>>>>>>>>"+WeChatUtil.base_accessToken);
        return WeChatUtil.base_accessToken;
    }



    /**
     * 获取已关注的用户信息
     */
    public static JSONObject getCommonUserInfo(String openId) {
        JSONObject resultJson =  HttpUtil.get(
                GET_COMMON_USER_INFO_URL.replace("ACCESS_TOKEN",getAccessToken())
                        .replace("OPENID", openId)
        );
        System.out.println(">>>getCommonUserInfo>>>>>>>>>>>>>>>>>>>>"+resultJson);
        return resultJson;
    }

    /**
     * 获取网页授权的用户信息
     */
    public static JSONObject getWebUserInfo(String webAccesstoken, String openId) {
        JSONObject resultJson =  HttpUtil.get(
                GET_WEB_USER_INFO_URL.replace("ACCESS_TOKEN",webAccesstoken)
                        .replace("OPENID", openId)
        );
        System.out.println(">>>getWebUserInfo>>>>>>>>>>>>>>>>>>>>"+resultJson);
        return resultJson;
    }

    private static String jsonMenu;

    static {
        try {
            jsonMenu = "{\n" +
                    "     \"button\":[\n" +
                    "     {    \n" +
                    "          \"type\":\"scancode_push\",\n" +
                    "          \"name\":\"扫码取号\",\n" +
                    "          \"key\":\"V1001_SCANCODE_PUSH\"\n" +
                    "      },\n" +
                    "     {    \n" +
                    "          \"type\":\"view\",\n" +
                    "          \"name\":\"预约取号\",\n" +
                    "          \"url\":\""+ REDIRECT_WITH_CODE_URL
                    .replace("APPID", APP_ID)
                    .replace("REDIRECT_URI", URLEncoder.encode("http://127.0.0.1:8090/api/wechat/appointment_menu","UTF-8"))
                    .replace("SCOPE", AuthorizeScopeTypeEnum.SNSAPI_BASE.getType())
                    +"\"\n" +
                    "      },\n" +
                    "     {    \n" +
                    "          \"type\":\"view\",\n" +
                    "          \"name\":\"我的\",\n" +
                    "          \"url\":\""+ REDIRECT_WITH_CODE_URL
                    .replace("APPID", APP_ID)
                    .replace("REDIRECT_URI", URLEncoder.encode("http://127.0.0.1:8090/api/wechat/serviceHistory_view","UTF-8"))
                    .replace("SCOPE", AuthorizeScopeTypeEnum.SNSAPI_BASE.getType())
                    +"\"\n" +
                    "      }" +
                    " }";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建自定义菜单
     * @param jsonParam json数据
     */
    public static void createMenu(String jsonParam) {
        JSONObject resultJson =  HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()),jsonParam);
        System.out.println(">>>createMenu>>>>>>>>>>>>>>>>>>>>"+resultJson);
    }

    /**
     * 删除自定义菜单
     */
    public static void deleteMenu() {
        JSONObject resultJson =  HttpUtil.get(DELETE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()));
        System.out.println(">>>deleteMenu>>>>>>>>>>>>>>>>>>>>"+resultJson);
    }


    private static String jsonTemplateMsg = "{\n" +
            "           \"touser\":\"ohm_z1EjFrY2vTmLbRSK0haIV10w\",\n" +
            "           \"template_id\":\"8hnejHal2RdZruS5IF3LUgbQbimSBkgugKrw8Vo0h_k\",\n" +
            "           \"url\":\"https://www.baidu.com\",\n" +
            "           \"data\":{\n" +
            "                   \"top\": {\n" +
            "                       \"value\":\"尊敬的XXX，您好！\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"deptName\":{\n" +
            "                       \"value\":\"七里庄营业厅\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"currentNum\": {\n" +
            "                       \"value\":\"GH1024\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"serviceType\": {\n" +
            "                       \"value\":\"宽带报装\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"aheadNum\": {\n" +
            "                       \"value\":\"20人\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"ewt\": {\n" +
            "                       \"value\":\"10分钟\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   },\n" +
            "                   \"bottom\":{\n" +
            "                       \"value\":\"我们将尽快为您服务，请留意微信平台排号变化通知，中国联通温馨提醒！\",\n" +
            "                       \"color\":\"#173177\"\n" +
            "                   }\n" +
            "           }\n" +
            "       }";

    /**
     * 发送模板消息
     */
    public static void sendTemplateMessage(TemplateMessageDTO messageDTO) {
        JSONObject resultJson =  HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN",getAccessToken()),messageDTO.toString());
        System.out.println(">>>sendTemplateMessage>>>>>>>>>>>>>>>>>" + resultJson);
    }

    private static String qrcodeParam = "{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"SCAN_QRCODE_NUM\",\"scene_id\":}}}";
    /**
     * 创建带参数的公众号二维码图片
     * @param paramDTO
     * @return  ticket 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
     *          expire_seconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
     *          url 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    public static JSONObject createQrcode(CreateQrcodeParamDTO paramDTO) {
        JSONObject resultJson =  HttpUtil.post(QRCODE_CREATE_URL.replace("ACCESS_TOKEN",getAccessToken()),paramDTO.toString());
        System.out.println(">>>createQrcode>>>>>>>>>>>>>>>>>" + resultJson);
        return resultJson;
    }

    /**
     * 生成二维码图片，并返回地址
     * @param paramDTO
     */
    public static String getQrcodeUrl(CreateQrcodeParamDTO paramDTO) {
        JSONObject resultJson =  createQrcode(paramDTO);
        System.out.println("getQrcodeUrl>>>>>>>>>>>>>>" + resultJson.toString());
        if(resultJson != null && !resultJson.containsKey("errcode")){
            return QRCODE_GET_URL.replace("TICKET",  (String)resultJson.get("ticket"));
        }
        return resultJson.toString();
    }

    /**
     * 返回二维码图片url
     * @param ticket 二维码ticket
     * @return 二维码图片地址
     */
    public static String getQrcodeUrl(String ticket) {
        return QRCODE_GET_URL.replace("TICKET",  ticket);
    }

    public static void main(String[] args) {

//        getCommonUserInfo("ohm_z1EjFrY2vTmLbRSK0haIV10w");

//        createQrcode_TEST();

        createMenu(jsonMenu);

//        System.out.println(getAccessToken());

//        sendTemplateMsg_TEST();
    }

    private static void createQrcode_TEST() {
        CreateQrcodeParamDTO paramDTO = new CreateQrcodeParamDTO();
        paramDTO.setExpire_seconds(604800L);
        paramDTO.setAction_name(QrcodeActionNameEnum.QR_STR_SCENE.getActionName());
        paramDTO.setScene_str("场景id");
//        createQrcode(paramDTO);

        getQrcodeUrl(paramDTO);
    }

    private static void sendTemplateMsg_TEST(){
        Map<String, TemplateMessageDataItem> dataMap = new HashMap<>();
        TemplateMessageDataItem top = new TemplateMessageDataItem();
        top.setValue("尊敬的XXX，您好！");
        top.setColor("#173177");
        TemplateMessageDataItem bottom = new TemplateMessageDataItem();
        bottom.setValue("我们将尽快为您服务，请留意微信平台排号变化通知，中国联通温馨提醒！");
        bottom.setColor("#173177");
        dataMap.put("top", top);
        dataMap.put("bottom", bottom);
        TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();
        templateMessageDTO.setTouser("ohm_z1EjFrY2vTmLbRSK0haIV10w");
        templateMessageDTO.setTemplate_id("8hnejHal2RdZruS5IF3LUgbQbimSBkgugKrw8Vo0h_k");
        templateMessageDTO.setUrl("https://www.baidu.com");
        templateMessageDTO.setData(dataMap);

        sendTemplateMessage(templateMessageDTO);
    }
}
