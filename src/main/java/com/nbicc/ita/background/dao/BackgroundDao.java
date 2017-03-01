package com.nbicc.ita.background.dao;

import java.util.List;

import com.nbicc.ita.model.AbilityTemplate;
import com.nbicc.ita.model.AppBrandBounding;
import com.nbicc.ita.model.AppInfo;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.Device;
import com.nbicc.ita.model.DeviceMessageProtocol;
import com.nbicc.ita.model.DeviceProperty;
import com.nbicc.ita.model.DevicePropertyStandard;
import com.nbicc.ita.model.DevicePropertyTrigger;
import com.nbicc.ita.model.DevicePropertyTriggerRelationship;
import com.nbicc.ita.model.GatewayRelationship;
import com.nbicc.ita.model.IotCloudType;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.model.MessageProtocolItem;
import com.nbicc.ita.model.Qrcode;
import com.nbicc.ita.model.SerialPortData;

/** 
 * @author lishuhuan 
 * @date 2016年3月29日
 * 类说明 
 */
public interface BackgroundDao {
	public IotCloudUser getUserByName(String userName);
	
	public IotCloudUser getUserById(String id);
	
	public List<Brand> getAuditList();
	
	public IotCloudType getTypeById(String id);
	
	public List<IotCloudType> getTypeList();
	
	public Brand getAuditById(String id);
	
	public DeviceProperty getPropertyByName(String brandId,String name);
	
	public Brand getAuitByRegisterId(String id);
	
	public List<Brand> getAuditByState();
	
	public List<Brand> getAuditReject();
	
	public List<Device> getDeviceList(String id);
	
	public List<Brand> getAuditByUser(String id);
	
	public DeviceMessageProtocol getDeviceById(int id);
	
	public int getDeviceCount(String  id);
	
	public List<MessageProtocolItem> getItemById(int id);
	
	public List<IotCloudUser> getUserListAll();
	
	public List<IotCloudUser> getUserListByStatus(int status);
	
	public MessageProtocolItem getMessageProtocolItemById(int id);
	
	public List<IotCloudType> getTypeByParent(String id);
	
	public List<AbilityTemplate> getTemplateById(String typeId);
	
	public String getUserIdByDeviceKey(String key);
	
	public DeviceProperty getPropertyByName(String name);
	
	
	public List<DeviceProperty> getPropertyByProductId(String productId);
	
	public int getPropertyCount(String deviceKey);
	
	public List<SerialPortData> getSerialPort(String id);
	
	public String searchCode(String code);
	
	public List<DevicePropertyTrigger> getTriggerByBrand(String id);
	
	public DevicePropertyTrigger getTriggerById(String id);
	
	public DevicePropertyTrigger getTrigerById(String id);
	
	public String getDeveloperKey(String id);
	
	public Brand getBrandByDeviceProperty(String id);
	
	public SerialPortData getSerialByDpId(String dpId);
	
	public AppInfo getAppInfoById(String id);
	
	public List<AppBrandBounding> getAppBrandBoundingByBrandId(String id);
	
	public Brand getBrandByDeviceKey(String deviceKey);
	
	public String getQrcodeByBrandId(String id);
	
	public Short getMaxNumInAppInfo();
	
	public List<AppInfo> getAppInfoByBrand(String brandId);
	
	public AppInfo inspectA(String brandId);
	
	public AppInfo inspectI(String brandId);
	
	public List<Brand> getGatewayList(String userid);
	
	public String getPartsProtocol(String brandId);
	
	public String getGatewayIds(String brandId);
	
	public void deleteGatewayRelationshipBySub(String subId);

	public List<DevicePropertyStandard> getDevicePropertyStandard();

	public List<String> getPropertyWhere(String brandId);

	public DevicePropertyStandard getStandardByName(String name);

	public List<String> getAllStandardName();

	public DeviceProperty getPropertyById(String id);

	public AppInfo getAppInfoByNameAndUser(String userid, String packageName);

	public List<DevicePropertyTriggerRelationship> getTriggerRelationshipById(String id);

	public String getApnsEnvByDeviceKey(String key);

	public Brand getBrandByIdInPro(String brandId);
	
	void deleteBrand(String id);

	public List<AppInfo> getAppInfoByBrandIdInPro(String brandId);

	public Qrcode getQrcodeByBrandIdInPro(String brandId);

	public List<DeviceProperty> getPropertyByPid(String brandId);

	public List<DevicePropertyTriggerRelationship> getTriggerRelationshipByBrandId(String brandId);

	public List<AppInfo> getAppInfoByPid(String brandId);

	public Qrcode getQrcodeByBrandIdInUat(String brandId);

	public String getSecretByUserId(String id);

	public List<Brand> getProductByReleaseState(String state);

	public List<SerialPortData> getAllSerialPortDataPro();

	public List<SerialPortData> getAllSerialPortDataUat();
	

}
