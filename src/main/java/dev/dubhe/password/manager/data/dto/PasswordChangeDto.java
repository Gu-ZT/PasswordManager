package dev.dubhe.password.manager.data.dto;

import jakarta.validation.constraints.NotBlank;
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
}
