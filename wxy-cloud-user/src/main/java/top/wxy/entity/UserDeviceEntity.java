package top.wxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.framework.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * 用户设备绑定实体类
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_device")
public class UserDeviceEntity extends BaseEntity {
    @TableField("user_id")
    private Long userId;
    private Integer type;
    @TableField("group_name")
    private String groupName;
    @TableField("bind_time")
    private LocalDateTime bindTime;

    @TableField(exist = false)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private LocalDateTime updateTime;
}