package top.wxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 笼中雀
 */
@Data
@Schema(description = "⽤户vo")
public class UserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "id")
    private Long id;
    @Schema(description = "⽤户名")
    private String username;
    @Schema(description = "姓名")
    private String nickname;
    @Schema(description = "⼿机号")
    private String phone;
    @Schema(description = "头像")
    private String avatarUrl;
    @Schema(description = "租户ID")
    private Long tenantId;
    @Schema(description = "用户角色：1=超管，2=租户管理员，3=普通用户")
    private Integer role;
}

