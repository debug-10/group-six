package top.wxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 设备添加DTO
 * @author 笼中雀
 */
@Data
@Schema(description = "设备添加DTO")
public class DeviceAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "设备MAC地址", required = true)
    private String deviceMac;

    @Schema(description = "场景类型")
    private String type;
}