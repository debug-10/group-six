package top.wxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "发布笔记dto")
public class NoteDTO {
    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "用户id")
    @NotBlank(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "分类id")
    @NotBlank(message = "分类id不能为空")
    private Long categoryId;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @Schema(description = "标签")
    private List<String> tags;
}