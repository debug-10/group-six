package top.wxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.model.entity.Category;
import top.wxy.model.query.CategoryQuery;
import top.wxy.model.vo.CategoryInfoVO;


import java.util.List;

/**
 * @author 笼中雀
 */
public interface CategoryService extends IService<Category> {
    PageResult<CategoryInfoVO> getPage(CategoryQuery query);

    void add(CategoryInfoVO vo);

    void remove(List<Integer> ids);

    void update(CategoryInfoVO vo);

}
