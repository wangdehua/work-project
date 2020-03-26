package cn.com.datou.smile.gateway.service;

import cn.com.datou.smile.common.entity.R;
import cn.com.datou.smile.common.entity.ResultCode;
import cn.com.datou.smile.jedis.constant.RedisKeyConst;
import cn.com.datou.smile.jedis.service.JedisService;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @ClassName AuthenticationService
 * @Author wangdh
 * @Date 2020/3/24 17:11
 */
@Service
@Slf4j
public class AuthenticationService {

    /**
     * Authorization认证开头是"bearer "
     */
    private static final String BEARER = "Bearer ";

    /**
     * jwt token 密钥，主要用于token解析，签名验证
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    @Autowired
    private JedisService jedisService ;

    /**
     * 需要验证token的url
     * @return
     */
    public String getCheckTokenUrl(){
        return jedisService.getStringFromJedis(RedisKeyConst.CHECK_TOKE_URL, 0);
    }

    /**
     * 提取jwt token中的数据，转为json
     * @param authentication
     * @return
     */
    public String getUserToken(String authentication) {
        String token = "{}";
        try {
            return new ObjectMapper().writeValueAsString(getJwt(authentication).getBody()) ;
        } catch (JsonProcessingException e) {
            log.error("token json error:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 网关拒绝，返回401
     * @param
     */
    public Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        R r = new R();
        r.setCode(ResultCode.UN_AUTHORIZED.getCode());
        r.setMsg(ResultCode.UN_AUTHORIZED.getMsg());
        byte[] bits = JSONObject.toJSONString(r).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }


    /**
     * 网关异常返回 500
     * @param
     */
    public Mono<Void> gatewayException(ServerWebExchange exchange ) {
        ServerHttpResponse response = exchange.getResponse();
        R r = new R();
        r.setCode(ResultCode.REQ_REJECT.getCode());
        r.setMsg(ResultCode.REQ_REJECT.getMsg());
        byte[] bits = JSONObject.toJSONString(r).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    public Jws<Claims> getJwt(String jwtToken) {
        if (jwtToken.startsWith(BEARER)) {
            jwtToken = org.apache.commons.lang.StringUtils.substring(jwtToken, BEARER.length());
        }
        return Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(signingKey.getBytes()) //设置签名的秘钥
                .parseClaimsJws(jwtToken);
    }

    public boolean invalidJwtAccessToken(String authentication) {
        boolean invalid = Boolean.TRUE;
        try {
            getJwt(authentication);
            invalid = Boolean.FALSE;
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException ex) {
            log.error("user token error :{}", ex.getMessage());
        }
        return invalid;
    }

    /**
     *  进行鉴权操作
     */
    public boolean auth(String token ){
        //token 是否过期
        if(isTokenExpired(token)){
            return false ;
        }
        //redis 中是否存在token
        token = this.jedisService.getStringFromJedis(token, 1);
        if(StringUtils.isBlank(token)){
            return false ;
        }
        return true ;
    }

    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        Jws<Claims> jwt = getJwt(token);
        return jwt.getBody().getExpiration();
    }

    /**
     * 删除redis中保存的token信息
     * @param token
     */
    public void delRedisToken(String token){
        this.jedisService.delObjectFromJedis(token,1);
    }
}
