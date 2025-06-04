package top.wxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.model.entity.SysManager;
import top.wxy.model.query.ChangePasswordQuery;
import top.wxy.model.query.SysManagerQuery;
import top.wxy.model.vo.SysManagerVO;
import top.wxy.security.user.ManagerDetail;

import java.util.List;

public interface SysManagerService extends IService<SysManager> {

    /**
     * 管理员列表
     *
     * @param query
     * @return
     */
    PageResult<SysManagerVO> page(SysManagerQuery query);

    /**
     * 新增管理员
     *
     * @param vo
     */
    void save(SysManagerVO vo);

    /**
     * 修改管理员信息
     *
     * @param vo
     */
    void update(SysManagerVO vo);

    /**
     * 删除管理员信息
     *
     * @param idList
     */
    void delete(List<Long> idList);

    /**
     * 修改密码
     *
     * @param query
     */

    void changePassword(ChangePasswordQuery query);

    /**
     * 获取管理员信息
     *
     * @param manage
     * @return
     */

    SysManagerVO getManagerInfo(ManagerDetail manage);
}
