package top.wxy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.wxy.model.entity.User;
import top.wxy.model.query.UserQuery;
import top.wxy.model.vo.UserInfoVO;

import java.util.List;

/**
 * @author mqxu
 * @date 2024/11/18
 * @description UserMapper
 **/
public interface UserMapper extends BaseMapper<User> {

    default User getByPhone(String phone) {
        return this.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
    }

    List<UserInfoVO> getUserPage(Page<UserInfoVO> page, @Param("query") UserQuery query);
}