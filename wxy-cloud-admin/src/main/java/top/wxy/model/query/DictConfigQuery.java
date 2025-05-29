package top.wxy.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "字典配置查询")
public class DictConfigQuery {
    @Schema(description = "字典名称")
    private String title;

    @Schema(description = "字典编号")
    @NotBlank(message = "字典编号禁止为空")
    private String number;
}
