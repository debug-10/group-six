package top.wxy.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxy.common.model.BaseServiceImpl;
import top.wxy.convert.SysManagerConvert;
import top.wxy.enums.SuperAdminEnum;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.mapper.SysManagerMapper;
import top.wxy.model.entity.SysManager;
import top.wxy.model.query.ChangePasswordQuery;
import top.wxy.model.query.SysManagerQuery;
import top.wxy.model.vo.SysManagerVO;
import top.wxy.security.user.ManagerDetail;
import top.wxy.service.SysManagerService;

import java.util.List;

/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class SysManagerServiceImpl extends BaseServiceImpl<SysManagerMapper, SysManager> implements SysManagerService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResult<SysManagerVO> page(SysManagerQuery query) {
        Page<SysManagerVO> page = new Page<>(query.getPage(), query.getLimit());
        List<SysManagerVO> list = baseMapper.getManagerPage(page, query);
        return new PageResult<>(list, page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysManagerVO vo) {
        SysManager entity = SysManagerConvert.INSTANCE.convert(vo);
        // 删除旧的 setSuperAdmin 调用，因为新表结构中没有这个字段

        // 判断用户名是否存在
        SysManager manager = baseMapper.getByUsername(entity.getUsername());
        if (manager != null) {
            throw new ServerException("用户名已经存在");
        }

        // 确保密码不为空
        if (entity.getPassword() == null || entity.getPassword().trim().isEmpty()) {
            throw new ServerException("密码不能为空");
        }

        // 使用 PasswordEncoder 加密密码，它会自动添加 {bcrypt} 前缀
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);

        // 保存用户
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysManagerVO vo) {
        SysManager entity = SysManagerConvert.INSTANCE.convert(vo);
        // 判断用户名是否存在
        SysManager manager = baseMapper.getByUsername(entity.getUsername());
        if (manager != null && !manager.getId().equals(entity.getId())) {
            throw new ServerException("用户名已经存在");
        }

        // 如果密码不为空，则进行加密
        if (entity.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(entity.getPassword());
            entity.setPassword(encodedPassword);
        }

        // 更新用户
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 删除管理员
        removeByIds(idList);
    }

    @Override
    public SysManagerVO getManagerInfo(ManagerDetail manage) {
        SysManagerVO sysManagerVO = new SysManagerVO();
        System.out.println(">>>>>>getInfo" + manage.getId());
        SysManager sysManager = baseMapper.selectById(manage.getId());
        if (sysManager == null) {
            throw new ServerException("管理员不存在");
        }
        sysManagerVO.setId(sysManager.getId());
        sysManagerVO.setAvatarUrl(sysManager.getAvatarUrl());
        sysManagerVO.setUsername(sysManager.getUsername());
        sysManagerVO.setPhone(sysManager.getPhone());
        sysManagerVO.setNickname(sysManager.getNickname());
        sysManagerVO.setTenantId(sysManager.getTenantId());
        sysManagerVO.setRole(sysManager.getRole());
        sysManagerVO.setStatus(sysManager.getStatus());
        sysManagerVO.setCreateTime(sysManager.getCreateTime());
        return sysManagerVO;
    }

    @Override
    public void changePassword(ChangePasswordQuery query) {
        SysManager sysManager = baseMapper.selectById(query.getId()); // 假设ChangePasswordQuery也需要更新字段名
        if (sysManager == null) {
            throw new ServerException("管理员不存在");
        }
        // 使用 PasswordEncoder 加密密码，它会自动添加 {bcrypt} 前缀
        String encodedPassword = passwordEncoder.encode(query.getPassword());
        sysManager.setPassword(encodedPassword);
        updateById(sysManager);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long managerId, String newPassword) {
        SysManager manager = baseMapper.selectById(managerId);
        if (manager == null) {
            throw new ServerException("用户不存在");
        }
        
        // 使用 PasswordEncoder 加密密码，它会自动添加 {bcrypt} 前缀
        String encodedPassword = passwordEncoder.encode(newPassword);
        manager.setPassword(encodedPassword);
        
        // 更新密码
        baseMapper.updateById(manager);
    }
}
