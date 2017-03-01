package com.nbicc.ita.rest;

import static com.nbicc.ita.constant.ParameterKeys.AGE;
import static com.nbicc.ita.constant.ParameterKeys.AUDIT_REASON;
import static com.nbicc.ita.constant.ParameterKeys.AUDIT_STATUS;
import static com.nbicc.ita.constant.ParameterKeys.COMPANY_ADDRESS;
import static com.nbicc.ita.constant.ParameterKeys.COMPANY_INFO;
import static com.nbicc.ita.constant.ParameterKeys.COMPANY_NAME;
import static com.nbicc.ita.constant.ParameterKeys.EDUCATION;
import static com.nbicc.ita.constant.ParameterKeys.EMAIL;
import static com.nbicc.ita.constant.ParameterKeys.ID;
import static com.nbicc.ita.constant.ParameterKeys.LEGAL_REPRESENTATIVE;
import static com.nbicc.ita.constant.ParameterKeys.PERSON_NAME;
import static com.nbicc.ita.constant.ParameterKeys.PHONE_NUMBER;
import static com.nbicc.ita.constant.ParameterKeys.SESSION_USER;
import static com.nbicc.ita.constant.ParameterKeys.SEX;
import static com.nbicc.ita.constant.ParameterKeys.SOCIAL_CODE;
import static com.nbicc.ita.constant.ParameterKeys.TECHNICAL_PERSON;
import static com.nbicc.ita.constant.ParameterKeys.USER_NAME;
import static com.nbicc.ita.constant.ParameterKeys.USER_PSW;
import static com.nbicc.ita.constant.ParameterKeys.VERITIFY_CODE;
import static com.nbicc.ita.constant.ParameterKeys.WORD_ADDRESS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbicc.ita.constant.ResponseCode;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.service.BackgroundUserService;
import com.nbicc.ita.util.BusinessHelper;
import com.nbicc.ita.util.LoginRequired;
import com.nbicc.ita.util.MobClient;
import com.nbicc.ita.util.ResponseFactory;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/** 
 * @author lishuhuan
 * @date 2016年7月2日
 * 用户注册与登录的相关
 */
@Component("userHandler")
@Path("user")
public class UserHandler {

	@Autowired
	private BackgroundUserService userService;
	

	@POST
	@Path("/login")
	public Response login(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = userService.login(requestMap.get(USER_NAME), requestMap.get(USER_PSW));
		if (user == null) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
		if (user.getResponseCode().compareTo(ResponseCode.SUCCESS) == 0) {
			request.getSession().setAttribute(SESSION_USER, user);
			Map<String, Object> map=new HashMap<>();
			map.put("id", user.getUserId());
			map.put("type", user.getUserType());
			return ResponseFactory.response(Response.Status.OK, user.getResponseCode(), map);
		}
		return ResponseFactory.response(Response.Status.OK, user.getResponseCode(), null);
	}

