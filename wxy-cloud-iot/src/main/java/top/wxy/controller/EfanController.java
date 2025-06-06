package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.wxy.framework.common.utils.Result;
import top.wxy.service.EfanService;

@AllArgsConstructor
@RestController
@RequestMapping("/efan")
@Tag(name = "Efan设备控制")
public class EfanController {

    @Resource
    private EfanService efanService;

    @PostMapping("/normal")
    @Operation(summary = "开启普通模式")
    public Result<?> startNormalMode() {
        efanService.startNormalMode();
        return Result.ok();
    }

    @PostMapping("/powerful")
    @Operation(summary = "开启强劲模式")
    public Result<?> startPowerfulMode() {
        efanService.startPowerfulMode();
        return Result.ok();
    }

    @PostMapping("/off")
    @Operation(summary = "关闭设备")
    public Result<?> turnOff() {
        efanService.turnOff();
        return Result.ok();
    }

    @GetMapping("/status/{deviceId}")
    @Operation(summary = "查询风扇状态")
    public Result<String> getFanStatus(@PathVariable Long deviceId) {
        Integer statusCode = efanService.getFanStatus(deviceId);
        String status = statusCode == 1 ? "在线" : "离线";
        return Result.ok(status);
    }
}