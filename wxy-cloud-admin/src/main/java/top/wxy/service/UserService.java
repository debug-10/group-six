package top.wxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.model.dto.UserEditDTO;
import top.wxy.model.entity.User;
import top.wxy.model.query.UserQuery;
import top.wxy.model.vo.UserInfoVO;

/**
 * @author mqxu
 * @date 2024/11/18
 * @description UserService
 **/
public interface UserService  extends IService<User> {

    PageResult<UserInfoVO> page(UserQuery query);

    void update(UserEditDTO dto);

    void export(UserQuery query, HttpServletResponse response);

    void enabled(Integer userId);
}