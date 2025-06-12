package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.wxy.framework.common.utils.Result;
import top.wxy.service.NightLightService;

@RestController
@RequestMapping("/nightlight")
@Tag(name = "智能夜灯控制")
public class NightLightController {

    @Resource
    private NightLightService nightLightService;

    @PostMapping("/on")
    @Operation(summary = "开灯")
    public Result<?> turnOn() {
        // 默认开灯为暖白色 (255,255,255)
        nightLightService.turnOn(255, 255, 255);
        return Result.ok();
    }

    @PostMapping("/off")
    @Operation(summary = "关灯")
    public Result<?> turnOff() {
        nightLightService.turnOff();
        return Result.ok();
    }

    @PostMapping("/color")
    @Operation(summary = "设置颜色")
    public Result<?> setColor(@RequestParam Integer r, @RequestParam Integer g, @RequestParam Integer b) {
        nightLightService.turnOn(r, g, b);
        return Result.ok();
    }

    @PostMapping("/auto")
    @Operation(summary = "设置自动模式")
    public Result<?> setAutoMode(@RequestParam Boolean auto) {
        nightLightService.setAutoMode(auto);
        return Result.ok();
    }

    @PostMapping("/always-on")
    @Operation(summary = "设置常亮模式")
    public Result<?> setAlwaysOnMode(@RequestParam Boolean alwaysOn) {
        nightLightService.setAlwaysOnMode(alwaysOn);
        return Result.ok();
    }

    @PostMapping("/timer")
    @Operation(summary = "设置定时")
    public Result<?> setTimer(@RequestParam Integer minutes) {
        nightLightService.setTimer(minutes);
        return Result.ok();
    }

    @GetMapping("/status/{deviceId}")
    @Operation(summary = "查询夜灯状态")
    public Result<String> getNightLightStatus(@PathVariable Long deviceId) {
        Integer statusCode = nightLightService.getNightLightStatus(deviceId);
        String status = statusCode == 1 ? "开启" : "关闭";
        return Result.ok(status);
    }
}