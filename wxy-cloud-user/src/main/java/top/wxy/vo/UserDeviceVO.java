package top.wxy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户设备VO
 * @author 笼中雀
 */
@Data
@Schema(description = "用户设备信息")
public class UserDeviceVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "绑定ID")
    private Long id;
    
    @Schema(description = "设备类型")
    private Integer type;
    
    @Schema(description = "设备分组名称")
    private String groupName;
    
    @Schema(description = "绑定时间")
    private LocalDateTime bindTime;
    
    @Schema(description = "该类型下的设备列表")
    private List<DeviceVO> devices;
}