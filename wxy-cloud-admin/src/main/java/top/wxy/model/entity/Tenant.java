package top.wxy.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_tenant")
public class Tenant {
    @TableId(value = "tenant_id", type = IdType.AUTO)
    private Long tenantId;

    private String tenantName;
    private Long creatorId;
    private Integer packageType;
    private String adminUsername;
    private Integer status;

    @TableField(fill = FieldFill.INSERT, exist = false)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, exist = false)
    private LocalDateTime updateTime;
}