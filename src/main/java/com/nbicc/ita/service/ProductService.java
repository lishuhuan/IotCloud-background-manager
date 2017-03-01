package com.nbicc.ita.service;

import static com.nbicc.ita.constant.ParameterKeys.H5_PATH;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.nbicc.ita.background.dao.BackgroundDao;
import com.nbicc.ita.background.dao.CloudDao;
import com.nbicc.ita.background.dao.CloudDaoPro;
import com.nbicc.ita.model.AbilityTemplate;
import com.nbicc.ita.model.AppBrandBounding;
import com.nbicc.ita.model.AppInfo;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.Device;
import com.nbicc.ita.model.DeviceProperty;
import com.nbicc.ita.model.DevicePropertyTrigger;
import com.nbicc.ita.model.DevicePropertyTriggerRelationship;
import com.nbicc.ita.model.IotCloudType;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.model.Qrcode;
import com.nbicc.ita.model.SerialPortData;
import com.nbicc.ita.util.SFTPUtil;

@Transactional
@Component("productService")
public class ProductService {

	@Autowired
	private CloudDao cloudDao;
	
	@Autowired
	private CloudDaoPro cloudDaoPro;

	@Autowired
	private BackgroundDao backgroundDao;

	@SuppressWarnings("unchecked")
	public Map<String, String> audit(String id, String userid, String type, String productName, String productModel,
			String productBrand, String identification, String communicationProtocol, String connectionType,
			String codeType, String protocolAnalysis, String context, String imgPath, String iotType,
			String userGuideUrl, String bluetooth, String bluetoothCode,String isGateway,String appSupport,String communicationMode) {
		Brand brand = new Brand();
		IotCloudUser user = backgroundDao.getUserById(userid);
		brand.setId(id);
		brand.setUser(user);
		IotCloudType brandType = backgroundDao.getTypeById(type);
		brand.setUserGuideUrl(userGuideUrl);
		brand.setTypeId(brandType);
		brand.setProductName(productName);
		brand.setProductModel(productModel);
		brand.setProductBrand(productBrand);
		if(isGateway!=null && !"".equals(isGateway)){
			brand.setIsGateway(Integer.parseInt(isGateway));
		}
		brand.setAppSupport(appSupport);
		brand.setCommunicationMode(communicationMode);
		
		if (null != protocolAnalysis && !"".equals(protocolAnalysis)) {
			brand.setProtocolAnalysis(Integer.parseInt(protocolAnalysis));
		}
		if (null != identification && !"".equals(identification)) {
			brand.setIdentification(Integer.parseInt(identification));
		}
		if (null != communicationProtocol && !"".equals(communicationProtocol)) {
			brand.setCommunicationProtocol(Integer.parseInt(communicationProtocol));
			if (communicationProtocol.equals("2")) {
				brand.setProtocolAnalysis(1);
			}
		}

		if (null != bluetooth && !"".equals(bluetooth)) {
			brand.setBluetooth(Integer.parseInt(bluetooth));
			if ("2".equals(bluetooth)) {
				brand.setBluetoothCode(bluetoothCode);
			}
		}
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String key = str.replace("-", "");
		brand.setDeviceKey(key);
		brand.setContext(context);
		brand.setCreatedAt(new Date());
		brand.setCreatedBy(userid);

		if (null != connectionType && !"".equals(connectionType)) {
			brand.setConnectType(Integer.parseInt(connectionType));
		}
		if (iotType != null && !"".equals(iotType)) {
			brand.setIotType(Integer.parseInt(iotType));
		}
		if (codeType != null && !"".equals(codeType)) {
			brand.setCodeType(Integer.parseInt(codeType));
			if ("1".equals(codeType)) {
				qrcodeInsert(id, userid);
			}
		}

		cloudDao.add(brand);
		Map<String, String> map = new HashMap<>();
		map.put("device_key", key);
		map.put("id", id);
		return map;

	}

