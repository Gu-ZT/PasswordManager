package dev.dubhe.password.manager.service;

import dev.dubhe.password.manager.data.vo.UserLoginVo;

public interface IUserService {
    UserLoginVo register(String username, String password);

    UserLoginVo login(String username, String password);
}
