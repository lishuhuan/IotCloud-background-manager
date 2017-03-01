package com.nbicc.ita.constant;

import java.util.Date;

public class Hql {
	
    public static final String GET_USER_BY_NAME = "from IotCloudUser user where user.username = ?";
    
    public static final String GET_USER_LIST_ALL="from IotCloudUser user";
    
    public static final String GET_USER_LIST_BY_STATUS="from IotCloudUser user where user.auditStatus=?";
    
    public static final String GET_USER_BY_ID ="select  new IotCloudUser(userId,  username,  email,  authority,  userType,  companyName,companyInfo,  socialCode,  legalRepresentative,  companyAddress,technicalPerson,  contactNumber,  registerTime,  content,  personName,sex,  age,  education,  workAddress) from IotCloudUser user where user.userId=?";
	
	public static final String GET_AUDIT_LIST = "from Brand b where b.state=0";
	
	public static final String GET_TYPE_BY_ID="from IotCloudType type where type.id=?";
	
	public static final String GET_TYPE_BY_PARENT="from IotCloudType a where a.parentId=?";
	
	public static final String GET_TYPE_LIST="from IotCloudType a where a.parentId=?";
	
	public static final String GET_AUIT_BY_ID="from Brand b where b.id=?";
	
	public static final String GET_AUDIT_BY_STATE="from Brand b where b.state=2";
	
	public static final String GET_AUDIT_REJECT="from Brand b where b.state=3";
	
	public static final String GET_DEVICE_LIST="from Device de where de.brand.brandId=?";
	
	public static final String GET_AUDIT_BY_USER="from Brand b where b.user.userId=?";
	
	public static final String GET_DEVICE_BY_ID="from DeviceMessageProtocol de where de.dmpId=?";
	
	public static final String GET_PROPERTY_COUNT="select count(a) from SerialPortData a where a.deviceKey=?";
	
	public static final String GET_DEVICE_COUNT="select count(u) from MessageProtocol u where u.deviceMessageProtocol.dmpId=?";
	
	public static final String GET_BRAND_SERIES_LIST="from BrandSeries b";
	
	public static final String GET_BRAND_SERIES_BY_ID="from BrandSeries br where br.brandSeriesId=?";
	
	public static final String GET_BRAND_SERIES_COUNT="select count(u) from BrandSeries u";
	
	public static final String GET_BRAND_NAME_BY_NAME="from BrandName b where b.brandNameInfo=?";
	
	public static final String GET_MESSAGE_PROTOCOL_ITEM_BY_ID="from MessageProtocolItem m where m.itemId=?";
	
	public static final String GET_TEMPLATE_BY_TYPE_ID="from AbilityTemplate a where a.typeId=?";

	public static final String GET_USER_ID_BY_DEVICE_KEY="select b.user.userId from Brand b where b.deviceKey=?";
	
	public static final String GET_SECRET_BY_USER_ID="select secret from IotCloudUser where userId=?";
	
	public static final String GET_BRAND_BY_RELEASE_STATE="select new Brand(id,typeId.typeName,connectType,codeType,createdAt,user.username,productName ,productModel,productBrand,identification,protocolAnalysis,communicationProtocol,state,ruleStatus,deviceApplyTotal,isGateway) from Brand where state=?";
	
	public static final String GET_PROPERTY_BY_NAME="from DeviceProperty de where de.enName=?";
	
	public static final String GET_PROPERTY_BY_ID="from DeviceProperty de where de.id=?";
	
	public static final String GET_TRIGGER_RELATION_BY_ID="from DevicePropertyTriggerRelationship where trigger.id=?";
	
	public static final String GET_PROPERTY_BY_KEY="from SerialPortData a where a.deviceKey=?";
	
	public static final String GET_QRCODE_BY_CODE="select qrcodeValue from Qrcode where qrcodeValue=?";
	
	public static final String GET_RULE_BY_PRODUCT="from DevicePropertyTrigger a where a.brand.id=?";
	
	public static final String GET_RULE_BY_ID="from DevicePropertyTrigger a where a.id=?";
	
	public static final String GET_TRIGGER_BY_ID="from DevicePropertyTrigger a where a.id=?";
	
	public static final String GET_PROPERTY_BY_PRODUCT_ID="from DeviceProperty a where a.brand.id=? and a.isStandardProperty=1";
	
	public static final String GET_PROPERTY_BY_PID="from DeviceProperty where brand.id=?";
	
	public static final String GET_PROPERTY_BY_NAME_AND_BRAND="from DeviceProperty a where a.brand.id=? and a.enName=?";
	
	public static final String GET_DEVELOPER_ID_BY_USER="select secret from IotCloudUser where username=?";
	
	public static final String GET_SERIAL_PORT_DATA_BY_DPID="from SerialPortData a where a.dpId=?";
	
	public static final String GET_APPINFO_BY_ID="from AppInfo where id=?";
	
	public static final String GET_APP_BRAND_BOUNDING_BY_BRAND_ID="from AppBrandBounding where brand.id=?";
	
	public static final String GET_BRAND_BY_DEVICEKEY="from brand where deviceKey=?";
	
	public static final String GET_QRCODE_BY_BRANDID="select qrcodeValue from Qrcode where qrcodeType=1 and referenceId=?";
	
	//public static final String GET_MAX_NUM_IN_APP_INFO="select MAX(app_info_id) from app_info";
	
	public static final String GET_GATEWAY_LIST="from Brand where user.userId=? and isGateway=1";
	
	public static final String GET_ALL_STANDARD_PROPERTY="from DevicePropertyStandard";
	
	public static final String GET_ALL_STANDARD_NAME="select enName from DevicePropertyStandard";
	
	public static final String GET_STANDARD_PROPERTY_BY_NAME="from DevicePropertyStandard where enName=?";
	
	public static final String GET_ACCESSORY_PROTOCOL="select gatewayPartsProtocol from Brand where id=?";
	
	public static final String ALL_SERIAL_DATA="from SerialPortData";
}
