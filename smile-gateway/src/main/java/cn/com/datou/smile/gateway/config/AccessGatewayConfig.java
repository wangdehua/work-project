package cn.com.datou.smile.gateway.config;

import cn.com.datou.smile.gateway.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求url权限校验
 */
@Configuration
@Slf4j
public class AccessGatewayConfig implements GlobalFilter {

    @Autowired
    private AuthenticationService authenticationService ;

    private static final String LOGIN = "login" ;

    private static final String LOGOUT = "logout" ;

    private static final String REGISTER = "register" ;

    /**
     * 1. 首先网关检查请求的url 是否需要验证token , 从redis或者配置文件查询
     * 2. token是否有效，无效直接返回401，不调用签权服务
     * 3. 调用签权服务器看是否对该请求有权限，有权限进入下一个filter，没有权限返回401
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange , GatewayFilterChain chain) {
        try{
            ServerHttpRequest request = exchange.getRequest();
            String url = request.getPath().value();
            String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            log.info("request gateway url : {} ", url );
            log.info("request gateway header token : {} ", authentication );

            //判断接口是否为 "登陆" "注册" 不予拦截,直接去操作对应的业务 ,
            // 登陆成功/注册成功后需要保存token到redis并返回给客户端
            if(url.contains(LOGIN) || url.contains(REGISTER)){
                return chain.filter(exchange);
            }
            //注销,需要更新redis里的token
            if(url.contains(LOGOUT)){
                //删除redis中的token信息,执行业务
                this.authenticationService.delRedisToken(authentication) ;
                return chain.filter(exchange);
            }

            //1 . 验证是否需要鉴权
            String checkTokenUrl = this.authenticationService.getCheckTokenUrl() ;
            log.info("gateway need authentication url : {} " , checkTokenUrl );
            if (StringUtils.isBlank(checkTokenUrl) || !checkTokenUrl.contains(url)) {
                return chain.filter(exchange);
            }

            //2 . 请求是否携带token , 如果不带直接返回401
            if(StringUtils.isBlank(authentication)){
                return this.authenticationService.unauthorized(exchange);
            }

            //3 . 进行鉴权
            //token是否有效
            if (this.authenticationService.invalidJwtAccessToken(authentication)) {
                //网关未授权
                return this.authenticationService.unauthorized(exchange);
            }

            //调用auth 进行鉴权操作 , 鉴权结果返回 true/false
            if(this.authenticationService.auth(authentication)){
                ServerHttpRequest.Builder builder = request.mutate();
                //可以从token中获取用户信息
                //String userToken = this.authenticationService.getUserToken(authentication);
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }

            return this.authenticationService.unauthorized(exchange) ;
        }catch(Exception e){
            //返回异常
            e.printStackTrace();
            return this.authenticationService.gatewayException(exchange) ;
        }
    }
}
