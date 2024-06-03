package dev.dubhe.password.manager.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "用户名或密码不能为空")
    private String username;
    @NotBlank(message = "用户名或密码不能为空")
    private String password;
}
