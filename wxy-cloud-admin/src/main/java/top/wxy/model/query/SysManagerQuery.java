package top.wxy.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.common.model.Query;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "管理员查询")
public class SysManagerQuery extends Query {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "所属学校（租户）")
    private Long tenantId;

    @Schema(description = "用户类型")
    private Integer role;

    @Schema(description = "状态")
    private Integer status;
}
