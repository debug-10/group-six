package top.wxy.service.impl;



import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxy.common.model.BaseServiceImpl;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.convert.DeviceConvert;
import top.wxy.model.dto.DeviceStatusDTO;
import top.wxy.model.dto.DeviceUpdateDTO;
import top.wxy.model.entity.Device;
import top.wxy.mapper.DeviceMapper;
import top.wxy.model.query.DeviceQuery;
import top.wxy.service.DeviceService;
import top.wxy.model.vo.DeviceVO;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;@Slf4j
@Service
@AllArgsConstructor
public class DeviceServiceImpl extends BaseServiceImpl<DeviceMapper, Device> implements DeviceService {

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
    public void updateStatus(DeviceStatusDTO dto) {
        Device device = baseMapper.selectById(dto.getId());
        if (device == null) {
            throw new ServerException("设备不存在");
        }
        device.setStatus(dto.getStatus());
        baseMapper.updateById(device);
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