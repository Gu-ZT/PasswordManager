package dev.dubhe.password.manager.controller;

import dev.dubhe.password.manager.data.ResponseResult;
import dev.dubhe.password.manager.data.dto.LoginDto;
import dev.dubhe.password.manager.data.pojo.User;
import dev.dubhe.password.manager.exception.CustomException;
import dev.dubhe.password.manager.service.ITokenService;
import dev.dubhe.password.manager.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {
    private final IUserService userService;
    private final ITokenService tokenService;

    @PostMapping("/register")
    public ResponseResult register(@RequestBody LoginDto login) {
        return ResponseResult.success(userService.register(login.getUsername(), login.getPassword()));
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto login) {
        return ResponseResult.success(userService.login(login.getUsername(), login.getPassword()));
    }

    @GetMapping("/refresh")
    public ResponseResult refreshToken(HttpServletRequest request) {
        User user = tokenService.parserToken(tokenService.getToken(request));
        if (user == null) throw CustomException.unauthorized();
        return ResponseResult.success(tokenService.createToken(user));
    }
}
