package dev.dubhe.password.manager.data.dto;

import dev.dubhe.password.manager.util.RSAUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordAddDto {
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String url;
    private String desc;

    public String getPassword() {
        return RSAUtil.decryptByPrivateKey(this.password);
    }
}
