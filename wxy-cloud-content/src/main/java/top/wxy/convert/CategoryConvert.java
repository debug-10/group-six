package top.wxy.convert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.wxy.entity.Category;
import top.wxy.vo.CategoryVO;
import java.util.List;


/**
 * @author 笼中雀
 */
@Mapper
public interface CategoryConvert {
    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);
    CategoryVO convert(Category entity);
    List<CategoryVO> convertList(List<Category> list);
}
