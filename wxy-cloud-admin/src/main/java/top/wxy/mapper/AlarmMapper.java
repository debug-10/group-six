package top.wxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import top.wxy.model.entity.Alarm;

import java.util.List;
import java.util.Map;

public interface AlarmMapper extends BaseMapper<Alarm> {

    @Select("SELECT COUNT(*) FROM t_alarm WHERE DATE(create_time) = CURDATE()")
    int countToday();

    /**
     * 最近7天告警趋势
     */
    @Select("""
        SELECT DATE(create_time) as day, COUNT(*) as count
        FROM t_alarm
        WHERE create_time >= CURDATE() - INTERVAL 6 DAY
        GROUP BY day ORDER BY day
    """)
    List<Map<String, Object>> countByDayLast7();

    /**
     * 告警等级分布
     */
    @Select("""
        SELECT level, COUNT(*) as count
        FROM t_alarm
        GROUP BY level ORDER BY level
    """)
    List<Map<String, Object>> countByLevel();

    /**
     * 最近告警记录（最新10条）
     */
    @Select("SELECT * FROM t_alarm ORDER BY create_time DESC LIMIT 10")
    List<Alarm> selectRecentAlarms();
}