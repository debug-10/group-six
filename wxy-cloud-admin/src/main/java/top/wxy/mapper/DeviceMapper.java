package top.wxy.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.wxy.model.entity.Device;
import top.wxy.model.query.DeviceQuery;
import top.wxy.model.vo.DeviceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    List<DeviceVO> getDevicePage(Page<DeviceVO> page, @Param("query") DeviceQuery query);

    Device getByDeviceMac(@Param("deviceMac") String deviceMac);
}