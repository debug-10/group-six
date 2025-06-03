package top.wxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.wxy.model.dto.ApiResponse;
import top.wxy.model.dto.PaginationDTO;
import top.wxy.model.dto.TenantDTO;
import top.wxy.model.dto.TenantListDTO;
import top.wxy.model.entity.Tenant;
import top.wxy.service.TenantService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tenants")
@Tag(name = "租户管理" ,description = "租户管理")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping
    @Operation(summary = "创建租户")
    public ApiResponse<Map<String, Long>> create(@RequestBody TenantDTO tenantDTO) {
        Tenant tenant = tenantService.convertToEntity(tenantDTO);
//        tenant.setTenantId(System.currentTimeMillis()); // 临时生成 tenantId，实际应由业务逻辑提供
        tenant = tenantService.saveTenant(tenant);
        Map<String, Long> data = new HashMap<>();
        data.put("tenant_id", tenant.getTenantId());
        return ApiResponse.success("租户创建成功", data);
    }

    @GetMapping
    @Operation(summary = "分页查询租户")
    public ApiResponse<Map<String, Object>> listTenants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer packageType) {
        IPage<TenantListDTO> tenantPage = tenantService.listTenants(page, limit, status, packageType);

        PaginationDTO pagination = new PaginationDTO();
        pagination.setTotal(tenantPage.getTotal());
        pagination.setPage(page);
        pagination.setLimit(limit);

        Map<String, Object> data = new HashMap<>();
        data.put("tenants", tenantPage.getRecords());
        data.put("pagination", pagination);

        return ApiResponse.success("查询成功", data);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询租户")
    public ApiResponse<Map<String, TenantListDTO>> getTenantById(@PathVariable Long id) {
        TenantListDTO tenant = tenantService.getTenantById(id);
        Map<String, TenantListDTO> data = new HashMap<>();
        data.put("tenant", tenant);
        return ApiResponse.success("查询成功", data);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新租户信息")
    public ApiResponse<Void> updateTenant(@PathVariable Long id, @RequestBody TenantDTO tenantDTO) {
        tenantService.updateTenant(id, tenantDTO);
        return ApiResponse.success("租户信息更新成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除租户信息")
    public ApiResponse<Void> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ApiResponse.success("租户删除成功");
    }
}