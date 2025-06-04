package top.wxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.wxy.model.dto.AlarmDTO;
import top.wxy.model.dto.AlarmListDTO;
import top.wxy.model.entity.Alarm;

public interface AlarmService {
    Alarm insert(Alarm entity);

    Alarm saveAlarm(Alarm entity);

    Alarm convertToEntity(AlarmDTO dto);

    IPage<AlarmListDTO> listAlarms(int page, int limit, Integer status, Integer level);

    AlarmListDTO getAlarmById(Long id);

    void updateAlarm(Long id, AlarmDTO dto);

    void deleteAlarm(Long id);
}