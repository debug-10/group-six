package top.wxy.service;

import org.springframework.security.core.userdetails.UserDetails;
import top.wxy.model.entity.SysManager;

public interface SysManagerDetailsService {
    /**
     * 获取 UserDetails 对象
     */
    UserDetails getManagerDetails(SysManager sysManager);
}
