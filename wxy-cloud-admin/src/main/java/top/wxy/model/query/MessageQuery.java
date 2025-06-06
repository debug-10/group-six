package top.wxy.model.query;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MessageQuery {
    private Long userId;
    private String deviceId;
    private Integer type;
    private Integer isTop;
    private LocalDateTime createTime; // 与实体类保持一致
    private LocalDateTime updateTime;
}