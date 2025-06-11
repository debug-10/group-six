package top.wxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import top.wxy.entity.TenantEntity;
import top.wxy.framework.mybatis.dao.BaseDao;

@Mapper
public interface TenantDAO extends BaseDao<TenantEntity> {

    //通过租户的ID查到租户订阅的套餐类型，第三步在PackageDAO
    default Integer getPackageTypeByTenantId(Integer tenantId) {
        TenantEntity tenant = this.selectOne(new QueryWrapper<TenantEntity>()
                .select("package_type")
                .eq("tenant_id", tenantId));
        return tenant != null ? tenant.getPackageType() : null;
    }
}
