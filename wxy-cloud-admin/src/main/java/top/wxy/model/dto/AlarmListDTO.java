package top.wxy.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmListDTO {
    private Long id;
    private Long deviceId;
    private String type;
    private Integer level;
    private String message;
    private Integer status;
    private LocalDateTime createTime;
}