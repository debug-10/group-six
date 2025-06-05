package top.wxy.service;



import com.baomidou.mybatisplus.extension.service.IService;
import top.wxy.model.dto.DeviceStatusDTO;
import top.wxy.model.dto.DeviceUpdateDTO;
import top.wxy.model.entity.Device;
import top.wxy.model.query.DeviceQuery;
import top.wxy.model.vo.DeviceVO;
import jakarta.servlet.http.HttpServletResponse;
import top.wxy.framework.common.utils.PageResult;

public interface DeviceService extends IService<Device> {
    PageResult<DeviceVO> page(DeviceQuery query);

    void update(DeviceUpdateDTO dto);

    void export(DeviceQuery query, HttpServletResponse response);

    void updateStatus(DeviceStatusDTO dto);

    // 新增自定义方法，返回 Device 对象
    Device saveDevice(Device entity);
}