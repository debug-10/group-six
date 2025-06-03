package top.wxy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.wxy.convert.SysManagerConvert;
import top.wxy.enums.AccountStatusEnum;
import top.wxy.model.entity.SysManager;
import top.wxy.security.user.ManagerDetail;
import top.wxy.service.SysManagerDetailsService;


import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SysManagerDetailsServiceImpl implements SysManagerDetailsService {
    @Override
    public UserDetails getManagerDetails(SysManager sysManager) {
        // 转换成UserDetail对象
        ManagerDetail managerDetail = SysManagerConvert.INSTANCE.convertDetail(sysManager);

        // 账号不可用
        if (sysManager.getStatus() == AccountStatusEnum.DISABLE.getValue()) {
            managerDetail.setEnabled(false);
        }

        // 确保密码正确设置
        managerDetail.setPassword(sysManager.getPassword());
        
        // 设置权限集合（避免空指针异常）
        // 由于删除了角色权限功能，这里设置一个空的权限集合或默认权限
        Set<String> authorities = new HashSet<>();
        // 可以根据需要添加默认权限，例如：
        // authorities.add("ROLE_ADMIN");
        managerDetail.setAuthoritySet(authorities);

        return managerDetail;
    }
}
