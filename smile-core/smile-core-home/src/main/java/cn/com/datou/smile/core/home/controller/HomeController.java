package cn.com.datou.smile.core.home.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName HomeController
 * @Author wangdh
 * @Date 2020/3/24 23:19
 */
@RestController
@RequestMapping(value = "/home")
@Slf4j
public class HomeController {

    @GetMapping(value = "/test")
    public Map<String,Object> test(@RequestBody Map<String,Object> map){
        log.info("-------------------------------------------------" + map);
        map.put("retCode","0000");
        map.put("retMsg","suc");
        return map ;
    }

}
