package com.nbicc.ita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.nbicc.ita.constant.TempIpConfig;

/** 
 * @author zhuolin(zl@nbicc.com) 
 * @date 2015年10月21日
 * 类说明 
 */
@Configuration
public class RedisBeanConfig {

	@Bean
	public JedisConnectionFactory connectionFactory(){
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName(TempIpConfig.REDIS_IP);
		connectionFactory.setPort(TempIpConfig.REDIS_PORT);//newcon platform
//		connectionFactory.setPort(6381);//test platform
		connectionFactory.setPassword(TempIpConfig.REDIS_PSW);
		connectionFactory.setDatabase(1);
		return connectionFactory;
	}
	
	@Bean
	public StringRedisTemplate redisTemplate(){
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(connectionFactory());
		return redisTemplate;
	}
}
