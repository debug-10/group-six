package top.wxy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ID参数")
public class IdVO {

    @Schema(description = "id")
    private Integer id;
}
