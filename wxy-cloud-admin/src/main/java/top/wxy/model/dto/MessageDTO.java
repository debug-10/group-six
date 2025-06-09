package top.wxy.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private String deviceId;
    private Integer type;
    private String title;
    private String content;
    private Integer isTop;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}