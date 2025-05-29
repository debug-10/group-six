package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.common.utils.Result;
import top.wxy.model.query.CategoryQuery;
import top.wxy.model.vo.CategoryInfoVO;
import top.wxy.service.CategoryService;

import java.util.List;

/**
 * @author 笼中雀
 */

@Tag(name = "分类管理")
@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("page")
    @Operation(summary = "分类列表")
    public Result<PageResult<CategoryInfoVO>> page(@RequestBody @Validated CategoryQuery query) {
        PageResult<CategoryInfoVO> page = categoryService.getPage(query);
        return Result.ok(page);
    }

    @PostMapping("add")
    @Operation(summary = "新增分类")
    public Result add(@RequestBody @Validated CategoryInfoVO vo) {
        categoryService.add(vo);
        return Result.ok();
    }

    @PostMapping("edit")
    @Operation(summary = "修改分类")
    public Result<String> update(@RequestBody @Valid CategoryInfoVO vo) {
        categoryService.update(vo);
        return Result.ok();
    }

    @PostMapping("remove")
    @Operation(summary = "删除分类")
    public Result remove(@RequestBody List<Integer> ids) {
        try {
            if (ids.size() == 0) {
                throw new ServerException("选择数据不能为空");
            }
            categoryService.remove(ids); // 执行删除操作
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace(); // 打印详细异常
            return Result.error("删除失败，请稍后再试！");
        }
    }

}
