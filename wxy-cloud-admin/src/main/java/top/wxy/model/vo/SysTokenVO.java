package top.wxy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户登录")
public class SysTokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "access_token")
    private String access_token;
    
    @Schema(description = "用户信息")
    private UserInfo user;
    
    @Data
    @Schema(description = "用户信息")
    public static class UserInfo {
        @Schema(description = "用户ID")
        private Long id;
        
        @Schema(description = "用户名")
        private String username;
        
        @Schema(description = "昵称")
        private String nickname;
        
        @Schema(description = "手机号")
        private String phone;
        
        @Schema(description = "头像")
        private String avatarUrl;
        
        @Schema(description = "租户ID")
        private Long tenantId;
        
        @Schema(description = "角色：1-超级管理员，2-租户管理员，3-普通用户")
        private Integer role;
        
        @Schema(description = "状态：1-启用，0-禁用")
        private Integer status;
    }
}
