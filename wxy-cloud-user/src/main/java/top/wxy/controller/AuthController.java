package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.dto.MobileLoginDTO;
import top.wxy.dto.AccountLoginDTO;
import top.wxy.framework.common.exception.ErrorCode;
import top.wxy.framework.common.utils.Result;
import top.wxy.framework.security.utils.TokenUtils;
import top.wxy.service.AuthService;
import top.wxy.sms.service.RonglianyunSmsService;
import top.wxy.vo.AccountLoginVO;
import top.wxy.vo.MobileLoginVO;


/**
 * @author 笼中雀
 */
@RestController
@RequestMapping("api/auth")
@Tag(name = "认证模块")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    //private final AliyunSmsService smsService;
    private final RonglianyunSmsService smsService;
    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    public Result<AccountLoginVO> accountLogin(@RequestBody AccountLoginDTO login) {
        AccountLoginVO accountLoginVO = authService.loginByAccount(login);
        return Result.ok(accountLoginVO);
    }
    @PostMapping("send/code")
    @Operation(summary = "发送短信验证码")
    public Result<String> sendCode(String mobile) {
        boolean flag = smsService.sendSms(mobile);
        if (!flag) {
            return Result.error(ErrorCode.CODE_SEND_FAIL);
        }
        return Result.ok();
    }
    @PostMapping("mobile")
    @Operation(summary = "⼿机号登录")
    public Result<MobileLoginVO> mobile(@RequestBody MobileLoginDTO login)
    {
        MobileLoginVO mobileLoginVO = authService.loginByMobile(login);
        return Result.ok(mobileLoginVO);
    }
    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout(HttpServletRequest request) {
        authService.logout(TokenUtils.getAccessToken(request));
        return Result.ok();
    }
}
