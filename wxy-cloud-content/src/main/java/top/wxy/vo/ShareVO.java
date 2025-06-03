package top.wxy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import top.wxy.framework.common.utils.DateUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 笼中雀
 */
@Data
@Schema(description = "分享")
public class ShareVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @NotBlank(message = "id不能为空")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "是否原创 0：否   1：是  ", required = true)
    @Range(min = 0, max = 1, message = "是否原创")
    private Integer isOriginal;

    @Schema(description = "下载地址")
    private String downloadUrl;


    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;
}