package com.nbicc.ita.rest;

import static com.nbicc.ita.constant.ParameterKeys.APNS_ENV;
import static com.nbicc.ita.constant.ParameterKeys.BRAND_ID;
import static com.nbicc.ita.constant.ParameterKeys.H5_PATH;
import static com.nbicc.ita.constant.ParameterKeys.PACKAGE_NAME;
import static com.nbicc.ita.constant.ParameterKeys.PAGE_PATH;
import static com.nbicc.ita.constant.ParameterKeys.PRODUCT_ID;
import static com.nbicc.ita.constant.ParameterKeys.REGISTER_ID;
import static com.nbicc.ita.constant.ParameterKeys.SESSION_USER;
import static com.nbicc.ita.constant.ParameterKeys.SN;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.reflect.TypeToken;
import com.is.gson.GsonFactory;
import com.nbicc.ita.constant.ResponseCode;
import com.nbicc.ita.model.AppInfo;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.service.FunctionService;
import com.nbicc.ita.service.ProductService;
import com.nbicc.ita.util.BusinessHelper;
import com.nbicc.ita.util.HttpsTest;
import com.nbicc.ita.util.LoginRequired;
import com.nbicc.ita.util.ResponseFactory;

import net.sf.json.JSONObject;

@Component("functionHandle")
@Path("function")
public class FunctionHandle {

	@Autowired
	private ProductService productService;

	@Autowired
	private FunctionService functionService;

	@POST
	@Path("/pagePreview")
	@LoginRequired
	public Response pagePreview(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams)
			throws IOException {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		Brand brand = productService.getAuditById(requestMap.get(REGISTER_ID));
		String zippath = H5_PATH + brand.getDeviceKey() + "/" + "detail.zip";
		File file = new File(zippath);
		if (!file.isFile()) {
			zippath = H5_PATH + brand.getDeviceKey() + "/" + "detail.rar";
			File filerar = new File(zippath);
			if (!filerar.isFile()) {
				return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL,
						"Zip file is not exist!");
			}
		}
		String despath = PAGE_PATH + brand.getDeviceKey();
		// 删除原先解压缩目录下的H5文件
		functionService.deleteDirectory(despath);

		// 解压缩文件
		functionService.unZipFiles(zippath, despath);

		// 从已解压缩的文件夹中读取文件名字
		String preview = brand.getPreviewPage();
		if (!preview.startsWith("/")) {
			preview = "/" + preview;
		}
		String mainPath = despath + preview;
		File main = new File(mainPath);
		if (main.isFile()) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, main);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL,
					"Can not find the path!+" + mainPath);
		}

	}

	@POST
	@Path("/getQrcodeByPage")
	@LoginRequired
	public Response getQrcodeByPage(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String productId = requestMap.get(PRODUCT_ID);
		String sn = requestMap.get(SN);
		String key = productService.getAuditById(productId).getDeviceKey();
		String result = HttpsTest.postForm("https://iot-expeed.tech:8083/device/register?product_id="+key+"&sn="+sn);
		Map<String, Object> map = GsonFactory.createGson().fromJson(result, new TypeToken<Map<String, Object>>() {
		}.getType());
		if ((int) (double) map.get("result_code") == 0) {
			@SuppressWarnings("unchecked")
			Map<String, Object> data = (Map<String, Object>) map.get("data");
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, data.get("qrticket"));
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getQrcodeByDeviceKey")
	public Map<String, Object> getQrcodeByDeviceKey(@Context HttpServletRequest request,
			MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String key = requestMap.get("key");
		String sn = requestMap.get("sn");
		String result = HttpsTest.postForm("https://iot-expeed.tech:8083/device/register?product_id="+key+"&sn="+sn);

		Map<String, Object> resultMap = new HashMap<>();

		Map<String, Object> map = GsonFactory.createGson().fromJson(result, new TypeToken<Map<String, Object>>() {
		}.getType());
		if ((int) (double) map.get("result_code") == 0) {
			Map<String, Object> data = (Map<String, Object>) map.get("data");
			resultMap.put("result_code", 0);
			resultMap.put("qrticket", data.get("qrticket"));
			resultMap.put("device_id", data.get("deviceid"));
			resultMap.put("device_license", data.get("devicelicence"));
			return resultMap;
		}
		else{
			return map;
		}
	}

	/**
	 * @author lish
	 * @date 2016年9月8日 类说明 :app开发配置
	 */
	@POST
	@Path("/appDevelopSetting")
	public Response appDevelopSetting(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		if (null == user) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "no login!");
		}
		boolean state = productService.appDevelopSetting(requestMap.get(PACKAGE_NAME),
				requestMap.get("mobKeyI"), requestMap.get("accessIdI"), requestMap.get("secretKeyI"), 
				requestMap.get("mobKeyA"), requestMap.get("accessIdA"), requestMap.get("secretKeyA"), 
				user.getUserId(), requestMap.get(APNS_ENV),requestMap.get(BRAND_ID));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	
	/**
	 * @author lish
	 * @date 2016年9月8日 类说明 :编辑app开发配置
	 */
	@POST
	@Path("/editAppInfo")
	public Response editAppInfo(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		if (null == user) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "no login!");
		}
		boolean state = productService.editAppInfo(requestMap.get("appInfoId"),requestMap.get(PACKAGE_NAME),
				requestMap.get("mobKeyI"), requestMap.get("accessIdI"), requestMap.get("secretKeyI"), 
				requestMap.get("mobKeyA"), requestMap.get("accessIdA"), requestMap.get("secretKeyA"), 
				user.getUserId(), requestMap.get(APNS_ENV));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	
	@POST
	@Path("/deleteAppInfo")
	public Response deleteAppInfo(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=productService.deleteAppInfo(requestMap.get("appInfoId"));
		if(state){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@Path("/searchAppInfo")
	public Response searchAppInfo(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<AppInfo> list=productService.getAppInfoByBrand(requestMap.get(BRAND_ID));		
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, list);
		/*if(list.size()>0){
			JSONObject jsonObject=new JSONObject();
			for(AppInfo info:list){
				if(info.getAppNameIos()!=null){
					jsonObject.put("ios", info);
				}
				else{
					jsonObject.put("android", info);
				}
			}
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, jsonObject);
		}
		else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, list);
		}*/
	}
	
	
	/**
	 * @author lish
	 * @date 2017年1月11日 类说明 :开发人员使用，获取设备上送数据
	 */
	@POST
	@Path("/getHistoryData")
	public String getHistoryData(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		JSONObject params=new JSONObject();
		String deviceId=requestMap.get("deviceId");
		params.put("deviceId", deviceId);
		JSONObject time=new JSONObject();
		time.put("$lte", new Date().getTime());
		JSONObject timestamp=new JSONObject();
		timestamp.put("timestamp", time);
		params.put("condition", timestamp);
		
		JSONObject sort=new JSONObject();
		sort.put("timestamp", -1);
		params.put("sort", sort);
		params.put("queryNum", 50);
		params.put("currentPage", 1);
		String pro=requestMap.get("param");
		List<String> list=new ArrayList<>();
		list.add(pro);
		params.put("idList", list);
		String result=HttpsTest.postMethod("https://iot-expeed.tech:8083/device/queryDeviceStatus", params);
		return result;
	}

}
