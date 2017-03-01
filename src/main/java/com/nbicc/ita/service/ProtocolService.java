package com.nbicc.ita.service;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nbicc.ita.background.dao.BackgroundDao;
import com.nbicc.ita.background.dao.CloudDao;
import com.nbicc.ita.background.dao.RedisRepository;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.DeviceProperty;
import com.nbicc.ita.model.DevicePropertyStandard;
import com.nbicc.ita.model.DevicePropertyTrigger;
import com.nbicc.ita.model.DevicePropertyTriggerRelationship;
import com.nbicc.ita.model.SerialPortData;
import com.nbicc.ita.rest.service.RestDeviceService;
import com.nbicc.ita.util.RSAUtils;

@Transactional
@Component("protocolService")
public class ProtocolService {

	@Autowired
	private CloudDao cloudDao;

	@Autowired
	private RestDeviceService restDeviceService;

	@Autowired
	private RedisRepository redisRepository;

	@Autowired
	private BackgroundDao backgroundDao;


	@SuppressWarnings("restriction")
	public String RSAcode(String deviceId, String deviceKey) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = RSAUtils.getKeys();
		// 生成公钥和私钥
		RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
		RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");

		BigInteger bif = publicKey.getModulus();

		String nString = bif.toString(16);

		String pubk = new String(new Base64().encode(publicKey.getEncoded())); // 私钥字符串base64编码
		String prik = new String(new Base64().encode(privateKey.getEncoded())); // 公钥字符串base64编码
		/*byte[] a=new Base64().decode(prik.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(a);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey ppp = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);*/

		String version = "0100";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("version", version);
		jsonObj.put("device_key", deviceKey);
		jsonObj.put("device_id", deviceId);
		jsonObj.put("public_key", nString);
		String jsonstr = jsonObj.toString();

		/* String jsonstr = GsonFactory.createGson().toJson(jsonObj); */

		byte[] encoded = new Base64().encode(jsonstr.getBytes());
		String encode = new String(encoded);

		com.nbicc.ita.response.RestResponse response=restDeviceService.uploadSafeKey(deviceId, pubk, prik);
		int state=response.getResultCode();

