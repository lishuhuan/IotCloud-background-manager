package com.nbicc.ita.rest;

import static com.nbicc.ita.constant.ParameterKeys.BLUETOOTH;
import static com.nbicc.ita.constant.ParameterKeys.BLUETOOTH_CODE;
import static com.nbicc.ita.constant.ParameterKeys.BRAND_ID;
import static com.nbicc.ita.constant.ParameterKeys.CODE_TYPE;
import static com.nbicc.ita.constant.ParameterKeys.COMMUNICATION_PROTOCOL;
import static com.nbicc.ita.constant.ParameterKeys.CONNECTION_TYPE;
import static com.nbicc.ita.constant.ParameterKeys.CONTEXT;
import static com.nbicc.ita.constant.ParameterKeys.DATA_TYPE;
import static com.nbicc.ita.constant.ParameterKeys.Description;
import static com.nbicc.ita.constant.ParameterKeys.ID;
import static com.nbicc.ita.constant.ParameterKeys.IDENTIFICATION;
import static com.nbicc.ita.constant.ParameterKeys.IMG_PATH;
import static com.nbicc.ita.constant.ParameterKeys.INCREMENT;
import static com.nbicc.ita.constant.ParameterKeys.IOT_TYPE;
import static com.nbicc.ita.constant.ParameterKeys.ITEM_CONTENT;
import static com.nbicc.ita.constant.ParameterKeys.ITEM_ID;
import static com.nbicc.ita.constant.ParameterKeys.LENGTH;
import static com.nbicc.ita.constant.ParameterKeys.MAC;
import static com.nbicc.ita.constant.ParameterKeys.NET_CONFIG;
import static com.nbicc.ita.constant.ParameterKeys.OP_TYPE;
import static com.nbicc.ita.constant.ParameterKeys.PRODUCT_BRAND;
import static com.nbicc.ita.constant.ParameterKeys.PRODUCT_ID;
import static com.nbicc.ita.constant.ParameterKeys.PRODUCT_MODEL;
import static com.nbicc.ita.constant.ParameterKeys.PRODUCT_NAME;
import static com.nbicc.ita.constant.ParameterKeys.PROTOCOL_ANALYSIS;
import static com.nbicc.ita.constant.ParameterKeys.REGISTER_ID;
import static com.nbicc.ita.constant.ParameterKeys.RESOLVING;
import static com.nbicc.ita.constant.ParameterKeys.SESSION_USER;
import static com.nbicc.ita.constant.ParameterKeys.STATUS;
import static com.nbicc.ita.constant.ParameterKeys.TITLE;
import static com.nbicc.ita.constant.ParameterKeys.TYPE_ID;
import static com.nbicc.ita.constant.ParameterKeys.USER_GUIDE_URL;
import static com.nbicc.ita.constant.ParameterKeys.USER_ID;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.is.gson.GsonFactory;
import com.nbicc.ita.constant.ResponseCode;
import com.nbicc.ita.model.AbilityTemplate;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.Device;
import com.nbicc.ita.model.IotCloudType;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.model.MessageProtocolItem;
import com.nbicc.ita.service.EquipmentService;
import com.nbicc.ita.service.ProductService;
import com.nbicc.ita.util.BusinessHelper;
import com.nbicc.ita.util.HttpsTest;
import com.nbicc.ita.util.LoginRequired;
import com.nbicc.ita.util.ResponseFactory;
import com.nbicc.ita.util.ResponseText;

/**
 * @author lishuhuan
 * @date 2016年7月12日 产品的相关类
 */
@Component("productAudit")
@Path("product")
public class ProductAudit {

	/**
	 * 
	 */

	@Autowired
	private ProductService productService;
	
	@Autowired
	private EquipmentService equipmentService;
	