	public void qrcodeInsert(String brandId, String userid) {
		String result = null;
		while (true) {
			String qrcode = getqrcode();
			String searchCode = backgroundDao.searchCode(qrcode);
			if (searchCode == null || "".equals(searchCode)) {
				result = qrcode;
				break;
			}
		}
		String id = UUID.randomUUID().toString().replace("-", "");
		Qrcode qrcode = new Qrcode();
		qrcode.setId(id);
		qrcode.setQrcodeValue(result);
		qrcode.setQrcodeType(1);
		qrcode.setReferenceId(brandId);
		qrcode.setCreatedAt(new Date());
		qrcode.setCreatedBy(userid);
		cloudDao.add(qrcode);

	}

	public String getqrcode() {
		String result = UUID.randomUUID().toString().replace("-", "");
		result = result.substring(0, 6);
		return result;
	}

	public Brand getAuditById(String id) {
		return backgroundDao.getAuditById(id);
	}

	public Boolean openRuleStatus(String id, String status, String userid) {
		Brand brand = backgroundDao.getAuditById(id);
		brand.setRuleStatus(Integer.parseInt(status));
		cloudDao.update(brand);

		if (status.equals("1")) {
			AppBrandBounding bounding = new AppBrandBounding();
			String appid = UUID.randomUUID().toString().replace("-", "");
			bounding.setId(appid);
			bounding.setBrand(brand);
			AppInfo info = backgroundDao.getAppInfoById("3dad8620a5b14918a088c69258dd7fa7");
			bounding.setAppInfo(info);
			bounding.setCreatedAt(new Date());
			bounding.setCreatedBy(userid);
			cloudDao.add(bounding);
			
			AppInfo info2 = backgroundDao.getAppInfoById("762ef88297844287a05aff924518f197");
			bounding.setAppInfo(info2);
			cloudDao.add(bounding);
		}
		if (status.equals("0")) {
			List<AppBrandBounding> bounding = backgroundDao.getAppBrandBoundingByBrandId(brand.getId());
			cloudDao.batchDelete(bounding);
		}
		return true;
	}

	public int getRuleStatus(String id) {
		Brand brand = backgroundDao.getAuditById(id);
		int state = brand.getRuleStatus();
		return state;
	}

	public Boolean update(String id, String userid, String type, String productName, String productModel,
			String productBrand, String identification, String protocolAnalysis, String communicationProtocol,
			String context, String connectionType, String codeType, String netConfig, String imgPath) {
		Brand brand = backgroundDao.getAuditById(id);
		IotCloudType brandType = backgroundDao.getTypeById(type);
		brand.setTypeId(brandType);
		brand.setProductName(productName);
		brand.setProductModel(productModel);
		brand.setProductBrand(productBrand);
		brand.setImgPath(imgPath);
		if (null != identification) {
			brand.setIdentification(Integer.parseInt(identification));
		}
		// audit.setIdentificationNum(identificationNum);
		if (null != protocolAnalysis) {
			brand.setProtocolAnalysis(Integer.parseInt(protocolAnalysis));
		}
		if (null != communicationProtocol) {
			brand.setCommunicationProtocol(Integer.parseInt(communicationProtocol));
		}
		brand.setContext(context);
		brand.setUpdatedAt(new Date());
		brand.setUpdatedBy(userid);
		cloudDao.update(brand);
		return true;
	}

	public List<Brand> getAuditList() {
		return backgroundDao.getAuditList();

	}

	public List<IotCloudType> getTypeList() {
		return backgroundDao.getTypeList();
	}

	public List<IotCloudType> getTypeByParent(String id) {
		return backgroundDao.getTypeByParent(id);
	}

	@SuppressWarnings({ "unchecked", "null" })
	public Boolean throughApply(String registerId, String userId) {
		Brand brand = backgroundDao.getAuditById(registerId);
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String id = str.replace("-", "");
		brand.setId(id);
		brand.setBrandInfo(brand.getProductName());
		brand.setDeviceKey(brand.getDeviceKey());
		brand.setCreatedAt(new Date());
		brand.setCreatedBy(userId);
		brand.setState(2);
		cloudDao.update(brand);
		return true;

	}

