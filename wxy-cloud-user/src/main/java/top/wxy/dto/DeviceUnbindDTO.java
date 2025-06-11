package top.wxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设备解绑DTO
 * @author 笼中雀
 */
@Data
@Schema(description = "设备解绑DTO")
public class DeviceUnbindDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "设备类型", required = true)
    private Integer type;
}