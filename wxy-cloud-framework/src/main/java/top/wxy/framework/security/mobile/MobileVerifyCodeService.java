package top.wxy.framework.security.mobile;

/**
 * @author 笼中雀
 */
public interface MobileVerifyCodeService {

    boolean verifyCode(String mobile, String code);
}