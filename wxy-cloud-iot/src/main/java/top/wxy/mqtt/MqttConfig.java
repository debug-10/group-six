package top.wxy.mqtt;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "mqtt") // 保留原有配置绑定
@PropertySource(value = "nacos:common.yaml", ignoreResourceNotFound = true) // 显式指定配置源
public class MqttConfig {
    private String brokerUrl;
    private String username;
    private String password;
    private String clientId;

    // 直接通过 @Value 注入主题配置（无需在类中定义字段）
    @Value("${mqtt.fan-control-topic}")
    private String fanControlTopic; // 风扇控制主题（从配置文件获取）

    @Value("${mqtt.rgb-control-topic}")
    private String rgbControlTopic; // RGB控制主题（从配置文件获取）

    @Value("${mqtt.fan-status-topic}")
    private String fanStatusTopic; // 风扇状态主题（从配置文件获取）

    @Value("${mqtt.rgb-status-topic}")
    private String rgbStatusTopic; // RGB状态主题（从配置文件获取）

    @PostConstruct
    public void init() {
        log.info("MQTT 主机: {} 客户端ID：{}", this.brokerUrl, this.clientId);
        log.info("主题配置：风扇控制={}, RGB控制={}", fanControlTopic, rgbControlTopic);
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        return factory;
    }

    // ---------------------- 输出通道（发送消息） ----------------------
    @Bean
    public MessageChannel fanControlChannel() {
        return new DirectChannel(); // 风扇控制指令通道
    }

    @Bean
    public MessageChannel rgbControlChannel() {
        return new DirectChannel(); // RGB控制指令通道
    }

    // 风扇控制消息处理器（使用注入的主题）
    @Bean
    @ServiceActivator(inputChannel = "fanControlChannel")
    public MqttPahoMessageHandler fanControlMessageHandler() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                fanControlTopic, // 直接使用注入的主题值
                mqttClientFactory()
        );
        handler.setAsync(true);
        handler.setDefaultQos(1);
        handler.setConverter(new DefaultPahoMessageConverter());
        return handler;
    }

    // RGB控制消息处理器（使用注入的主题）
    @Bean
    @ServiceActivator(inputChannel = "rgbControlChannel")
    public MqttPahoMessageHandler rgbControlMessageHandler() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                rgbControlTopic, // 直接使用注入的主题值
                mqttClientFactory()
        );
        handler.setAsync(true);
        handler.setDefaultQos(1);
        handler.setConverter(new DefaultPahoMessageConverter());
        return handler;
    }

    // ---------------------- 输入通道（接收消息） ----------------------
    @Bean
    public MessageChannel fanStatusChannel() {
        return new DirectChannel(); // 风扇状态通道
    }

    @Bean
    public MessageProducer fanStatusInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                clientId + "_fan_in",
                mqttClientFactory(),
                new String[]{fanStatusTopic} // 使用注入的状态主题
        );
        adapter.setCompletionTimeout(5000);
        adapter.setOutputChannel(fanStatusChannel());
        return adapter;
    }

    @Bean
    public MessageChannel rgbStatusChannel() {
        return new DirectChannel(); // RGB状态通道
    }

    @Bean
    public MessageProducer rgbStatusInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                clientId + "_rgb_in",
                mqttClientFactory(),
                new String[]{rgbStatusTopic} // 使用注入的状态主题
        );
        adapter.setCompletionTimeout(5000);
        adapter.setOutputChannel(rgbStatusChannel());
        return adapter;
    }
}