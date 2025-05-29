package top.wxy.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 笼中雀
 */

@Data
@Schema(name = "CategoryInfoVO",description = "分类信息返回vo")
public class CategoryInfoVO {
    @Schema(name="pk_id",description = "分类id")
    private Integer pkId;

    @Schema(name="title",description = "标题")
    private String title;

    @Schema(name="type",description = "类型")
    private Integer type;

    @Schema(name="description",description = "描述")
    private String description;

    @Schema(name = "createTime", description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
