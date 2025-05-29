package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.model.entity.SysMenu;
import top.wxy.model.vo.SysMenuVO;

import java.util.List;

@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenu convert(SysMenuVO vo);

    SysMenuVO convert(SysMenu entity);

    List<SysMenuVO> convertList(List<SysMenu> list);

}
