package com.cco.ccoservice.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;

/**
 * redis缓存配置
 * @author xiebq
 * @create 2019/12/9 0009
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**redis密码**/
    @Value("${spring.redis.password}")
    public String password;
    @Value("${spring.redis.clusters}")
    public String cluster;

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    public  Object getFieldValueByObject (Object object , String targetFieldName) throws Exception {
        // 获取该对象的Class
        Class objClass = object.getClass();
        // 获取所有的属性数组
        Field[] fields = objClass.getDeclaredFields();
        for (Field field:fields) {
            // 属性名称
            field.setAccessible(true);
            String currentFieldName = field.getName();
            if(currentFieldName.equals(targetFieldName)){
                return field.get(object); // 通过反射拿到该属性在此对象中的值(也可能是个对象)
            }
        }
        return null;
    }

    /**
     * 通过反射获取JedisCluster
     * @param factory
     * @return
     */
    @Bean
    public JedisCluster redisCluster(RedisConnectionFactory factory){
        Object object =null;
        try {
            object= getFieldValueByObject(factory,"cluster");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (JedisCluster)object;

    }

    @Bean(name="redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<Object>(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(stringSerializer);

        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name="factory")
    public RedisConnectionFactory factory(RedisClusterConfiguration clusterConfig){
        JedisConnectionFactory redisConnectionFactory=new JedisConnectionFactory(clusterConfig);
        String redisPassword = password;
        redisConnectionFactory.setPassword(redisPassword);
        redisConnectionFactory.setPoolConfig(createJedisPoolConfig());
        redisConnectionFactory.setTimeout(30000);
        redisConnectionFactory.setUsePool(true);
        return redisConnectionFactory;
    }

    @Bean(name="clusterConfig")
    public RedisClusterConfiguration clusterConfig(){
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        String[] nodes = cluster.split(",");
        System.out.println("cluster " + cluster);
        System.out.println("nodes " + nodes);
        for(String node : nodes){
            String[] host =  node.split(":");
            RedisNode redis = new RedisNode(host[0], Integer.parseInt(host[1]));
            config.addClusterNode(redis);
        }

        return config;
    }


    public JedisPoolConfig createJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(false);

        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

        //是否启用pool的jmx管理功能, 默认true
        config.setJmxEnabled(true);

        //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
        config.setJmxNamePrefix("pool");

        //是否启用后进先出, 默认true
        config.setLifo(true);

        //最大空闲连接数, 默认8个
        config.setMaxIdle(2000);

        //最大连接数, 默认8个
        config.setMaxTotal(5000);

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(10000);

        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(1800000);

        //最小空闲连接数, 默认0
        config.setMinIdle(0);

        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(3);

        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(1800000);

        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(false);

        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);

        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(-1);

        return config;
    }
}
