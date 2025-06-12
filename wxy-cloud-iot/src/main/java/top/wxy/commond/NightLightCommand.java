package top.wxy.commond;

import lombok.Data;

@Data
public class NightLightCommand {
    private String power;
    private Integer r;
    private Integer g;
    private Integer b;
    private Boolean auto;
    private Boolean alwaysOn;
    private Integer timer;
    

}