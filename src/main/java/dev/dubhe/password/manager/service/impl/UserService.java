package dev.dubhe.password.manager.service.impl;

import dev.dubhe.password.manager.data.dao.IUserDao;
import dev.dubhe.password.manager.data.pojo.User;
import dev.dubhe.password.manager.data.vo.UserLoginVo;
import dev.dubhe.password.manager.exception.CustomException;
import dev.dubhe.password.manager.service.ITokenService;
import dev.dubhe.password.manager.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final PasswordEncoder encoder;
    private final ITokenService tokenService;

    @Override
    public UserLoginVo register(String username, String password) {
        User user = userDao.getByUserName(username);
        if (user != null) throw new CustomException("用户已存在");
        password = encoder.encode(password);
        user = new User(username, password);
        userDao.save(user);
        String token = tokenService.createToken(user);
        return new UserLoginVo(user.getId(), user.getNickname(), token);
    }

    @Override
    public UserLoginVo login(String username, String password) {
        User user = userDao.getByUserName(username);
        if (user == null) throw new CustomException("用户不存在");
        String token = tokenService.createToken(user);
        return new UserLoginVo(user.getId(), user.getNickname(), token);
    }
}
