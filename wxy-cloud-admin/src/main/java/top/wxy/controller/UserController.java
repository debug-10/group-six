package top.wxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.wxy.model.dto.ApiResponse;
import top.wxy.model.dto.PaginationDTO;
import top.wxy.model.dto.UserDTO;
import top.wxy.model.entity.User;
import top.wxy.service.UserService;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 笼中雀
 */
@RestController
@RequestMapping("/users")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "创建用户")
    public ApiResponse<Map<String, Long>> create(@Valid @RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userService.save(user); // 保存用户
        Map<String, Long> data = new HashMap<>();
        data.put("user_id", user.getId());
        return ApiResponse.success("用户创建成功", data);
    }

    @GetMapping
    @Operation(summary = "分页查询用户")
    public ApiResponse<Map<String, Object>> listUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer role) {
        IPage<UserDTO> userPage = userService.listUsers(page, limit, status, role);

        PaginationDTO pagination = new PaginationDTO();
        pagination.setTotal(userPage.getTotal());
        pagination.setPage(page);
        pagination.setLimit(limit);

        Map<String, Object> data = new HashMap<>();
        data.put("users", userPage.getRecords());
        data.put("pagination", pagination);

        return ApiResponse.success("查询成功", data);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询用户")
    public ApiResponse<Map<String, UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO == null) {
            return ApiResponse.error("用户不存在");
        }
        userDTO.setPassword(null); // 避免返回敏感字段
        Map<String, UserDTO> data = new HashMap<>();
        data.put("user", userDTO);
        return ApiResponse.success("查询成功", data);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户")
    public ApiResponse<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ApiResponse.success("用户信息更新成功");
    }

    @PutMapping("/status/{id}")
    @Operation(summary = "切换用户状态")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.toggleUserStatus(id);
        return ApiResponse.success("用户状态切换成功");
    }
}