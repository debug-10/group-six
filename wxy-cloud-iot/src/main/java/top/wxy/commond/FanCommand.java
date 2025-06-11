package top.wxy.commond;

import lombok.Data;

@Data // 自动生成 getter/setter + 无参构造
public class FanCommand {
    private int speed; // 移除 final 修饰
    private Integer timer; // 定时时间（分钟）

    // 保留业务构造方法
    public FanCommand(int speed) {
        this.speed = speed;
    }
    
    // 添加带定时的构造方法
    public FanCommand(int speed, Integer timer) {
        this.speed = speed;
        this.timer = timer;
    }
}