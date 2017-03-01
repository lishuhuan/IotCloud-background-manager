package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity(name = "Brand")
@Table(name = "brand")
public class Brand implements CloudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4664007718406355518L;
	
	

	public Brand(String id, String typeName, Integer connectType, Integer codeType, Date createdAt, String username,
			String productName, String productModel, String productBrand, Integer identification, Integer protocolAnalysis,
			Integer communicationProtocol, Integer state, Integer ruleStatus, Integer deviceApplyTotal, Integer isGateway) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.connectType = connectType;
		this.codeType = codeType;
		this.createdAt = createdAt;
		this.username = username;
		this.productName = productName;
		this.productModel = productModel;
		this.productBrand = productBrand;
		this.identification = identification;
		this.protocolAnalysis = protocolAnalysis;
		this.communicationProtocol = communicationProtocol;
		this.state = state;
		this.ruleStatus = ruleStatus;
		this.deviceApplyTotal = deviceApplyTotal;
		this.isGateway = isGateway;
	}
	
	public Brand() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "type_Id") 
	private IotCloudType typeId;
	
	@Column(name = "brand_id")
	private Integer brandId;

	@Column(name = "brand_info")
	private String brandInfo;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "firmware_id")
	private Firmware firmware;

	@Column(name = "device_key")
	private String deviceKey;
	
	@Column(name = "connect_type")
	private Integer connectType;
	
	@Column(name = "code_type")
	private Integer codeType;
	
/*	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private Set<Device> device;*/
	
