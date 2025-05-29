package top.wxy.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "菜单筛选项")
public class SysMenuQuery {
    @Schema(description = "菜单名称")
    private String title;
}
