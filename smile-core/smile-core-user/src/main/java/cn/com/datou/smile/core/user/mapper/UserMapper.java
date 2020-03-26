package cn.com.datou.smile.core.user.mapper;

import cn.com.datou.smile.core.user.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getUserInfo(User user) ;

}