/*	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private Set<AppBrandGroup> appBrandGroups;*/
	
	@Version
	@Column(name = "entity_version")
	private long entityVersion;
	
	@Column(name = "main_firmware")
	private String mainFirmware;
	
	@Column(name = "sub_firmware")
	private String subFirmware;
	
	@Column(name = "iot_type")
	private Integer iotType;
	
	@Column(name = "user_guide_url")
	private String userGuideUrl;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "del_flag")
	private Integer delFlag;
	
	@Column(name = "is_open")
	private Integer isOpen;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private IotCloudUser user;
	
	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_model")
	private String productModel;

	@Column(name = "product_brand")
	private String productBrand;

	@Column(name = "identification")
	private Integer identification;


	@Column(name = "protocol_analysis")
	private Integer protocolAnalysis;

	@Column(name = "communication_protocol")
	private Integer communicationProtocol;

	@Column(name = "context")
	private String context;

	@Column(name = "state")
	private Integer state;


	@Column(name = "audit_time")
	private Date auditTime;
	
	@Column(name = "img_path")
	private String imgPath;

	@Column(name = "h5_path")
	private String h5Path;
	
	@Column(name = "preview_page")
	private String previewPage;
	
	@Column(name = "rule_status")
	private Integer ruleStatus;
	
	@Column(name = "bluetooth_encipherment")
	private Integer bluetooth;
	
	@Column(name = "bluetooth_code")
	private String bluetoothCode;
	
	@Column(name = "sd_count")
	private Integer sdCount;
	
	@Column(name = "device_apply_total")
	private Integer deviceApplyTotal;
	
	@Column(name = "device_new_apply_num")
	private Integer deviceNewApplyNum;
	
	@Column(name = "is_gateway")
	private Integer isGateway;
	
	@Column(name = "app_support")
	private String appSupport;
	
	@Column(name = "communication_mode")
	private String communicationMode;
	
	@Column(name = "gateway_parts_protocol")
	private String gatewayPartsProtocol;
	
	@Transient
	private String typeLevel2;

	@Transient
	private String typeLevel1;

	@Transient
	private String typeLevelName2;

	@Transient
	private String typeLevelName1;
	
	@Transient
	private String typeName;
	
	@Transient
	private String username;
	
	public String getGatewayPartsProtocol() {
		return gatewayPartsProtocol;
	}
	
	public void setGatewayPartsProtocol(String gatewayPartsProtocol) {
		this.gatewayPartsProtocol = gatewayPartsProtocol;
	}
	
	
	public Integer getIsGateway() {
		return isGateway;
	}

	public void setIsGateway(Integer isGateway) {
		this.isGateway = isGateway;
	}

	public String getAppSupport() {
		return appSupport;
	}

	public void setAppSupport(String appSupport) {
		this.appSupport = appSupport;
	}

	public String getCommunicationMode() {
		return communicationMode;
	}

	public void setCommunicationMode(String communicationMode) {
		this.communicationMode = communicationMode;
	}

	public Integer getDeviceApplyTotal() {
		return deviceApplyTotal;
	}

	public void setDeviceApplyTotal(Integer deviceApplyTotal) {
		this.deviceApplyTotal = deviceApplyTotal;
	}

	public Integer getDeviceNewApplyNum() {
		return deviceNewApplyNum;
	}

	public void setDeviceNewApplyNum(Integer deviceNewApplyNum) {
		this.deviceNewApplyNum = deviceNewApplyNum;
	}

	public void setSdCount(Integer sdCount) {
		this.sdCount = sdCount;
	}
	
	public Integer getSdCount() {
		return sdCount;
	}
	
	
	public Integer getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(Integer bluetooth) {
		this.bluetooth = bluetooth;
	}

	public String getBluetoothCode() {
		return bluetoothCode;
	}

	public void setBluetoothCode(String bluetoothCode) {
		this.bluetoothCode = bluetoothCode;
	}

	public String getTypeLevel2() {
		return typeLevel2;
	}

	public void setTypeLevel2(String typeLevel2) {
		this.typeLevel2 = typeLevel2;
	}

	public String getTypeLevel1() {
		return typeLevel1;
	}

	public void setTypeLevel1(String typeLevel1) {
		this.typeLevel1 = typeLevel1;
	}

	public String getTypeLevelName2() {
		return typeLevelName2;
	}

	public void setTypeLevelName2(String typeLevelName2) {
		this.typeLevelName2 = typeLevelName2;
	}

	public String getTypeLevelName1() {
		return typeLevelName1;
	}

	public void setTypeLevelName1(String typeLevelName1) {
		this.typeLevelName1 = typeLevelName1;
	}

	public IotCloudUser getUser() {
		return user;
	}

	public void setUser(IotCloudUser user) {
		this.user = user;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public Integer getIdentification() {
		return identification;
	}

	public void setIdentification(Integer identification) {
		this.identification = identification;
	}

	public Integer getProtocolAnalysis() {
		return protocolAnalysis;
	}

	public void setProtocolAnalysis(Integer protocolAnalysis) {
		this.protocolAnalysis = protocolAnalysis;
	}

	public Integer getCommunicationProtocol() {
		return communicationProtocol;
	}

	public void setCommunicationProtocol(Integer communicationProtocol) {
		this.communicationProtocol = communicationProtocol;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getH5Path() {
		return h5Path;
	}

	public void setH5Path(String h5Path) {
		this.h5Path = h5Path;
	}

	public String getPreviewPage() {
		return previewPage;
	}

	public void setPreviewPage(String previewPage) {
		this.previewPage = previewPage;
	}

	public Integer getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(Integer ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public Integer getCodeType() {
		return codeType;
	}
	
	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}
	
	public Integer getIsOpen() {
		return isOpen;
	}
	
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	
	
	
	public void setIotType(Integer iotType) {
		this.iotType = iotType;
	}
	
	public Integer getIotType() {
		return iotType;
	}
	
	public String getMainFirmware() {
		return mainFirmware;
	}

	public void setMainFirmware(String mainFirmware) {
		this.mainFirmware = mainFirmware;
	}

	public String getSubFirmware() {
		return subFirmware;
	}

	public void setSubFirmware(String subFirmware) {
		this.subFirmware = subFirmware;
	}
	
/*	public Set<Device> getDevice() {
		return device;
	}

	public void setDevice(Set<Device> device) {
		this.device = device;
	}*/

	
	public String getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(String brandInfo) {
		this.brandInfo = brandInfo;
	}


	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IotCloudType getTypeId() {
		return typeId;
	}

	public void setTypeId(IotCloudType typeId) {
		this.typeId = typeId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getUserGuideUrl() {
		return userGuideUrl;
	}

	public void setUserGuideUrl(String userGuideUrl) {
		this.userGuideUrl = userGuideUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public long getEntityVersion() {
		return entityVersion;
	}

	public void setEntityVersion(long entityVersion) {
		this.entityVersion = entityVersion;
	}
	
	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	
	
	public Integer getConnectType() {
		return connectType;
	}

	public void setConnectType(Integer connectType) {
		this.connectType = connectType;
	}
	
	public Firmware getFirmware() {
		return firmware;
	}

	public void setFirmware(Firmware firmware) {
		this.firmware = firmware;
	}

/*	public Set<AppBrandGroup> getAppBrandGroups() {
		return appBrandGroups;
	}

	public void setAppBrandGroups(Set<AppBrandGroup> appBrandGroups) {
		this.appBrandGroups = appBrandGroups;
	}*/
	
	@Override
	public String toString() {
		StringBuilder sbd = new StringBuilder("Brand[");
		sbd.append("id:").append(brandId).append(";");
		sbd.append("brandInfo:").append(brandInfo).append(";]");
		return sbd.toString();
	}


}
