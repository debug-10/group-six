package top.wxy.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;

    private String phone;

    private String nickname;

    private String avatarUrl;

    private Integer role;

}