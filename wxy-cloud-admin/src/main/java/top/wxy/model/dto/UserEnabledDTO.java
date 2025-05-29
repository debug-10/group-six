package top.wxy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author mqxu
 * @date 2024/11/18
 * @description UserEnabledDTO
 **/
@Data
@Schema(description = "用户状态修改dto")
public class UserEnabledDTO {
    @Schema(description = "用户id")
    private Integer pkId;

    @Schema(description = "账户状态")
    private Integer enabled;
}