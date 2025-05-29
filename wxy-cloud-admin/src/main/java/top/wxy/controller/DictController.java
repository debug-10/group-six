package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.common.utils.Result;
import top.wxy.model.dto.DictRequestDTO;
import top.wxy.model.entity.Dict;
import top.wxy.model.query.DictQuery;
import top.wxy.service.DictService;

import java.util.List;

@Tag(name = "字典管理")
@RestController
@RequestMapping("dict")
@AllArgsConstructor
public class DictController {
    private final DictService dictService;

    @PostMapping("page")
    @Operation(summary = "字典列表")
    public Result<PageResult<Dict>> page(@RequestBody @Validated DictQuery query) {
        PageResult<Dict> page = dictService.getPage(query);
        return Result.ok(page);
    }


    @PostMapping("save")
    @Operation(summary = "新增字典")
    public Result save(@RequestBody @Validated DictRequestDTO requestDTO) {
        dictService.add(requestDTO);
        return Result.ok();
    }

    @PostMapping("edit")
    @Operation(summary = "修改字典")
    public Result edit(@RequestBody @Validated DictRequestDTO requestDTO) {
        dictService.edit(requestDTO);
        return Result.ok();
    }

    @PostMapping("remove")
    @Operation(summary = "删除字典")
    public Result remove(@RequestBody List<Integer> ids) {
        if (ids.size() == 0) {
            throw new ServerException("选择数据不能为空");
        }
        dictService.remove(ids);
        return Result.ok();
    }

}
