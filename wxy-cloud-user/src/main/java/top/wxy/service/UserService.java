package top.wxy.service;

import top.wxy.dto.UserDTO;
import top.wxy.entity.UserEntity;
import top.wxy.framework.mybatis.service.BaseService;
import top.wxy.vo.UserVO;


/**
 * @author 笼中雀
 */
public interface UserService extends BaseService<UserEntity> {
    void save(UserDTO vo);
    void update(UserDTO dto);
    UserVO getByMobile(String mobile);
    UserVO getById(Long id);
}
