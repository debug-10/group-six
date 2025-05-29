package top.wxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.wxy.model.entity.Category;
import top.wxy.model.query.CategoryQuery;
import top.wxy.model.vo.CategoryInfoVO;

import java.util.List;

/**
 * @author 笼中雀
 */
public interface CategoryMapper extends BaseMapper<Category> {
    List<CategoryInfoVO> getPage(Page<CategoryInfoVO> page, @Param("query") CategoryQuery query);
}
