package top.wxy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "设备创建DTO")
public class DeviceCreateDTO implements Serializable {
    private String deviceMac;
    private String name;
    private Integer type;
    private Long tenantId;
    private Integer status;
    private Float temperature;
    private Float humidity;
    private String location; // 新增字段
}