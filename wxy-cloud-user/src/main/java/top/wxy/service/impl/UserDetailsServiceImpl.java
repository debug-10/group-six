package top.wxy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.wxy.convert.UserConvert;
import top.wxy.dao.UserDao;
import top.wxy.entity.UserEntity;
import top.wxy.service.MyUserDetailsService;

/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MyUserDetailsService myUserDetailsService;
    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("⽤户名或密码错误");
        }
        return myUserDetailsService.getUserDetails(UserConvert.INSTANCE.convertDetail(userEntity));
    }
}