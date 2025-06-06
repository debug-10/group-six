package top.wxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.framework.mybatis.entity.BaseEntity;

/**
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String phone;
    private String nickname;
    private String avatarUrl;
    private Integer status;
    private Long tenantId;
    private Integer role;

    @TableField(exist = false)
    private Integer deleted;
}
