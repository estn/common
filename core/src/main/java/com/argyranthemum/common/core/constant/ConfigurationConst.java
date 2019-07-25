package com.argyranthemum.common.core.constant;

/**
 * Description:配置常量类，本类中的数据需要启动时设置
 * Author: Estn
 * CreateTime: 2014-07-10 01:30
 */
public class ConfigurationConst {

    private ConfigurationConst() {
    }

    /**
     * 资源访问的URL地址,
     */
    public static String RESOURCE_ROOT_URL;

    /**
     * 资源存放文件夹的路径,
     */
    public static String RESOURCE_ROOT_PATH;

    /**
     * token有效期，单位秒
     */
    public static long TOKEN_EXPIRY = 7776000;


    /**
     * 微信支付
     */
    public static String WXPAY_APPID;

    public static String WXPAY_MCHID;

    public static String WXPAY_KEY;

    public static String WXPAY_NOTIFY_URL;

    public static String WXPAY_P12;

    /**
     * 支付宝支付
     */
    public static String ALIPAY_APPID;

    public static String ALIPAY_APP_PRIVATE_KEY;

    public static String ALIPAY_APP_PUBLIC_KEY;

    public static String ALIPAY_ALIPAY_PUBLIC_KEY;

    public static String ALIPAY_NOTIFY_URL;

    /**
     * 当前系统环境
     */
    public static String ENV;

    public static Boolean IS_RELEASE = true;

    public static String DEV = "dev";

    public static String TEST = "test";

    public static String RELEASE = "release";

}
