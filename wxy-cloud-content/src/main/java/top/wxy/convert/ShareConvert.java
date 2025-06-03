package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.entity.Share;
import top.wxy.vo.ShareVO;

import java.util.List;


/**
 * @author 笼中雀
 */
@Mapper
public interface ShareConvert {
    ShareConvert INSTANCE = Mappers.getMapper(ShareConvert.class);

    ShareVO convert(Share entity);

    List<ShareVO> convertList(List<Share> list);
}