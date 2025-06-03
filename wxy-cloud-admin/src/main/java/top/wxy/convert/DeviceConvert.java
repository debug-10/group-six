package top.wxy.convert;



import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.model.dto.DeviceCreateDTO;
import top.wxy.model.dto.DeviceUpdateDTO;
import top.wxy.model.dto.DeviceStatusDTO;
import top.wxy.model.entity.Device;
import top.wxy.model.vo.DeviceVO;

import java.util.List;

@Mapper
public interface DeviceConvert {
    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);

    Device toEntity(DeviceCreateDTO dto);

    Device toEntity(DeviceUpdateDTO dto);

    Device toEntity(DeviceStatusDTO dto);

    DeviceVO toVO(Device entity);

    List<DeviceVO> toVOList(List<Device> entities);
}
