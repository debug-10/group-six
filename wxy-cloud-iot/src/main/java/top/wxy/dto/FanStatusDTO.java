package top.wxy.dto;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 风扇状态上报DTO
 * 对应MQTT消息格式：{"fan": {"power": "OFF", "speed": 0, "timer": 0}}
 */
@Data
public class FanStatusDTO {
    @JSONField(name = "fan")
    private FanStatus fanStatus;

    @Data
    public static class FanStatus {
        private String power; // "ON" 或 "OFF"
        private Integer speed; // 风速值（可选）
        private Integer timer; // 剩余定时时间（秒）
    }
}