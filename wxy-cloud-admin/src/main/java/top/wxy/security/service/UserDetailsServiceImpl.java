package top.wxy.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.wxy.mapper.SysManagerMapper;
import top.wxy.model.entity.SysManager;
import top.wxy.service.SysManagerDetailsService;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SysManagerDetailsService sysManagerDetailsService;
    private final SysManagerMapper sysManagerMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库加载用户
        SysManager sysManager = sysManagerMapper.getByUsername(username);
        if (sysManager == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        // 确保密码字段已正确加载
        if (sysManager.getPassword() == null) {
            throw new UsernameNotFoundException("用户密码未正确配置");
        }

        // 转换为 UserDetails 对象
        return sysManagerDetailsService.getManagerDetails(sysManager);
    }
}
