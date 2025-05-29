package top.wxy.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.common.model.Query;

/**
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "分类查询")
public class CategoryQuery extends Query {
    @Schema(description = "标题")
    private String title;

    @Schema(description = "类型")
    private String type;

}