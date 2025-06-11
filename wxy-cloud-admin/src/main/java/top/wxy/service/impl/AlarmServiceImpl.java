package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wxy.mapper.AlarmMapper;
import top.wxy.mapper.DeviceMapper;
import top.wxy.model.dto.AlarmDTO;
import top.wxy.model.dto.AlarmListDTO;
import top.wxy.model.entity.Alarm;
import top.wxy.model.entity.Device;
import top.wxy.model.entity.Tenant;
import top.wxy.service.AlarmService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements AlarmService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private AlarmMapper alarmMapper;

    @Override
    public Alarm insert(Alarm entity) {
        alarmMapper.insert(entity);
        return entity;
    }

    @Override
    public Alarm saveAlarm(Alarm entity) {
        // 验证 deviceId 是否存在
        QueryWrapper<Device> deviceQuery = new QueryWrapper<>();
        deviceQuery.eq("id", entity.getDeviceId());
        Device device = deviceMapper.selectOne(deviceQuery);
        if (device == null) {
            throw new RuntimeException("Device ID " + entity.getDeviceId() + " does not exist in t_device table");
        }

        System.out.println("Before saving alarm: " + entity);
        this.save(entity);
        System.out.println("After saving alarm, id: " + entity.getId());
        return entity;
    }

    @Override
    public Alarm convertToEntity(AlarmDTO dto) {
        Alarm alarm = new Alarm();
        alarm.setDeviceId(dto.getDeviceId());
        alarm.setType(dto.getType());
        alarm.setLevel(dto.getLevel());
        alarm.setMessage(dto.getMessage());
        alarm.setStatus(0);
        return alarm;
    }

    @Override
    public IPage<AlarmListDTO> listAlarms(int page, int limit, Integer status, Integer level) {
        QueryWrapper<Alarm> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (level != null) {
            queryWrapper.eq("level", level);
        }

        IPage<Alarm> alarmPage = new Page<>(page, limit);
        alarmPage = this.page(alarmPage, queryWrapper);

        IPage<AlarmListDTO> resultPage = alarmPage.convert(this::convertToListDTO);
        return resultPage;
    }

    @Override
    public List<AlarmListDTO> getAlarmsByDeviceId(String deviceId) {
        LambdaQueryWrapper<Alarm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Alarm::getDeviceId, deviceId)
                    .orderByDesc(Alarm::getCreateTime);
        
        List<Alarm> alarms = this.list(queryWrapper);
        return alarms.stream()
                     .map(this::convertToListDTO)
                     .collect(Collectors.toList());
    }

    @Override
    public void updateAlarm(Long id, AlarmDTO dto) {
        Alarm alarm = this.getById(id);
        if (alarm == null) {
            throw new RuntimeException("Alarm not found with id: " + id);
        }
        if (dto.getStatus() != null) {
            alarm.setStatus(dto.getStatus());
        }
        this.updateById(alarm);
    }

    @Override
    public void deleteAlarm(Long id) {
        Alarm alarm = this.getById(id);
        if (alarm == null) {
            throw new RuntimeException("Alarm not found with id: " + id);
        }
        this.removeById(id);
    }

    private AlarmListDTO convertToListDTO(Alarm alarm) {
        AlarmListDTO dto = new AlarmListDTO();
        dto.setId(alarm.getId());
        dto.setDeviceId(alarm.getDeviceId());
        dto.setType(alarm.getType());
        dto.setLevel(alarm.getLevel());
        dto.setMessage(alarm.getMessage());
        dto.setStatus(alarm.getStatus());
        dto.setCreateTime(alarm.getCreateTime());
        return dto;
    }
}