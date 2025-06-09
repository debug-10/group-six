package top.wxy.convert;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import top.wxy.model.entity.SysManager;
import top.wxy.model.vo.SysManagerVO;
import top.wxy.security.user.ManagerDetail;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:38:02+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class SysManagerConvertImpl implements SysManagerConvert {

    @Override
    public SysManager convert(SysManagerVO vo) {
        if ( vo == null ) {
            return null;
        }

        SysManager sysManager = new SysManager();

        sysManager.setId( vo.getId() );
        sysManager.setUsername( vo.getUsername() );
        sysManager.setPassword( vo.getPassword() );
        sysManager.setPhone( vo.getPhone() );
        sysManager.setNickname( vo.getNickname() );
        sysManager.setAvatarUrl( vo.getAvatarUrl() );
        sysManager.setTenantId( vo.getTenantId() );
        sysManager.setRole( vo.getRole() );
        sysManager.setStatus( vo.getStatus() );
        sysManager.setCreateTime( vo.getCreateTime() );

        return sysManager;
    }

    @Override
    public ManagerDetail convertDetail(SysManager entity) {
        if ( entity == null ) {
            return null;
        }

        ManagerDetail managerDetail = new ManagerDetail();

        managerDetail.setId( entity.getId() );
        managerDetail.setUsername( entity.getUsername() );
        managerDetail.setPassword( entity.getPassword() );
        managerDetail.setPhone( entity.getPhone() );
        managerDetail.setNickname( entity.getNickname() );
        managerDetail.setAvatarUrl( entity.getAvatarUrl() );
        managerDetail.setTenantId( entity.getTenantId() );
        managerDetail.setRole( entity.getRole() );
        managerDetail.setStatus( entity.getStatus() );

        return managerDetail;
    }

    @Override
    public List<SysManagerVO> convertList(List<SysManager> list) {
        if ( list == null ) {
            return null;
        }

        List<SysManagerVO> list1 = new ArrayList<SysManagerVO>( list.size() );
        for ( SysManager sysManager : list ) {
            list1.add( sysManagerToSysManagerVO( sysManager ) );
        }

        return list1;
    }

    protected SysManagerVO sysManagerToSysManagerVO(SysManager sysManager) {
        if ( sysManager == null ) {
            return null;
        }

        SysManagerVO sysManagerVO = new SysManagerVO();

        sysManagerVO.setId( sysManager.getId() );
        sysManagerVO.setUsername( sysManager.getUsername() );
        sysManagerVO.setPassword( sysManager.getPassword() );
        sysManagerVO.setPhone( sysManager.getPhone() );
        sysManagerVO.setNickname( sysManager.getNickname() );
        sysManagerVO.setAvatarUrl( sysManager.getAvatarUrl() );
        sysManagerVO.setTenantId( sysManager.getTenantId() );
        sysManagerVO.setRole( sysManager.getRole() );
        sysManagerVO.setStatus( sysManager.getStatus() );
        sysManagerVO.setCreateTime( sysManager.getCreateTime() );

        return sysManagerVO;
    }
}
