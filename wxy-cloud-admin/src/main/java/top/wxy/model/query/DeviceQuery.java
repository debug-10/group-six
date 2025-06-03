package top.wxy.model.query;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.common.model.Query;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "设备查询参数")
public class DeviceQuery extends Query {
    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "租户ID")
    private Long tenantId;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "类型")
    private Integer type;
}