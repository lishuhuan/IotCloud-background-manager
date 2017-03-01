package com.nbicc.ita.background.dao;

import java.util.Map;


/** 
 * @author zhuolin(zl@nbicc.com) 
 * @date 2015年10月21日
 * 类说明 
 */

public interface RedisRepository {
	
	public boolean insert(String key,String value);
	
	public String get(String key);
	
	public void remove(String key);  
	
    public void removeAll();  
    
    public boolean update(String key,String value);
    
}
