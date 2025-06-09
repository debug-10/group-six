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


    private String createTime;
    private String updateTime;
}