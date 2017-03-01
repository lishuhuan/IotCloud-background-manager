package com.nbicc.ita.rest;

import static com.nbicc.ita.constant.ParameterKeys.COMPARE_VALUE;
import static com.nbicc.ita.constant.ParameterKeys.COMPARE_VALUE_TWO;
import static com.nbicc.ita.constant.ParameterKeys.DEVICE_ID;
import static com.nbicc.ita.constant.ParameterKeys.DEVICE_KEY;
import static com.nbicc.ita.constant.ParameterKeys.DEVICE_SN;
import static com.nbicc.ita.constant.ParameterKeys.ID;
import static com.nbicc.ita.constant.ParameterKeys.IOT_PATH;
import static com.nbicc.ita.constant.ParameterKeys.NUMBER;
import static com.nbicc.ita.constant.ParameterKeys.OPERATOR;
import static com.nbicc.ita.constant.ParameterKeys.OPERATOR_TWO;
import static com.nbicc.ita.constant.ParameterKeys.PROPERTY_CATAGORY;
import static com.nbicc.ita.constant.ParameterKeys.PROPERTY_CODE;
import static com.nbicc.ita.constant.ParameterKeys.PROPERTY_ID;
import static com.nbicc.ita.constant.ParameterKeys.PROPERTY_ID_TWO;
import static com.nbicc.ita.constant.ParameterKeys.PROPERTY_NAME;
import static com.nbicc.ita.constant.ParameterKeys.PROPERTY_RANGE;
import static com.nbicc.ita.constant.ParameterKeys.PROPERTY_UNIT;
import static com.nbicc.ita.constant.ParameterKeys.PUSH_CONTEXT;
import static com.nbicc.ita.constant.ParameterKeys.REGISTER_ID;
import static com.nbicc.ita.constant.ParameterKeys.RULE_NAME;
import static com.nbicc.ita.constant.ParameterKeys.RULE_TYPE;
import static com.nbicc.ita.constant.ParameterKeys.RULE_TYPE_TWO;
import static com.nbicc.ita.constant.ParameterKeys.SESSION_USER;
import static com.nbicc.ita.constant.ParameterKeys.TRIGGER_ID;
import static com.nbicc.ita.constant.ParameterKeys.WORD_PATH;
import static com.nbicc.ita.constant.ParameterKeys.WR_FLAG;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.internal.LinkedTreeMap;
import com.nbicc.ita.constant.ResponseCode;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.DeviceProperty;
import com.nbicc.ita.model.DevicePropertyStandard;
import com.nbicc.ita.model.DevicePropertyTrigger;
import com.nbicc.ita.model.DevicePropertyTriggerRelationship;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.model.SerialPortData;
import com.nbicc.ita.rest.service.RestDeviceService;
import com.nbicc.ita.service.EquipmentService;
import com.nbicc.ita.service.ProductService;
import com.nbicc.ita.service.ProtocolService;
import com.nbicc.ita.util.BusinessHelper;
import com.nbicc.ita.util.LoginRequired;
import com.nbicc.ita.util.PoiXwpf;
import com.nbicc.ita.util.ResponseFactory;

@Component("protocolHandle")
@Path("protocol")
public class ProtocolHandle {

	@Autowired
	private ProtocolService protocolService;

	@Autowired
	private RestDeviceService restDeviceService;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private ProductService productService;

