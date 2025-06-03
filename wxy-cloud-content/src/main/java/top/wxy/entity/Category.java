package top.wxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wxy.framework.mybatis.entity.BaseEntity;

/**
 * @author 笼中雀
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_category")
public class Category extends BaseEntity {
    private String name;
    private String cover;
}

