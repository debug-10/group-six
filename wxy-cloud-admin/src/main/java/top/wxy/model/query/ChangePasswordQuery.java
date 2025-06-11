package top.wxy.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 笼中雀
 */
@Data
@Schema(description = "修改管理员密码")
public class ChangePasswordQuery {
    @Schema(description = "主键")
    private Long id;
    
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
