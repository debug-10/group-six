package top.wxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.wxy.model.dto.TenantDTO;
import top.wxy.model.dto.TenantListDTO;
import top.wxy.model.entity.Tenant;

public interface TenantService {
    Tenant insert(Tenant entity);

    Tenant saveTenant(Tenant entity);

    Tenant convertToEntity(TenantDTO dto);

    IPage<TenantListDTO> listTenants(int page, int limit, Integer status, Integer packageType);

    TenantListDTO getTenantById(Long id);

    void updateTenant(Long id, TenantDTO dto);

    void deleteTenant(Long id);
}