package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ApnsInfo")
@Table(name = "apns_info")
public class ApnsInfo {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "apns_id")
	private short apnsId;
	
	@Column(name = "apns_environment")
	private int apnsEnvironment;
	
	@Column(name = "apns_prod_cert")
	private String apnsProdCert;
	
	@Column(name = "apns_prod_cert_psw")
	private String apnsProdCertPsw;
	
	@Column(name = "apns_prod_cert_deadline")
	private String apnsProdCertDeadline;
	
	@Column(name = "apns_dev_cert")
	private String apnsDevCert;
	
	@Column(name = "apns_dev_cert_psw")
	private String apnsDevCertPsw;
	
	@Column(name = "apns_dev_cert_deadline")
	private String apnsDevCertDeadline;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getApnsId() {
		return apnsId;
	}

	public void setApnsId(short apnsId) {
		this.apnsId = apnsId;
	}

	public int getApnsEnvironment() {
		return apnsEnvironment;
	}

	public void setApnsEnvironment(int apnsEnvironment) {
		this.apnsEnvironment = apnsEnvironment;
	}

	public String getApnsProdCert() {
		return apnsProdCert;
	}

	public void setApnsProdCert(String apnsProdCert) {
		this.apnsProdCert = apnsProdCert;
	}

	public String getApnsProdCertPsw() {
		return apnsProdCertPsw;
	}

	public void setApnsProdCertPsw(String apnsProdCertPsw) {
		this.apnsProdCertPsw = apnsProdCertPsw;
	}

	public String getApnsProdCertDeadline() {
		return apnsProdCertDeadline;
	}

	public void setApnsProdCertDeadline(String apnsProdCertDeadline) {
		this.apnsProdCertDeadline = apnsProdCertDeadline;
	}

	public String getApnsDevCert() {
		return apnsDevCert;
	}

	public void setApnsDevCert(String apnsDevCert) {
		this.apnsDevCert = apnsDevCert;
	}

	public String getApnsDevCertPsw() {
		return apnsDevCertPsw;
	}

	public void setApnsDevCertPsw(String apnsDevCertPsw) {
		this.apnsDevCertPsw = apnsDevCertPsw;
	}

	public String getApnsDevCertDeadline() {
		return apnsDevCertDeadline;
	}

	public void setApnsDevCertDeadline(String apnsDevCertDeadline) {
		this.apnsDevCertDeadline = apnsDevCertDeadline;
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
	
	

}
