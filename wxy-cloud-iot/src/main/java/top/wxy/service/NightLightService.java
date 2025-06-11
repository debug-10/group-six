package top.wxy.service;

import top.wxy.entity.Device;

/**
 * @author 笼中雀
 */
public interface NightLightService {
    void turnOn(Integer r, Integer g, Integer b);
    void turnOff();
    void setAutoMode(boolean auto);
    void setAlwaysOnMode(boolean alwaysOn);
    void setTimer(int minutes);
    Integer getNightLightStatus(Long deviceId);
    void updateDeviceStatus(Device device);
}