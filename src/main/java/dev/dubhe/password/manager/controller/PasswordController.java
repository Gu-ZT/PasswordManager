package dev.dubhe.password.manager.controller;

import dev.dubhe.password.manager.data.ResponseResult;
import dev.dubhe.password.manager.data.dto.KeyDto;
import dev.dubhe.password.manager.data.dto.PasswordAddDto;
import dev.dubhe.password.manager.data.dto.PasswordChangeDto;
import dev.dubhe.password.manager.data.pojo.User;
import dev.dubhe.password.manager.exception.CustomException;
import dev.dubhe.password.manager.service.IPasswordService;
import dev.dubhe.password.manager.service.ITokenService;
import dev.dubhe.password.manager.util.RSAUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pwd")
@CrossOrigin(origins = "*")
public class PasswordController {
    private final ITokenService tokenService;
    private final IPasswordService passwordService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PasswordAddDto add, HttpServletRequest request) {
        User user = tokenService.parserToken(tokenService.getToken(request));
        if (user == null) throw CustomException.unauthorized();
        if (!passwordService.createPassword(user, add.getUsername(), add.getPassword(), add.getUrl(), add.getDesc())) {
            throw CustomException.operationFailed();
        }
        return ResponseResult.SUCCESS;
    }

    @PostMapping("/change")
    public ResponseResult change(@RequestBody PasswordChangeDto change, HttpServletRequest request) {
        User user = tokenService.parserToken(tokenService.getToken(request));
        if (user == null) throw CustomException.unauthorized();
        if (!passwordService.changePassword(user, change.getId(), change.getUsername(), change.getPassword(), change.getUrl(), change.getDesc())) {
            throw CustomException.operationFailed();
        }
        return ResponseResult.SUCCESS;
    }

    @DeleteMapping("/remove/{id}")
    public ResponseResult remove(@PathVariable Long id, HttpServletRequest request) {
        User user = tokenService.parserToken(tokenService.getToken(request));
        if (user == null) throw CustomException.unauthorized();
        if (!passwordService.removePassword(user, id)) {
            throw CustomException.operationFailed();
        }
        return ResponseResult.SUCCESS;
    }

    @PostMapping("/get")
    public ResponseResult get(@RequestBody KeyDto dto, HttpServletRequest request) {
        String token = tokenService.getToken(request);
        User user = tokenService.parserToken(token);
        if (user == null) throw CustomException.unauthorized();
        return ResponseResult.success(passwordService.getPasswords(user, token, RSAUtil.decoderPublicKey(dto.getKey().replace("\r", ""))));
    }
}