	@POST
	@Path("/register")
	public Response register(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String id=str.replace("-", "");
		boolean state = userService.register(id,requestMap.get(USER_NAME), requestMap.get(USER_PSW),requestMap.get(EMAIL));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}

	}
	
	@POST
	@Path("/personInfo")
	public Response personInfo(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user=userService.getUserByName(requestMap.get(USER_NAME));
		boolean state=userService.registerPerson(user, requestMap.get(PERSON_NAME), requestMap.get(SEX), requestMap.get(AGE), requestMap.get(EDUCATION), requestMap.get(WORD_ADDRESS));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@Path("/enterpriseInfo")
	public Response enterpriseInfo(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user=userService.getUserByName(requestMap.get(USER_NAME));
		boolean state=userService.registerEnterprise(user,requestMap.get(COMPANY_NAME),requestMap.get(COMPANY_INFO),requestMap.get(SOCIAL_CODE),
				requestMap.get(LEGAL_REPRESENTATIVE),requestMap.get(COMPANY_ADDRESS),requestMap.get(TECHNICAL_PERSON),requestMap.get(PHONE_NUMBER));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@Path("/message")
	public Response message(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams)
			throws ApiException {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String phone=requestMap.get(PHONE_NUMBER);
		int num = (int) (Math.random() * 9000);
		String message = String.valueOf(num);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(20 * 60);
		session.setAttribute("veritfy"+phone, message);

		TaobaoClient client = new DefaultTaobaoClient("	http://gw.api.taobao.com/router/rest", "23333158",
				"2648ff59c34561ae585ae67e84e57c97");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		// req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("注册验证");
		req.setSmsParamString("{'code':" + "'" + message + "'" + ",'product':'IotCloud'}");
		req.setRecNum(phone);
		req.setSmsTemplateCode("SMS_6770624");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		if (rsp.getResult().getSuccess()) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@Path("/vertifyMessageWeb")
	public Response vertifyMessageWeb(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String phone=requestMap.get(PHONE_NUMBER);
		String vertifyCode=requestMap.get(VERITIFY_CODE);
		HttpSession session = request.getSession();
		String serverCode=(String) session.getAttribute("veritfy"+phone);
		if(serverCode.equals(vertifyCode)){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@Path("/userExamine")
	public Response userExamine(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state = userService.userExamine(requestMap.get(ID), requestMap.get(AUDIT_STATUS), requestMap.get(AUDIT_REASON));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@Path("/vertifyMessage")
	public Response vertifyMessage(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String phoneNumber=requestMap.get(PHONE_NUMBER);
		String vertifyCode=requestMap.get(VERITIFY_CODE);
		String address = "https://webapi.sms.mob.com/sms/verify";
		MobClient client = null;
		try {
			client = new MobClient(address);
			client.addParam("appkey", "dd365e75fdec").addParam("phone", phoneNumber)
					.addParam("zone", "86").addParam("code", vertifyCode);
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			client.addRequestProperty("Accept", "application/json");
			String result = client.post();
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, result);
		} finally {
			client.release();
		}
		 
	}
	
	@POST
	@Path("/getUserList")
	public Response getUserList(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<IotCloudUser> list=userService.getUserList(requestMap.get(AUDIT_STATUS));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, list);
	}
	
	@POST
	@Path("/mobileUnique")
	public Response mobileUnique(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String phone=requestMap.get(PHONE_NUMBER);
		IotCloudUser user=userService.getUserByMobile(phone);
		int state=0;
		if(user!=null){
			state=1;
		}
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, state);
		
	}
	
	@POST
	@Path("/getUserById")
	@LoginRequired
	public Response getUserById(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user=userService.getUserById(requestMap.get(ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, user);
	}
	
	@POST
	@LoginRequired
	@Path("/sendMessageForKey")
	public Response sendMessageForKey(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams)
			throws ApiException {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String phone=requestMap.get(PHONE_NUMBER);
		int num = (int) (Math.random() * 9000);
		String message = String.valueOf(num);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(20 * 60);
		session.setAttribute("key"+phone, message);

		TaobaoClient client = new DefaultTaobaoClient("	http://gw.api.taobao.com/router/rest", "23333158",
				"2648ff59c34561ae585ae67e84e57c97");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		// req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("注册验证");
		req.setSmsParamString("{'code':" + "'" + message + "'" + ",'name':'查看开发者密钥'}");
		req.setRecNum(phone);
		req.setSmsTemplateCode("SMS_13405017");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		if (rsp.getResult().getSuccess()) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@Path("/getDeveloperKey")
	public Response getDeveloperKey(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = userService.login(requestMap.get(USER_NAME), requestMap.get(USER_PSW));
		if (user == null) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
		else if (user.getResponseCode().compareTo(ResponseCode.SUCCESS) == 0) {
			String key=user.getSecret();
			return ResponseFactory.response(Response.Status.OK, user.getResponseCode(), key);
		}
		else {
			return ResponseFactory.response(Response.Status.OK, user.getResponseCode(), null);
		}
	}
	
	@GET
	@LoginRequired
	@Path("/getUserInfo")
	public Response setGatewayParts(@Context HttpServletRequest request){
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		if(user!=null){
			Map<String, String> map=new HashMap<>();
			map.put("id", user.getUserId());
			map.put("phone", user.getUsername());
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, map);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

		
	
}
