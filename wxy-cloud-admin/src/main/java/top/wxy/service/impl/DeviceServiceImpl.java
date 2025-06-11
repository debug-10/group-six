package top.wxy.service.impl;



import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxy.common.model.BaseServiceImpl;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.convert.DeviceConvert;
import top.wxy.model.dto.AlarmDTO;
import top.wxy.model.dto.DeviceStatusDTO;
import top.wxy.model.dto.DeviceUpdateDTO;
import top.wxy.model.entity.Alarm;
import top.wxy.model.entity.Device;
import top.wxy.mapper.DeviceMapper;
import top.wxy.model.query.DeviceQuery;
import top.wxy.service.AlarmService;
import top.wxy.service.DeviceService;
import top.wxy.model.vo.DeviceVO;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 笼中雀
 */
@Slf4j
@Service
@AllArgsConstructor
public class DeviceServiceImpl extends BaseServiceImpl<DeviceMapper, Device> implements DeviceService {

    private AlarmService alarmService;
    
    @Override
    public PageResult<DeviceVO> page(DeviceQuery query) {
        Page<DeviceVO> page = new Page<>(query.getPage(), query.getLimit());
        List<DeviceVO> list = baseMapper.getDevicePage(page, query);
        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public void update(DeviceUpdateDTO dto) {
        Device device = DeviceConvert.INSTANCE.toEntity(dto);
        Device existing = baseMapper.getByDeviceMac(device.getDeviceMac());
        if (existing != null && !existing.getId().equals(device.getId())) {
            throw new ServerException("设备MAC地址已存在");
        }
        baseMapper.updateById(device);
        
        // 如果状态发生变化，创建告警
        if (dto.getStatus() != null) {
            try {
                DeviceStatusDTO statusDTO = new DeviceStatusDTO();
                statusDTO.setId(dto.getId());
                statusDTO.setStatus(dto.getStatus());
                updateStatus(statusDTO);
            } catch (Exception e) {
                log.error("创建设备状态告警失败", e);
            }
        }
    }

    @Override
    public void export(DeviceQuery query, HttpServletResponse response) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getTenantId().toString()), Device::getTenantId, query.getTenantId())
                .eq(query.getStatus() != null, Device::getStatus, query.getStatus())
                .eq(query.getType() != null, Device::getType, query.getType());
        List<Device> list = baseMapper.selectList(wrapper);
        List<DeviceVO> excelData = DeviceConvert.INSTANCE.toVOList(list);
        try {
            String fileName = URLEncoder
                    .encode("设备信息" + System.currentTimeMillis() + ".xls", StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            EasyExcelFactory.write(response.getOutputStream(), DeviceVO.class)
                    .charset(StandardCharsets.UTF_8)
                    .excelType(ExcelTypeEnum.XLS)
                    .sheet()
                    .doWrite(excelData);
        } catch (Exception e) {
            log.error("导出设备信息异常", e);
            throw new ServerException("导出设备信息异常");
        }
    }

    @Override
    @Transactional
    public void updateStatus(DeviceStatusDTO dto) {
        log.info("开始更新设备状态: {}", dto);
        Device device = baseMapper.selectById(dto.getId());
        if (device == null) {
            log.error("设备不存在: {}", dto.getId());
            throw new ServerException("设备不存在");
        }
        
        // 更新设备状态
        device.setStatus(dto.getStatus());
        baseMapper.updateById(device);
        log.info("设备状态已更新: 设备ID={}, 新状态={}", device.getId(), dto.getStatus());
        
        // 直接创建告警信息，不检查状态是否变化
        try {
            createAlarmForDevice(device);
        } catch (Exception e) {
            log.error("创建设备状态告警失败", e);
        }
    }
    
    /**
     * 为设备创建告警信息
     * @param device 设备信息
     */
    private void createAlarmForDevice(Device device) {
        log.info("开始创建设备状态告警: 设备ID={}, 状态={}", device.getId(), device.getStatus());
        
        // 创建告警DTO
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setDeviceId(String.valueOf(device.getId()));
        alarmDTO.setType("1"); // 设置告警类型
        
        // 根据设备状态设置告警级别和消息
        if (device.getStatus() == 0) { 
            alarmDTO.setLevel(2); // 中级告警
            alarmDTO.setMessage("设备 " + device.getName() + " (" + device.getDeviceMac() + ") 已掉线");
        } else { 
            alarmDTO.setLevel(1); // 低级告警
            alarmDTO.setMessage("设备 " + device.getName() + " (" + device.getDeviceMac() + ") 已上线");
        }
        
        // 设置告警状态为未处理
        alarmDTO.setStatus(0);
        
        log.info("准备保存告警信息: {}", alarmDTO);
        try {
            // 转换为实体并保存
            Alarm alarm = alarmService.convertToEntity(alarmDTO);
            alarmService.saveAlarm(alarm);
            log.info("已成功创建设备状态告警: {}", alarm.getMessage());
        } catch (Exception e) {
            log.error("创建设备状态告警失败: {}", e.getMessage(), e);
            throw e; // 重新抛出异常，让调用方知道出错了
        }
    }
    @Transactional
    @Override
    public Device saveDevice(Device entity) {
        entity.setDeleted(0);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());

        System.out.println("Before saving device: " + entity);
        boolean saved = this.save(entity);
        System.out.println("Save result: " + saved);
        System.out.println("After saving device, id: " + entity.getId());
        return entity;
    }

}