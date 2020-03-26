package cn.com.datou.smile.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName ApiApplication
 * @Author wangdh
 * @Date 2020/3/24 13:02
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan(basePackages = "cn.com.datou.smile")
@EnableFeignClients(basePackages = "cn.com.datou.smile")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
