package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import top.wxy.config.OpenWeatherConfig;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "天气模块")
public class WeatherController {

    private final OpenWeatherConfig config;
    private final RestTemplate restTemplate;

    public WeatherController(OpenWeatherConfig config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/weather")
    @Operation(summary = "获取天气")
    public Object getWeather(
            @RequestParam(required = true) String city,  // 必须传递城市参数
            @RequestParam(name = "lang", defaultValue = "en") String lang  // 可
    ) {
        try {
            // 构建请求URL（支持语言参数，如中文传'zh_cn'）
            String url = String.format(
                    "%s?q=%s&appid=%s&units=metric&lang=%s",
                    config.getApiUrl(),
                    city,
                    config.getApiKey(),
                    lang
            );

            // 发送HTTP请求并获取响应
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

            if (response == null) {
                return buildErrorResponse("404", "No weather data found for the city");
            }
            return response;

        } catch (HttpClientErrorException.BadRequest e) {
            // 处理400错误（如无效城市名）
            return buildErrorResponse("400", "Invalid city name: " + e.getStatusText());
        } catch (HttpClientErrorException.NotFound e) {
            // 处理404错误（城市未找到）
            return buildErrorResponse("404", "City not found: " + e.getStatusText());
        } catch (Exception e) {
            // 通用异常处理（如网络问题）
            e.printStackTrace();
            return buildErrorResponse("500", "Internal server error: " + e.getMessage());
        }
    }

    // 构建错误响应
    private Map<String, String> buildErrorResponse(String code, String message) {
        Map<String, String> error = new HashMap<>();
        error.put("code", code);
        error.put("message", message);
        return error;
    }
}