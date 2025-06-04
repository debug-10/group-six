package top.wxy.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.wxy.model.entity.News;

public interface NewsMapper extends BaseMapper<News> {
    // 基础CRUD已由BaseMapper提供，如需自定义SQL可在此添加方法
}
