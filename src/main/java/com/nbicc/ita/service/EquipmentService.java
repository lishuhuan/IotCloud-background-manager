package com.nbicc.ita.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nbicc.ita.background.dao.BackgroundDao;
import com.nbicc.ita.background.dao.CloudDao;
import com.nbicc.ita.model.Brand;
import com.nbicc.ita.model.GatewayRelationship;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.model.MessageProtocolItem;

@Transactional
@Component("equipmentService")
public class EquipmentService {

	@Autowired
	private CloudDao cloudDao;
	
	@Autowired
	private BackgroundDao backgroundDao;
	
	
	public List<Brand> getAuditByUser(String id){
		return backgroundDao.getAuditByUser(id);
	}
	
	public Boolean deleteProduct(String id){
		Brand brand=backgroundDao.getAuditById(id);
		cloudDao.delete(brand);
		return true;
	}
	
	
	/*public Boolean addAttr(String registerId,String title,String description,String opType,String dataType,String itemContent,String length){
		IotCloudAudit iotCloudAudit=backgroundDao.getAuditById(registerId);
		DeviceMessageProtocol deviceMessageProtocol=new DeviceMessageProtocol();
		if(null==iotCloudAudit.getDeviceMessageProtocol()){
			deviceMessageProtocol.setSdkKey(iotCloudAudit.getBrand().getDeviceKey());
			deviceMessageProtocol.setTagName("3222");
			deviceMessageProtocol.setMpState(0);
			deviceMessageProtocol.setDescription(iotCloudAudit.getProductName()+"上送数据");
			deviceMessageProtocol.setMpType(1);
			deviceMessageProtocol.setPassOrientation(0);
			deviceMessageProtocol.setInsertById(iotCloudAudit.getRegisterId());
			deviceMessageProtocol.setInsertByName(iotCloudAudit.getUser().getUsername());
			deviceMessageProtocol.setInsertAt(new Date());
			cloudDao.add(deviceMessageProtocol);
			iotCloudAudit.setDeviceMessageProtocol(deviceMessageProtocol);
			cloudDao.update(iotCloudAudit);			
		}
		else{
			deviceMessageProtocol=iotCloudAudit.getDeviceMessageProtocol();		
		}
		MessageProtocol messageProtocol=new MessageProtocol();
		messageProtocol.setDeviceMessageProtocol(deviceMessageProtocol);
		messageProtocol.setSdkKey(iotCloudAudit.getBrand().getDeviceKey());
		String dmpId=String.valueOf(iotCloudAudit.getDeviceMessageProtocol().getDmpId());
		int count=backgroundDao.getDeviceCount(dmpId);
		int t=count+1;
		String tagName=String.format("%4s", Integer.toHexString(t)).replace(' ', '0');
		messageProtocol.setTagName(tagName);
		messageProtocol.setMpState(0);
		messageProtocol.setDescription(description);
		messageProtocol.setMpType(1);
		messageProtocol.setPassOrientation(0);
		messageProtocol.setInsertById(iotCloudAudit.getRegisterId());
		messageProtocol.setInsertByName(iotCloudAudit.getUser().getUsername());
		messageProtocol.setInsertAt(new Date());
		cloudDao.add(messageProtocol);
		
		MessageProtocolItem messageProtocolItem=new MessageProtocolItem();
		messageProtocolItem.setmProtocol(messageProtocol);
		messageProtocolItem.setItemType(Integer.parseInt(dataType));
		messageProtocolItem.setItemContent(itemContent);
		messageProtocolItem.setItemTitle(title);
		messageProtocolItem.setItemDescription(description);
		messageProtocolItem.setInsertAt(new Date());
		messageProtocolItem.setItemOperation(Integer.parseInt(opType));
		messageProtocolItem.setItemSeq(1);
		messageProtocolItem.setBitLength(Integer.parseInt(length));
		cloudDao.add(messageProtocolItem);
		return true;
	}*/
	
	public Boolean editAttr(String itemId,String title,String description,String opType,String dataType,String itemContent,String length,String resolving,String increment){
		MessageProtocolItem messageProtocolItem=backgroundDao.getMessageProtocolItemById(Integer.parseInt(itemId));
		messageProtocolItem.setItemTitle(title);
		messageProtocolItem.setItemDescription(description);
		messageProtocolItem.setItemOperation(Integer.parseInt(opType));
		messageProtocolItem.setBitLength(Integer.parseInt(length));
		messageProtocolItem.setItemType(Integer.parseInt(dataType));
		messageProtocolItem.setItemContent(itemContent);
		if(null!=resolving.trim() && !"".equals(resolving.trim())){
			messageProtocolItem.setResolving(Float.parseFloat(resolving));
		}
		if(null!=increment.trim() && !"".equals(increment.trim())){
			messageProtocolItem.setIncrement(Float.parseFloat(increment));
		}
		cloudDao.update(messageProtocolItem);
		return true;
	}
	
	public Brand getAuitById(String id){
		return backgroundDao.getAuditById(id);
	}
	
	public Brand getAuitByRegisterId(String id){
		return backgroundDao.getAuitByRegisterId(id);
	}

	
	public List<MessageProtocolItem> getItemById(String id){
		return backgroundDao.getItemById(Integer.parseInt(id));
	}
	
	public String getUserIdByDeviceKey(String key){
		return backgroundDao.getUserIdByDeviceKey(key);
	}
	
	public Boolean setGatewayParts(String id,String protocol,String gatewayIds,String userid){
		Brand brand=backgroundDao.getAuditById(id);
		brand.setGatewayPartsProtocol(protocol);
		cloudDao.update(brand);
		
		backgroundDao.deleteGatewayRelationshipBySub(id);
		
		String[] gatewayid=gatewayIds.split(",");
		for(String gid:gatewayid){
			GatewayRelationship relationship=new GatewayRelationship();
			String uid=UUID.randomUUID().toString().replace("-", "");
			relationship.setId(uid);
			relationship.setSubEquipment(id);
			relationship.setGatewayId(gid);
			relationship.setCreatedAt(new Date());
			relationship.setCreatedBy(userid);
			cloudDao.add(relationship);		
		}
		return true;
	}
	
	public List<Brand> getGatewayList(String userid){
		return backgroundDao.getGatewayList(userid);
	}
	
	public String getPartsProtocol(String brandId){
		return backgroundDao.getPartsProtocol(brandId);
	}
	
	public String getGatewayIds(String brandId){
		return backgroundDao.getGatewayIds(brandId);
	}

	public String getApnsEnvByDeviceKey(String key) {
		// TODO Auto-generated method stub
		return backgroundDao.getApnsEnvByDeviceKey(key);
	}
	

}
