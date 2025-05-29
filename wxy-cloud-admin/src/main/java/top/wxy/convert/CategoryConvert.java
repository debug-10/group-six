package top.wxy.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.model.entity.Category;
import top.wxy.model.vo.CategoryInfoVO;

/**
 * @author 笼中雀
 */
@Mapper
public interface CategoryConvert {
    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    Category convert(CategoryInfoVO vo);
}