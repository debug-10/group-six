package top.wxy.service;
import top.wxy.entity.Device;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.mybatis.service.BaseService;
import top.wxy.query.DeviceQuery;
import top.wxy.vo.DeviceVO;

/**
 * @author 笼中雀
 */
public interface DeviceService extends BaseService<Device> {
    PageResult<DeviceVO> page(DeviceQuery query);
    /**
     * 发送命令
     *
     * @param deviceId 设备id
     * @param command 命令
     */
    void sendCommand(String deviceId, String command);
}
