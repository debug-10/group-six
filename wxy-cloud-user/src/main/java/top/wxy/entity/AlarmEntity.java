package top.wxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.framework.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * 告警信息实体类
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_alarm")
public class AlarmEntity extends BaseEntity {
    
    @TableField("device_id")
    private String deviceId;
    
    private String type;
    
    private Integer level;
    
    private String message;
    
    private Integer status;
    
    @TableField("create_time")
    private LocalDateTime createTime;
}