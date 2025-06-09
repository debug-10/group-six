package top.wxy.commond;

import lombok.Data;

@Data // 自动生成 getter/setter + 无参构造
public class LightCommand {
    private int r, g, b;
    public LightCommand(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}