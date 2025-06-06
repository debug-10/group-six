package top.wxy.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import top.wxy.entity.DeviceEntity;
import top.wxy.framework.mybatis.dao.BaseDao;

import java.util.List;

/**
 * 设备Dao
 * @author 笼中雀
 */
@Mapper
public interface DeviceDao extends BaseDao<DeviceEntity> {
    
    default List<DeviceEntity> getByDeviceMacs(List<String> deviceMacs) {
        return this.selectList(new QueryWrapper<DeviceEntity>()
                .in("device_mac", deviceMacs));
    }
    
    default List<DeviceEntity> getByType(Integer type) {
        return this.selectList(new QueryWrapper<DeviceEntity>()
                .eq("type", type));
    }
}