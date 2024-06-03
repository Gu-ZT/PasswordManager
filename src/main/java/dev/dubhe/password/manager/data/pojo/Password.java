package dev.dubhe.password.manager.data.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("password")
public class Password extends AbstractSqlBase {
    private Long user;
    private String url;
    private String username;
    private String password;
    private String description;

    public Password(Long user, String url, String description, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
        this.url = url;
        this.description = description;
    }
}
