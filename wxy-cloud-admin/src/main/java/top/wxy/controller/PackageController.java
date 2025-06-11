package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.wxy.model.entity.Package;
import top.wxy.service.PackageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@Tag(name = "套餐管理")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    // 创建套餐
    @PostMapping
    @Operation(summary = "创建套餐")
    public boolean create(@RequestBody Package pkg) {
        return packageService.save(pkg);
    }

    // 修改套餐
    @PutMapping("/{id}")
    @Operation(summary = "修改套餐")
    public boolean update(@PathVariable Integer id, @RequestBody Package pkg) {
        pkg.setPackageId(id);
        return packageService.updateById(pkg);
    }

    // 删除套餐
    @DeleteMapping("/{id}")
    @Operation(summary = "删除套餐")
    public boolean delete(@PathVariable Integer id) {
        return packageService.removeById(id);
    }

    // 查询单个
    @GetMapping("/{id}")
    @Operation(summary = "查询套餐")
    public Package getById(@PathVariable Integer id) {
        return packageService.getById(id);
    }

    // 查询所有
    @GetMapping
    @Operation(summary = "查询所有套餐")
    public List<Package> list() {
        return packageService.list(new QueryWrapper<>());
    }
}
