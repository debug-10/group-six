package top.wxy.service;

import top.wxy.dto.DeviceAddDTO;
import top.wxy.dto.DeviceUnbindDTO;
import top.wxy.vo.UserDeviceVO;

import java.util.List;

/**
 * 设备服务接口
 * @author 笼中雀
 */
public interface DeviceService {
    
    /**
     * 添加设备到用户
     * @param dto 设备添加DTO
     */
    void addDeviceToUser(DeviceAddDTO dto);
    
    /**
     * 获取用户的设备列表
     * @return 用户设备列表
     */
    List<UserDeviceVO> getUserDevices();
    
    /**
     * 解绑用户设备
     * @param dto 设备解绑DTO
     */
    void unbindUserDevice(DeviceUnbindDTO dto);
}