package top.wxy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "公告详情视图对象")
public class NewsDetailVO {


    @Schema(description = "公告标题", required = true)
    private String title;

    @Schema(description = "公告内容", required = true)
    private String content;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}