package top.wxy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxy.convert.UserConvert;
import top.wxy.dao.UserDao;
import top.wxy.dto.UserDTO;
import top.wxy.entity.UserEntity;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.mybatis.service.impl.BaseServiceImpl;
import top.wxy.framework.security.cache.TokenStoreCache;
import top.wxy.framework.security.user.SecurityUser;
import top.wxy.framework.security.utils.TokenUtils;
import top.wxy.service.UserService;
import top.wxy.vo.UserVO;

/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserDao, UserEntity> implements UserService {
    private final TokenStoreCache tokenStoreCache;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserDTO dto) {
        UserEntity entity = UserConvert.INSTANCE.convert(dto);
        // 设置默认角色为3（普通用户）
        if (entity.getRole() == null) {
            entity.setRole(3);
        }
        // 判断⽤户名是否存在
        UserEntity user = baseMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("⽤户名已经存在" );
        }
        // 判断⼿机号是否存在
        user = baseMapper.getByPhone(entity.getPhone());
        if (user != null) {
            throw new ServerException("⼿机号已经存在" );
        }
        // 保存⽤户
        baseMapper.insert(entity);
    }

    @Override
public void update(UserDTO dto) {
    // 先获取当前用户实体
    UserEntity currentEntity = baseMapper.getById(SecurityUser.getUser().getId());
    if (currentEntity == null) {
        throw new ServerException("用户不存在");
    }
    
    // 只更新非空字段
    UserEntity entity = UserConvert.INSTANCE.convert(dto);
    entity.setId(SecurityUser.getUser().getId());
    
    // 如果头像URL为空，保留原来的值
    if (entity.getAvatarUrl() == null) {
        entity.setAvatarUrl(currentEntity.getAvatarUrl());
    }
    
    if (dto.getPassword() != null) {
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
    }
    
    // 更新用户
    updateById(entity);
    // 删除用户缓存
    tokenStoreCache.deleteUser(TokenUtils.getAccessToken());
}

    @Override
    public UserVO getByPhone(String mobile) {
        UserEntity user = baseMapper.getByPhone(mobile);
        return UserConvert.INSTANCE.convert(user);
    }

    @Override
    public UserVO getById(Long id) {
        UserEntity user = baseMapper.getById(id);
        return UserConvert.INSTANCE.convert(user);
    }
}
