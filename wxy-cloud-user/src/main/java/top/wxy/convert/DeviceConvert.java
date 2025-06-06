package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.entity.DeviceEntity;
import top.wxy.entity.UserDeviceEntity;
import top.wxy.vo.DeviceVO;
import top.wxy.vo.UserDeviceVO;

import java.util.List;

/**
 * 设备转换类
 * @author 笼中雀
 */
@Mapper
public interface DeviceConvert {
    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);
    
    DeviceVO convert(DeviceEntity entity);
    List<DeviceVO> convertList(List<DeviceEntity> entities);
    
    UserDeviceVO convert(UserDeviceEntity entity);
    List<UserDeviceVO> convertUserDeviceList(List<UserDeviceEntity> entities);
}