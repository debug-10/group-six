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
        // 判断⽤户名是否存在
        UserEntity user = baseMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("⽤户名已经存在" );
        }
        // 判断⼿机号是否存在
        user = baseMapper.getByMobile(entity.getMobile());
        if (user != null) {
            throw new ServerException("⼿机号已经存在" );
        }
// 保存⽤户
        baseMapper.insert(entity);
    }

    @Override
    public void update(UserDTO dto) {
        UserEntity entity = UserConvert.INSTANCE.convert(dto);
        entity.setId(SecurityUser.getUser().getId());
        if (dto.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        // 更新⽤户
        updateById(entity);
        // 删除⽤户缓存
        tokenStoreCache.deleteUser(TokenUtils.getAccessToken());
    }

    @Override
    public UserVO getByMobile(String mobile) {
        UserEntity user = baseMapper.getByMobile(mobile);
        return UserConvert.INSTANCE.convert(user);
    }

    @Override
    public UserVO getById(Long id) {
        UserEntity user = baseMapper.getById(id);
        return UserConvert.INSTANCE.convert(user);
    }
}
