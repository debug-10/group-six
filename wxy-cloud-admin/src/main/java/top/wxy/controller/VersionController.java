//package top.wxy.controller;
//
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import top.wxy.model.entity.VersionEntity;
//import top.wxy.framework.common.utils.Result;
//import top.wxy.service.VersionService;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//
///**
// * 版本管理控制器（无逻辑删除）
// */
//@RestController
//@RequestMapping("/api/versions")
//@RequiredArgsConstructor
//@Tag(name = "版本管理")
//public class VersionController {
//
//    private final VersionService versionService;
//
//    /**
//     * 创建新版本
//     */
//    @PostMapping
//    @Operation(summary = "创建新版本")
//    public Result<VersionEntity> create(@RequestBody VersionEntity entity) {
//        return Result.ok(versionService.createVersion(entity));
//    }
//
//    /**
//     * 更新版本信息
//     */
//    @PutMapping("/{id}")
//    @Operation(summary = "更新版本")
//    public Result<Void> update(@PathVariable Long id, @RequestBody VersionEntity entity) {
//        versionService.updateVersion(id, entity);
//        return Result.ok();
//    }
//
//    /**
//     * 物理删除版本
//     */
//    @DeleteMapping("/{id}")
//    @Operation(summary = "删除版本（物理删除）")
//    public Result<Void> delete(@PathVariable Long id) {
//        versionService.deleteVersion(id);
//        return Result.ok();
//    }
//
//    /**
//     * 查询最新版本
//     */
//    @GetMapping("/latest")
//    @Operation(summary = "查询最新版本")
//    public Result<VersionEntity> latest(@RequestParam String platform) {
//        return Result.ok(versionService.getLatestVersion(platform));
//    }
//
//    /**
//     * 分页查询版本列表
//     */
//    @GetMapping
//    @Operation(summary = "分页查询版本列表")
//    public Result<Page<VersionEntity>> page(
//            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
//            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
//            @RequestParam(required = false) String platform,
//            @RequestParam(required = false) String version) {
//        return Result.ok(versionService.getVersionPage(pageNum, pageSize, platform, version));
//    }
//}