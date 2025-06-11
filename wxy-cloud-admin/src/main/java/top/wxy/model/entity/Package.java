package top.wxy.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_package")
public class Package {

    @TableId
    private Integer packageId;

    // MyBatis-Plus 默认不识别 JSON，可用 String 或自定义类型 + TypeHandler
    private String permissions; // 存储 JSON 字符串
}
