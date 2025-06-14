package top.wxy.framework.security.user;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 笼中雀
 */
public class SecurityUser {

    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        UserDetail user;
        try {
            user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }

        return user;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        UserDetail user = getUser();
        if (user == null) {
            return null;
        }

        return user.getId();
    }
    public static ManagerDetail getManager() {
        ManagerDetail user;
        try {
            user = (ManagerDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return new ManagerDetail();
        }

        return user;
    }

    public static Integer getManagerId() {
        return getManager().getPkId();
    }
}