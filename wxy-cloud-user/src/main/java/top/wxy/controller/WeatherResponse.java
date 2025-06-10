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
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int cod;
    private String message;
    private Main main;
    private Weather[] weather;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    private long dt;
    private int id;

    private String cityName;
    private String weatherMainCn;
    private String weatherDescCn;
    private String dateTimeCn;
    private String sunriseCn;
    private String sunsetCn;

    public void setName(String name) {
        this.name = name;
        this.cityName = WeatherUtils.getChineseCityName(name);
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
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private double sea_level;
        private double grnd_level;

        public Main() {}
        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        @JsonProperty("feels_like")
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
        private int id;
        private String main;
        private String description;
        private String icon;

        public Weather() {}

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
        private double speed;
        private double deg;
        private double gust;

        public Wind() {}

        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
        public double getDeg() { return deg; }
        public void setDeg(double deg) { this.deg = deg; }
        public double getGust() { return gust; }
        public void setGust(double gust) { this.gust = gust; }
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
        private int all;
        public Clouds() {}
        public int getAll() { return all; }
        public void setAll(int all) { this.all = all; }

        public String getCloudCoverCn() {
            if (all < 10) {
                return "晴朗";
            }
            if (all < 30) {
                return "少云";
            }
            if (all < 70) {
                return "多云";
            }
            return "阴天";
        }
    }

    // 内部类：系统信息
    public static class Sys {
        private String country;
        private int sunrise;
        private int sunset;

        public Sys() {}

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public int getSunrise() { return sunrise; }
        public void setSunrise(int sunrise) { this.sunrise = sunrise; }
        public int getSunset() { return sunset; }
        public void setSunset(int sunset) { this.sunset = sunset; }
        public String getCountryCn() {
            return WeatherUtils.getCountryCn(country);
        }
    }
}