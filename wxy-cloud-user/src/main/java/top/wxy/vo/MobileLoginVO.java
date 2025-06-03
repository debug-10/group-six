package top.wxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 笼中雀
 */
@Data
@Schema(description = "手机号登录vo")
public class MobileLoginVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "accessToken")
    private String accessToken;
}
