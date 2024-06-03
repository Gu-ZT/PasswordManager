package dev.dubhe.password.manager.data.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.dubhe.password.manager.data.dao.IPasswordDao;
import dev.dubhe.password.manager.data.mapper.PasswordMapper;
import dev.dubhe.password.manager.data.pojo.Password;
import org.springframework.stereotype.Service;

@Service
public class PasswordDao extends ServiceImpl<PasswordMapper, Password> implements IPasswordDao {
}
