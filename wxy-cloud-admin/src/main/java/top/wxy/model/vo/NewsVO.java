package top.wxy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "公告视图对象")
public class NewsVO {

    @Schema(description = "公告ID")
    private Long id;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "可见范围：1-所有用户可见，2-指定租户可见")
    private Integer visibleRange;

    @Schema(description = "指定租户ID")
    private Long tenantId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 扩展字段：租户名称（用于前端显示）
    @Schema(description = "租户名称")
    private String tenantName;
}