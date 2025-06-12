package top.wxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tenant")
public class TenantEntity {

    private Integer tenant_id;
    private String tenant_type;
    @TableField("package_type")
    private Integer packageType;
    private String package_name;
    private Integer status;
    private Integer creator_id;

}
