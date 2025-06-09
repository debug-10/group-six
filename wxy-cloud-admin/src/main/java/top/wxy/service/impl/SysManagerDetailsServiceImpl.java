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
        ManagerDetail managerDetail = SysManagerConvert.INSTANCE.convertDetail(sysManager);
        if (sysManager.getStatus() == AccountStatusEnum.DISABLE.getValue()) {
            managerDetail.setEnabled(false);
        }
        managerDetail.setPassword(sysManager.getPassword());

        Set<String> authorities = new HashSet<>();
        managerDetail.setAuthoritySet(authorities);

        return managerDetail;
    }
}
