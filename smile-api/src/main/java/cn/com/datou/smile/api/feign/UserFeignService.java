package cn.com.datou.smile.api.feign;

import cn.com.datou.smile.common.vo.CoreUserRequest;
import cn.com.datou.smile.common.vo.CoreUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName UserFeignService
 * @Description 调用core-user的feign
 * @Author wangdh
 * @Date 2020/3/26 17:32
 */
@Component
@FeignClient(value = "core-user",path="/user")
public interface UserFeignService {

    /**
     *  查询用户信息
     * @param request
     * @return
     */
    @PostMapping(value="/getUserInfo")
    CoreUserResponse getUserInfo(@RequestBody CoreUserRequest request);

}
