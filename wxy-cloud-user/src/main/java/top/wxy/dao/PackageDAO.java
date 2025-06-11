package top.wxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import top.wxy.entity.PackageEntity;
import top.wxy.framework.mybatis.dao.BaseDao;

@Mapper
public interface PackageDAO extends BaseDao<PackageEntity> {

    default String getPermissionsByPackageType(Integer packageId) {
        PackageEntity pkg = this.selectOne(new QueryWrapper<PackageEntity>()
                .select("permissions")
                .eq("package_id", packageId));
        return pkg != null ? pkg.getPermissions() : null;
    }
}
