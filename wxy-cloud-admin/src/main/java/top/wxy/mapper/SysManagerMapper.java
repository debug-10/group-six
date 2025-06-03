package top.wxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.wxy.model.entity.SysManager;
import top.wxy.model.query.SysManagerQuery;
import top.wxy.model.vo.SysManagerVO;

import java.util.List;

public interface SysManagerMapper extends BaseMapper<SysManager> {

    default SysManager getByUsername(String username) {
        return this.selectOne(new LambdaQueryWrapper<SysManager>()
                .eq(SysManager::getUsername, username)
                .eq(SysManager::getDeleteFlag, 0)  // 只查询未删除的用户
                .select(SysManager::getPkId, 
                        SysManager::getUsername, 
                        SysManager::getPassword,  // 确保选择密码字段
                        SysManager::getAvatar,
                        SysManager::getSuperAdmin,
                        SysManager::getStatus));
    }

    List<SysManagerVO> getManagerPage(Page<SysManagerVO> page, @Param("query") SysManagerQuery query);
}
