package top.wxy.framework.common.cache;

/**
 * @author 笼中雀
 */
public class RedisKeys {

    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return "api:captcha:" + key;
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return "api:token:" + accessToken;
    }

    public static String getManagerIdKey(Integer id) {
        return "sys:manager:" + id;
    }

}