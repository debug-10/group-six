package top.wxy.service.impl;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import top.wxy.dto.FanStatusDTO;
import top.wxy.entity.Device;
import top.wxy.mapper.EfanMapper;
import top.wxy.service.EfanService;

@Service
@Slf4j
@AllArgsConstructor
public class EfanServiceImpl implements EfanService {
    private static final String FAN_CONTROL_TOPIC = "device/dev_8CCE4ED1ED34_5/control";
    private static final String LIGHT_CONTROL_TOPIC = "device/dev_8CCE4ED1ED34_0/control";

    @Resource
    private EfanMapper efanMapper;

    // 关键修改3：确保注入的Bean名称与配置类中一致
    @Resource
    private MqttPahoMessageHandler mqttMessageHandler;

    @Override
    public void startNormalMode() {
        sendFanCommand(128);
        sendLightCommand(255, 255, 255);
    }

    @Override
    public void startPowerfulMode() {
        sendFanCommand(255);
        sendLightCommand(255, 0, 0);
    }

    @Override
    public void turnOff() {
        sendFanCommand(0);
    }

    @Override
    public Integer getFanStatus(Long deviceId) {
        Device device = efanMapper.selectById(deviceId);
        return device != null ? device.getStatus() : 0;
    }

    @Override
    public void handleFanStatusReport(FanStatusDTO statusDTO) {
        Integer status = "ON".equalsIgnoreCase(statusDTO.getFanStatus().getPower()) ? 1 : 0;
        Device device = new Device();
        device.setId(1L);
        device.setStatus(status);
        efanMapper.updateById(device);
    }

    private void sendFanCommand(int speed) {
        String payload = JSON.toJSONString(new FanCommand(speed));
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, FAN_CONTROL_TOPIC)
                .build();
        mqttMessageHandler.handleMessage(message);
    }

    private void sendLightCommand(int r, int g, int b) {
        String payload = JSON.toJSONString(new LightCommand(r, g, b));
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, LIGHT_CONTROL_TOPIC)
                .build();
        mqttMessageHandler.handleMessage(message);
    }

    private static class FanCommand {
        private final int speed;
        public FanCommand(int speed) { this.speed = speed; }
    }

    private static class LightCommand {
        private final int r, g, b;
        public LightCommand(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
}