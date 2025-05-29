package top.wxy.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wxy.convert.CategoryConvert;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.mapper.CategoryMapper;
import top.wxy.model.entity.Category;
import top.wxy.model.query.CategoryQuery;
import top.wxy.model.vo.CategoryInfoVO;
import top.wxy.service.CategoryService;


import java.util.List;

/**
 * @author 笼中雀
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public PageResult<CategoryInfoVO> getPage(CategoryQuery query) {
        Page<CategoryInfoVO> page = new Page<>(query.getPage(), query.getLimit());
        List<CategoryInfoVO> result = baseMapper.getPage(page, query);
        return new PageResult<>(result, page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CategoryInfoVO vo) {
        Category category = CategoryConvert.INSTANCE.convert(vo);
        // 保存角色
        baseMapper.insert(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<Integer> ids) {
        // 删除角色
        removeByIds(ids);
    }

    @Override
    public void update(CategoryInfoVO vo) {
        Category category = CategoryConvert.INSTANCE.convert(vo);
        // 更新角色
        baseMapper.updateById(category);
    }
}
