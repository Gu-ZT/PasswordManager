package dev.dubhe.password.manager.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.dubhe.password.manager.data.pojo.Password;
import dev.dubhe.password.manager.data.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordMapper extends BaseMapper<Password> {
}
