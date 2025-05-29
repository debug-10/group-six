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
import top.wxy.service.SysMenuService;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenStoreCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;
    private final SysMenuService sysMenuService;

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

        // 用户信息
        ManagerDetail managerDetail = (ManagerDetail) authentication.getPrincipal();
        
        // 获取用户权限
        managerDetail.setAuthoritySet(sysMenuService.getManagerAuthority(managerDetail));

        // 转换为UserDetail
        UserDetail user = new UserDetail();
        user.setId(managerDetail.getPkId().longValue());
        user.setUsername(managerDetail.getUsername());
        user.setPassword(managerDetail.getPassword());
        user.setEnabled(managerDetail.isEnabled());
        user.setAccountNonExpired(managerDetail.isAccountNonExpired());
        user.setAccountNonLocked(managerDetail.isAccountNonLocked());
        user.setCredentialsNonExpired(managerDetail.isCredentialsNonExpired());
        user.setAuthoritySet(managerDetail.getAuthoritySet());

        // 生成 accessToken
        String accessToken = TokenUtils.generator();
        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, user);

        return new SysTokenVO(accessToken);
    }

    @Override
    public void logout(String accessToken) {
        // 用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);

        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);
    }

}
