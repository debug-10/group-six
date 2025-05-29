package top.wxy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "创建公告请求对象")
public class NewsCreateDTO {

    @NotBlank(message = "公告标题不能为空")
    @Schema(description = "公告标题", required = true)
    private String title;

    @NotBlank(message = "公告内容不能为空")
    @Schema(description = "公告内容", required = true)
    private String content;

    @NotNull(message = "可见范围不能为空")
    @Schema(description = "可见范围：1-所有用户可见，2-指定租户可见", required = true)
    private Integer visibleRange;

    @Schema(description = "指定租户ID（visibleRange=2时必填）")
    private Long tenantId;
}