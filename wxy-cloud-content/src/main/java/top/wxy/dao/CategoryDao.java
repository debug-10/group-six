package top.wxy.dao;

import org.apache.ibatis.annotations.Mapper;
import top.wxy.entity.Category;
import top.wxy.framework.mybatis.dao.BaseDao;

/**
 * @author 笼中雀
 */
@Mapper
public interface CategoryDao extends BaseDao<Category> {
}