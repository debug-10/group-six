package top.wxy.convert;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import top.wxy.dto.DeviceDTO;
import top.wxy.entity.Device;
import top.wxy.vo.DeviceVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T13:14:31+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Oracle Corporation)"
)
public class DeviceConvertImpl implements DeviceConvert {

    @Override
    public Device convert(DeviceDTO deviceDto) {
        if ( deviceDto == null ) {
            return null;
        }

        Device device = new Device();

        device.setDeviceId( deviceDto.getDeviceId() );
        device.setName( deviceDto.getName() );
        device.setType( deviceDto.getType() );

        return device;
    }

    @Override
    public DeviceVO convert(Device device) {
        if ( device == null ) {
            return null;
        }

        DeviceVO deviceVO = new DeviceVO();

        deviceVO.setId( device.getId() );
        deviceVO.setDeviceId( device.getDeviceId() );
        deviceVO.setName( device.getName() );
        deviceVO.setType( device.getType() );
        deviceVO.setStatus( device.getStatus() );
        deviceVO.setTemperature( device.getTemperature() );
        deviceVO.setHumidity( device.getHumidity() );
        deviceVO.setCreateTime( device.getCreateTime() );

        return deviceVO;
    }

    @Override
    public List<DeviceVO> convertList(List<Device> list) {
        if ( list == null ) {
            return null;
        }

        List<DeviceVO> list1 = new ArrayList<DeviceVO>( list.size() );
        for ( Device device : list ) {
            list1.add( convert( device ) );
        }

        return list1;
    }
}