		return encode;
	}

	public String getRsaKey(String id) {
		return redisRepository.get(id);
	}
	
	public DeviceProperty getPropertyByName(String brandId,String name){
		return backgroundDao.getPropertyByName(brandId,name);
	}
	
	public Boolean productProperty(String productId,String code,String name,String unit,
			String catagory,String range,String wrFlag,String number){
		Brand brand=backgroundDao.getAuditById(productId);
		for(int i=0;i<Integer.parseInt(number);i++){
			DeviceProperty deviceProperty=new DeviceProperty();
			UUID uuid = UUID.randomUUID();
			String str = uuid.toString();
			String id = str.replace("-", "");
			deviceProperty.setId(id);
			if(null!=brand){
				deviceProperty.setBrand(brand);
			}
			deviceProperty.setChName(code);
			if(number.equals("1")){
				deviceProperty.setEnName(name);
			}
			else{
				int trueNumber=Integer.parseInt(number)-1;
				int length=String.valueOf(trueNumber).length();
				int num=i;
				String seri=String.format("%"+length+"s", num).replace(' ', '0');
				String enname=name+seri;
				deviceProperty.setEnName(enname);
			}
			deviceProperty.setWrFlag(Integer.parseInt(wrFlag));
			deviceProperty.setIsStandardProperty(1);
			deviceProperty.setFormatType(Integer.parseInt(catagory));
			deviceProperty.setFormatRule(range);
			deviceProperty.setPropertyUnit(unit);
			deviceProperty.setCreatedAt(new Date());
			deviceProperty.setCreatedBy(brand.getUser().getUserId());
			cloudDao.add(deviceProperty);
			if(brand.getProtocolAnalysis()==1 || brand.getCommunicationProtocol()==2){
				int count=brand.getSdCount();
				count=count+i+1;
				seriesPortData(brand, code, name, unit, catagory, range, Integer.parseInt(wrFlag),id,count);
			}
		}
		
		if(brand.getProtocolAnalysis()==1 || brand.getCommunicationProtocol()==2){
			brand.setSdCount(brand.getSdCount()+Integer.parseInt(number));
			cloudDao.update(brand);
		}
		
		return true;
		
		
	}
	
	public void seriesPortData(Brand brand,String code,String name,String unit,String catagory,String range,int wrFlag,String dpId,int count){
		SerialPortData data=new SerialPortData();
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String id = str.replace("-", "");
		data.setId(id);
		data.setDeviceKey(brand.getDeviceKey());
		String tagName=String.format("%4s", Integer.toHexString(count)).replace(' ', '0');
		data.setTagName(tagName.toUpperCase());
		if(null!=catagory){
			data.setFormatType(catagory);
		}
		data.setChName(code);
		data.setEnName(name);
		data.setWrFlag(wrFlag);
		data.setPropertyUnit(unit);
		data.setDpId(dpId);
		data.setFormatRule(range);
		data.setCreatedAt(new Date());
		data.setCreatedBy(brand.getUser().getUserId());
		cloudDao.add(data);
	}
	
	public String propertyRule(String productId,String name,String type,
			String propertyId,String operator,String value,String typeTwo,
			String propertyIdTwo,String operatorTwo,String valueTwo,String context){
		Brand brand=backgroundDao.getAuditById(productId);
		DevicePropertyTrigger trigger=new DevicePropertyTrigger();
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String id = str.replace("-", "");
		trigger.setId(id);
		if(null!=brand){
			trigger.setBrand(brand);
		}
		trigger.setTriggerName(name);
		trigger.setPushRule(context);
		trigger.setCreatedAt(new Date());
		trigger.setCreatedBy(brand.getUser().getUserId());
		cloudDao.add(trigger);
		String userid=null;
		if(brand.getUser()!=null){
			userid=brand.getUser().getUserId();
		}
		else{
			return "false";
		}
		addRule(trigger, type, propertyId, operator, value, userid);
		if(null!=typeTwo && !"".equals(typeTwo)){
			addRule(trigger, typeTwo, propertyIdTwo, operatorTwo, valueTwo, userid);
		}
		return id;
	
	}
	
	public Boolean addRule(DevicePropertyTrigger trigger,String type,String propertyId,String operator,String value,String userid){
		DevicePropertyTriggerRelationship rule=new DevicePropertyTriggerRelationship();
		String ruleId=UUID.randomUUID().toString().replace("-", "");
		rule.setId(ruleId);
		rule.setTrigger(trigger);
		DeviceProperty property=backgroundDao.getPropertyById(propertyId);
		String triggers=property.getTriggerIds();
		if(null==triggers || "".equals(triggers)){
			triggers=trigger.getId();
		}
		else{
			triggers+=","+trigger.getId();
		}
		property.setTriggerIds(triggers);
		cloudDao.update(property);
		rule.setProperty(property);
		rule.setRuleType(Integer.parseInt(type));
		if(null!=operator){
			rule.setOperator(Integer.parseInt(operator));
		}
		rule.setCompareValue(value);
		rule.setCreatedAt(new Date());
		rule.setCreatedBy(userid);
		cloudDao.add(rule);
		return true;
	}
	
	public List<DeviceProperty> getPropertyByProductId(String productId){
		return backgroundDao.getPropertyByProductId(productId);
	}
	
	public Boolean updatePropertyById(String userid,String id,String code,String name,String unit,String catagory,String range,String wrFlag){
		DeviceProperty property=backgroundDao.getPropertyById(id);
		property.setEnName(name);
		property.setChName(code);
		if(wrFlag!=null){
			property.setWrFlag(Integer.parseInt(wrFlag));
		}
		if(catagory!=null){
			property.setFormatType(Integer.parseInt(catagory));
		}
		property.setFormatRule(range);
		property.setPropertyUnit(unit);
		property.setUpdatedAt(new Date());
		property.setUpdatedBy(userid);
		cloudDao.update(property);
		
		if(property.getBrand().getProtocolAnalysis()==1 || property.getBrand().getCommunicationProtocol()==2){
			SerialPortData data=backgroundDao.getSerialByDpId(property.getId());
			if(null!=data){
				data.setChName(code);
				data.setEnName(name);
				data.setPropertyUnit(unit);
				if(wrFlag!=null){
					data.setWrFlag(Integer.parseInt(wrFlag));
				}
				data.setFormatType(catagory);
				data.setFormatRule(range);
				data.setUpdatedAt(new Date());
				data.setUpdatedBy(userid);
				cloudDao.update(data);
			}
			
		}
		return true;
	}
	
	public List<SerialPortData> getSerialPort(String id){
		return backgroundDao.getSerialPort(id);
	}

	public Boolean deleteProperty(String id){
		DeviceProperty property=backgroundDao.getPropertyById(id);
		cloudDao.delete(property);
		return true;
	}
	
	public Boolean deletePropertyBatch(String name,String brandId){
		String sql="delete from device_property where brand_id='"+brandId+"' and en_name regexp  '"+name+"[0-9]+'";
		cloudDao.deleteBySql(sql);
		return true;
	}
	
	public Boolean deleteTrigger(String id){
		DevicePropertyTrigger trigger=backgroundDao.getTrigerById(id);
		cloudDao.delete(trigger);
		return true;
	}
	
	public List<DevicePropertyTrigger> getTriggerByBrand(String id){
		return backgroundDao.getTriggerByBrand(id);
	}
	
	public DevicePropertyTrigger getTriggerById(String id){
		return backgroundDao.getTrigerById(id);
	}
	
	
	public DeviceProperty getPropertyById(String id){
		return backgroundDao.getPropertyById(id);
	}
	
	public Boolean updateRuleById(String id,String name,String type,String propertyId,String operator,String value,String typeTwo,String propertyIdTwo,String operatorTwo,String valueTwo,String context){
		DevicePropertyTrigger trigger=backgroundDao.getTrigerById(id);
		trigger.setTriggerName(name);
		cloudDao.update(trigger);
		List<DevicePropertyTriggerRelationship> list=backgroundDao.getTriggerRelationshipById(id);
		if(list!=null && list.size()>0){
			int size=list.size();
			DevicePropertyTriggerRelationship relationship1=list.get(0);
			relationship1.setRuleType(Integer.parseInt(type));
			DeviceProperty property=backgroundDao.getPropertyById(propertyId);
			relationship1.setProperty(property);
			relationship1.setOperator(Integer.parseInt(operator));
			relationship1.setCompareValue(value);
			cloudDao.update(relationship1);
			if(size==1){
				if(valueTwo!=null && !"".equals(valueTwo)){
					addRule(trigger, typeTwo, propertyIdTwo, operatorTwo, valueTwo, trigger.getCreatedBy());
				}
			}else{
				DevicePropertyTriggerRelationship relationship2=list.get(1);
				if(valueTwo!=null && !"".equals(valueTwo)){
					relationship2.setRuleType(Integer.parseInt(typeTwo));
					DeviceProperty property2=backgroundDao.getPropertyById(propertyIdTwo);
					relationship2.setProperty(property2);
					relationship2.setOperator(Integer.parseInt(operatorTwo));
					relationship2.setCompareValue(valueTwo);
					cloudDao.update(relationship2);
				}
				else{
					cloudDao.delete(relationship2);
				}
			}
			
		}
		return true;
		
		
	}

	public List<DevicePropertyStandard> getDevicePropertyStandard() {
		// TODO Auto-generated method stub
		return backgroundDao.getDevicePropertyStandard();
	}
	
	public List<String> getPropertyWhere(String brandId) {
		// TODO Auto-generated method stub
		return backgroundDao.getPropertyWhere(brandId);
	}

	public boolean setStandardProperty(String brandId, String name, String type) {
		// TODO Auto-generated method stub
		Brand brand=backgroundDao.getAuditById(brandId);
		if(type.equals("0")){
			String id=UUID.randomUUID().toString().replace("-", "");
			DevicePropertyStandard standard=backgroundDao.getStandardByName(name);
			DeviceProperty deviceProperty=new DeviceProperty();
			deviceProperty.setId(id);
			deviceProperty.setEnName(standard.getEnName());
			deviceProperty.setChName(standard.getChName());
			deviceProperty.setBrand(brand);
			
			deviceProperty.setWrFlag(standard.getWrFlag());
			deviceProperty.setFormatType(standard.getFormatType());
			deviceProperty.setFormatRule(standard.getFormatRule());
			deviceProperty.setPropertyUnit(standard.getPropertyUnit());
			deviceProperty.setCreatedAt(new Date());
			deviceProperty.setCreatedBy(brand.getUser().getUserId());
			deviceProperty.setIsStandardProperty(0);
			cloudDao.add(deviceProperty);
			
			if(brand.getProtocolAnalysis()==1 || brand.getCommunicationProtocol()==2){
				int count=brand.getSdCount();
				count=count+1;
				seriesPortData(brand, standard.getChName(), standard.getEnName(), 
						standard.getPropertyUnit(), String.valueOf(standard.getFormatType()), standard.getFormatRule(), 
						standard.getWrFlag(),id,count);
				brand.setSdCount(brand.getSdCount()+1);
				cloudDao.update(brand);
			}
		}
		else{
			DeviceProperty deviceProperty=backgroundDao.getPropertyByName(brandId,name);
			cloudDao.delete(deviceProperty);
		}
		return true;
	}

	public List<String> getAllStandardName() {
		// TODO Auto-generated method stub
		return backgroundDao.getAllStandardName();
	}
}
