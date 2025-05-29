package top.wxy.service;

import top.wxy.dto.MobileLoginDTO;
import top.wxy.dto.AccountLoginDTO;
import top.wxy.vo.AccountLoginVO;
import top.wxy.vo.MobileLoginVO;

/**
 * @author 笼中雀
 */
public interface AuthService {
    /**
     * 账号密码登录
     *
     * @param login 登录信息
     */
    AccountLoginVO loginByAccount(AccountLoginDTO login);
    /**
     * ⼿机短信登录
     *
     * @param login 登录信息
     */
    MobileLoginVO loginByMobile(MobileLoginDTO login);
    /**
     * 退出登录
     *
     * @param accessToken accessToken
     */
    void logout(String accessToken);
}
