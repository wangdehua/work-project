package cn.com.datou.smile.core.user.controller;

import cn.com.datou.smile.common.entity.R;
import cn.com.datou.smile.common.vo.CoreUserRequest;
import cn.com.datou.smile.common.vo.CoreUserResponse;
import cn.com.datou.smile.core.user.handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName UserController
 * @Author wangdh
 * @Date 2020/3/24 23:33
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserHandler userHandler ;

    @PostMapping(value = "/getUserInfo")
    public CoreUserResponse getUserInfo(@RequestBody CoreUserRequest request){
        return  this.userHandler.getUserInfo(request) ;
    }
}
