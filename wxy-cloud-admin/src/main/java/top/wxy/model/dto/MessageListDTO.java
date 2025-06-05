package top.wxy.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageListDTO {
    private Long id;
    private Long userId;
    private String deviceId;
    private Integer type;
    private String title;
    private String content;
    private Integer isTop;
//    private LocalDateTime createTime; // 与实体类保持一致
//    private LocalDateTime updateTime;

    private String createTime; // 改为 String 类型
    private String updateTime;
}