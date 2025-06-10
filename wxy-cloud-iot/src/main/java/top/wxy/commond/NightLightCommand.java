package top.wxy.commond;

import lombok.Data;

@Data
public class NightLightCommand {
    private String power;      // "ON" 或 "OFF"
    private Integer r;        // 红色值 (0-255)
    private Integer g;        // 绿色值 (0-255)
    private Integer b;        // 蓝色值 (0-255)
    private Boolean auto;     // 自动模式
    private Boolean alwaysOn; // 常亮模式
    private Integer timer;    // 定时时间（分钟）
    

}