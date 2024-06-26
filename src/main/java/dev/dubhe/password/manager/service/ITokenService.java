package dev.dubhe.password.manager.service;

import dev.dubhe.password.manager.data.pojo.User;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;

public interface ITokenService {
    String createToken(@Nonnull User user);
    User parserToken(String token);
    String getToken(HttpServletRequest request);
}
