package top.wxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wxy.model.entity.News;
import top.wxy.model.vo.NewsVO;
import top.wxy.model.dto.NewsCreateDTO;
import top.wxy.model.dto.NewsUpdateDTO;

import java.util.List;

public interface NewsService extends IService<News> {

    /**
     * 创建公告
     * @param dto 创建请求对象
     * @return 公告ID
     */
    Long createNews(NewsCreateDTO dto);

    /**
     * 更新公告
     * @param dto 更新请求对象
     */
    void updateNews(NewsUpdateDTO dto);

    /**
     * 删除公告
     * @param id 公告ID
     */
    void deleteNews(Long id);

    /**
     * 查询单个公告
     * @param id 公告ID
     * @return 公告视图对象
     */
    NewsVO getNewsById(Long id);

    /**
     * 查询公告列表
     * @param title 标题关键词（可选）
     * @param visibleRange 可见范围（可选）
     * @param tenantId 租户ID（可选）
     * @return 公告视图列表
     */
    List<NewsVO> listNews(String title, Integer visibleRange, Long tenantId);
}
