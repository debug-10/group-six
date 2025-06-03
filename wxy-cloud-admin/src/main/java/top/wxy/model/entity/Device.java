package top.wxy.model.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_device")
public class Device {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String deviceMac;
    private String name;
    private Integer type; // 1: 灯, 2: 温湿度传感器, 3: 蜂鸣器, 4: 红外传感器
    private Integer status; // 0: 离线, 1: 在线
    private Float temperature;
    private Float humidity;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;
    private Long tenantId;
    private String location; // 新增字段：设备安装位置

    // Getters and Setters 由 Lombok @Data 自动生成
}