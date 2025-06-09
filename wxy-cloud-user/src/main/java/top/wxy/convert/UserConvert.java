package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.wxy.dto.MobileLoginDTO;
import top.wxy.dto.UserDTO;
import top.wxy.entity.UserEntity;
import top.wxy.framework.security.user.UserDetail;
import top.wxy.vo.UserVO;

/**
 * @author 笼中雀
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    UserVO convert(UserEntity entity);
    UserEntity convert(UserDTO dto);
    UserVO convert(UserDetail userDetail);
    UserEntity convert(MobileLoginDTO dto);
    UserDetail convertDetail(UserEntity entity);
}