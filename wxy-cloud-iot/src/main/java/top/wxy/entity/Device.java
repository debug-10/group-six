package top.wxy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.framework.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_device")
public class Device extends BaseEntity {
    private String deviceMac; // 对应数据库字段 device_mac
    private String name;
    private Integer type;
    private Integer status; // 数据库为tinyint，对应Java Integer更合适
    private Float temperature;
    private Float humidity;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime; // 新增：创建时间，插入时自动填充

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; // 新增：更新时间，插入和更新时自动填充

    private Integer deleted; // 新增：删除标识
    private Long tenantId; // 新增：租户ID
    private String location; // 新增：设备安装位置
}