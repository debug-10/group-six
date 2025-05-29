package top.wxy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "更新公告请求对象")
public class NewsUpdateDTO {

    @NotNull(message = "公告ID不能为空")
    @Schema(description = "公告ID", required = true)
    private Long id;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "可见范围：1-所有用户可见，2-指定租户可见")
    private Integer visibleRange;

    @Schema(description = "指定租户ID")
    private Long tenantId;
}