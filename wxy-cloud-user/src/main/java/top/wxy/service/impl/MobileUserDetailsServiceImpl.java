package top.wxy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.wxy.convert.UserConvert;
import top.wxy.dao.UserDao;
import top.wxy.entity.UserEntity;
import top.wxy.framework.security.mobile.MobileUserDetailsService;
import top.wxy.service.MyUserDetailsService;


/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class MobileUserDetailsServiceImpl implements MobileUserDetailsService {
    private final MyUserDetailsService myUserDetailsService;
    private final UserDao sysUserDao;
    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        UserEntity userEntity = sysUserDao.getByPhone(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("⼿机号或验证码错误");
        }
        return myUserDetailsService.getUserDetails(UserConvert.INSTANCE.convertDetail(userEntity));
    }
}
