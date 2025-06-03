package top.wxy.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.wxy.framework.common.utils.DateUtils;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 笼中雀
 */
@Data
@Schema(description = "设备VO")
public class DeviceVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "设备唯一标识")
    private String deviceId;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "设备类型")
    private Integer type;

    @Schema(description = "设备状态")
    private Boolean status;

    @Schema(description = "温度")
    private Float temperature;

    @Schema(description = "湿度")
    private Float humidity;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;
}