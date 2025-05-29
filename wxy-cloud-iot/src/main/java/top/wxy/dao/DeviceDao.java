package top.wxy.dao;
import org.apache.ibatis.annotations.Mapper;
import top.wxy.entity.Device;
import top.wxy.framework.mybatis.dao.BaseDao;
import java.util.List;
import java.util.Map;

/**
 * @author 笼中雀
 */
@Mapper
public interface DeviceDao extends BaseDao<Device> {
    List<Device> getList(Map<String, Object> params);
}