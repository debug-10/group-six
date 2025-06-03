package top.wxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 笼中雀
 */
@Data
@Schema(description = "⽤户信息dto")
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "⽤户名", required = true)
    private String username;
    @Schema(description = "密码", required = true)
    private String password;
    @Schema(description = "昵称", required = true)
    private String nickname;
    @Schema(description = "头像")
    private String avatar;
}
