package cn.com.datou.smile.core.user.service.impl;

import cn.com.datou.smile.core.user.domain.User;
import cn.com.datou.smile.core.user.mapper.UserMapper;
import cn.com.datou.smile.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Author wangdh
 * @Date 2020/3/26 14:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper ;

    @Override
    public User getUserInfo(User user) {
        return this.userMapper.getUserInfo(user);
    }
}
