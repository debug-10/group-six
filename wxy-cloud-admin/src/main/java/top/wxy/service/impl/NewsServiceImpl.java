package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.wxy.mapper.NewsMapper;
import top.wxy.model.entity.News;
import top.wxy.model.vo.NewsVO;
import top.wxy.model.dto.NewsCreateDTO;
import top.wxy.model.dto.NewsUpdateDTO;
import top.wxy.service.NewsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public Long createNews(NewsCreateDTO dto) {
        News news = new News();
        BeanUtils.copyProperties(dto, news);
        this.save(news);
        return news.getId();
    }

    @Override
    public void updateNews(NewsUpdateDTO dto) {
        News news = this.getById(dto.getId());
        if (news == null) {
            throw new RuntimeException("公告不存在");
        }
        BeanUtils.copyProperties(dto, news, "id");
        this.updateById(news);
    }

    @Override
    public void deleteNews(Long id) {
        this.removeById(id);
    }

    @Override
    public NewsVO getNewsById(Long id) {
        News news = this.getById(id);
        if (news == null) {
            return null;
        }
        NewsVO vo = new NewsVO();
        BeanUtils.copyProperties(news, vo);
        // 补充租户名称（实际需关联t_tenant表查询）
        vo.setTenantName("模拟租户名称");
        return vo;
    }

    @Override
    public List<NewsVO> listNews(String title, Integer visibleRange, Long tenantId) {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(title != null, News::getTitle, title)
                .eq(visibleRange != null, News::getVisibleRange, visibleRange)
                .eq(tenantId != null, News::getTenantId, tenantId)
                .orderByDesc(News::getCreateTime);

        List<News> newsList = this.list(queryWrapper);
        return newsList.stream().map(news -> {
            NewsVO vo = new NewsVO();
            BeanUtils.copyProperties(news, vo);
            // 补充租户名称（实际需关联t_tenant表查询）
            vo.setTenantName("模拟租户名称");
            return vo;
        }).collect(Collectors.toList());
    }
}
