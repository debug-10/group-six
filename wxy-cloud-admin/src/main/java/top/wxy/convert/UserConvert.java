package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.model.dto.UserEditDTO;
import top.wxy.model.entity.User;
import top.wxy.model.vo.UserInfoVO;

import java.util.List;

/**
 * @author 笼中雀
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    List<UserInfoVO> convert(List<User> list);

    User convert(UserEditDTO dto);
}