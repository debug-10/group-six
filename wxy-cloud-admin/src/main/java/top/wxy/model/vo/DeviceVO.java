package top.wxy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "DeviceVO", description = "设备信息VO")
public class DeviceVO {
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
    @Schema(description = "租户ID")
    private Long tenantId;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "设备安装位置")
    private String location;

    @Data
    public static class Pagination {
        private Long total;
        private Integer page;
        private Integer limit;
    }

    private Pagination pagination;
    private java.util.List<DeviceVO> devices;
}