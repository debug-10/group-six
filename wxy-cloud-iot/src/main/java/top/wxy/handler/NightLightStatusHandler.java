package top.wxy.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import top.wxy.dto.NightLightStatusDTO;
import top.wxy.entity.Device;
import top.wxy.service.NightLightService;

@Slf4j
@Component
public class NightLightStatusHandler {

    @Resource
    private NightLightService nightLightService;

    @ServiceActivator(inputChannel = "nightlightStatusChannel")
    public void handleNightLightStatus(Message<String> message) {
        String payload = message.getPayload();
        log.info("收到夜灯状态消息: {}", payload);

        try {
            // 解析JSON
            NightLightStatusDTO statusDTO = JSON.parseObject(payload, NightLightStatusDTO.class);
            if (statusDTO == null || statusDTO.getLightStatus() == null) {
                log.warn("夜灯状态消息格式错误: {}", payload);
                return;
            }

            // 更新设备状态（ON/OFF → 1/0）
            Integer status = "ON".equalsIgnoreCase(statusDTO.getLightStatus().getPower()) ? 1 : 0;
            Device device = new Device();
            device.setId(2L); // 根据实际业务获取设备ID，这里假设夜灯ID为2
            device.setStatus(status);
            nightLightService.updateDeviceStatus(device);
            log.info("更新夜灯状态为: {}", status == 1 ? "开启" : "关闭");
        } catch (Exception e) {
            log.error("处理夜灯状态消息失败", e);
        }
    }
}