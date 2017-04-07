package me.jessyan.mvparms.demo;

import android.os.Environment;

public class AppConfig {

    public static boolean IS_DEVELOPING = false; // APP开发状态  true-开发状态    false-正式上线状态(关闭log日志等)

    public static class Net {

        //SESSION缓存参数
        public static String DECRYPT_RANDOM = "";
        public static String ENCRYPT_RANDOM = "";
        public static String ACCESS_TOKEN = "";
        public static String COOKIE = "";

        //正式环境(上线配置)
        public static String IP = "jms.yhqz.com";
        public static String PORT = "53102";
        public static String IMAGE_URL_HEAD = "http://www.yhqz.com";//图片地址
        public static String HOST = "http://" + IP + ":" + PORT;

        public static final String CLIENT_ID = "6b8b9faf-de7b-4589-982e-0414faa39158";
        public static final String CLIENT_SECRET = "815c8dba-1fb9-4711-b574-51897151ae23";
        public static final String RESPONSE_TYPE = "code";
        public static final String GRANT_TYPE = "authorization_code";
        public static final String COOKIE_HEAD = "136a3d03-9748-4f83-a54f-9b2a93f979a1=";

        /**
         * 配置网络环境
         *
         * @param ip
         * @param port
         * @param imageHead
         */
        public static void setupConfig(String ip, String port, String imageHead) {
            IP = ip;
            PORT = port;
            IMAGE_URL_HEAD = imageHead;
            HOST = "http://" + IP + ":" + PORT;
        }
    }

    public static final String PACKAGE_NAME = "com.yhqz.onepurse";
    public static final String APP_NAME = "onepurse";

    public static final String APP_PREF = "pref_" + APP_NAME;
    public static final String MAIN_DIR = Environment.getExternalStorageDirectory() + "/" + APP_NAME;//自定义的SD卡app文件夹
    public static final String MAIN_DIR_IMG = AppConfig.MAIN_DIR + "/img";
    public static final String MAIN_DIR_CRASH = AppConfig.MAIN_DIR + "/crash";
    public static final String MAIN_DIR_CACHE = AppConfig.MAIN_DIR + "/cache";
    public static final String MAIN_DIR_CACHE_A = AppConfig.MAIN_DIR_CACHE + "/a";

    public static final String WX_APPID = "wxa71330c69d438ebb";
    public static final String WX_SECRET = "e6fc9fd31fd0f224ce16aeb4439b608b";
    public static final String QQ_APPID = "1105037126";
    public static final String QQ_APPKEY = "4m6r9wOP4WQiYy3Y";

    public static final String SERVICE_PHONE = "4009669290";

    //新浪支付的配置
    public static final String RECHARGE_SUCCESS_URL = "http://www.yhqz.com/";
    public static final String RECHARGE_FAIL_URL = "http://www.yhqz.com/";

}