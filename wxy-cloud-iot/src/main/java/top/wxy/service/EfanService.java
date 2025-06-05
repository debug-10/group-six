package top.wxy.service;


import top.wxy.dto.FanStatusDTO;

public interface EfanService {
    // 1. 开启普通模式（风扇中速+白色灯）
    void startNormalMode();

    // 2. 开启强劲模式（风扇全速+红色灯）
    void startPowerfulMode();

    // 3. 关闭设备（风扇停止）
    void turnOff();

    // 4. 查询风扇状态（返回数据库中的status字段值）
    Integer getFanStatus(Long deviceId);

    // 5. 处理风扇状态上报（解析power字段并更新数据库）
    void handleFanStatusReport(FanStatusDTO statusDTO);
}