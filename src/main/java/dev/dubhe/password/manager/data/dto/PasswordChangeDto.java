package dev.dubhe.password.manager.data.dto;

import dev.dubhe.password.manager.util.RSAUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChangeDto {
    private Long id;
    private String username;
    private String password;
    private String url;
    private String desc;

    public String getPassword() {
        return RSAUtil.decryptByPrivateKey(this.password);
    }
}
