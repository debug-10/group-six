package top.wxy.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;

    private String phone;

    private String nickname;

    private String avatarUrl;

    private Long tenantId;

    private Integer role;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}