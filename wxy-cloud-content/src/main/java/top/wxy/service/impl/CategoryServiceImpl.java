package top.wxy.service.impl;

import org.springframework.stereotype.Service;
import top.wxy.dao.CategoryDao;
import top.wxy.entity.Category;
import top.wxy.framework.mybatis.service.impl.BaseServiceImpl;
import top.wxy.service.CategoryService;

/**
 * @author 笼中雀
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryDao, Category> implements CategoryService {
}