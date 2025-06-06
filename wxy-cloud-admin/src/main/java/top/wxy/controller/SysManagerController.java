package top.wxy.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wxy.framework.common.exception.ServerException;
import top.wxy.framework.common.utils.PageResult;
import top.wxy.framework.common.utils.Result;
import top.wxy.model.query.ChangePasswordQuery;
import top.wxy.model.query.SysManagerQuery;
import top.wxy.model.vo.SysManagerVO;
import top.wxy.security.user.ManagerDetail;
import top.wxy.security.user.SecurityUser;
import top.wxy.service.SysManagerService;

import java.util.List;

/**
 * @author 笼中雀
 */
@Tag(name = "管理员管理")
@AllArgsConstructor
@RestController
@RequestMapping("/sys/manager")
public class SysManagerController {

    private final SysManagerService sysManagerService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<SysManagerVO>> page(@RequestBody @Valid SysManagerQuery query) {
        PageResult<SysManagerVO> page = sysManagerService.page(query);
        return Result.ok(page);
    }

    @PostMapping("add")
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody @Valid SysManagerVO vo) {
        // 新增密码不能为空
        if (StrUtil.isBlank(vo.getPassword())) {
            return Result.error("密码不能为空");
        }
        // 保存
        sysManagerService.save(vo);
        return Result.ok();
    }

    @PostMapping("edit")
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody @Valid SysManagerVO vo) {
        sysManagerService.update(vo);
        return Result.ok();
    }

    @PostMapping("remove")
    @Operation(summary = "删除")
    public Result<String> delete(@RequestBody List<Long> idList) {
        Long managerId = SecurityUser.getManagerId();
        if (idList.contains(managerId)) {
            return Result.error("不能删除当前登录管理员");
        }
        sysManagerService.delete(idList);
        return Result.ok();
    }

    @PostMapping("getManagerInfo")
    @Operation(summary = "获取管理员信息")
    public Result<SysManagerVO> getManagerInfo() {
        ManagerDetail manager = SecurityUser.getManager();
        return Result.ok(sysManagerService.getManagerInfo(manager));
    }

    @PostMapping("changePassword")
    @Operation(summary = "修改密码")
    public Result<String> editPassword(@RequestBody @Valid ChangePasswordQuery query) {
        ManagerDetail manager = SecurityUser.getManager();
        if (manager.getId() == null) {
            throw new ServerException("管理员不存在");
        }
        query.setId(manager.getId());
        sysManagerService.changePassword(query);
        return Result.ok();
    }
}
