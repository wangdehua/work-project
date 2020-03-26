package cn.com.datou.smile.jedis.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

@ConfigurationProperties(prefix = "spring.redis")
@Component
@Data
@Slf4j
public class JedisConfig {

    private String host;

    private int port;

    private int timeout;

    private String password;

    @Bean(value = "initJedisPool")
    public Pool<Jedis> initJedisPool(){
        log.info("=================== redis 连接池初始化成功 .... =======================");
        return  new JedisPool() ;
    }
}
