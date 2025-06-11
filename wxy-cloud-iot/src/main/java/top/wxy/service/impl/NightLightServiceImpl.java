package top.wxy.service.impl;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import top.wxy.entity.Device;
import top.wxy.mapper.NightLightMapper;
import top.wxy.service.NightLightService;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NightLightServiceImpl implements NightLightService {

    @Value("${mqtt.nightlight-control-topic}")
    private String nightlightControlTopic;

    @Resource
    private NightLightMapper nightLightMapper;

    @Resource(name = "nightlightControlChannel")
    private MessageChannel nightlightControlChannel;

    @Override
    public void turnOn(Integer r, Integer g, Integer b) {
        Map<String, Object> command = new HashMap<>();
        command.put("power", "ON");
        
        // 如果提供了RGB值，则设置颜色
        if (r != null && g != null && b != null) {
            command.put("r", r);
            command.put("g", g);
            command.put("b", b);
        }
        
        sendNightLightCommand(command);
    }

    @Override
    public void turnOff() {
        Map<String, Object> command = new HashMap<>();
        command.put("power", "OFF");
        sendNightLightCommand(command);
    }

    @Override
    public void setAutoMode(boolean auto) {
        Map<String, Object> command = new HashMap<>();
        command.put("auto", auto);
        sendNightLightCommand(command);
    }

    @Override
    public void setAlwaysOnMode(boolean alwaysOn) {
        Map<String, Object> command = new HashMap<>();
        command.put("alwaysOn", alwaysOn);
        sendNightLightCommand(command);
    }

    @Override
    public void setTimer(int minutes) {
        Map<String, Object> command = new HashMap<>();
        command.put("timer", minutes);
        sendNightLightCommand(command);
    }

    @Override
    public Integer getNightLightStatus(Long deviceId) {
        Device device = nightLightMapper.selectById(deviceId);
        return device != null ? device.getStatus() : 0;
    }

    @Override
    public void updateDeviceStatus(Device device) {
        nightLightMapper.updateById(device);
    }

    private void sendNightLightCommand(Map<String, Object> command) {
        String payload = JSON.toJSONString(command);
        log.info("准备发送的夜灯控制MQTT消息: {}", payload);
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, nightlightControlTopic)
                .build();
        boolean success = nightlightControlChannel.send(message);
        log.info("夜灯控制MQTT消息发送结果: {}", success ? "成功" : "失败");
    }
}