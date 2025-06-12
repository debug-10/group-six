package top.wxy.model.dto;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserUpdateDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;

    private String phone;

    private String nickname;

    private String avatarUrl;

    private Integer role;
}