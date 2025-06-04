package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wxy.mapper.UserMapper;
import top.wxy.model.dto.UserDTO;
import top.wxy.model.entity.User;
import top.wxy.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        BeanUtils.copyProperties(userDTO, user);
        user.setId(id);
        userMapper.updateById(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public IPage<UserDTO> listUsers(int page, int limit, Integer status, Integer role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (role != null) {
            queryWrapper.eq("role", role);
        }

        IPage<User> userPage = new Page<>(page, limit);
        userPage = userMapper.selectPage(userPage, queryWrapper);

        IPage<UserDTO> userDTOPage = userPage.convert(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTO.setPassword(null); // 避免返回敏感字段
            return userDTO;
        });

        return userDTOPage;
    }
}