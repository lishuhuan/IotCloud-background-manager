package com.nbicc.ita.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.nbicc.ita.constant.ParameterKeys;
import com.nbicc.ita.constant.ResponseCode;


/**
   @author lishuhuan 
 * @date 2016年3月23日    用户登录验证拦截器
 */
public class LoginRequiredInterceptor implements MethodInterceptor {


	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (methodInvocation.getMethod().isAnnotationPresent(
				LoginRequired.class)) {
			Object[] args = methodInvocation.getArguments();
			if (args.length != 0) {
				for (Object obj : args) {
					if (obj instanceof HttpServletRequest) {
						HttpServletRequest request = (HttpServletRequest)obj;
						if(request.getSession().getAttribute(ParameterKeys.SESSION_USER) == null){
							return ResponseFactory.response(Response.Status.OK, ResponseCode.NOT_LOGIN, "no login");
						}
					}
				}
			}
		}
		return methodInvocation.proceed();
		
	}
}
