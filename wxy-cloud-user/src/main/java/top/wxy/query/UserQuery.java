package top.wxy.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.framework.common.query.Query;

/**
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "⽤户查询")
public class UserQuery extends Query {
    @Schema(description = "⽤户名")
    private String username;
    @Schema(description = "⼿机号")
    private String phone;
}
