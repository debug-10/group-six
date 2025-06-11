package top.wxy.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 夜灯状态上报DTO
 * 对应MQTT消息格式：{"light": {"power": "ON", "r": 255, "g": 255, "b": 255, "auto": true, "alwaysOn": false, "lux": 45.5, "motion": false, "timer": 0}}
 */
@Data
public class NightLightStatusDTO {
    @JSONField(name = "light")
    private LightStatus lightStatus;

    @Data
    public static class LightStatus {
        private String power;    // "ON" 或 "OFF"
        private Integer r;       // 红色值 (0-255)
        private Integer g;       // 绿色值 (0-255)
        private Integer b;       // 蓝色值 (0-255)
        private Boolean auto;    // 自动模式
        private Boolean alwaysOn; // 常亮模式
        private Float lux;       // 光照值
        private Boolean motion;  // 是否检测到运动
        private Integer timer;   // 剩余定时时间（秒）
    }
}