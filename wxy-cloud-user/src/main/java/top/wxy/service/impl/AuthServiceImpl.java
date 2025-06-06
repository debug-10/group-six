package top.wxy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.wxy.convert.UserConvert;
import top.wxy.dto.AccountLoginDTO;
import top.wxy.dto.MobileLoginDTO;
import top.wxy.entity.UserEntity;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.security.cache.TokenStoreCache;
import top.wxy.framework.security.mobile.MobileAuthenticationToken;
import top.wxy.framework.security.user.UserDetail;
import top.wxy.framework.security.utils.JwtUtil;
import top.wxy.service.AuthService;
import top.wxy.service.UserService;
import top.wxy.vo.AccountLoginVO;
import top.wxy.vo.MobileLoginVO;
import top.wxy.vo.UserVO;


/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenStoreCache tokenStoreCache;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AccountLoginVO loginByAccount(AccountLoginDTO login) {
        Authentication authentication;
        try {
            // ⽤户认证
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ServerException("⽤户名或密码错误");
        }
        // ⽤户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();
        // ⽣成 accessToken
        String accessToken = JwtUtil.createToken(user.getId());
        // 保存⽤户信息到缓存
        tokenStoreCache.saveUser(accessToken, user);
        AccountLoginVO accountLoginVO = new AccountLoginVO();
        accountLoginVO.setId(user.getId());
        accountLoginVO.setAccessToken(accessToken);
        accountLoginVO.setUsername(user.getUsername());
        return accountLoginVO;
    }
    @Override
    public MobileLoginVO loginByMobile(MobileLoginDTO login) {
        UserVO userVO = userService.getByPhone(login.getMobile());
        if (userVO == null) {
            UserEntity entity = UserConvert.INSTANCE.convert(login);
            entity.setUsername(login.getMobile());
            entity.setPassword(passwordEncoder.encode("123456"));
            entity.setNickname("新⽤户");
            entity.setAvatarUrl("https://my-wxy-bucket.oss-cn-nanjing.aliyuncs.com/picture/liang.jpg");
            entity.setRole(3);
            entity.setPhone(login.getMobile());
            userService.save(entity);
            userVO = userService.getByPhone(login.getMobile());
        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new MobileAuthenticationToken(login.getMobile(), login.getCode()));
        } catch (BadCredentialsException e) {
            throw new ServerException("⼿机号或验证码错误");
        }
        // ⽤户信息
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String accessToken = JwtUtil.createToken(userDetail.getId());
        // 保存⽤户信息到缓存
        tokenStoreCache.saveUser(accessToken, userDetail);
        MobileLoginVO mobileLoginVO = new MobileLoginVO();
        mobileLoginVO.setId(userDetail.getId());
        mobileLoginVO.setAccessToken(accessToken);
        if (userVO != null) {
            mobileLoginVO.setMobile(userVO.getPhone());
        }
        return mobileLoginVO;
    }
    @Override
    public void logout(String accessToken) {
        // 删除⽤户信息
        tokenStoreCache.deleteUser(accessToken);
    }
}

