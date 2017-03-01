package com.nbicc.ita.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbicc.ita.constant.ResponseCode;
import com.nbicc.ita.service.ThirdPartyInterfaceService;
import com.nbicc.ita.util.HttpClient;
import com.nbicc.ita.util.ResponseFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component("thirdPartyInterface")
@Path("server")
public class ThirdPartyInterface {

	@Autowired
	private ThirdPartyInterfaceService thirdPartyInterfaceService;
	
	
	@POST
	@Path("/alarm")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alarm(JSONObject jsonObject){
		return ResponseFactory.response(Response.Status.OK,ResponseCode.SUCCESS, jsonObject);
	}
	
	@POST
	@Path("/output")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject output(JSONObject jsonObject){
		JSONArray dataarray=jsonObject.getJSONArray("data");
		String deviceId=jsonObject.getString("device_id");
		String deviceKey=jsonObject.getString("deviceKey");
		String messageType=jsonObject.getString("messageType");
		System.out.println(jsonObject);
		JSONObject response = new JSONObject();
		try {
			JSONObject result=new JSONObject();	
			JSONObject key=new JSONObject();
			for(Object object:dataarray){
				JSONObject detail=JSONObject.fromObject(object);
				String timestamp=detail.getString("timestamp");
				JSONObject value=detail.getJSONObject("value");
			}

			
			result.put("Key", key);
			HttpClient.doPost("http://60.12.215.94:8016/ActiveWarninghandler.ashx", result);
			response.put("result_code", 0);
			response.put("result_message", "ok");
			return response;
		} catch (Exception e) {
			response.put("result_code", 1);
			response.put("result_message", "failure");
			return response;
		}
	}
}
