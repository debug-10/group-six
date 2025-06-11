package top.wxy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.security.cache.TokenStoreCache;
import top.wxy.framework.security.user.UserDetail;
import top.wxy.model.vo.SysAccountLoginVO;
import top.wxy.model.vo.SysTokenVO;
import top.wxy.security.user.ManagerDetail;
import top.wxy.security.utils.TokenUtils;
import top.wxy.service.AuthService;

/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenStoreCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;

    @Override
    public SysTokenVO loginByAccount(SysAccountLoginVO params) {
        Authentication authentication;
        try {
            // 用户认证
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(params.getUsername(), params.getPassword());
            // 执行认证
            authentication = authenticationManager.authenticate(authToken);
        } catch (DisabledException e) {
            throw new ServerException("该账号已被禁用");
        } catch (BadCredentialsException e) {
            throw new ServerException("用户名或密码错误");
        }

        ManagerDetail managerDetail = (ManagerDetail) authentication.getPrincipal();

        UserDetail user = new UserDetail();
        user.setId(managerDetail.getId().longValue());
        user.setUsername(managerDetail.getUsername());
        user.setPassword(managerDetail.getPassword());
        user.setEnabled(managerDetail.isEnabled());
        user.setAccountNonExpired(managerDetail.isAccountNonExpired());
        user.setAccountNonLocked(managerDetail.isAccountNonLocked());
        user.setCredentialsNonExpired(managerDetail.isCredentialsNonExpired());
        user.setAuthoritySet(managerDetail.getAuthoritySet());

        String accessToken = TokenUtils.generator();
        tokenStoreCache.saveUser(accessToken, user);
    
        // 构建用户信息
        SysTokenVO.UserInfo userInfo = new SysTokenVO.UserInfo();
        userInfo.setId(managerDetail.getId());
        userInfo.setUsername(managerDetail.getUsername());
        userInfo.setNickname(managerDetail.getNickname());
        userInfo.setPhone(managerDetail.getPhone());
        userInfo.setAvatarUrl(managerDetail.getAvatarUrl());
        userInfo.setTenantId(managerDetail.getTenantId());
        userInfo.setRole(managerDetail.getRole());
        userInfo.setStatus(managerDetail.getStatus());

        return new SysTokenVO(accessToken, userInfo);
    }

    @Override
    public void logout(String accessToken) {
        UserDetail user = tokenStoreCache.getUser(accessToken);
        tokenStoreCache.deleteUser(accessToken);
    }

}
