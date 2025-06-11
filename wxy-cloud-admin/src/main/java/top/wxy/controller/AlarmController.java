package top.wxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.wxy.model.dto.ApiResponse;
import top.wxy.model.dto.PaginationDTO;
import top.wxy.model.dto.AlarmDTO;
import top.wxy.model.dto.AlarmListDTO;
import top.wxy.model.entity.Alarm;
import top.wxy.service.AlarmService;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 笼中雀
 */
@RestController
@RequestMapping("/alarms")
@Tag(name = "告警管理")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @PostMapping
    @Operation(summary = "创建告警")
    public ApiResponse<Map<String, Long>> create(@RequestBody AlarmDTO alarmDTO) {
        Alarm alarm = alarmService.convertToEntity(alarmDTO);
        alarm = alarmService.saveAlarm(alarm);
        Map<String, Long> data = new HashMap<>();
        data.put("alarm_id", alarm.getId());
        return ApiResponse.success("告警创建成功", data);
    }

    @GetMapping
    @Operation(summary = "分页查询告警")
    public ApiResponse<Map<String, Object>> listAlarms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer level) {
        IPage<AlarmListDTO> alarmPage = alarmService.listAlarms(page, limit, status, level);

        PaginationDTO pagination = new PaginationDTO();
        pagination.setTotal(alarmPage.getTotal());
        pagination.setPage(page);
        pagination.setLimit(limit);

        Map<String, Object> data = new HashMap<>();
        data.put("alarms", alarmPage.getRecords());
        data.put("pagination", pagination);

        return ApiResponse.success("查询成功", data);
    }



    // 将原有的接口路径修改为根据设备ID查询
    @GetMapping("/{deviceId}")
    @Operation(summary = "根据设备ID查询告警")
    public ApiResponse<Map<String, List<AlarmListDTO>>> getAlarmsByDeviceId(@PathVariable String deviceId) {
        List<AlarmListDTO> alarms = alarmService.getAlarmsByDeviceId(deviceId);
        Map<String, List<AlarmListDTO>> data = new HashMap<>();
        data.put("alarms", alarms);
        return ApiResponse.success("查询成功", data);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新告警")
    public ApiResponse<Void> updateAlarm(@PathVariable Long id, @RequestBody AlarmDTO alarmDTO) {
        alarmService.updateAlarm(id, alarmDTO);
        return ApiResponse.success("告警信息更新成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除告警")
    public ApiResponse<Void> deleteAlarm(@PathVariable Long id) {
        alarmService.deleteAlarm(id);
        return ApiResponse.success("告警删除成功");
    }
}