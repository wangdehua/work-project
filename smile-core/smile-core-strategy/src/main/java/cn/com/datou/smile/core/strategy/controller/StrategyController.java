package cn.com.datou.smile.core.strategy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName StrategyController
 * @Author wangdh
 * @Date 2020/3/24 23:33
 */
@RestController
@RequestMapping(value = "/strategy")
@Slf4j
public class StrategyController {

    @GetMapping(value = "/test")
    public Map<String,Object> test(@RequestBody Map<String,Object> map){
        log.info("-------------------------------------------------" + map);
        map.put("retCode","0000");
        map.put("retMsg","suc");
        return map ;
    }
}
