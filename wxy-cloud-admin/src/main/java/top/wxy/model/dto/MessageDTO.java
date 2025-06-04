package top.wxy.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    // 移除 id、userId、createTime、updateTime
    private String deviceId;
    private Integer type;
    private String title;
    private String content;
    private Integer isTop;

    private LocalDateTime createTime; // 与实体类保持一致
    private LocalDateTime updateTime;
}