package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "AppInfo")
@Table(name = "app_info")
public class AppInfo {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "app_info_id")
	private short appInfoId;
	
	@Column(name = "app_name_a")
	private String appNameAndroid;
	
	@Column(name = "app_name_i")
	private String appNameIos;
	
	@Column(name = "app_version_a")
	private String appVersionA;
	
	@Column(name = "app_version_i")
	private String appVersionI;
	
	@Column(name = "app_path_a")
	private String appPathA;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "apns_id")
	private ApnsInfo apnsInfo;
	
	@Column(name = "app_describe")
    private String appDescribe;
	
	@Column(name = "app_sdk")
	private String appSdk;
	
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
	
	@Column(name = "mob_key_a")
	private String mobKeyA;
	
	@Column(name = "mob_key_i")
	private String mobKeyI;
	
	@Column(name = "accessId_a")
	private String accessIdA;
	
	@Column(name = "secretKey_a")
	private String secretKeyA;
	
	@Column(name = "accessId_i")
	private String accessIdI;
	
	@Column(name = "secretKey_i")
	private String secretKeyI;
	
	@Column(name = "apns_env")
	private String apnsEnv;
	
	public void setApnsEnv(String apnsEnv) {
		this.apnsEnv = apnsEnv;
	}
	
	public String getApnsEnv() {
		return apnsEnv;
	}
	

	public String getAccessIdA() {
		return accessIdA;
	}

	public void setAccessIdA(String accessIdA) {
		this.accessIdA = accessIdA;
	}

	public String getSecretKeyA() {
		return secretKeyA;
	}

	public void setSecretKeyA(String secretKeyA) {
		this.secretKeyA = secretKeyA;
	}

	public String getAccessIdI() {
		return accessIdI;
	}

	public void setAccessIdI(String accessIdI) {
		this.accessIdI = accessIdI;
	}

	public String getSecretKeyI() {
		return secretKeyI;
	}

	public void setSecretKeyI(String secretKeyI) {
		this.secretKeyI = secretKeyI;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(short appInfoId) {
		this.appInfoId = appInfoId;
	}

	public String getAppNameAndroid() {
		return appNameAndroid;
	}

	public void setAppNameAndroid(String appNameAndroid) {
		this.appNameAndroid = appNameAndroid;
	}

	public String getAppNameIos() {
		return appNameIos;
	}

	public void setAppNameIos(String appNameIos) {
		this.appNameIos = appNameIos;
	}

	public String getAppVersionA() {
		return appVersionA;
	}

	public void setAppVersionA(String appVersionA) {
		this.appVersionA = appVersionA;
	}

	public String getAppVersionI() {
		return appVersionI;
	}

	public void setAppVersionI(String appVersionI) {
		this.appVersionI = appVersionI;
	}

	public String getAppPathA() {
		return appPathA;
	}

	public void setAppPathA(String appPathA) {
		this.appPathA = appPathA;
	}

	public ApnsInfo getApnsInfo() {
		return apnsInfo;
	}

	public void setApnsInfo(ApnsInfo apnsInfo) {
		this.apnsInfo = apnsInfo;
	}

	public String getAppDescribe() {
		return appDescribe;
	}

	public void setAppDescribe(String appDescribe) {
		this.appDescribe = appDescribe;
	}

	public String getAppSdk() {
		return appSdk;
	}

	public void setAppSdk(String appSdk) {
		this.appSdk = appSdk;
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

	public String getMobKeyA() {
		return mobKeyA;
	}

	public void setMobKeyA(String mobKeyA) {
		this.mobKeyA = mobKeyA;
	}

	public String getMobKeyI() {
		return mobKeyI;
	}

	public void setMobKeyI(String mobKeyI) {
		this.mobKeyI = mobKeyI;
	}
	
	

}
