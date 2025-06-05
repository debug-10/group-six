package top.wxy.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
@Data
@Schema(description = "设备状态DTO")
public class DeviceStatusDTO implements Serializable {
    @NotNull
    @Schema(description = "设备ID")
    private Long id;
    @NotNull
    @Schema(description = "状态")
    private Integer status;
}
