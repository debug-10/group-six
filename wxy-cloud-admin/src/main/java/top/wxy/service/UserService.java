package top.wxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.wxy.model.dto.UserDTO;
import top.wxy.model.entity.User;

public interface UserService extends IService<User> {
    UserDTO getUserById(Long id);

    void updateUser(Long id, UserDTO userDTO);

    boolean toggleUserStatus(Long id);

    IPage<UserDTO> listUsers(int page, int limit, Integer status, Integer role);
}