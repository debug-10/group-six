package top.wxy.commond;

import lombok.Data;

@Data // 自动生成 getter/setter + 无参构造

//ps:g和b接反了，记得反过来写!!!!!
public class LightCommand {
    private int r, g, b;
    public LightCommand(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}