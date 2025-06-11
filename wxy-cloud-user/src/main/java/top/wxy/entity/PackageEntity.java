package top.wxy.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_package")
public class PackageEntity {
    private Integer package_id;
    private String permissions;
}
