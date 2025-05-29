package top.wxy.sms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 笼中雀
 */
public class SmsUtils {
    /**
     * 校验⼿机号码
     *
     * @param mobile 电话
     * @return boolean
     */
    public static boolean checkPhone(String mobile) {
        if (mobile == null || mobile.length() != 11) {
            return false;
        }
        // 中国⼤陆⼿机号码的正则表达式，可能需要根据实际情况进⾏调整
        String regex = "^1[3-9]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }
}