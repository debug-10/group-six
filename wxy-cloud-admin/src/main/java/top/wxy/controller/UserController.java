package top.wxy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.common.utils.Result;
import top.wxy.model.dto.UserEditDTO;
import top.wxy.model.query.UserQuery;
import top.wxy.model.vo.UserInfoVO;
import top.wxy.service.UserService;

/**
 * @author mqxu
 * @date 2024/11/18
 * @description UserController
 **/
@RestController
@AllArgsConstructor
@Tag(name = "用户管理", description = "用户管理")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:user:view')")
    public Result<PageResult<UserInfoVO>> page(@RequestBody @Valid UserQuery query) {
        return Result.ok(userService.page(query));
    }

    @PostMapping("edit")
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Result<String> update(@RequestBody @Valid UserEditDTO dto) {
        userService.update(dto);
        return Result.ok();
    }

    @PostMapping("export")
    @Operation(summary = "导出")
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export(@RequestBody UserQuery query, HttpServletResponse response) {
        userService.export(query, response);
    }

    @PostMapping("enabled")
    @Operation(summary = "账户状态修改")
    @PreAuthorize("hasAuthority('sys:user:freeze')")
    public Result<String> enabled(@RequestParam Integer userId) {
        userService.enabled(userId);
        return Result.ok();
    }
}