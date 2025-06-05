package top.wxy.security.user;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUser {
    /**
     * 获取用户信息
     */
    public static ManagerDetail getManager() {
        ManagerDetail user;
        try {
            user = (ManagerDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return new ManagerDetail();
        }

        return user;
    }

    /**
     * 获取用户ID
     */
    public static Long getManagerId() { // 修改返回类型为Long
        return getManager().getId(); // 已经使用getId()
    }
}