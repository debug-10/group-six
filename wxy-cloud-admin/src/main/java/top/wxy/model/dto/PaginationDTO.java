package top.wxy.model.dto;

import lombok.Data;

@Data
public class PaginationDTO {
    private long total;
    private int page;
    private int limit;
}