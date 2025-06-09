package top.wxy.convert;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import top.wxy.entity.DeviceEntity;
import top.wxy.entity.UserDeviceEntity;
import top.wxy.vo.DeviceVO;
import top.wxy.vo.UserDeviceVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:38:21+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class DeviceConvertImpl implements DeviceConvert {

    @Override
    public DeviceVO convert(DeviceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DeviceVO deviceVO = new DeviceVO();

        deviceVO.setId( entity.getId() );
        deviceVO.setDeviceMac( entity.getDeviceMac() );
        deviceVO.setName( entity.getName() );
        deviceVO.setType( entity.getType() );
        deviceVO.setStatus( entity.getStatus() );
        deviceVO.setTemperature( entity.getTemperature() );
        deviceVO.setHumidity( entity.getHumidity() );
        deviceVO.setLocation( entity.getLocation() );

        return deviceVO;
    }

    @Override
    public List<DeviceVO> convertList(List<DeviceEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DeviceVO> list = new ArrayList<DeviceVO>( entities.size() );
        for ( DeviceEntity deviceEntity : entities ) {
            list.add( convert( deviceEntity ) );
        }

        return list;
    }

    @Override
    public UserDeviceVO convert(UserDeviceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserDeviceVO userDeviceVO = new UserDeviceVO();

        userDeviceVO.setId( entity.getId() );
        userDeviceVO.setType( entity.getType() );
        userDeviceVO.setGroupName( entity.getGroupName() );
        userDeviceVO.setBindTime( entity.getBindTime() );

        return userDeviceVO;
    }

    @Override
    public List<UserDeviceVO> convertUserDeviceList(List<UserDeviceEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserDeviceVO> list = new ArrayList<UserDeviceVO>( entities.size() );
        for ( UserDeviceEntity userDeviceEntity : entities ) {
            list.add( convert( userDeviceEntity ) );
        }

        return list;
    }
}
