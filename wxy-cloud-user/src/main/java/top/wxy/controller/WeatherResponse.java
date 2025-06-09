package top.wxy.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import top.wxy.config.WeatherUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


// 根响应对象
@Getter
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

    // 新增：中文描述字段
    private String cityName;          // 中文城市名
    private String weatherMainCn;     // 天气主类中文
    private String weatherDescCn;     // 天气描述中文
    private String dateTimeCn;        // 数据时间中文
    private String sunriseCn;         // 日出时间中文
    private String sunsetCn;          // 日落时间中文

    public void setName(String name) {
        this.name = name;
        this.cityName = WeatherUtils.getChineseCityName(name); // 设置中文城市名
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
        if (weather != null && weather.length > 0) {
            this.weatherMainCn = WeatherUtils.getWeatherMainCn(weather[0].getMain());
            this.weatherDescCn = WeatherUtils.getWeatherDescriptionCn(weather[0].getId());
        }
    }

    public void setDt(long dt) {
        this.dt = dt;
        this.dateTimeCn = formatUnixTime(dt);
    }

    public void setSys(Sys sys) {
        this.sys = sys;
        if (sys != null) {
            this.sunriseCn = formatUnixTime(sys.getSunrise());
            this.sunsetCn = formatUnixTime(sys.getSunset());
        }
    }

    // 工具方法：将Unix时间戳转换为中文日期时间
    private String formatUnixTime(long unixTime) {
        if (unixTime <= 0) return "";
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        return dateTime.format(formatter);
    }

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

        // 无参构造函数（必须）
        public Main() {}

        // Getters and Setters
        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        @JsonProperty("feels_like")  // 明确映射JSON字段名
        public double getFeelsLike() { return feels_like; }
        public void setFeelsLike(double feels_like) { this.feels_like = feels_like; }

        @JsonProperty("temp_min")
        public double getTempMin() { return temp_min; }
        public void setTempMin(double temp_min) { this.temp_min = temp_min; }

        @JsonProperty("temp_max")
        public double getTempMax() { return temp_max; }
        public void setTempMax(double temp_max) { this.temp_max = temp_max; }

        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }

        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }

        @JsonProperty("sea_level")
        public double getSeaLevel() { return sea_level; }
        public void setSeaLevel(double sea_level) { this.sea_level = sea_level; }

        @JsonProperty("grnd_level")
        public double getGrndLevel() { return grnd_level; }
        public void setGrndLevel(double grnd_level) { this.grnd_level = grnd_level; }

        // 新增：带单位的温度显示
        public String getTempWithUnit() {
            return String.format("%.1f℃", temp);
        }

        public String getFeelsLikeWithUnit() {
            return String.format("%.1f℃", feels_like);
        }

        public String getTempMinWithUnit() {
            return String.format("%.1f℃", temp_min);
        }

        public String getTempMaxWithUnit() {
            return String.format("%.1f℃", temp_max);
        }
    }

    // 内部类：天气描述
    public static class Weather {
        private int id;               // 天气ID（如800=晴天）
        private String main;          // 天气主类（如"Clear"）
        private String description;   // 详细描述（如"clear sky"）
        private String icon;          // 图标代码（如"01d"）

        // 无参构造函数
        public Weather() {}

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    // 内部类：风速数据
    public static class Wind {
        private double speed;         // 风速（m/s）
        private double deg;           // 风向（度）
        private double gust;          // 阵风风速（m/s，可选）

        // 无参构造函数
        public Wind() {}

        // Getters and Setters
        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
        public double getDeg() { return deg; }
        public void setDeg(double deg) { this.deg = deg; }
        public double getGust() { return gust; }
        public void setGust(double gust) { this.gust = gust; }

        // 新增：带单位的风速显示
        public String getSpeedWithUnit() {
            return String.format("%.1f米/秒", speed);
        }

        public String getGustWithUnit() {
            return String.format("%.1f米/秒", gust);
        }

        // 新增：风向中文描述
        public String getDirectionCn() {
            double direction = deg % 360;
            if (direction >= 337.5 || direction < 22.5) return "北风";
            if (direction >= 22.5 && direction < 67.5) return "东北风";
            if (direction >= 67.5 && direction < 112.5) return "东风";
            if (direction >= 112.5 && direction < 157.5) return "东南风";
            if (direction >= 157.5 && direction < 202.5) return "南风";
            if (direction >= 202.5 && direction < 247.5) return "西南风";
            if (direction >= 247.5 && direction < 292.5) return "西风";
            if (direction >= 292.5 && direction < 337.5) return "西北风";
            return "未知风向";
        }
    }

    // 内部类：云层数据
    public static class Clouds {
        private int all;              // 云层覆盖率（%）

        // 无参构造函数
        public Clouds() {}

        // Getters and Setters
        public int getAll() { return all; }
        public void setAll(int all) { this.all = all; }

        // 新增：云层覆盖率中文描述
        public String getCloudCoverCn() {
            if (all < 10) return "晴朗";
            if (all < 30) return "少云";
            if (all < 70) return "多云";
            return "阴天";
        }
    }

    // 内部类：系统信息
    public static class Sys {
        private String country;       // 国家代码（如"CN"）
        private int sunrise;          // 日出时间戳（UTC）
        private int sunset;           // 日落时间戳（UTC）

        // 无参构造函数
        public Sys() {}

        // Getters and Setters
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public int getSunrise() { return sunrise; }
        public void setSunrise(int sunrise) { this.sunrise = sunrise; }
        public int getSunset() { return sunset; }
        public void setSunset(int sunset) { this.sunset = sunset; }

        // 新增：国家代码转中文
        public String getCountryCn() {
            return WeatherUtils.getCountryCn(country);
        }
    }
}