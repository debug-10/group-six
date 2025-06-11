package top.wxy.service;

import top.wxy.entity.Device;

public interface EfanService {
    void startNormalMode();
    void startPowerfulMode();
    void turnOff();
    Integer getFanStatus(Long deviceId);
    void updateDeviceStatus(Device device); // 新增方法
    void setTimer(int minutes); // 新增定时方法
}