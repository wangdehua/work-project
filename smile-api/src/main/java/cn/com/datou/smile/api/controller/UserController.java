package cn.com.datou.smile.api.controller;

import cn.com.datou.smile.api.handler.UserHandler;
import cn.com.datou.smile.common.entity.R;
import cn.com.datou.smile.common.vo.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Author wangdh
 * @Date 2020/3/24 23:46
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserHandler userHandler ;

    /**
     *  查询用户信息
     * @param request
     * @return
     */
    @PostMapping(value = "/v1/getUserInfo")
    public R getUserInfo(@RequestBody ApiRequest request){
        return this.userHandler.getUserInfo(request) ;
    }
}
