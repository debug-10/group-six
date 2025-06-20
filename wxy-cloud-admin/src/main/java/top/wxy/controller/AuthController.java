package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.framework.common.utils.Result;
import top.wxy.model.vo.SysAccountLoginVO;
import top.wxy.model.vo.SysTokenVO;
import top.wxy.security.utils.TokenUtils;
import top.wxy.service.AuthService;

/**
 * @author 笼中雀
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("sys/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    public Result<SysTokenVO> login(@RequestBody SysAccountLoginVO login) {
        return Result.ok(authService.loginByAccount(login));
    }


    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout(HttpServletRequest request) {
        authService.logout(TokenUtils.getAccessToken(request));

        return Result.ok();
    }

}
