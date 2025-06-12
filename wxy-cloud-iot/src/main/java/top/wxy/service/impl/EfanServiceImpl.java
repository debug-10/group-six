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
import top.wxy.commond.FanCommand;  // 导入新位置的类
import top.wxy.commond.LightCommand;  // 导入新位置的类
import top.wxy.entity.Device;
import top.wxy.mapper.EfanMapper;
import top.wxy.service.EfanService;

@Service
@Slf4j
public class EfanServiceImpl implements EfanService {

    // 直接通过 @Value 注入主题配置（无需在类中定义字段）
    @Value("${mqtt.fan-control-topic}")
    private String fanControlTopic; // 风扇控制主题（从配置文件获取）

    @Value("${mqtt.rgb-control-topic}")
    private String rgbControlTopic; // RGB控制主题（从配置文件获取）

    @Value("${mqtt.fan-status-topic}")
    private String fanStatusTopic; // 风扇状态主题（从配置文件获取）

    @Value("${mqtt.rgb-status-topic}")
    private String rgbStatusTopic; // RGB状态主题（从配置文件获取）

    @Resource
    private EfanMapper efanMapper;

    // 注入通道
    @Resource(name = "fanControlChannel")
    private MessageChannel fanControlChannel;

    @Resource(name = "rgbControlChannel")
    private MessageChannel rgbControlChannel;

    //赋值顺序是r b g!!!!!!

    @Override
    //普通模式128+天蓝色R: 135, G: 206, B: 255
    public void startNormalMode() {
        sendFanCommand(128);
        sendLightCommand(135, 255, 206);
    }

    @Override
    //强劲模式255+紫红色R: 255, G: 0, B: 255
    public void startPowerfulMode() {
        sendFanCommand(255);
        sendLightCommand(255, 255, 0);
    }

    @Override
    //关闭时0+白奶色R: 255, G: 248, B: 231
    public void turnOff() {
        sendFanCommand(0);
        sendLightCommand(255, 231, 248); // 补充RGB控制
    }

    @Override
    public Integer getFanStatus(Long deviceId) {
        Device device = efanMapper.selectById(deviceId);
        return device != null ? device.getStatus() : 0;
    }

    @Override
    public void updateDeviceStatus(Device device) {
        efanMapper.updateById(device);
    }
    
    @Override
    public void setTimer(int minutes) {
        // 创建带定时参数的命令，不设置speed，或设置为非0值
        FanCommand command = new FanCommand(128); // 设置为中速，或者不设置speed
        command.setTimer(minutes);
        
        String payload = JSON.toJSONString(command);
        log.info("准备发送的风扇定时MQTT消息: {}", payload);
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, fanControlTopic)
                .build();
        boolean success = fanControlChannel.send(message);
        log.info("风扇定时MQTT消息发送结果: {}", success ? "成功" : "失败");
    }

    private void sendFanCommand(int speed) {
        String payload = JSON.toJSONString(new FanCommand(speed));
        log.info("准备发送的MQTT消息: {}", payload);
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, fanControlTopic)
                .build();
        boolean success = fanControlChannel.send(message);
        log.info("MQTT消息发送结果: {}", success ? "成功" : "失败");
    }

    private void sendLightCommand(int r, int g, int b) {
        String payload = JSON.toJSONString(new LightCommand(r, g, b));
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, rgbControlTopic)
                .build();
        boolean success = rgbControlChannel.send(message);
        log.info("发送RGB控制指令结果: {}", success ? "成功" : "失败");
    }
}