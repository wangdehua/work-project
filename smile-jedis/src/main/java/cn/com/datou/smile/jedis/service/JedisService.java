package cn.com.datou.smile.jedis.service;


import cn.com.datou.smile.jedis.config.JedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JedisService {

    @Autowired
    private JedisConfig jedisConfig ;

    public Pool<Jedis> getJedisPool() {
        return jedisConfig.initJedisPool();
    }

    /**
     *  获取redis 连接
     * @return
     * @throws JedisException
     */
    public Jedis getJedis() throws JedisException {
        Jedis jedis = null;
        try {
            return (Jedis)this.getJedisPool().getResource() ;
        } catch (JedisException var3) {
            if(jedis != null) {
                this.returnJedis(jedis);
            }
            throw var3;
        }
    }

    /**
     * 关闭redis连接
     * @param jedis
     */
    public void returnJedis(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    public String addStringToJedis(String key, String value, int cacheSeconds, int dbIndex) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            result = jedis.getSet(key, value);
            if(cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception var11) {
            log.error("add key/value to Redis error", var11);
            throw var11;
        } finally {
            this.returnJedis(jedis);
        }
        return result;
    }

    public void appendStringToJedis(String key, String value, int cacheSeconds, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            jedis.append(key, value);
            if(cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception var10) {
            log.error("append key/value to Redis error", var10);
        } finally {
            this.returnJedis(jedis);
        }
    }

    public String getStringFromJedis(String key, int dbIndex) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            if(jedis.exists(key).booleanValue()) {
                value = jedis.get(key);
                if(null == value || "".equals(value) || "nil".equalsIgnoreCase(value)) {
                    value = null;
                }
            }
        } catch (Exception var9) {
            log.error("get string from Redis error", var9);
        } finally {
            this.returnJedis(jedis);
        }
        return value;
    }

    public void delObjectFromJedis(String key, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            if(jedis.exists(key).booleanValue()) {
                jedis.del(key);
            }
        } catch (Exception var8) {
            log.error("del string from Redis error", var8);
        } finally {
            this.returnJedis(jedis);
        }
    }

    public String addMapToJedis(String key, Map<String, String> map, int cacheSeconds, int dbIndex) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            result = jedis.hmset(key, map);
        } catch (Exception var11) {
            log.error("add map to Redis error", var11);
        } finally {
            this.returnJedis(jedis);
        }
        return result;
    }

    public Long addMapParamToJedis(String mapKey, String paramKey, String value, int cacheSeconds, int dbIndex) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            if(0 != cacheSeconds) {
                jedis.expire(mapKey, cacheSeconds);
            }
            result = jedis.hset(mapKey, paramKey, value);
        } catch (Exception var12) {
            log.error("add map param to Redis error", var12);
        } finally {
            this.returnJedis(jedis);
        }
        return result;
    }

    public Map<String, String> getMapFromJedis(String key, int dbIndex) {
        Jedis jedis = null;
        Map result = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            result = jedis.hgetAll(key);
        } catch (Exception var9) {
            log.error("get map from Redis error", var9);
        } finally {
            this.returnJedis(jedis);
        }
        return result;
    }

    public String getMapParamFromJedis(String mapKey, String paramKey, int dbIndex) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            result = jedis.hget(mapKey, paramKey);
        } catch (Exception var10) {
            log.error("get map param from Redis error", var10);
        } finally {
            this.returnJedis(jedis);
        }
        return result;
    }

    public void delMapParamFromJedis(String mapKey, String paramKey, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            if(jedis.hexists(mapKey, paramKey).booleanValue()) {
                jedis.hdel(mapKey, new String[]{paramKey});
            }
        } catch (Exception var9) {
            log.error("get map from Redis error", var9);
        } finally {
            this.returnJedis(jedis);
        }
    }

    public List<String> getStringFromJedis(String[] keys, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            List var4 = jedis.mget(keys);
            return var4;
        } catch (Exception var8) {
            log.error("get string list from Redis error", var8);
        } finally {
            this.returnJedis(jedis);
        }
        return null;
    }

    public void addListToJedis(String key, List<String> list, int cacheSeconds, int dbIndex) {
        if(list != null && list.size() > 0) {
            Jedis jedis = null;
            try {
                jedis = this.getJedis();
                jedis.select(dbIndex);
                if(jedis.exists(key).booleanValue()) {
                    jedis.del(key);
                }
                Iterator i$ = list.iterator();
                while(i$.hasNext()) {
                    String aList = (String)i$.next();
                    jedis.rpush(key, new String[]{aList});
                }
                if(cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            } catch (Exception var11) {
                log.error("add list to Redis error", var11);
            } finally {
                this.returnJedis(jedis);
            }
        }
    }

    public void addArrayToJedis(String key, String[] value, int cacheSeconds, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            jedis.sadd(key, value);
        } catch (Exception var10) {
            log.error("add array to Redis error", var10);
        } finally {
            this.returnJedis(jedis);
        }
    }

    public void removeSetJedis(String key, String value, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            jedis.srem(key, new String[]{value});
        } catch (Exception var9) {
            log.error("remove array to Redis error", var9);
        } finally {
            this.returnJedis(jedis);
        }
    }

    public void pushDataToListJedis(String key, String data, int cacheSeconds, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            jedis.rpush(key, new String[]{data});
            if(cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception var10) {
            log.error("push data to Redis error", var10);
        } finally {
            this.returnJedis(jedis);
        }
    }

    public List<String> lrange(String key, long begin, long end, int dbIndex) {
        Jedis jedis = null;
        List list = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            list = jedis.lrange(key, begin, end);
        } catch (Exception var13) {
            log.error("lrange error", var13);
        } finally {
            this.returnJedis(jedis);
        }
        return list;
    }

    public List<String> lrangeAll(String key, int dbIndex) {
        Jedis jedis = null;
        List list = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            list = jedis.lrange(key, 0L, -1L);
        } catch (Exception var9) {
            log.error("lrange error", var9);
        } finally {
            this.returnJedis(jedis);
        }
        return list;
    }
}

