package com.vee.shang.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cgd
 * @date 2017/11/23.
 */
public class RespCodeConstants {
    public static int SUCCESS = 0;
    public static int FAIL = -1;
    public static int PARAMETER_ILLEGAL = 4001;
    public static int APP_API_RESULT_CODE_4002 = 4002;
    public static int APP_API_RESULT_CODE_4003 = 4003;
    public static int APP_API_RESULT_CODE_4004 = 4004;
    public static int APP_API_RESULT_CODE_4005 = 4005;
    public static int APP_API_RESULT_CODE_4006 = 4006;
    public static int APP_API_RESULT_CODE_4007 = 4007;
    public static int APP_API_RESULT_CODE_4008 = 4008;
    public static int APP_API_RESULT_CODE_4009 = 4009;
    public static int APP_API_RESULT_CODE_4010 = 4010;
    public static int APP_API_RESULT_CODE_4011 = 4011;
    public static int APP_API_RESULT_CODE_4012 = 4012;
    public static int APP_API_RESULT_CODE_4013 = 4013;
    public static int APP_API_RESULT_CODE_4014 = 4014;
    public static int APP_API_RESULT_CODE_4015 = 4015;
    public static int APP_API_RESULT_CODE_4016 = 4016;
    public static int APP_API_RESULT_CODE_4017 = 4017;
    public static int APP_API_RESULT_CODE_4020 = 4020;
    public static String SYSNETWORK_FAIL = "网络错误，请您稍后再试";
    public static String APP_API_RESULT_MSG_4002 = "token失效";
    public static String APP_API_RESULT_MSG_4004 = "用户未注册";
    public static String APP_API_RESULT_MSG_4005 = "用户未登陆";
    public static String APP_API_RESULT_MSG_4006 = "登陆超时";
    public static String APP_API_RESULT_MSG_4007 = "deviceid非法";
    public static String APP_API_RESULT_MSG_4008 = "用户绑定退出码";
    public static String ID_ISNULL_FAIL = "ID为空";
    static Map<Integer, String> codemap = new HashMap();

    public RespCodeConstants() {
    }

    public static String getErrmsg(int errcode) {
        return (String)codemap.get(Integer.valueOf(errcode));
    }

    static {
        codemap.put(Integer.valueOf(SUCCESS), "");
        codemap.put(Integer.valueOf(FAIL), "请求错误");
        codemap.put(Integer.valueOf(PARAMETER_ILLEGAL), "非法参数");
        codemap.put(Integer.valueOf(APP_API_RESULT_CODE_4011), "sign invalid");
        codemap.put(Integer.valueOf(APP_API_RESULT_CODE_4012), "access link expired");
        codemap.put(Integer.valueOf(APP_API_RESULT_CODE_4013), "timestamp invalid");
        codemap.put(Integer.valueOf(APP_API_RESULT_CODE_4014), "apiversion is empty");
        codemap.put(Integer.valueOf(APP_API_RESULT_CODE_4015), "concurrent request error");
        codemap.put(Integer.valueOf(APP_API_RESULT_CODE_4016), "token userid illegal");
        codemap.put(Integer.valueOf(APP_API_RESULT_CODE_4017), "aes decryption exception");
    }
}

