package top.wxy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "公告概览视图对象")
public class NewsVO {

    @Schema(description = "公告ID")
    private Long id;

    @Schema(description = "公告标题", required = true)
    private String title;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;
    @Schema(description = "租户名称")
    private String tenantName;
}