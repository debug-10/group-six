package top.wxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设备VO
 * @author 笼中雀
 */
@Data
@Schema(description = "设备信息")
public class DeviceVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "设备ID")
    private Long id;
    
    @Schema(description = "设备MAC地址")
    private String deviceMac;
    
    @Schema(description = "设备名称")
    private String name;
    
    @Schema(description = "设备类型")
    private Integer type;
    
    @Schema(description = "设备状态")
    private Integer status;
    
    @Schema(description = "温度")
    private Float temperature;
    
    @Schema(description = "湿度")
    private Float humidity;
    
    @Schema(description = "设备位置")
    private String location;
}