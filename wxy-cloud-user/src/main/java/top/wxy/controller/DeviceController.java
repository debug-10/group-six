package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.wxy.dto.DeviceAddDTO;
import top.wxy.dto.DeviceUnbindDTO;
import top.wxy.framework.common.utils.Result;
import top.wxy.service.DeviceService;
import top.wxy.vo.UserDeviceVO;

import java.util.List;

/**
 * 设备控制器
 * @author 笼中雀
 */
@RestController
@RequestMapping("api/device")
@AllArgsConstructor
@Tag(name = "设备模块")
public class DeviceController {
    
    private final DeviceService deviceService;
    
    @PostMapping("add")
    @Operation(summary = "添加设备到用户")
    public Result<String> addDeviceToUser(@RequestBody @Valid DeviceAddDTO dto) {
        deviceService.addDeviceToUser(dto);
        return Result.ok("设备添加成功");
    }
    
    @GetMapping("list")
    @Operation(summary = "获取用户设备列表")
    public Result<List<UserDeviceVO>> getUserDevices() {
        List<UserDeviceVO> userDevices = deviceService.getUserDevices();
        return Result.ok(userDevices);
    }
    
    @PostMapping("unbind")
    @Operation(summary = "解绑用户设备")
    public Result<String> unbindUserDevice(@RequestBody @Valid DeviceUnbindDTO dto) {
        deviceService.unbindUserDevice(dto);
        return Result.ok("设备解绑成功");
    }
}