package top.wxy.service;

import top.wxy.entity.Device;

public interface NightLightService {
    // 开关控制
    void turnOn(Integer r, Integer g, Integer b);
    void turnOff();
    
    // 模式控制
    void setAutoMode(boolean auto);
    void setAlwaysOnMode(boolean alwaysOn);
    void setTimer(int minutes);
    
    // 状态查询
    Integer getNightLightStatus(Long deviceId);
    void updateDeviceStatus(Device device);
}