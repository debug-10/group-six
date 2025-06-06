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
                // 删除 deleteFlag 条件，因为新表结构中没有这个字段
                .select(SysManager::getId,
                        SysManager::getUsername,
                        SysManager::getPassword,
                        SysManager::getAvatarUrl,
                        SysManager::getPhone,
                        SysManager::getNickname,
                        SysManager::getTenantId,
                        SysManager::getRole,
                        SysManager::getStatus));
    }

    List<SysManagerVO> getManagerPage(Page<SysManagerVO> page, @Param("query") SysManagerQuery query);
}
