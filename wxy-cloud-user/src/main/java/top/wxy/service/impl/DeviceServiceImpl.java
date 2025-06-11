package top.wxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxy.convert.DeviceConvert;
import top.wxy.dao.*;
import top.wxy.dto.DeviceAddDTO;
import top.wxy.entity.DeviceEntity;
import top.wxy.entity.UserDeviceEntity;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.security.user.SecurityUser;
import top.wxy.service.DeviceService;
import top.wxy.vo.DeviceVO;
import top.wxy.vo.UserDeviceVO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 设备服务实现类
 * @author 笼中雀
 */
@Slf4j
@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    
    private final DeviceDao deviceDao;
    private final UserDeviceDao userDeviceDao;
    private final UserDao userDao;
    private final TenantDAO tenantDao;
    private final PackageDAO packageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDeviceToUser(DeviceAddDTO dto) {
        Long userId = SecurityUser.getUser().getId();

        // 根据单个device_mac查询设备信息
        DeviceEntity device = deviceDao.selectOne(new QueryWrapper<DeviceEntity>()
                .eq("device_mac", dto.getDeviceMac()));

        if (device == null) {
            throw new ServerException("未找到对应的设备信息");
        }

        // === 新增：权限校验逻辑 ===
        // 1. 通过用户ID查询租户ID，打印拿到的用户ID和查到的租户ID
        Long tenantId = userDao.getTenantIdByUserId(userId);
        log.info("用户ID: {}, 查询到的租户ID: {}", userId, tenantId);
        if (tenantId == null) {
            throw new ServerException("用户未关联租户信息");
        }

        // 2. 通过租户ID查询套餐类型，打印拿到的租户ID和查到的套餐类型
        Integer packageType = tenantDao.getPackageTypeByTenantId(tenantId.intValue());
        log.info("租户ID: {}, 查询到的套餐类型: {}", tenantId, packageType);
        if (packageType == null) {
            throw new ServerException("租户未关联套餐信息");
        }

        // 3. 通过套餐类型查询权限列表，打印拿到的套餐类型和查到的权限数组
        String permissionsStr = packageDao.getPermissionsByPackageType(packageType);
        List<Integer> permissions = parsePermissions(permissionsStr);
        log.info("套餐类型: {}, 查询到的权限数组: {}", packageType, permissions);

        // 4. 校验设备类型是否在权限范围内
        if (!permissions.contains(device.getType())) {
            throw new ServerException("您没有绑定此类设备的权限！");
        }
        // === 权限校验结束 ===
        UserDeviceEntity existingUserDevice = userDeviceDao.getByUserIdAndType(userId, device.getType());
        if (existingUserDevice != null) {
            throw new ServerException("您已经绑定了该类型的设备场景");
        }

        // 创建用户设备绑定记录
        UserDeviceEntity userDevice = new UserDeviceEntity();
        userDevice.setUserId(userId);
        userDevice.setType(device.getType());
        userDevice.setBindTime(LocalDateTime.now());

        userDeviceDao.insert(userDevice);
    }

    @Override
    public List<UserDeviceVO> getUserDevices() {
        Long userId = SecurityUser.getUser().getId();

        List<UserDeviceEntity> userDevices = userDeviceDao.getByUserId(userId);
        List<UserDeviceVO> result = DeviceConvert.INSTANCE.convertUserDeviceList(userDevices);
        
        for (UserDeviceVO userDeviceVO : result) {
            List<DeviceEntity> devices = deviceDao.getByType(userDeviceVO.getType());
            List<DeviceVO> deviceVOs = DeviceConvert.INSTANCE.convertList(devices);
            userDeviceVO.setDevices(deviceVOs);
        }
        
        return result;
    }

    //将JSON字符串解析为数值列
    private List<Integer> parsePermissions(String permissionsStr) {
        if (permissionsStr == null || permissionsStr.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(permissionsStr, new TypeReference<List<Integer>>() {});
        } catch (JsonProcessingException e) {
            log.error("解析权限字符串失败: {}", permissionsStr, e);
            return Collections.emptyList();
        }
    }
}