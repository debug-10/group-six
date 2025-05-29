package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.wxy.framework.common.utils.Result;
import top.wxy.model.vo.NewsDetailVO;
import top.wxy.model.vo.NewsVO;
import top.wxy.model.dto.NewsCreateDTO;
import top.wxy.model.dto.NewsUpdateDTO;
import top.wxy.service.NewsService;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@Tag(name = "公告管理", description = "公告的增删改查接口")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // 创建公告：POST /api/news
    @PostMapping
    @Operation(summary = "创建公告", description = "新增一条公告记录")
    public Result<Long> createNews(@RequestBody @Valid NewsCreateDTO dto) {
        Long newsId = newsService.createNews(dto);
        return Result.ok(newsId);
    }

    // 更新公告：PUT /api/news/{id}
    @PutMapping("/{id}")
    @Operation(summary = "更新公告")
    public Result<Void> updateNews(@PathVariable Long id, @RequestBody @Valid NewsUpdateDTO dto) {
        // 自动将路径id注入DTO，无需前端手动设置
        dto.setId(id);
        newsService.updateNews(dto);
        return Result.ok();
    }


    // 删除公告：DELETE /api/news/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "删除公告", description = "根据公告ID删除记录")
    public Result<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return Result.ok();
    }

    // 查询单个公告：GET /api/news/{id} 返回详细版视图
    @GetMapping("/{id}")
    @Operation(summary = "查询单个公告", description = "根据公告ID查询详细信息")
    public Result<NewsDetailVO> getNewsById(@PathVariable Long id) { // 修改返回类型为NewsDetailVO
        NewsDetailVO news = newsService.getNewsById(id);
        return Result.ok(news);
    }

    // 查询公告列表：GET /api/news/list 返回简略版视图
    @GetMapping("/list")
    @Operation(summary = "查询公告列表", description = "支持标题、可见范围、租户ID过滤，返回简略信息")
    public Result<List<NewsVO>> listNews(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer visibleRange,
            @RequestParam(required = false) Long tenantId) {
        List<NewsVO> newsList = newsService.listNews(title, visibleRange, tenantId);
        return Result.ok(newsList);
    }
}
