package top.wxy.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.security.cache.TokenStoreCache;
import top.wxy.framework.security.user.UserDetail;
import top.wxy.security.user.ManagerDetail;
import top.wxy.security.utils.TokenUtils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author 笼中雀
 */
@Component("adminAuthenticationTokenFilter")
@Slf4j
public class AuthenticationTokenFilter extends top.wxy.framework.security.filter.AuthenticationTokenFilter {
    private final TokenStoreCache tokenStoreCache;

    public AuthenticationTokenFilter(TokenStoreCache tokenStoreCache) {
        super(tokenStoreCache);
        this.tokenStoreCache = tokenStoreCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        MyRequestWrapper myRequestWrapper = null;
        // 获取请求body
        try {
            myRequestWrapper = new MyRequestWrapper(request);
        } catch (IOException e) {
            log.error("get request body exception", e);
            throw new ServerException("参数异常");
        }
        log.info(getIp(myRequestWrapper) + myRequestWrapper.getRequestURI() + readAsChars(myRequestWrapper));
        
        String accessToken = TokenUtils.getAccessToken(myRequestWrapper);
        // accessToken为空，表示未登录
        if (StringUtils.isBlank(accessToken)) {
            chain.doFilter(myRequestWrapper, response);
            return;
        }

        // 获取登录用户信息
        UserDetail userDetail = tokenStoreCache.getUser(accessToken);
        if (userDetail == null) {
            chain.doFilter(myRequestWrapper, response);
            return;
        }

        // 转换为 ManagerDetail
        ManagerDetail managerDetail = new ManagerDetail();
        managerDetail.setPkId(userDetail.getId().intValue());
        managerDetail.setUsername(userDetail.getUsername());
        managerDetail.setPassword(userDetail.getPassword());
        managerDetail.setEnabled(userDetail.isEnabled());
        managerDetail.setAccountNonExpired(userDetail.isAccountNonExpired());
        managerDetail.setAccountNonLocked(userDetail.isAccountNonLocked());
        managerDetail.setCredentialsNonExpired(userDetail.isCredentialsNonExpired());
        managerDetail.setAuthoritySet(userDetail.getAuthoritySet());

        // 用户存在
        Authentication authentication = new UsernamePasswordAuthenticationToken(managerDetail, null, managerDetail.getAuthorities());

        // 新建 SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        chain.doFilter(myRequestWrapper, response);
    }

    public static String readAsChars(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