	@POST
	@Path("/securityProtocol")
	public String securityProtocol(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams)
			throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String deviceKey = requestMap.get(DEVICE_KEY);
		String deviceSn = requestMap.get(DEVICE_SN);
		com.nbicc.ita.response.RestResponse response = restDeviceService.register(deviceKey, deviceSn, "000000000000");
		if (response.getResultCode() == 0) {
			LinkedTreeMap linkedTreeMap = (LinkedTreeMap) response.getResultObject();
			String deviceId = linkedTreeMap.get("device_id").toString();
			String result = protocolService.RSAcode(deviceId, deviceKey);

			return result;

		} else {
			return "exist false";
		}

	}

	@POST
	@Path("/createLicense")
	public String createLicense(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams)
			throws Exception {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String deviceSn = requestMap.get(DEVICE_SN);
		String productId = requestMap.get(REGISTER_ID);
		Brand brand = equipmentService.getAuitById(productId);
		String deviceKey = brand.getDeviceKey();
		com.nbicc.ita.response.RestResponse response = restDeviceService.register(deviceKey, deviceSn, "000000000000");
		if (response.getResultCode() == 0) {
			LinkedTreeMap linkedTreeMap = (LinkedTreeMap) response.getResultObject();
			String deviceId = linkedTreeMap.get("device_id").toString();
			String result = protocolService.RSAcode(deviceId, deviceKey);

			return result;

		} else {
			return "exist false";
		}
	}

	@POST
	@Path("/getRsaKey")
	public String getRsaKey(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String deviceId = requestMap.get(DEVICE_ID);
		String key = protocolService.getRsaKey(deviceId);
		return key;
	}

	@POST
	@Path("/testCode")
	@Consumes(MediaType.APPLICATION_JSON)
	public String testCode(JSONObject query) {

		return "123";

	}
	
	@POST
	@LoginRequired
	@Path("/propertyNameCheck")
	public Response propertyNameCheck(@Context HttpServletRequest request,
			MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		DeviceProperty property=protocolService.getPropertyByName(requestMap.get("brandId"),requestMap.get(PROPERTY_NAME));
		List<String> list=protocolService.getAllStandardName();
		if((list!=null && list.contains(requestMap.get(PROPERTY_NAME))) || property!=null){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "The parameter already exists!");
		}
		else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		}
	}

	@POST
	@LoginRequired
	@Path("/productParamUpload")
	public Response productPropertyUpload(@Context HttpServletRequest request,
			MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state = protocolService.productProperty(requestMap.get(REGISTER_ID), requestMap.get(PROPERTY_CODE),
				requestMap.get(PROPERTY_NAME), requestMap.get(PROPERTY_UNIT), requestMap.get(PROPERTY_CATAGORY),
				requestMap.get(PROPERTY_RANGE), requestMap.get(WR_FLAG),requestMap.get(NUMBER));
		
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@LoginRequired
	@Path("/propertyRule")
	public Response propertyRule(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		String result = protocolService.propertyRule(requestMap.get(REGISTER_ID), requestMap.get(RULE_NAME),
				requestMap.get(RULE_TYPE), requestMap.get(PROPERTY_ID), requestMap.get(OPERATOR),
				requestMap.get(COMPARE_VALUE), requestMap.get(RULE_TYPE_TWO), requestMap.get(PROPERTY_ID_TWO),
				requestMap.get(OPERATOR_TWO), requestMap.get(COMPARE_VALUE_TWO), requestMap.get(PUSH_CONTEXT));
		if (!"false".equals(result)) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, result);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "User is not exist!");
		}
	}

	@POST
	@LoginRequired
	@Path("/getPropertyRule")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPropertyRule(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		Brand brand = productService.getAuditById(requestMap.get(REGISTER_ID));
		if (null != brand) {
			List<DevicePropertyTrigger> list = protocolService.getTriggerByBrand(brand.getId());
			for (DevicePropertyTrigger trigger : list) {
				Set<DevicePropertyTriggerRelationship> relationships = new HashSet<>();
				Iterator<DevicePropertyTriggerRelationship> it = trigger.getRelationships().iterator();
				while (it.hasNext()) {
					DevicePropertyTriggerRelationship old = it.next();
					old.setTrigger(null);
					relationships.add(old);
				}
				trigger.setRelationships(relationships);
			}
			/*
			 * DevicePropertyTrigger trigger = list.get(0);
			 * Set<DevicePropertyTriggerRelationship> relationships =
			 * trigger.getRelationships();
			 * Iterator<DevicePropertyTriggerRelationship> iterator =
			 * relationships.iterator(); while (iterator.hasNext()) {
			 * DevicePropertyTriggerRelationship relationship=iterator.next();
			 * DevicePropertyTrigger trigger2=relationship.getTrigger();
			 * System.out.println(relationship); }
			 */
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, list);
		} else
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);

	}
	
	@POST
	@LoginRequired
	@Path("/getPropertyRuleById")
	public Response getPropertyRuleById(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		DevicePropertyTrigger trigger=protocolService.getTriggerById(requestMap.get(ID));
		Set<DevicePropertyTriggerRelationship> relationships = new HashSet<>();
		Iterator<DevicePropertyTriggerRelationship> it = trigger.getRelationships().iterator();
		while (it.hasNext()) {
			DevicePropertyTriggerRelationship old = it.next();
			old.setTrigger(null);
			relationships.add(old);
		}
		trigger.setRelationships(relationships);
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, trigger);
	}

	@POST
	@LoginRequired
	@Path("/getPropertyByProductId")
	public Response getPropertyByProductId(@Context HttpServletRequest request,
			MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<DeviceProperty> list = protocolService.getPropertyByProductId(requestMap.get(REGISTER_ID));
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, list);
	}

	@POST
	@LoginRequired
	@Path("/updatePropertyById")
	public Response updatePropertyById(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		IotCloudUser user = (IotCloudUser) request.getSession().getAttribute(SESSION_USER);
		if (user == null) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "please log on!");
		}
		boolean state = protocolService.updatePropertyById(user.getUserId(), requestMap.get(ID),
				requestMap.get(PROPERTY_CODE), requestMap.get(PROPERTY_NAME), requestMap.get(PROPERTY_UNIT),
				requestMap.get(PROPERTY_CATAGORY), requestMap.get(PROPERTY_RANGE), requestMap.get(WR_FLAG));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@LoginRequired
	@Path("/deletePropertyById")
	public Response deletePropertyById(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state = protocolService.deleteProperty(requestMap.get(PROPERTY_ID));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@LoginRequired
	@Path("/deletePropertyBatch")
	public Response deletePropertyBatch(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state = protocolService.deletePropertyBatch(requestMap.get("name"),requestMap.get("brandId"));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@POST
	@LoginRequired
	@Path("/deleteTriggerById")
	public Response deleteTriggerById(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state = protocolService.deleteTrigger(requestMap.get(TRIGGER_ID));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

	@GET
	@LoginRequired
	@Path("/serialPortDownload/{id}")
	public Response serialPortDownload(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("id") String productId) throws Exception {
		Brand brand = productService.getAuditById(productId);
		List<SerialPortData> list = protocolService.getSerialPort(brand.getDeviceKey());
		PoiXwpf poiXwpf = new PoiXwpf();
		boolean state = false;
		if (brand.getCommunicationProtocol() == 2) {
			state = poiXwpf.wordOperationBlueTooth(IOT_PATH + "lanya.docx", brand.getProductName(), list, brand.getDeviceKey());
		} else {
			state = poiXwpf.wordOperation(IOT_PATH + "temp.docx", brand.getProductName(), list, brand.getDeviceKey());
		}
		if (state) {
			boolean down = poiXwpf.downloadLocal(response,brand);
			if (down) {
				File file = new File(WORD_PATH + brand.getDeviceKey() + ".docx");
				if (file.isFile()) {
					file.delete();
				}
				return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
			} else {
				return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "Download mistake!");
			}
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL,
					"It has a error when generate the words!");
		}
	}

	@POST
	@LoginRequired
	@Path("/getPropertyById")
	public Response getPropertyById(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		DeviceProperty property = protocolService.getPropertyById(requestMap.get(ID));
		if(null==property){
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, "null param!");
		}
		else{
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, property);
		}
	}
	
	@POST
	@LoginRequired
	@Path("/updateRuleById")
	public Response updateRuleById(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=protocolService.updateRuleById(requestMap.get(ID),requestMap.get(RULE_NAME),
				requestMap.get(RULE_TYPE), requestMap.get(PROPERTY_ID), requestMap.get(OPERATOR),
				requestMap.get(COMPARE_VALUE), requestMap.get(RULE_TYPE_TWO), requestMap.get(PROPERTY_ID_TWO),
				requestMap.get(OPERATOR_TWO), requestMap.get(COMPARE_VALUE_TWO), requestMap.get(PUSH_CONTEXT));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}
	
	@POST
	@LoginRequired
	@Path("/getStandardProperty")
	public Response getStandardProperty(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		List<DevicePropertyStandard> allStandard=protocolService.getDevicePropertyStandard();
		List<String> name=protocolService.getPropertyWhere(requestMap.get("brandId"));
		for(DevicePropertyStandard standard:allStandard){
			if(name!=null && name.contains(standard.getEnName())){
				standard.setDelFlag(0);
			}
		}
		return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, allStandard);
	}
	

	@POST
	@LoginRequired
	@Path("/setStandardProperty")
	public Response setStandardProperty(@Context HttpServletRequest request, MultivaluedMap<String, String> formParams) {
		Map<String, String> requestMap = BusinessHelper.changeMap(formParams);
		boolean state=protocolService.setStandardProperty(requestMap.get("brandId"),requestMap.get("name"),requestMap.get("type"));
		if (state) {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.SUCCESS, null);
		} else {
			return ResponseFactory.response(Response.Status.OK, ResponseCode.REQUEST_FAIL, null);
		}
	}

}
