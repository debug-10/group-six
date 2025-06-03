package top.wxy.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
@Data
@Schema(description = "设备更新DTO")
public class DeviceUpdateDTO implements Serializable {
    @NotNull
    @Schema(description = "设备ID")
    private Long id;
    private String name;
    private Integer status;
    private Float temperature;
    private Float humidity;
    private String location; // 新增字段
}