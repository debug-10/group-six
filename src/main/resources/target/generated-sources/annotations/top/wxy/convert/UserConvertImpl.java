package top.wxy.convert;

import javax.annotation.processing.Generated;
import top.wxy.dto.MobileLoginDTO;
import top.wxy.dto.UserDTO;
import top.wxy.entity.UserEntity;
import top.wxy.framework.security.user.UserDetail;
import top.wxy.vo.UserVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:38:21+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public UserVO convert(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId( entity.getId() );
        userVO.setUsername( entity.getUsername() );
        userVO.setNickname( entity.getNickname() );
        userVO.setPhone( entity.getPhone() );
        userVO.setAvatarUrl( entity.getAvatarUrl() );
        userVO.setTenantId( entity.getTenantId() );
        userVO.setRole( entity.getRole() );

        return userVO;
    }

    @Override
    public UserEntity convert(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername( dto.getUsername() );
        userEntity.setPassword( dto.getPassword() );
        userEntity.setNickname( dto.getNickname() );
        userEntity.setAvatarUrl( dto.getAvatarUrl() );

        return userEntity;
    }

    @Override
    public UserVO convert(UserDetail userDetail) {
        if ( userDetail == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId( userDetail.getId() );
        userVO.setUsername( userDetail.getUsername() );
        userVO.setNickname( userDetail.getNickname() );
        userVO.setPhone( userDetail.getPhone() );
        userVO.setAvatarUrl( userDetail.getAvatarUrl() );
        userVO.setTenantId( userDetail.getTenantId() );
        userVO.setRole( userDetail.getRole() );

        return userVO;
    }

    @Override
    public UserEntity convert(MobileLoginDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        return userEntity;
    }

    @Override
    public UserDetail convertDetail(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserDetail userDetail = new UserDetail();

        userDetail.setId( entity.getId() );
        userDetail.setUsername( entity.getUsername() );
        userDetail.setPassword( entity.getPassword() );
        userDetail.setNickname( entity.getNickname() );
        userDetail.setAvatarUrl( entity.getAvatarUrl() );
        userDetail.setPhone( entity.getPhone() );
        userDetail.setStatus( entity.getStatus() );
        userDetail.setCreateTime( entity.getCreateTime() );
        userDetail.setTenantId( entity.getTenantId() );
        userDetail.setRole( entity.getRole() );

        return userDetail;
    }
}
