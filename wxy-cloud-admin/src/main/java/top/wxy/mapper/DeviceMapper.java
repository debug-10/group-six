package top.wxy.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;
import top.wxy.model.entity.Device;
import top.wxy.model.query.DeviceQuery;
import top.wxy.model.vo.DeviceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    List<DeviceVO> getDevicePage(Page<DeviceVO> page, @Param("query") DeviceQuery query);

    Device getByDeviceMac(@Param("deviceMac") String deviceMac);

    /**
     * 设备总数
     */
    @Select("SELECT COUNT(*) FROM t_device WHERE deleted = 0")
    int countAll();

    /**
     * 在线设备数
     */
    @Select("SELECT COUNT(*) FROM t_device WHERE status = 1 AND deleted = 0")
    int countOnline();

    /**
     * 今日新增设备数
     */
    @Select("SELECT COUNT(*) FROM t_device WHERE DATE(create_time) = CURDATE() AND deleted = 0")
    int countToday();

    /**
     * 所有设备经纬度位置
     */
    @Select("""
        SELECT name, location
        FROM t_device
        WHERE location IS NOT NULL AND location != '' AND deleted = 0
    """)
    List<Map<String, String>> selectDeviceLocations();
}