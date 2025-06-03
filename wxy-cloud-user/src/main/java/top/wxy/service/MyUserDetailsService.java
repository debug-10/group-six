package top.wxy.service;

import org.springframework.security.core.userdetails.UserDetails;
import top.wxy.framework.security.user.UserDetail;

/**
 * @author 笼中雀
 */
public interface MyUserDetailsService {
    UserDetails getUserDetails(UserDetail userDetail);

}
