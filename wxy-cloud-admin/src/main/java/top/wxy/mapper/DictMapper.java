package top.wxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.wxy.model.entity.Dict;
import top.wxy.model.query.DictQuery;

import java.util.List;

public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 字典分页列表
     *
     * @param page
     * @param query
     * @return
     */
    List<Dict> getPage(Page<Dict> page, @Param("query") DictQuery query);
}
