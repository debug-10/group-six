package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wxy.mapper.TenantMapper;
import top.wxy.mapper.UserMapper;
import top.wxy.model.dto.TenantDTO;
import top.wxy.model.dto.TenantListDTO;
import top.wxy.model.entity.Tenant;
import top.wxy.model.entity.User;
import top.wxy.service.TenantService;

@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Tenant saveTenant(Tenant entity) {
        // 验证 adminUsername 是否存在
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("username", entity.getAdminUsername());
        User user = userMapper.selectOne(userQuery);
        if (user == null) {
            throw new RuntimeException("Admin username " + entity.getAdminUsername() + " does not exist in t_user table");
        }

        System.out.println("Before saving tenant: " + entity);
        this.save(entity);
        System.out.println("After saving tenant, tenantId: " + entity.getTenantId());
        return entity;
    }

    @Override
    public Tenant convertToEntity(TenantDTO dto) {
        Tenant tenant = new Tenant();
        tenant.setTenantName(dto.getTenantName());
        tenant.setPackageType(dto.getPackageType());
        tenant.setAdminUsername(dto.getAdminUsername());
        tenant.setStatus(1); // 默认启用
        return tenant;
    }

    @Override
    public IPage<TenantListDTO> listTenants(int page, int limit, Integer status, Integer packageType) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (packageType != null) {
            queryWrapper.eq("package_type", packageType);
        }

        IPage<Tenant> tenantPage = new Page<>(page, limit);
        tenantPage = this.page(tenantPage, queryWrapper);

        IPage<TenantListDTO> resultPage = tenantPage.convert(this::convertToListDTO);
        return resultPage;
    }

    @Override
    public TenantListDTO getTenantById(Long id) {
        Tenant tenant = this.getById(id);
        if (tenant == null) {
            throw new RuntimeException("Tenant not found with id: " + id);
        }
        return convertToListDTO(tenant);
    }

    @Override
    public void updateTenant(Long id, TenantDTO dto) {
        Tenant tenant = this.getById(id);
        if (tenant == null) {
            throw new RuntimeException("Tenant not found with id: " + id);
        }
        if (dto.getPackageType() != null) {
            tenant.setPackageType(dto.getPackageType());
        }
        if (dto.getStatus() != null) {
            tenant.setStatus(dto.getStatus());
        }
        this.updateById(tenant);
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant tenant = this.getById(id);
        if (tenant == null) {
            throw new RuntimeException("Tenant not found with id: " + id);
        }
        tenant.setStatus(0); // 逻辑删除
        this.updateById(tenant);
    }

    private TenantListDTO convertToListDTO(Tenant tenant) {
        TenantListDTO dto = new TenantListDTO();
        dto.setTenantId(tenant.getTenantId());
        dto.setTenantName(tenant.getTenantName());
        dto.setPackageType(tenant.getPackageType());
        dto.setStatus(tenant.getStatus());
        dto.setCreateTime(tenant.getCreateTime());
        dto.setUpdateTime(tenant.getUpdateTime());
        return dto;
    }
}