	public List<Brand> getAuditByState() {
		return backgroundDao.getAuditByState();
	}

	public Boolean rejectApply(String registerId) {
		Brand audit = backgroundDao.getAuditById(registerId);
		audit.setState(3);
		cloudDao.update(audit);
		return true;
	}

	public List<Brand> getAuditReject() {
		return backgroundDao.getAuditReject();
	}

	public List<Device> getDeviceList(String id) {
		return backgroundDao.getDeviceList(id);
	}

	public List<AbilityTemplate> getTemplateById(String typeId) {
		return backgroundDao.getTemplateById(typeId);
	}

	public String updateH5Path(String id, String main) throws IOException {
		Brand brand = backgroundDao.getAuditById(id);
		if (null == brand.getH5Path() || "".equals(brand.getH5Path())) {
			String path = H5_PATH + brand.getDeviceKey();
			File file = new File(path);
			boolean state = file.mkdirs();
			if (state) {
				brand.setPreviewPage(main);
				brand.setH5Path(path);
				cloudDao.update(brand);
				return path;
			} else {
				return "error";
			}
		} else {
			brand.setPreviewPage(main);
			cloudDao.update(brand);
			return brand.getH5Path();
		}
	}

	public String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public Boolean appDevelopSetting(String packageName,String appKeyI,String accessIdI,String secretKeyI,String appKeyA,String accessIdA,String secretKeyA,String userid,String apnsEnv,String brandId){
		String id=UUID.randomUUID().toString().replace("-", "");
		AppInfo info=new AppInfo();
		info.setId(id);
		info.setCreatedAt(new Date());
		info.setCreatedBy(userid);
		AppInfo appInfo=backgroundDao.getAppInfoByNameAndUser(userid,packageName);
		String sdk="";
		if(appInfo!=null){
			sdk=appInfo.getAppSdk();
		}
		else{
			sdk=UUID.randomUUID().toString().replace("-", "");
		}
		info.setAppSdk(sdk);
		info.setApnsEnv(apnsEnv);
		short num=backgroundDao.getMaxNumInAppInfo();
		int max=Integer.valueOf(num)+1;
		short b=(short)max;
		info.setAppInfoId(b);
		info.setAppNameIos(packageName);
		info.setSecretKeyI(secretKeyI);
		info.setAccessIdI(accessIdI);
		info.setMobKeyI(appKeyI);
		info.setAppNameAndroid(packageName);
		info.setSecretKeyA(secretKeyA);
		info.setAccessIdA(accessIdA);
		info.setMobKeyA(appKeyA);
		cloudDao.add(info);
		AppBrandBounding bounding=new AppBrandBounding();
		String appid=UUID.randomUUID().toString().replace("-", "");
		Brand brand=backgroundDao.getAuditById(brandId);
		bounding.setId(appid);
		bounding.setBrand(brand);
		bounding.setAppInfo(info);
		bounding.setCreatedAt(new Date());
		bounding.setCreatedBy(userid);
		cloudDao.add(bounding);
		
		if(brand.getIsOpen()==0){
			brand.setIsOpen(1);
			cloudDao.update(brand);
		}
		return true;
	}
	
	public Boolean editAppInfo(String id,String packageName,String appKeyI,String accessIdI,String secretKeyI,String appKeyA,String accessIdA,String secretKeyA,String userid,String apnsEnv){
		AppInfo info=backgroundDao.getAppInfoById(id);
		info.setUpdatedAt(new Date());
		info.setUpdatedBy(userid);
		info.setApnsEnv(apnsEnv);
		info.setAppNameIos(packageName);
		info.setSecretKeyI(secretKeyI);
		info.setAccessIdI(accessIdI);
		info.setMobKeyI(appKeyI);
		info.setAppNameAndroid(packageName);
		info.setSecretKeyA(secretKeyA);
		info.setAccessIdA(accessIdA);
		info.setMobKeyA(appKeyA);
		cloudDao.update(info);
		return true;
	}

