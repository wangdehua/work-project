package cn.com.datou.smile.core.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName CoreUserApplication
 * @Author wangdh
 * @Date 2020/3/24 23:41
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan(basePackages = "cn.com.datou.smile")
public class CoreUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreUserApplication.class, args);
    }

}
