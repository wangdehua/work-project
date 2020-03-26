package cn.com.datou.smile.core.nearby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan(basePackages = "cn.com.datou.smile")
public class CoreNearbyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreNearbyApplication.class, args);
    }
}
