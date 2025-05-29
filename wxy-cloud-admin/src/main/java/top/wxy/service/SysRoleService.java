package top.wxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.model.entity.SysRole;
import top.wxy.model.query.SysRoleQuery;
import top.wxy.model.vo.SysRoleVO;

import java.util.List;

/**
 * @author 笼中雀
 */
public interface SysRoleService extends IService<SysRole> {
    PageResult<SysRoleVO> page(SysRoleQuery query);

    List<SysRoleVO> getList(SysRoleQuery query);

    void save(SysRoleVO vo);

    void update(SysRoleVO vo);

    void delete(List<Integer> idList);
}
