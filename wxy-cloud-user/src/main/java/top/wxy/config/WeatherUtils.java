package top.wxy.config;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {
    // 天气主类英文转中文映射
    private static final Map<String, String> WEATHER_MAIN_CN_MAP = new HashMap<>();
    // 天气描述ID转中文映射
    private static final Map<Integer, String> WEATHER_DESCRIPTION_CN_MAP = new HashMap<>();
    // 城市名英文转中文映射（简化版，实际应使用完整映射表）
    private static final Map<String, String> CITY_NAME_CN_MAP = new HashMap<>();
    // 国家代码转中文映射
    private static final Map<String, String> COUNTRY_CN_MAP = new HashMap<>();

    static {
        // 初始化天气主类映射
        WEATHER_MAIN_CN_MAP.put("Thunderstorm", "雷暴");
        WEATHER_MAIN_CN_MAP.put("Drizzle", "毛毛雨");
        WEATHER_MAIN_CN_MAP.put("Rain", "雨");
        WEATHER_MAIN_CN_MAP.put("Snow", "雪");
        WEATHER_MAIN_CN_MAP.put("Atmosphere", "大气");
        WEATHER_MAIN_CN_MAP.put("Clear", "晴");
        WEATHER_MAIN_CN_MAP.put("Clouds", "云");
        WEATHER_MAIN_CN_MAP.put("Mist", "薄雾");
        WEATHER_MAIN_CN_MAP.put("Smoke", "烟雾");
        WEATHER_MAIN_CN_MAP.put("Haze", "霾");
        WEATHER_MAIN_CN_MAP.put("Dust", "沙尘");
        WEATHER_MAIN_CN_MAP.put("Fog", "雾");
        WEATHER_MAIN_CN_MAP.put("Sand", "沙");
        WEATHER_MAIN_CN_MAP.put("Dust", "尘土");
        WEATHER_MAIN_CN_MAP.put("Ash", "火山灰");
        WEATHER_MAIN_CN_MAP.put("Squall", "飑");
        WEATHER_MAIN_CN_MAP.put("Tornado", "龙卷风");

        // 初始化天气描述映射（部分示例）
        WEATHER_DESCRIPTION_CN_MAP.put(200, "雷阵雨伴有轻雨");
        WEATHER_DESCRIPTION_CN_MAP.put(201, "雷阵雨");
        WEATHER_DESCRIPTION_CN_MAP.put(202, "强雷阵雨");
        WEATHER_DESCRIPTION_CN_MAP.put(210, "轻雷暴");
        WEATHER_DESCRIPTION_CN_MAP.put(211, "雷暴");
        WEATHER_DESCRIPTION_CN_MAP.put(212, "强雷暴");
        WEATHER_DESCRIPTION_CN_MAP.put(221, "零散雷暴");
        WEATHER_DESCRIPTION_CN_MAP.put(230, "雷阵雨伴有小雨");
        WEATHER_DESCRIPTION_CN_MAP.put(231, "雷阵雨伴有中雨");
        WEATHER_DESCRIPTION_CN_MAP.put(232, "雷阵雨伴有大雨");
        WEATHER_DESCRIPTION_CN_MAP.put(300, "轻毛毛雨");
        WEATHER_DESCRIPTION_CN_MAP.put(301, "毛毛雨");
        WEATHER_DESCRIPTION_CN_MAP.put(302, "重毛毛雨");
        WEATHER_DESCRIPTION_CN_MAP.put(310, "轻毛毛雨夹雨");
        WEATHER_DESCRIPTION_CN_MAP.put(311, "毛毛雨夹雨");
        WEATHER_DESCRIPTION_CN_MAP.put(312, "重毛毛雨夹雨");
        WEATHER_DESCRIPTION_CN_MAP.put(313, "阵雨夹毛毛雨");
        WEATHER_DESCRIPTION_CN_MAP.put(314, "重阵雨夹毛毛雨");
        WEATHER_DESCRIPTION_CN_MAP.put(321, "阵雨");
        WEATHER_DESCRIPTION_CN_MAP.put(500, "小雨");
        WEATHER_DESCRIPTION_CN_MAP.put(501, "中雨");
        WEATHER_DESCRIPTION_CN_MAP.put(502, "大雨");
        WEATHER_DESCRIPTION_CN_MAP.put(503, "暴雨");
        WEATHER_DESCRIPTION_CN_MAP.put(504, "特大暴雨");
        WEATHER_DESCRIPTION_CN_MAP.put(511, "冻雨");
        WEATHER_DESCRIPTION_CN_MAP.put(520, "小阵雨");
        WEATHER_DESCRIPTION_CN_MAP.put(521, "阵雨");
        WEATHER_DESCRIPTION_CN_MAP.put(522, "大阵雨");
        WEATHER_DESCRIPTION_CN_MAP.put(531, "零散阵雨");
        WEATHER_DESCRIPTION_CN_MAP.put(600, "小雪");
        WEATHER_DESCRIPTION_CN_MAP.put(601, "雪");
        WEATHER_DESCRIPTION_CN_MAP.put(602, "大雪");
        WEATHER_DESCRIPTION_CN_MAP.put(611, "雨夹雪");
        WEATHER_DESCRIPTION_CN_MAP.put(612, "小阵雨夹雪");
        WEATHER_DESCRIPTION_CN_MAP.put(613, "阵雨夹雪");
        WEATHER_DESCRIPTION_CN_MAP.put(615, "小雨夹雪");
        WEATHER_DESCRIPTION_CN_MAP.put(616, "雨夹雪");
        WEATHER_DESCRIPTION_CN_MAP.put(620, "小阵雪");
        WEATHER_DESCRIPTION_CN_MAP.put(621, "阵雪");
        WEATHER_DESCRIPTION_CN_MAP.put(622, "大阵雪");
        WEATHER_DESCRIPTION_CN_MAP.put(701, "薄雾");
        WEATHER_DESCRIPTION_CN_MAP.put(711, "烟雾");
        WEATHER_DESCRIPTION_CN_MAP.put(721, "霾");
        WEATHER_DESCRIPTION_CN_MAP.put(731, "扬沙");
        WEATHER_DESCRIPTION_CN_MAP.put(741, "雾");
        WEATHER_DESCRIPTION_CN_MAP.put(751, "沙");
        WEATHER_DESCRIPTION_CN_MAP.put(761, "尘土");
        WEATHER_DESCRIPTION_CN_MAP.put(762, "火山灰");
        WEATHER_DESCRIPTION_CN_MAP.put(771, "飑");
        WEATHER_DESCRIPTION_CN_MAP.put(781, "龙卷风");
        WEATHER_DESCRIPTION_CN_MAP.put(800, "晴");
        WEATHER_DESCRIPTION_CN_MAP.put(801, "少云");
        WEATHER_DESCRIPTION_CN_MAP.put(802, "多云");
        WEATHER_DESCRIPTION_CN_MAP.put(803, "碎云");
        WEATHER_DESCRIPTION_CN_MAP.put(804, "阴");

        // 初始化城市名映射（简化版）
        CITY_NAME_CN_MAP.put("Beijing", "北京");
        CITY_NAME_CN_MAP.put("Shanghai", "上海");
        CITY_NAME_CN_MAP.put("Guangzhou", "广州");
        CITY_NAME_CN_MAP.put("Shenzhen", "深圳");
        CITY_NAME_CN_MAP.put("Nanjing", "南京");
        CITY_NAME_CN_MAP.put("Hangzhou", "杭州");
        CITY_NAME_CN_MAP.put("Chengdu", "成都");
        CITY_NAME_CN_MAP.put("Wuhan", "武汉");
        CITY_NAME_CN_MAP.put("Chongqing", "重庆");
        CITY_NAME_CN_MAP.put("Tianjin", "天津");

        // 初始化国家代码映射
        COUNTRY_CN_MAP.put("CN", "中国");
        COUNTRY_CN_MAP.put("US", "美国");
        COUNTRY_CN_MAP.put("UK", "英国");
        COUNTRY_CN_MAP.put("JP", "日本");
        COUNTRY_CN_MAP.put("KR", "韩国");
        COUNTRY_CN_MAP.put("FR", "法国");
        COUNTRY_CN_MAP.put("DE", "德国");
        COUNTRY_CN_MAP.put("RU", "俄罗斯");
        COUNTRY_CN_MAP.put("IT", "意大利");
        COUNTRY_CN_MAP.put("ES", "西班牙");
    }

    // 获取天气主类的中文描述
    public static String getWeatherMainCn(String main) {
        return WEATHER_MAIN_CN_MAP.getOrDefault(main, main);
    }

    // 获取天气描述的中文描述
    public static String getWeatherDescriptionCn(int id) {
        return WEATHER_DESCRIPTION_CN_MAP.getOrDefault(id, "未知天气");
    }

    // 获取城市的中文名称
    public static String getChineseCityName(String cityName) {
        return CITY_NAME_CN_MAP.getOrDefault(cityName, cityName);
    }

    // 获取国家代码的中文名称
    public static String getCountryCn(String countryCode) {
        return COUNTRY_CN_MAP.getOrDefault(countryCode, countryCode);
    }
}