	public Brand getBrandByDeviceKey(String deviceKey) {
		return backgroundDao.getBrandByDeviceKey(deviceKey);
	}

	public List<AppInfo> getAppInfoByBrand(String brandId) {
		return backgroundDao.getAppInfoByBrand(brandId);
	}

	public boolean deleteAppInfo(String id) {
		// TODO Auto-generated method stub
		AppInfo info=backgroundDao.getAppInfoById(id);
		cloudDao.delete(info);
		return true;
	}
	
	public boolean releaseOperation(String brandId,String state) {
		// TODO Auto-generated method stub
		if("1".equals(state)){
			Brand brandUat=backgroundDao.getAuditById(brandId);
			brandUat.setState(3);
			cloudDao.update(brandUat);
		}
		else{
			Brand brand=backgroundDao.getBrandByIdInPro(brandId);
			List<AppInfo> appInfos=backgroundDao.getAppInfoByBrandIdInPro(brandId);
			if(brand!=null){
				int qrcode=brand.getCodeType();
				if(qrcode==1){
					Qrcode qr=backgroundDao.getQrcodeByBrandIdInPro(brandId);
					cloudDaoPro.deleteMany(appInfos,brand,qr);
				}
				else{
					cloudDaoPro.deleteMany(appInfos,brand);
				}
			}
			
			
			Brand brandUat=backgroundDao.getAuditById(brandId);
			brandUat.setState(2);
			cloudDao.update(brandUat);
			List<SerialPortData> portDatas=backgroundDao.getSerialPort(brandUat.getDeviceKey());
			List<DeviceProperty> properties=backgroundDao.getPropertyByPid(brandId);
			List<DevicePropertyTrigger> triggers=backgroundDao.getTriggerByBrand(brandId);
			List<DevicePropertyTriggerRelationship> relationships=backgroundDao.getTriggerRelationshipByBrandId(brandId);
			List<AppBrandBounding> boundings=backgroundDao.getAppBrandBoundingByBrandId(brandId);
			List<AppInfo> infos=backgroundDao.getAppInfoByPid(brandId);
			int qrcodeUat=brandUat.getCodeType();
			Qrcode qr=null;
			if(qrcodeUat==1){
				qr=backgroundDao.getQrcodeByBrandIdInUat(brandId);
			}
			cloudDaoPro.addMany(brandUat, qr,portDatas,properties,triggers,relationships,infos,boundings);
			
			fileTransfer(brand.getH5Path());
		}
		return true;
	}

	public void fileTransfer(String dest) {
		// TODO Auto-generated method stub
		ChannelSftp sftp = null;  
		Session session = null;  
		try {  
		    session = SFTPUtil.connect("121.40.90.27", 22, "root", "Nbicc#Expeed#Server@0608");  
		    Channel channel = session.openChannel("sftp");  
		    channel.connect();  
		    sftp = (ChannelSftp) channel;  
		    SFTPUtil.upload(dest, dest, sftp);  
		} catch (Exception e) {  
		    e.printStackTrace();  
		}finally{  
		    if(sftp != null)sftp.disconnect();  
		    if(session != null)session.disconnect();  
		}  
	}

	public boolean applyRelease(String pid) {
		// TODO Auto-generated method stub
		Brand brandUat=backgroundDao.getAuditById(pid);
		brandUat.setState(1);
		cloudDao.update(brandUat);
		return true;
	}

	public List<Brand> getProductByReleaseState(String state) {
		// TODO Auto-generated method stub
		return backgroundDao.getProductByReleaseState(state);
	}

	public void dataTransfer() {
		// TODO Auto-generated method stub
		List<SerialPortData> devSerial=backgroundDao.getAllSerialPortDataPro();
		
		List<SerialPortData> uatSerial=backgroundDao.getAllSerialPortDataUat();
		
		for(SerialPortData devdata:devSerial){
			int i=0;
			for(SerialPortData uat:uatSerial){
				if(uat.getId().equals(devdata.getId())){
					i=1;
					break;
				}
			}
			
			if(i==0){
				cloudDao.add(devdata);
			}
		}
	}
}
