package top.wxy.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.convert.CategoryConvert;
import top.wxy.entity.Category;
import top.wxy.framework.common.utils.Result;
import top.wxy.service.CategoryService;
import top.wxy.vo.CategoryVO;
import java.util.List;


/**
 * @author 笼中雀
 */
@RestController
@RequestMapping("api/category")
@Tag(name = "分类模块")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("list")
    public Result<List<CategoryVO>> getCategoryList() {
        List<Category> categories = categoryService.list();
        List<CategoryVO> list = CategoryConvert.INSTANCE.convertList(categories);
        return Result.ok(list);
    }
}
