package top.wxy.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("t_news")
@Schema(description = "咨询公告类")
public class News {

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "公告标题")
    @TableField("title")
    private String title;

    @Schema(description = "公告内容")
    @TableField("content")
    private String content;

    @Schema(description = "可见范围：1-所有用户可见，2-指定租户可见")
    @TableField("visible_range")
    private Integer visibleRange;

    @Schema(description = "指定租户ID")
    @TableField("tenant_id")
    private Long tenantId;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}