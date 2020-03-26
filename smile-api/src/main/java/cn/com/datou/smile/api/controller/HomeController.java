package cn.com.datou.smile.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName HomeController
 * @Author wangdh
 * @Date 2020/3/24 13:03
 */
@RestController
@RequestMapping(value = "/home/v1")
@Slf4j
public class HomeController {

    @PostMapping(value = "/test")
    public Map<String,Object> test(@RequestBody Map<String,Object> map){
        log.info("=====================================" + map.toString());
        map.put("code","200");
        map.put("msg","suc");
        return map ;
    }

}
