package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nbicc.ita.constant.ResponseCode;

/** 
 * @author lishuhuan
 * @date 2016年3月24日
 * 类说明 
 */
@Entity(name = "IotCloudUser")
@Table(name = "iotcloud_user")
public class IotCloudUser {
	
	public IotCloudUser(){
		
	}
	
	public IotCloudUser(String userId, String username, String email, int authority, int userType, String companyName,
			String companyInfo, String socialCode, String legalRepresentative, String companyAddress,
			String technicalPerson, String contactNumber, Date registerTime, String content, String personName,
			String sex, String age, String education, String workAddress) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.authority = authority;
		this.userType = userType;
		this.companyName = companyName;
		this.companyInfo = companyInfo;
		this.socialCode = socialCode;
		this.legalRepresentative = legalRepresentative;
		this.companyAddress = companyAddress;
		this.technicalPerson = technicalPerson;
		this.contactNumber = contactNumber;
		this.registerTime = registerTime;
		this.content = content;
		this.personName = personName;
		this.sex = sex;
		this.age = age;
		this.education = education;
		this.workAddress = workAddress;
	}



	@Id
	@Column(name = "id")
	private String userId;
	
	@Column(name = "username")
	private String username;
	
	
	@Column(name = "password")
	private String password;
	
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "authority")
	private int authority;
	
	@Column(name = "user_type")
	private int userType;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "company_info")
	private String companyInfo;
	
	@Column(name = "social_code")
	private String socialCode;
	
	@Column(name = "legal_representative")
	private String legalRepresentative;
	
	@Column(name = "company_address")
	private String companyAddress;
	
	@Column(name = "technical_person")
	private String technicalPerson;
	
	@Column(name = "contact_number")
	private String contactNumber;
	
	@Column(name = "register_Time")
	private Date registerTime;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "person_name")
	private String personName;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name = "age")
	private String age;
	
	@Column(name = "education")
	private String education;
	
	@Column(name = "work_address")
	private String workAddress;
	
	@Column(name = "entity_version")
	private long entityVersion;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "del_flag")
	private int delFlag;
	
	@Column(name="audit_status")
	private int auditStatus;
	
	@Column(name="audit_reason")
	private String auditReason;

	@Column(name="secret")
	private String secret;
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public String getSecret() {
		return secret;
	}
	
	
	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}
	
	public String getAuditReason() {
		return auditReason;
	}
	
	
	
	
	
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	public String getSocialCode() {
		return socialCode;
	}
	public void setSocialCode(String socialCode) {
		this.socialCode = socialCode;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getTechnicalPerson() {
		return technicalPerson;
	}
	public void setTechnicalPerson(String technicalPerson) {
		this.technicalPerson = technicalPerson;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public long getEntityVersion() {
		return entityVersion;
	}
	public void setEntityVersion(long entityVersion) {
		this.entityVersion = entityVersion;
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
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	@Transient
	private ResponseCode responseCode;
	
	
	public ResponseCode getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
