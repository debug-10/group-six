package top.wxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.framework.mybatis.entity.BaseEntity;

/**
 * 设备实体类
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_device")
public class DeviceEntity extends BaseEntity {
    @TableField("device_mac")
    private String deviceMac;
    private String name;
    private Integer type;
    private Integer status;
    private Float temperature;
    private Float humidity;
    private String location;
    @TableField("tenant_id")
    private Long tenantId;
    private Integer deleted;
}