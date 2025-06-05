package top.wxy.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantListDTO {
    private Long tenantId;
    private String tenantName;
    private Integer packageType;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}