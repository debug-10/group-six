package top.wxy.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.wxy.convert.DeviceConvert;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.common.utils.Result;
import top.wxy.model.dto.DeviceCreateDTO;
import top.wxy.model.dto.DeviceStatusDTO;
import top.wxy.model.dto.DeviceUpdateDTO;
import top.wxy.model.query.DeviceQuery;
import top.wxy.service.DeviceService;
import top.wxy.model.vo.DeviceVO;

@RestController
@AllArgsConstructor
@Tag(name = "设备管理", description = "设备管理")
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping
    @Operation(summary = "创建设备")
//    @PreAuthorize("hasAuthority('sys:device:create')")
    public Result<Long> create(@RequestBody @Valid DeviceCreateDTO dto) {
        return Result.ok(deviceService.saveDevice(DeviceConvert.INSTANCE.toEntity(dto)).getId());
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询设备")
//    @PreAuthorize("hasAuthority('sys:device:view')")
    public Result<PageResult<DeviceVO>> page(@RequestBody @Valid DeviceQuery query) {
        return Result.ok(deviceService.page(query));
    }

    @PutMapping
    @Operation(summary = "更新设备")
//    @PreAuthorize("hasAuthority('sys:device:edit')")
    public Result<String> update(@RequestBody @Valid DeviceUpdateDTO dto) {
        deviceService.update(dto);
        return Result.ok();
    }

    @PostMapping("/export")
    @Operation(summary = "导出设备")
//    @PreAuthorize("hasAuthority('sys:device:export')")
    public void export(@RequestBody DeviceQuery query, HttpServletResponse response) {
        deviceService.export(query, response);
    }

    @PutMapping("/status")
    @Operation(summary = "更新设备状态")
//    @PreAuthorize("hasAuthority('sys:device:status')")
    public Result<String> updateStatus(@RequestBody @Valid DeviceStatusDTO dto) {
        deviceService.updateStatus(dto);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除设备")
//    @PreAuthorize("hasAuthority('sys:device:delete')")
    public Result<String> delete(@PathVariable Long id) {
        deviceService.removeById(id);
        return Result.ok();
    }
}