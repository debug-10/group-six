package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.framework.common.utils.Result;
import top.wxy.mapper.AlarmMapper;
import top.wxy.mapper.DeviceMapper;
import top.wxy.model.entity.Alarm;

import java.util.*;

/**
 * 数据大屏控制器
 */
@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "数据大屏")
@AllArgsConstructor
public class DashboardController {

    private final DeviceMapper deviceMapper;
    private final AlarmMapper alarmMapper;

    /**
     * 分类一：设备总览统计
     */
    @GetMapping("/summary")
    @Operation(summary = "设备总览")
    public Result<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("deviceTotal", deviceMapper.countAll());
        summary.put("deviceOnline", deviceMapper.countOnline());
        summary.put("deviceToday", deviceMapper.countToday());
        summary.put("alarmToday", alarmMapper.countToday());
        return Result.ok(summary);
    }

    /**
     * 分类二：告警趋势数据（近7日）
     */
    @GetMapping("/alarmTrend")
    @Operation(summary = "告警趋势")
    public Result<List<Map<String, Object>>> getAlarmTrend() {
        List<Map<String, Object>> trend = alarmMapper.countByDayLast7();
        return Result.ok(trend);
    }

    /**
     * 分类三：告警等级分布
     */
    @GetMapping("/alarmLevelStats")
    @Operation(summary = "告警等级")
    public Result<List<Map<String, Object>>> getAlarmLevelStats() {
        List<Map<String, Object>> levelStats = alarmMapper.countByLevel();
        return Result.ok(levelStats);
    }

    /**
     * 分类四：最近告警记录列表
     */
    @GetMapping("/recentAlarms")
    @Operation(summary = "最近告警列表")
    public Result<List<Alarm>> getRecentAlarms() {
        List<Alarm> recentAlarms = alarmMapper.selectRecentAlarms();
        return Result.ok(recentAlarms);
    }

    /**
     * 分类五：设备地图点位（经纬度）
     */
    @GetMapping("/deviceMap")
    @Operation(summary = "设备地图")
    public Result<List<Map<String, Object>>> getDeviceMap() {
        List<Map<String, String>> rawList = deviceMapper.selectDeviceLocations();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, String> item : rawList) {
            String[] loc = item.get("location").split(",");
            if (loc.length == 2) {
                try {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("name", item.get("name"));
                    obj.put("value", List.of(
                            Double.parseDouble(loc[0]),
                            Double.parseDouble(loc[1])
                    ));
                    result.add(obj);
                } catch (NumberFormatException e) {
                    // 可以添加日志记录非法坐标
                }
            }
        }
        return Result.ok(result);
    }
}
