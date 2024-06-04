package dev.dubhe.password.manager.service;

import dev.dubhe.password.manager.data.pojo.User;
import dev.dubhe.password.manager.data.vo.PasswordVo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

public interface IPasswordService {
    boolean createPassword(@Nonnull User user, @Nonnull String username, @Nonnull String password, @Nullable String url, @Nullable String desc);

    boolean changePassword(@Nonnull User user, @Nonnull Long password, @Nullable String username, @Nullable String pwd, @Nullable String url, @Nullable String desc);

    boolean removePassword(@Nonnull User user, @Nonnull Long password);

    List<PasswordVo> getPasswords(@Nonnull User user, @Nonnull String token);
}
