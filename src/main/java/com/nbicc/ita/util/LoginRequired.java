package com.nbicc.ita.util;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/** 
 * @author lishuhuan 
 * @date 2016年3月23日
 * 自定义annotation，进入方法前需要登录验证 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}
