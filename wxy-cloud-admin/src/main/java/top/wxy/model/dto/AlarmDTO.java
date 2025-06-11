package top.wxy.model.dto;

import lombok.Data;

@Data
public class AlarmDTO {
    private String deviceId;
    private String type;
    private Integer level;
    private String message;
    private Integer status;
}