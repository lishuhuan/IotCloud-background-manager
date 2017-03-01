package com.nbicc.ita.dao.impl;

import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nbicc.ita.background.dao.BackgroundDao;
import com.nbicc.ita.background.dao.CloudDao;
import com.nbicc.ita.background.dao.CloudDaoPro;
import com.nbicc.ita.constant.Hql;
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
import com.nbicc.ita.model.IotCloudType;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.model.MessageProtocolItem;
import com.nbicc.ita.model.Qrcode;
import com.nbicc.ita.model.SerialPortData;

/**
 * @author lishuhuan
 * @date 2016年3月29日 类说明
 */

@Repository("backgroundDao")
public class BackgroundDaoImpl implements BackgroundDao {

	@Autowired
	private CloudDao cloudDao;
	
	@Autowired
	private CloudDaoPro cloudDaoPro;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private SessionFactory sessionFactoryPro;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Session getSessionPro() {
		Configuration configure = new Configuration();
		sessionFactoryPro=configure.configure().buildSessionFactory();
		return sessionFactoryPro.openSession();
	}
	
	public void closeSessionPro(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	@Override
	public IotCloudUser getUserByName(String userName) {
		return (IotCloudUser) cloudDao.getByHql(Hql.GET_USER_BY_NAME, userName);
	}

	@Override
	public List<Brand> getAuditList() {
		return cloudDao.findByHql(Hql.GET_AUDIT_LIST);
	}

	@Override
	public IotCloudType getTypeById(String id) {
		// TODO Auto-generated method stub
		return (IotCloudType) cloudDao.getByHql(Hql.GET_TYPE_BY_ID, id);
	}

	@Override
	public List<IotCloudType> getTypeList() {
		// TODO Auto-generated method stub
		return cloudDao.findByHql(Hql.GET_TYPE_LIST, "0");
	}

	@Override
	public Brand getAuditById(String id) {
		return (Brand) cloudDao.getByHql(Hql.GET_AUIT_BY_ID, id);
	}
	
	@Override
	public Brand getBrandByIdInPro(String id) {
		return (Brand) cloudDaoPro.getByHql(Hql.GET_AUIT_BY_ID, id);
	}

	@Override
	public Brand getAuitByRegisterId(String id) {
		Query query = getSession().createSQLQuery(
				"select a.*,c.id as typeLevel2,d.id as typeLevel1,c.type_Name AS typeLevelName1,d.type_Name AS typeLevelName2 from brand a LEFT JOIN iotcloud_type b on b.id=a.type_id LEFT JOIN iotcloud_type c ON c.id=b.parent_id LEFT JOIN iotcloud_type d on d.id=c.parent_id where a.id='"
						+ id + "'");
		Brand audit = (Brand) ((SQLQuery) query).addEntity(Brand.class).uniqueResult();
		return audit;
	}

	@Override
	public List<Brand> getAuditByState() {
		return cloudDao.findByHql(Hql.GET_AUDIT_BY_STATE);
	}

	@Override
	public List<Brand> getAuditReject() {
		return cloudDao.findByHql(Hql.GET_AUDIT_REJECT);
	}

	@Override
	public IotCloudUser getUserById(String id) {
		return (IotCloudUser) cloudDao.getByHql(Hql.GET_USER_BY_ID, id);
	}

	@Override
	public List<Device> getDeviceList(String id) {
		return cloudDao.findByHql(Hql.GET_DEVICE_LIST, id);
	}

	@Override
	public List<Brand> getAuditByUser(String id) {
		return cloudDao.findByHql(Hql.GET_AUDIT_BY_USER, id);
	}

	@Override
	public DeviceMessageProtocol getDeviceById(int id) {
		return (DeviceMessageProtocol) cloudDao.getByHql(Hql.GET_DEVICE_BY_ID, id);
	}

	@Override
	public int getDeviceCount(String id) {
		return getSumBy(Hql.GET_DEVICE_COUNT, Integer.parseInt(id));
	}

	private int getSumBy(String hql, int parameter) {
		int sum = 0;
		Query query = getSession().createQuery(hql);
		query.setParameter(0, parameter);
		sum = ((Long) query.iterate().next()).intValue();
		return sum;
	}

	private int getSum(String hql, String parameter) {
		int sum = 0;
		Query query = getSession().createQuery(hql);
		query.setParameter(0, parameter);
		sum = ((Long) query.iterate().next()).intValue();
		return sum;
	}

	@Override
	public List<MessageProtocolItem> getItemById(int id) {
		Query query = getSession().createSQLQuery(
				"SELECT a.* from message_protocol_item a,message_protocol b,iotcloud_audit d where a.mp_id=b.mp_id and b.dmp_id=d.dmp_id and d.id="
						+ id);
		List<MessageProtocolItem> list = ((SQLQuery) query).addEntity(MessageProtocolItem.class).list();
		return list;
	}
	
	@Override
	public Brand getBrandByDeviceProperty(String id){
		Query query = getSession().createSQLQuery(
				"select a.* from brand a LEFT JOIN device_property b on b.brand_id=a.id where b.id='"+ id + "'");
		Brand audit = (Brand) ((SQLQuery) query).addEntity(Brand.class).uniqueResult();
		return audit;
	}

	@Override
	public MessageProtocolItem getMessageProtocolItemById(int id) {
		return (MessageProtocolItem) cloudDao.getByHql(Hql.GET_MESSAGE_PROTOCOL_ITEM_BY_ID, id);
	}

	@Override
	public List<IotCloudUser> getUserListAll() {
		return cloudDao.findByHql(Hql.GET_USER_LIST_ALL);
	}

	@Override
	public List<IotCloudUser> getUserListByStatus(int status) {
		return cloudDao.findByHql(Hql.GET_USER_LIST_BY_STATUS, status);
	}

	@Override
	public List<IotCloudType> getTypeByParent(String id) {
		return cloudDao.findByHql(Hql.GET_TYPE_BY_PARENT, id);
	}

	@Override
	public List<AbilityTemplate> getTemplateById(String typeId) {
		return cloudDao.findByHql(Hql.GET_TEMPLATE_BY_TYPE_ID, typeId);
	}

	@Override
	public String getUserIdByDeviceKey(String key) {
		return (String) cloudDao.getByHql(Hql.GET_USER_ID_BY_DEVICE_KEY, key);
	}

	@Override
	public DeviceProperty getPropertyByName(String name) {
		return (DeviceProperty) cloudDao.getByHql(Hql.GET_PROPERTY_BY_NAME, name);
	}

	@Override
	public List<DeviceProperty> getPropertyByProductId(String productId) {
		return cloudDao.findByHql(Hql.GET_PROPERTY_BY_PRODUCT_ID, productId);
	}

	@Override
	public int getPropertyCount(String deviceKey) {
		return getSum(Hql.GET_PROPERTY_COUNT, deviceKey);
	}

	@Override
	public List<SerialPortData> getSerialPort(String id) {
		return cloudDao.findByHql(Hql.GET_PROPERTY_BY_KEY, id);
	}

	@Override
	public String searchCode(String code) {
		return (String) cloudDao.getByHql(Hql.GET_QRCODE_BY_CODE, code);
	}

	@Override
	public List<DevicePropertyTrigger> getTriggerByBrand(String id) {
		return cloudDao.findByHql(Hql.GET_RULE_BY_PRODUCT, id);
	}

	@Override
	public DevicePropertyTrigger getTrigerById(String id) {
		return (DevicePropertyTrigger) cloudDao.getByHql(Hql.GET_TRIGGER_BY_ID, id);
	}
	
	@Override
	public String getDeveloperKey(String id){
		return (String) cloudDao.getByHql(Hql.GET_DEVELOPER_ID_BY_USER, id);
	}
	
	@Override
	public SerialPortData getSerialByDpId(String dpId){
		return (SerialPortData) cloudDao.getByHql(Hql.GET_SERIAL_PORT_DATA_BY_DPID, dpId);
	}
	
	@Override
	public AppInfo getAppInfoById(String id){
		return (AppInfo) cloudDao.getByHql(Hql.GET_APPINFO_BY_ID, id);
	}
	
	@Override
	public List<AppBrandBounding> getAppBrandBoundingByBrandId(String id){
		return cloudDao.findByHql(Hql.GET_APP_BRAND_BOUNDING_BY_BRAND_ID, id);
	}
	
	@Override
	public Brand getBrandByDeviceKey(String deviceKey){
		return (Brand) cloudDao.getByHql(Hql.GET_BRAND_BY_DEVICEKEY, deviceKey);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppInfo> getAppInfoByBrand(String brandId){
		Query query = getSession().createSQLQuery(
				"select a.* from app_info a LEFT JOIN app_brand_bounding b on b.app_info_id=a.id where b.brand_id='"+brandId+"' and b.app_info_id not in ('3dad8620a5b14918a088c69258dd7fa7','762ef88297844287a05aff924518f197')");
		List<AppInfo> list = ((SQLQuery) query).addEntity(AppInfo.class).list();
		return list;
	}
	
	@Override
	public AppInfo inspectA(String brandId){
		Query query = getSession().createSQLQuery(
				"select a.* from app_info a LEFT JOIN app_brand_bounding b on b.app_info_id=a.id where b.brand_id='"+brandId+"' and a.app_name_a is NOT NULL");
		AppInfo appInfo =(AppInfo) ((SQLQuery) query).addEntity(AppInfo.class).uniqueResult();
		return appInfo;
	}
	
	@Override
	public AppInfo inspectI(String brandId){
		Query query = getSession().createSQLQuery(
				"select a.* from app_info a LEFT JOIN app_brand_bounding b on b.app_info_id=a.id where b.brand_id='"+brandId+"' and a.app_name_i is NOT NULL");
		AppInfo appInfo =(AppInfo) ((SQLQuery) query).addEntity(AppInfo.class).uniqueResult();
		return appInfo;
	}
	
	@Override
	public String getQrcodeByBrandId(String id){
		return (String) cloudDao.getByHql(Hql.GET_QRCODE_BY_BRANDID, id);
	}
	
	@Override
	public Short getMaxNumInAppInfo(){
		Query query = getSession().createSQLQuery("select MAX(app_info_id) from app_info");
		short result=(short) ((SQLQuery) query).uniqueResult();
		return result;
	}

	@Override
	public DevicePropertyTrigger getTriggerById(String id) {
		// TODO Auto-generated method stub
		return (DevicePropertyTrigger) cloudDao.getByHql(Hql.GET_RULE_BY_ID, id);
	}

	@Override
	public DeviceProperty getPropertyByName(String brandId,String name) {
		// TODO Auto-generated method stub
		List<DeviceProperty> list= cloudDao.findByHql(Hql.GET_PROPERTY_BY_NAME_AND_BRAND,brandId, name);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<Brand> getGatewayList(String userid) {
		// TODO Auto-generated method stub
		return cloudDao.findByHql(Hql.GET_GATEWAY_LIST, userid);
	}

	@Override
	public String getPartsProtocol(String brandId) {
		// TODO Auto-generated method stub
		return (String) cloudDao.getByHql(Hql.GET_ACCESSORY_PROTOCOL, brandId);
	}

	@Override
	public String getGatewayIds(String brandId) {
		Query query = getSession().createSQLQuery("select GROUP_CONCAT(gateway_id) from gateway_relationship where sub_equipment='"+brandId+"'");
		String result=(String) query.uniqueResult();
		return result;
	}

	@Override
	public void deleteGatewayRelationshipBySub(String subId) {
		Query query = getSession().createSQLQuery("DELETE from gateway_relationship where sub_equipment='"+subId+"'");
		query.executeUpdate();
		
	}

	@Override
	public List<DevicePropertyStandard> getDevicePropertyStandard() {
		// TODO Auto-generated method stub
		return cloudDao.findByHql(Hql.GET_ALL_STANDARD_PROPERTY);
	}

	@Override
	public List<String> getPropertyWhere(String brandId) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery("select a.enName from DeviceProperty a where a.brand.id='"+brandId+"' and a.isStandardProperty=0");
		List<String> result=query.list();
		return result;
	}

	@Override
	public DevicePropertyStandard getStandardByName(String name) {
		// TODO Auto-generated method stub
		return (DevicePropertyStandard) cloudDao.getByHql(Hql.GET_STANDARD_PROPERTY_BY_NAME, name);
	}

	@Override
	public List<String> getAllStandardName() {
		// TODO Auto-generated method stub
		return cloudDao.findByHql(Hql.GET_ALL_STANDARD_NAME);
	}

	@Override
	public DeviceProperty getPropertyById(String id) {
		// TODO Auto-generated method stub
		return (DeviceProperty) cloudDao.getByHql(Hql.GET_PROPERTY_BY_ID, id);
	}

	@Override
	public AppInfo getAppInfoByNameAndUser(String user, String packageName) {
		Query query = getSession().createSQLQuery(
				"select a.* from app_info a LEFT JOIN app_brand_bounding b on b.app_info_id=a.id  LEFT JOIN brand c on c.id=b.brand_id where c.user_id='"+user+"' and (a.app_name_a='"+packageName+"' or a.app_name_i='"+packageName+"')");
		List<AppInfo> appInfo =((SQLQuery) query).addEntity(AppInfo.class).list();
		if(appInfo!=null && appInfo.size()>0){
			return appInfo.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<DevicePropertyTriggerRelationship> getTriggerRelationshipById(String id) {
		// TODO Auto-generated method stub
		return cloudDao.findByHql(Hql.GET_TRIGGER_RELATION_BY_ID, id);
	}

	@Override
	public String getApnsEnvByDeviceKey(String key) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery(
				"select a.apns_env from app_info a LEFT JOIN app_brand_bounding b on a.id=b.app_info_id LEFT JOIN brand c on b.brand_id=c.id where c.device_key='"+key+"' limit 1");
		return query.uniqueResult().toString();
	}


	@Override
	public void deleteBrand(String id) {
		// TODO Auto-generated method stub
		Session session=getSessionPro();
		Query query = session.createSQLQuery("delete from brand where id='"+id+"'");
		query.executeUpdate();
		closeSessionPro(session);
	}

	@Override
	public List<AppInfo> getAppInfoByBrandIdInPro(String brandId) {
		// TODO Auto-generated method stub
		Session session=getSessionPro();
		Query query = session.createSQLQuery("select a.* from app_info a "
				+ "LEFT JOIN app_brand_bounding b on b.app_info_id=a.id "
				+ "where b.brand_id='"+brandId+"' and b.app_info_id not in ('3dad8620a5b14918a088c69258dd7fa7','762ef88297844287a05aff924518f197')");
		List<AppInfo> appInfos =((SQLQuery) query).addEntity(AppInfo.class).list();
		closeSessionPro(session);
		return appInfos;
	}

	@Override
	public Qrcode getQrcodeByBrandIdInPro(String brandId) {
		Session session=getSessionPro();
		Query query = session.createSQLQuery("select * from qrcode where reference_id='"+brandId+"'");
		Qrcode qrcode =(Qrcode) ((SQLQuery) query).addEntity(Qrcode.class).uniqueResult();
		closeSessionPro(session);
		return qrcode;
	}

	@Override
	public List<DeviceProperty> getPropertyByPid(String brandId) {
		// TODO Auto-generated method stub
		return cloudDao.findByHql(Hql.GET_PROPERTY_BY_PID, brandId);
	}

	@Override
	public List<DevicePropertyTriggerRelationship> getTriggerRelationshipByBrandId(String brandId) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery("select a.* from device_property_trigger_relationship a LEFT JOIN device_property_trigger b on b.id=a.trigger_id where b.brand_id='"+brandId+"'");
		List<DevicePropertyTriggerRelationship> list = ((SQLQuery) query).addEntity(DevicePropertyTriggerRelationship.class).list();
		return list;
	}

	@Override
	public List<AppInfo> getAppInfoByPid(String brandId) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery("SELECT a.* from app_info a LEFT JOIN app_brand_bounding b on a.id=b.app_info_id where b.brand_id='"+brandId+"'");
		List<AppInfo> list = ((SQLQuery) query).addEntity(AppInfo.class).list();
		return list;
	}

	@Override
	public Qrcode getQrcodeByBrandIdInUat(String brandId) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery("select * from qrcode where reference_id='"+brandId+"'");
		Qrcode qrcode =(Qrcode) ((SQLQuery) query).addEntity(Qrcode.class).uniqueResult();
		return qrcode;
	}

	@Override
	public String getSecretByUserId(String id) {
		// TODO Auto-generated method stub
		return (String) cloudDao.getByHql(Hql.GET_SECRET_BY_USER_ID, id);
	}

	@Override
	public List<Brand> getProductByReleaseState(String state) {
		// TODO Auto-generated method stub
		int s=Integer.valueOf(state);
		return cloudDao.findByHql(Hql.GET_BRAND_BY_RELEASE_STATE, s);
	}

	@Override
	public List<SerialPortData> getAllSerialPortDataPro() {
		// TODO Auto-generated method stub
		return cloudDaoPro.findByHql(Hql.ALL_SERIAL_DATA);
	}

	@Override
	public List<SerialPortData> getAllSerialPortDataUat() {
		// TODO Auto-generated method stub
		return cloudDao.findByHql(Hql.ALL_SERIAL_DATA);
	}


}
