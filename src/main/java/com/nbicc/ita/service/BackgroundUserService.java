package com.nbicc.ita.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nbicc.ita.background.dao.BackgroundDao;
import com.nbicc.ita.background.dao.CloudDao;
import com.nbicc.ita.constant.ResponseCode;
import com.nbicc.ita.model.IotCloudUser;
import com.nbicc.ita.util.MobClient;
import com.nbicc.ita.util.PasswordUtil;

/**
 * @author zhuolin(zl@nbicc.com)
 * @date 2016年1月14日 类说明
 */
@Transactional
@Component("backgroundUserService")
public class BackgroundUserService {
	@Autowired
	private BackgroundDao backgroundDao;

	@Autowired
	private CloudDao cloudDao;

	@Autowired
	private static MobClient mobClient;

	public IotCloudUser login(String userName, String userPsw) {
		IotCloudUser user = null;
		if (userName == null || userPsw == null) {
			user = new IotCloudUser();
			user.setResponseCode(ResponseCode.USER_NOT_EXIST);
			return user;
		} else {
			user = backgroundDao.getUserByName(userName);
			if (user == null) {
				user = new IotCloudUser();
				user.setResponseCode(ResponseCode.USER_NOT_EXIST);
				return user;
			}
			if (PasswordUtil.validatePassword(user.getPassword(), userPsw)) {
				user.setResponseCode(ResponseCode.SUCCESS);
				return user;
			} else {
				user = new IotCloudUser();
				user.setResponseCode(ResponseCode.WRONG_PSW);
				return user;
			}
		}

	}

	public Boolean register(String id, String username, String password, String email) {
		IotCloudUser user = new IotCloudUser();
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String secret = str.replace("-", "");
		user.setUserId(id);
		user.setUsername(username);
		user.setPassword(PasswordUtil.generatePassword(password));
		user.setEmail(email);
		user.setSecret(secret);

		user.setAuditStatus(2);

		user.setCreatedAt(new Date());
		user.setCreatedBy(id);
		user.setRegisterTime(new Date());

		cloudDao.add(user);
		return true;

	}

	public IotCloudUser getUserByName(String username) {
		return backgroundDao.getUserByName(username);
	}

	public Boolean registerEnterprise(IotCloudUser user, String companyName, String companyInfo, String socialCode,
			String legalRepresentative, String companyAddress, String technicalPerson, String phoneNumber) {
		user.setCompanyName(companyName);
		user.setCompanyInfo(companyInfo);
		user.setSocialCode(socialCode);
		user.setLegalRepresentative(legalRepresentative);
		user.setCompanyAddress(companyAddress);
		user.setTechnicalPerson(technicalPerson);
		user.setContactNumber(phoneNumber);
		user.setUserType(1);
		user.setUpdatedAt(new Date());
		user.setUpdatedBy(user.getUserId());
		user.setAuditStatus(1);
		cloudDao.update(user);
		return true;
	}

	public Boolean registerPerson(IotCloudUser user, String personName, String sex, String age, String education,
			String workAddress) {
		user.setPersonName(personName);
		user.setSex(sex);
		user.setAge(age);
		user.setEducation(education);
		user.setWorkAddress(workAddress);
		cloudDao.update(user);
		return true;
	}

	public Boolean userExamine(String id, String auditStatus, String auditReason) {
		IotCloudUser user = backgroundDao.getUserById(id);
		user.setAuditStatus(Integer.parseInt(auditStatus));
		user.setAuditReason(auditReason);
		cloudDao.update(user);
		return true;
	}

	public List<IotCloudUser> getUserList(String status) {
		if (null == status) {
			return backgroundDao.getUserListAll();
		} else {
			return backgroundDao.getUserListByStatus(Integer.parseInt(status));
		}
	}

	public IotCloudUser getUserByMobile(String phone) {
		return backgroundDao.getUserByName(phone);
	}

	public IotCloudUser getUserById(String id) {
		return backgroundDao.getUserById(id);
	}
	
	public String getDeveloperKey(String id){
		return backgroundDao.getDeveloperKey(id);
	}

	public String getSecretByUserId(String id) {
		// TODO Auto-generated method stub
		return backgroundDao.getSecretByUserId(id);
	}

}
