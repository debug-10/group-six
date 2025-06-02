package top.wxy.model.dto;

import lombok.Data;

@Data
public class TenantDTO {
    private String tenantName;
    private Integer packageType;
    private String adminUsername;
    private Integer status;
}