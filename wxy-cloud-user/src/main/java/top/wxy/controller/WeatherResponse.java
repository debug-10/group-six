package top.wxy.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

// 根响应对象
@JsonIgnoreProperties(ignoreUnknown = true)  // 忽略未知字段
public class WeatherResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;          // 城市名
    private int cod;              // 状态码（如200成功，404失败）
    private String message;       // 状态信息（仅失败时返回）
    private Main main;            // 主要天气数据
    private Weather[] weather;    // 天气描述数组
    private Wind wind;            // 风速数据
    private Clouds clouds;        // 云层数据
    private Sys sys;              // 系统信息（如国家代码）
    private long dt;              // 数据时间戳
    private int id;               // 城市ID

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCod() { return cod; }
    public void setCod(int cod) { this.cod = cod; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }
    public Weather[] getWeather() { return weather; }
    public void setWeather(Weather[] weather) { this.weather = weather; }
    public Wind getWind() { return wind; }
    public void setWind(Wind wind) { this.wind = wind; }
    public Clouds getClouds() { return clouds; }
    public void setClouds(Clouds clouds) { this.clouds = clouds; }
    public Sys getSys() { return sys; }
    public void setSys(Sys sys) { this.sys = sys; }
    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // 内部类：主要天气数据
    public static class Main {
        private double temp;          // 温度（℃）
        private double feels_like;    // 体感温度（℃）
        private double temp_min;      // 最低温（℃）
        private double temp_max;      // 最高温（℃）
        private int pressure;         // 气压（hPa）
        private int humidity;         // 湿度（%）
        private double sea_level;     // 海平面气压（hPa，可选）
        private double grnd_level;    // 地面气压（hPa，可选）

        // Getters and Setters
        // ...（省略，与之前类似）
    }

    // 内部类：天气描述
    public static class Weather {
        private int id;               // 天气ID（如800=晴天）
        private String main;          // 天气主类（如"Clear"）
        private String description;   // 详细描述（如"clear sky"）
        private String icon;          // 图标代码（如"01d"）

        // Getters and Setters
        // ...（省略，与之前类似）
    }

    // 内部类：风速数据
    public static class Wind {
        private double speed;         // 风速（m/s）
        private double deg;           // 风向（度）
        private double gust;          // 阵风风速（m/s，可选）

        // Getters and Setters
        // ...（省略，与之前类似）
    }

    // 内部类：云层数据
    public static class Clouds {
        private int all;              // 云层覆盖率（%）
    }

    // 内部类：系统信息
    public static class Sys {
        private String country;       // 国家代码（如"CN"）
        private int sunrise;          // 日出时间戳（UTC）
        private int sunset;           // 日落时间戳（UTC）
    }
}