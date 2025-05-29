package top.wxy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "字典管理")
public class DictRequestDTO {
    @Schema(description = "主键")
    private Integer pkId;
    @Schema(description = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String title;
    @Schema(description = "字典编码")
    @NotBlank(message = "字典编码不能为空")
    private String number;
    @Schema(description = "字典描述")
    private String description;

}