package dev.dubhe.password.manager.controller;

import dev.dubhe.password.manager.data.ResponseResult;
import dev.dubhe.password.manager.data.dto.KeyDto;
import dev.dubhe.password.manager.util.LoremIpsumUtil;
import dev.dubhe.password.manager.util.RSAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @GetMapping("/public")
    public ResponseResult getPublicKey() {
        return ResponseResult.success(RSAUtil.encoderPublicKey(RSAUtil.getPublicKey()));
    }

    @PostMapping("/test/get")
    public ResponseResult test(@RequestBody KeyDto dto) {
        return ResponseResult.success(RSAUtil.encryptByPublicKey(LoremIpsumUtil.generateLoremIpsum(16), RSAUtil.decoderPublicKey(dto.getKey().replace("\r", ""))));
    }
}
