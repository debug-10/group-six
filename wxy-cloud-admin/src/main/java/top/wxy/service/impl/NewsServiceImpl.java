package top.wxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.wxy.mapper.NewsMapper;
import top.wxy.model.entity.News;
import top.wxy.model.vo.NewsDetailVO;
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
        // 处理默认值：visible_range 未传则为1（全员可见）
        if (dto.getVisibleRange() == null) {
            news.setVisibleRange(1);
        }
        this.save(news);
        return news.getId();
    }

    @Override
    public void updateNews(NewsUpdateDTO dto) {
        // 校验ID不能为空
        if (dto.getId() == null) {
            throw new IllegalArgumentException("更新公告时ID不能为空");
        }

        News news = this.getById(dto.getId());
        if (news == null) {
            throw new RuntimeException("公告不存在，ID: " + dto.getId());
        }

        // 复制DTO中的属性到实体（忽略ID，避免修改主键）
        BeanUtils.copyProperties(dto, news, "id");

        // 若需要强制限制可见范围（根据需求）
        // if (dto.getVisibleRange() != null) {
        //     news.setVisibleRange(dto.getVisibleRange());
        // }

        this.updateById(news);
    }

    @Override
    public void deleteNews(Long id) {
        this.removeById(id);
    }

    @Override
    public NewsDetailVO getNewsById(Long id) {
        News news = this.getById(id);
        if (news == null) {
            return null;
        }
        NewsDetailVO vo = new NewsDetailVO();
        // 只复制 NewsDetailVO 中存在的字段（title, content, createTime, updateTime）
        BeanUtils.copyProperties(news, vo);
        return vo;
    }

    @Override
    public List<NewsVO> listNews(String title, Integer visibleRange, Long tenantId) {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();

        // 超管场景：不传入任何参数时返回所有记录
        boolean isAdminQuery = title == null && visibleRange == null && tenantId == null;
        if (!isAdminQuery) {
            // 构建查询条件
            queryWrapper.like(title != null, News::getTitle, title);

            // 普通用户场景：仅根据租户ID筛选
            if (tenantId != null) {
                // 查询条件：(tenantId=null) 或者 (tenantId=指定值)
                // 即返回所有公共新闻和指定租户的新闻
                queryWrapper.and(wrapper ->
                        wrapper.isNull(News::getTenantId)
                                .or()
                                .eq(News::getTenantId, tenantId)
                );
            } else {
                // 未指定租户ID时，默认只查询租户ID为null的记录（公共新闻）
                queryWrapper.isNull(News::getTenantId);
            }
        }

        queryWrapper.orderByDesc(News::getCreateTime);

        List<News> newsList = this.list(queryWrapper);
        return newsList.stream().map(news -> {
            NewsVO vo = new NewsVO();
            BeanUtils.copyProperties(news, vo, "content");
            return vo;
        }).collect(Collectors.toList());
    }
}
