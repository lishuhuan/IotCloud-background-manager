package com.nbicc.ita.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.reflect.TypeToken;
import com.is.gson.GsonFactory;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.service.BackgroundUserService;
import com.nbicc.ita.service.EquipmentService;
import com.nbicc.ita.util.BusinessHelper;
import com.nbicc.ita.util.HttpsTest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component("equipmentHandle")
@Path("equipment")
public class EquipmentHandle {

	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private Cache protocolCache;
	
	@Autowired
	private BackgroundUserService userService;
	
	@GET
/*	@Consumes("application/json") */
	@Path("/gettoken")
	public Map<String, Object> gettoken(@QueryParam("developer_id") String id,@QueryParam("developer_secret") String secret,@Context HttpServletRequest request){
		String userSecret=userService.getSecretByUserId(id);
		Map<String, Object> map=new HashMap<>();
		if(secret.equals(userSecret)){
			Element element = protocolCache.get(id);
			String token=null;
			if(null==element){
				token=UUID.randomUUID().toString().trim().replaceAll("-", "");
				Element elementnew=new Element(id, token);
				protocolCache.put(elementnew);
			}
			else{
				token=(String) element.getValue();
				protocolCache.put(element);
			}
			map.put("result_code", 0);
			map.put("access_token", token);
			return map;
		}
		else {
			map.put("result_code", 1);
			map.put("access_token", null);
			return map;
		}
		
	}
	
	@GET
	/*@Consumes("application/json") */
	@Path("/getqrcode")
	public Map<String, Object> getqrcode(@QueryParam("access_token") String token,@QueryParam("device_key") String key,@QueryParam("sn") String sn,@Context HttpServletRequest request) throws Exception{
		String userid=equipmentService.getUserIdByDeviceKey(key);
		Map<String, Object> resultMap=new HashMap<>();
		if(null==userid){
			resultMap.put("result_code", 3);
			resultMap.put("error_message", "can not find the user by device_key!");
			return resultMap;
		}
		else {
			Element element = protocolCache.get(userid);
			if(null==element){
				resultMap.put("result_code", 4);
				resultMap.put("error_message", "This device doesn't belong to you!");
				return resultMap;
			}
			else if(!element.getValue().equals(token)){
				resultMap.put("result_code", 2);
				resultMap.put("error_message", "This device doesn't belong to you!");
				return resultMap;
			}
			else {
				String result=HttpsTest.postForm("http://120.26.49.225:8083/device/register?product_id="+key+"&sn="+sn);
				Map<String, Object> map = GsonFactory.createGson().fromJson(result, new TypeToken<Map<String, Object>>() {}.getType());
				if((int)(double)map.get("result_code")==0){
					@SuppressWarnings("unchecked")
					Map<String, Object> data= (Map<String, Object>) map.get("data");
					resultMap.put("result_code", 0);
					resultMap.put("qrticket", data.get("qrticket"));
					resultMap.put("device_id", data.get("deviceid"));
					resultMap.put("device_license", data.get("devicelicence"));
					return resultMap;
				}
				else {
					resultMap.put("result_code", 1);
					resultMap.put("error_message", "Data required by the interface hava a problem, please check it!");
					return resultMap;
				}
			}
		}
		
	}
	
	@GET
	/*@Consumes("application/json") */
	@Path("/getqrcodePro")
	public Map<String, Object> getqrcodePro(@QueryParam("access_token") String token,@QueryParam("device_key") String key,@QueryParam("sn") String sn,@Context HttpServletRequest request) throws Exception{
		String userid=equipmentService.getUserIdByDeviceKey(key);
		Map<String, Object> resultMap=new HashMap<>();
		if(null==userid){
			resultMap.put("result_code", 3);
			resultMap.put("error_message", "can not find the user by device_key!");
			return resultMap;
		}
		else {
			Element element = protocolCache.get(userid);
			if(null==element){
				resultMap.put("result_code", 4);
				resultMap.put("error_message", "This device doesn't belong to you!");
				return resultMap;
			}
			else if(!element.getValue().equals(token)){
				resultMap.put("result_code", 2);
				resultMap.put("error_message", "This device doesn't belong to you!");
				return resultMap;
			}
			else {
				String result=HttpsTest.postForm("https://iot-expeed.tech:8083/device/register?product_id="+key+"&sn="+sn);
				Map<String, Object> map = GsonFactory.createGson().fromJson(result, new TypeToken<Map<String, Object>>() {}.getType());
				if((int)(double)map.get("result_code")==0){
					@SuppressWarnings("unchecked")
					Map<String, Object> data= (Map<String, Object>) map.get("data");
					resultMap.put("result_code", 0);
					resultMap.put("qrticket", data.get("qrticket"));
					resultMap.put("device_id", data.get("deviceid"));
					resultMap.put("device_license", data.get("devicelicence"));
					return resultMap;
				}
				else {
					resultMap.put("result_code", 1);
					resultMap.put("error_message", "Data required by the interface hava a problem, please check it!");
					return resultMap;
				}
			}
		}
		
	}
	
	
	@POST
	/*@Consumes("application/json") */
	@Path("/temperatureRecord")
	public JSONObject temperatureRecord(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception{
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		JSONObject params=new JSONObject();
		String deviceId=requestMap.get("deviceId");
		params.put("deviceId", deviceId);
		JSONObject time=new JSONObject();
		time.put("$lte", requestMap.get("engTime"));
		time.put("$gte", requestMap.get("startTime"));
		JSONObject timestamp=new JSONObject();
		timestamp.put("timestamp", time);
		params.put("condition", timestamp);
		
		JSONObject sort=new JSONObject();
		sort.put("timestamp", -1);
		params.put("sort", sort);
		params.put("queryNum", requestMap.get("queryNum"));
		params.put("currentPage", requestMap.get("currentPage"));
		String pro=requestMap.get("param");
		List<String> list=new ArrayList<>();
		list.add(pro);
		params.put("idList", list);
		String result=HttpsTest.postMethod("https://iot-expeed.tech:8083/device/queryDeviceStatus", params);
		JSONObject jsonObject=JSONObject.fromObject(result);
		String resultCode=jsonObject.getString("result_code");
		JSONObject response=new JSONObject();
		if(resultCode!=null && Integer.parseInt(resultCode)==0){
			String count=jsonObject.getString("record_total");
			response.put("result_code", 0);
			response.put("record_total", count);
			JSONArray array=jsonObject.getJSONArray("record");
			int size=array.size();
			List<Map<String, Object>> maps=new ArrayList<>();
			for(int i=0;i<size;i++){
				JSONObject object=array.getJSONObject(i);
				JSONObject status=object.getJSONObject("status");
				long param=Long.parseLong(status.getString(pro));
				int s=0;
				if(param>375){
					s=1;
				}
				Map<String, Object> map=new HashMap<>();
				map.put("timestamp", object.getString("timestamp"));
				map.put("status", s);
				maps.add(map);
			}
			response.put("data", maps);
			return response;
		}
		else{
			response.put("result_code", 1);
			response.put("data", "");
			return response;
		}
	}

}