	@POST
	@Path("/productAudit")
	@LoginRequired
	public Response register(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		// IotCloudUser user=(IotCloudUser)
		// request.getSession().getAttribute(SESSION_USER);
		// String userid=String.valueOf(user.getUserId());
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String id = str.replace("-", "");
		Map<String, String> map = productService.audit(id, requestMap.get(USER_ID), requestMap.get(TYPE_ID),
				requestMap.get(PRODUCT_NAME), requestMap.get(PRODUCT_MODEL), requestMap.get(PRODUCT_BRAND),
				requestMap.get(IDENTIFICATION), requestMap.get(COMMUNICATION_PROTOCOL),
				requestMap.get(CONNECTION_TYPE),requestMap.get(CODE_TYPE),requestMap.get(PROTOCOL_ANALYSIS),  requestMap.get(CONTEXT),
				requestMap.get(IMG_PATH),requestMap.get(IOT_TYPE),requestMap.get(USER_GUIDE_URL),
				requestMap.get(BLUETOOTH),requestMap.get(BLUETOOTH_CODE),requestMap.get("isGateway")
				,requestMap.get("appSupport"),requestMap.get("communicationMode"));
		/* request.getSession().setAttribute("register_id", id); */
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, map);

	}
	
	@GET
	@LoginRequired
	@Path("/bluetoothAES")
	public Response bluetoothAES() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String id = str.replace("-", "");
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, id);
	}
	
	@POST
	@LoginRequired
	@Path("/bluetoothLicense")
	public Map<String, Object> bluetoothLicense(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String mac=requestMap.get(MAC);
		String id=requestMap.get(BRAND_ID);
		Brand brand=productService.getAuditById(id);
		
		String result=HttpsTest.postForm("https://iot-expeed.tech:8083/device/register?product_id="+brand.getDeviceKey()+"&sn="+mac);
		Map<String, Object> map = GsonFactory.createGson().fromJson(result, new TypeToken<Map<String, Object>>() {}.getType());
		return map;
	}


	@POST
	@LoginRequired
	@Path("/setRuleStatus")
	public Response openRuleStatus(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		if(null==user){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "no login!");
		}
		boolean state = productService.openRuleStatus(requestMap.get(ID),requestMap.get(STATUS),user.getUserId());
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@LoginRequired
	@Path("/getRuleStatus")
	public Response getRuleStatus(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		int state = productService.getRuleStatus(requestMap.get(ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, String.valueOf(state));
	}

	@POST
	@LoginRequired
	@Path("/updateProduct")
	public Response updateProduct(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		String userid = String.valueOf(user.getUserId());
		boolean state = productService.update(requestMap.get(ID), userid, requestMap.get(TYPE_ID),
				requestMap.get(PRODUCT_NAME), requestMap.get(PRODUCT_MODEL), requestMap.get(PRODUCT_BRAND),
				requestMap.get(IDENTIFICATION), requestMap.get(PROTOCOL_ANALYSIS),
				requestMap.get(COMMUNICATION_PROTOCOL), requestMap.get(CONTEXT), requestMap.get(CONNECTION_TYPE),
				requestMap.get(CODE_TYPE), requestMap.get(NET_CONFIG), requestMap.get(IMG_PATH));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@LoginRequired
	@Path("/getAuditList")
	public Response getAuditList(@Context HttpServletRequest request) {
		List<Brand> auditlist = productService.getAuditList();
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, auditlist);
	}

	@GET
	@LoginRequired
	@Path("/getTypeList")
	public Response getTypeList(@Context HttpServletRequest request) {
		List<IotCloudType> typelist = productService.getTypeList();
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, typelist);
	}

	@POST
	@LoginRequired
	@Path("/getTypeByParent")
	public Response getTypeByParent(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<IotCloudType> typelist = productService.getTypeByParent(requestMap.get(TYPE_ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, typelist);
	}

	@POST
	@Path("/throughApply")
	public Response throughApply(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		String userid = String.valueOf(user.getUserId());
		boolean state = productService.throughApply(requestMap.get(REGISTER_ID), userid);
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@Path("/getAuditByState")
	public Response getAuditByState(@Context HttpServletRequest request) {
		List<Brand> audits = productService.getAuditByState();
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, audits);
	}

	@POST
	@Path("/rejectApply")
	public Response rejectApply(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state = productService.rejectApply(requestMap.get(REGISTER_ID));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@Path("/getAuditReject")
	public Response getAuditReject(@Context HttpServletRequest request) {
		List<Brand> audits = productService.getAuditReject();
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, audits);
	}

	@POST
	@LoginRequired
	@Path("/getDeviceList")
	public Response getDeviceList(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<Device> devices = productService.getDeviceList(requestMap.get(REGISTER_ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, devices);
	}

	@POST
	@LoginRequired
	@Path("/getTemplateByType")
	public Response getTemplateByType(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<AbilityTemplate> list = productService.getTemplateById(requestMap.get(TYPE_ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, list);
	}


	@POST
	@LoginRequired
	@Path("/getH5Path")
	public Response getH5Path(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams)
			throws IOException {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		Brand brand = productService.getAuditById(requestMap.get(REGISTER_ID));
		Map<String, Object> map = new HashMap<>();
		int ruleStatus = brand.getRuleStatus();
		map.put("ruleStatus", ruleStatus);
		String path = brand.getH5Path();
		File file = new File(path);
		File[] all = file.listFiles();
		if (all!=null) {
			for (File f : all) {
				String fpath = f.getAbsolutePath();

				if (fpath.endsWith("zip") || fpath.endsWith("rar")) {
					map.put("h5zip", fpath);
				}

				if (fpath.endsWith("imgs")) {
					File fileimg = new File(fpath);
					if(fileimg.isDirectory()){
						File[] imgf=fileimg.listFiles();
						String[] imgs=new String[imgf.length];
						for(int i=0;i<imgf.length;i++){
							imgs[i]=imgf[i].getAbsolutePath();
						}
						map.put("h5img", imgs);
					}
				}
			}
		}
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, map);
	}
	
	@POST
	@LoginRequired
	@Path("/getProductByUser")
	public Response getProductByUser(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<Brand> audits=equipmentService.getAuditByUser(requestMap.get(USER_ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS,audits);
	}
	
	@POST
	@LoginRequired
	@Path("/deleteProduct")
	public Response deleteProduct(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=equipmentService.deleteProduct(requestMap.get(PRODUCT_ID));
		if(state){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	/*@POST
	@Path("/addAttr")
	public Response addAttr(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=equipmentService.addAttr(requestMap.get(REGISTER_ID), requestMap.get(TITLE), requestMap.get(Description), requestMap.get(OP_TYPE), requestMap.get(DATA_TYPE), requestMap.get(ITEM_CONTENT),requestMap.get(LENGTH));
		if(state){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
		
	}*/
	
	@POST
	@LoginRequired
	@Path("/editAttr")
	public Response editAttr(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=equipmentService.editAttr(requestMap.get(ITEM_ID), requestMap.get(TITLE), requestMap.get(Description), requestMap.get(OP_TYPE), requestMap.get(DATA_TYPE), requestMap.get(ITEM_CONTENT),requestMap.get(LENGTH), requestMap.get(RESOLVING), requestMap.get(INCREMENT));
		if(state){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@LoginRequired
	@Path("/getAuditById")
	public Response getAuditById(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		Brand brand=equipmentService.getAuitByRegisterId(requestMap.get(PRODUCT_ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS,brand);
	}
	
	@POST
	@LoginRequired
	@Path("/getItemById")
	public Response getItemById(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<MessageProtocolItem> items=equipmentService.getItemById(requestMap.get(REGISTER_ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS,items);
		
	}
	
	@POST
	@LoginRequired
	@Path("/setGatewayParts")
	public Response setGatewayParts(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		boolean state=equipmentService.setGatewayParts(requestMap.get("accessoryId"), requestMap.get("protocol"), requestMap.get("gatewayIds"),user.getUserId());
		if(state){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@LoginRequired
	@Path("/getGatewayList")
	public Response getGatewayList(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		List<Brand> brands=equipmentService.getGatewayList(user.getUserId());
		String protocol=equipmentService.getPartsProtocol(requestMap.get(BRAND_ID));
		String gatewatids=equipmentService.getGatewayIds(requestMap.get(BRAND_ID));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("gateways", brands);
		jsonObject.put("protocol", protocol);
		jsonObject.put("gatewayIds", gatewatids);
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS,jsonObject);
		
	}
	
	@POST
	@LoginRequired
	@Path("/productReleaseOperation")
	public Response productReleaseOperation(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=productService.releaseOperation(requestMap.get("pid"),requestMap.get("state"));
		if(state){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@LoginRequired
	@Path("/applyProductRelease")
	public Response applyProductRelease(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=productService.applyRelease(requestMap.get("pid"));
		if(state){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@LoginRequired
	@Path("/getProductByReleaseState")
	public Response getProductByReleaseState(@Context HttpServletRequest request,MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<Brand> list=productService.getProductByReleaseState(requestMap.get("state"));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, list);
	}
	
	@POST
	@Path("/test")
	public Response temperatureRecord(MultivaluedMap<String, String> formParams){
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		productService.dataTransfer();
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
	}

}
