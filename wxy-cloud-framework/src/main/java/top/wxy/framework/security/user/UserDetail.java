package top.wxy.framework.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 笼中雀
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private Integer gender;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
    private Long tenantId;
    private Integer role;

    /**
     * 帐户是否过期
     */
    private boolean isAccountNonExpired = true;
    /**
     * 帐户是否被锁定
     */
    private boolean isAccountNonLocked = true;
    /**
     * 密码是否过期
     */
    private boolean isCredentialsNonExpired = true;
    /**
     * 帐户是否可用
     */
    private boolean isEnabled = true;
    /**
     * 拥有权限集合
     */
    private Set<String> authoritySet;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}