package top.wxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import top.wxy.entity.UserDeviceEntity;
import top.wxy.framework.mybatis.dao.BaseDao;

import java.util.List;

/**
 * 用户设备Dao
 * @author 笼中雀
 */
@Mapper
public interface UserDeviceDao extends BaseDao<UserDeviceEntity> {
    
    default List<UserDeviceEntity> getByUserId(Long userId) {
        return this.selectList(new QueryWrapper<UserDeviceEntity>()
                .eq("user_id", userId));
    }
    
    default UserDeviceEntity getByUserIdAndType(Long userId, Integer type) {
        return this.selectOne(new QueryWrapper<UserDeviceEntity>()
                .eq("user_id", userId)
                .eq("type", type));
    }
}