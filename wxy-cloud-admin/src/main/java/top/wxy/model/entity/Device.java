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
    private Integer type;
    private Integer status;
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
    private String location;
}