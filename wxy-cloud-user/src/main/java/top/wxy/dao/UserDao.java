package top.wxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import top.wxy.entity.UserEntity;
import top.wxy.framework.mybatis.dao.BaseDao;

/**
 * @author 笼中雀
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {
    default UserEntity getById(Long id) {
        return this.selectOne(new QueryWrapper<UserEntity>().eq("id", id));
    }

    default UserEntity getByUsername(String username) {
        return this.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
    }
    default UserEntity getByPhone(String phone) {
        return this.selectOne(new QueryWrapper<UserEntity>().eq("phone", phone));
    }

    //先通过用户ID查用户租户，下一步在tenantDAO
    default Long getTenantIdByUserId(Long userId) {
        UserEntity user = this.selectOne(new QueryWrapper<UserEntity>()
                .select("tenant_id")
                .eq("id", userId));
        return user != null ? user.getTenantId() : null;
    }
}
