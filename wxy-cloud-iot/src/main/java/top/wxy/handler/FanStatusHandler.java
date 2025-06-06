package top.wxy.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import top.wxy.dto.FanStatusDTO;
import top.wxy.entity.Device;
import top.wxy.service.EfanService;

@Slf4j // 新增注解
@Component
public class FanStatusHandler {

    @Resource
    private EfanService efanService;

    @ServiceActivator(inputChannel = "fanStatusChannel")
    public void handleFanStatus(Message<String> message) {
        String payload = message.getPayload();
        log.info("收到风扇状态消息: {}", payload);

        try {
            // 解析JSON
            FanStatusDTO statusDTO = JSON.parseObject(payload, FanStatusDTO.class);
            if (statusDTO == null || statusDTO.getFanStatus() == null) {
                log.warn("风扇状态消息格式错误: {}", payload);
                return;
            }

            // 更新设备状态（ON/OFF → 1/0）
            Integer status = "ON".equalsIgnoreCase(statusDTO.getFanStatus().getPower()) ? 1 : 0;
            Device device = new Device();
            device.setId(1L); // 根据实际业务获取设备ID
            device.setStatus(status);
            efanService.updateDeviceStatus(device);
            log.info("更新设备状态为: {}", status == 1 ? "在线" : "离线");
        } catch (Exception e) {
            log.error("处理风扇状态消息失败", e);
        }
    }
}