package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxy.convert.DeviceConvert;
import top.wxy.dao.DeviceDao;
import top.wxy.dao.UserDeviceDao;
import top.wxy.dto.DeviceAddDTO;
import top.wxy.dto.DeviceUnbindDTO;
import top.wxy.entity.DeviceEntity;
import top.wxy.entity.UserDeviceEntity;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.security.user.SecurityUser;
import top.wxy.service.DeviceService;
import top.wxy.vo.DeviceVO;
import top.wxy.vo.UserDeviceVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备服务实现类
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    
    private final DeviceDao deviceDao;
    private final UserDeviceDao userDeviceDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDeviceToUser(DeviceAddDTO dto) {
        Long userId = SecurityUser.getUser().getId();

        DeviceEntity device = deviceDao.selectOne(new QueryWrapper<DeviceEntity>()
                .eq("device_mac", dto.getDeviceMac()));

        if (device == null) {
            throw new ServerException("未找到对应的设备信息");
        }
        UserDeviceEntity existingUserDevice = userDeviceDao.getByUserIdAndType(userId, device.getType());
        if (existingUserDevice != null) {
            throw new ServerException("您已经绑定了该类型的设备场景");
        }

        UserDeviceEntity userDevice = new UserDeviceEntity();
        userDevice.setUserId(userId);
        userDevice.setType(device.getType());
        userDevice.setBindTime(LocalDateTime.now());

        userDeviceDao.insert(userDevice);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbindUserDevice(DeviceUnbindDTO dto) {
        Long userId = SecurityUser.getUser().getId();
        UserDeviceEntity userDevice = userDeviceDao.getByUserIdAndType(userId, dto.getType());
        if (userDevice == null) {
            throw new ServerException("未找到对应类型的设备绑定记录");
        }
        userDeviceDao.deleteById(userDevice.getId());
    }
    
    @Override
    public List<UserDeviceVO> getUserDevices() {
        Long userId = SecurityUser.getUser().getId();

        List<UserDeviceEntity> userDevices = userDeviceDao.getByUserId(userId);
        List<UserDeviceVO> result = new ArrayList<>();

        for (UserDeviceEntity entity : userDevices) {
            UserDeviceVO vo = new UserDeviceVO();
            vo.setId(entity.getId());
            vo.setType(entity.getType());
            vo.setGroupName(entity.getGroupName());
            vo.setBindTime(entity.getBindTime());
            List<DeviceEntity> devices = deviceDao.getByType(entity.getType());
            List<DeviceVO> deviceVOs = new ArrayList<>();
            for (DeviceEntity deviceEntity : devices) {
                DeviceVO deviceVO = new DeviceVO();
                deviceVO.setId(deviceEntity.getId());
                deviceVO.setDeviceMac(deviceEntity.getDeviceMac());
                deviceVO.setName(deviceEntity.getName());
                deviceVO.setType(deviceEntity.getType());
                deviceVO.setStatus(deviceEntity.getStatus());
                deviceVO.setTemperature(deviceEntity.getTemperature());
                deviceVO.setHumidity(deviceEntity.getHumidity());
                deviceVO.setLocation(deviceEntity.getLocation());
                deviceVOs.add(deviceVO);
            }
            vo.setDevices(deviceVOs);
            result.add(vo);
        }
        
        return result;
    }
}