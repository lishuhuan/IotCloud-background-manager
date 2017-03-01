package com.nbicc.ita.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.nbicc.ita.background.dao.RedisRepository;

/** 
 * @author zhuolin(zl@nbicc.com) 
 * @date 2015年10月21日
 * Redis数据库操作
 */

@Repository("redisRepository")
public class RedisDaoImpl implements RedisRepository{
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	private RedisSerializer<String> getRedisSerializer() {  
        return stringRedisTemplate.getStringSerializer();  
    }  
	
	@Override
	public boolean insert(final String key, final String value) {
		boolean result = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
			
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.setNX(getRedisSerializer().serialize(key), getRedisSerializer().serialize(value));
			}
		});
		return result;
	}

	@Override
	public String get(final String key) {
		return stringRedisTemplate.execute(new RedisCallback<String>(){
			
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] keyBytes = getRedisSerializer().serialize(key);
				if (connection.exists(keyBytes)) {  
	                byte[] valueBytes = connection.get(keyBytes);  
	                return getRedisSerializer().deserialize(valueBytes);  
	            }  
	            return null;  
			}
		});
	}

	@Override
	public void remove(final String key) {
		stringRedisTemplate.delete(key);
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(final String key, final String value) {
		if(get(key) != null){
			boolean result = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
				
				@Override
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.set(getRedisSerializer().serialize(key), getRedisSerializer().serialize(value));
					return true;
				}
			});
			return result;
		}else{
			return false;
		}
	}



}
