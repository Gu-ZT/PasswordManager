package dev.dubhe.password.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import dev.dubhe.password.manager.data.dao.IPasswordDao;
import dev.dubhe.password.manager.data.pojo.Password;
import dev.dubhe.password.manager.data.pojo.User;
import dev.dubhe.password.manager.data.vo.PasswordVo;
import dev.dubhe.password.manager.exception.CustomException;
import dev.dubhe.password.manager.service.IPasswordService;
import dev.dubhe.password.manager.util.AESUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordService implements IPasswordService {
    private final IPasswordDao passwordDao;

    @Override
    public boolean createPassword(@Nonnull User user, @Nonnull String username, @Nonnull String password, @Nullable String url, @Nullable String desc) {
        password = AESUtil.encrypt(password, user.getPassword());
        Password pwd = new Password(user.getId(), url == null ? "" : url, desc == null ? "" : desc, username, password);
        return passwordDao.save(pwd);
    }

    @Override
    public boolean changePassword(@Nonnull User user, @Nonnull Long password, @Nonnull String username, String pwd, @Nullable String url, @Nullable String desc) {
        Password password1 = passwordDao.getById(password);
        if (!password1.getUser().equals(user.getId())) throw CustomException.forbidden();
        pwd = pwd == null ? password1.getPassword() : AESUtil.encrypt(pwd, user.getPassword());
        password1.setPassword(pwd);
        password1.setUrl(url == null ? password1.getUrl() : url);
        password1.setDescription(desc == null ? password1.getDescription() : desc);
        return passwordDao.updateById(password1);
    }

    @Override
    public boolean removePassword(@Nonnull User user, @Nonnull Long password) {
        Password password1 = passwordDao.getById(password);
        if (!password1.getUser().equals(user.getId())) throw CustomException.forbidden();
        return passwordDao.removeById(password1);
    }

    @Override
    public List<PasswordVo> getPasswords(@Nonnull User user, @Nonnull String token) {
        QueryWrapper<Password> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user", "%s".formatted(user.getId()));
        List<Password> list = passwordDao.list(queryWrapper);
        return list.stream().map(
            password -> new PasswordVo(
                password.getId().toString(),
                password.getUsername(),
                AESUtil.encrypt(AESUtil.decrypt(password.getPassword(), user.getPassword()), token),
                password.getUrl(),
                password.getDescription()
            )
        ).toList();
    }
}
