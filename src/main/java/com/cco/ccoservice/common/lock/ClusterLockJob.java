package com.cco.ccoservice.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * 模拟分布式锁
 * @author xiebq
 * @create 2020/3/16 0016
 * @since 1.0.0
 */
@Component
@Slf4j
public class ClusterLockJob {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${server.port}")
    private String port;

    public static final String LOCK_PRE = "lock_prefix_01_";


    //@Scheduled(cron = "0/5 * * * * *")
    public void lock(){
        String lockName = LOCK_PRE + "ClusterLockJob";
        String currentValue = getHostIp() + ":" + port;
        log.info(" ##  Redis is in lock......");
        Boolean ifAbsent = false;
        try{
            //设置锁
            ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockName, currentValue,60, TimeUnit.SECONDS);
            if (ifAbsent){
                log.info("Lock success,execute business,current time:" + System.currentTimeMillis());
                Thread.sleep(3000);
            }else {
                //获取锁失败
                String value = (String) redisTemplate.opsForValue().get(lockName);
                log.info("Lock fail,current lock belong to:" + value);
            }
        }catch (Exception e){
            log.error("ClusterLockJob exception error " + e);
        }finally {
            if (ifAbsent){
                //若分布式锁Value与本机Value一致，则当前机器获得锁，进行解锁
                redisTemplate.delete(lockName);
            }
        }
    }

    /**
     * 获取本机内网IP地址方法
     * @return
     */
    private static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
