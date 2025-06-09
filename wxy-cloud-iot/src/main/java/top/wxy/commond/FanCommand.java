package top.wxy.commond;

import lombok.Data;

@Data // 自动生成 getter/setter + 无参构造
public class FanCommand {
    private int speed; // 移除 final 修饰

    // 保留业务构造方法
    public FanCommand(int speed) {
        this.speed = speed;
    }
}