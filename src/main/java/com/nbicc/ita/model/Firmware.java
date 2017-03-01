package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * @author zhuolin(zl@nbicc.com) 
 * @date 2015年4月1日
 * 设备固件信息 
 */
@Entity(name = "Firmware")
@Table(name = "firmware")
public class Firmware implements CloudEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 637698743414199288L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int firmwareId;
	
	@Column(name = "firmware_path")
	private String firmwarePath;
	
	@Column(name = "firmware_MD5")
	private String md5;
	
	@Column(name = "firmware_size")
	private int firmwareSize;
	
	@Column(name = "firmware_version")
	private String firmwareVersion;
	
	
	
	@Column(name = "hard_version")
	private String hardVersion;

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

	public String getHardVersion() {
		return hardVersion;
	}

	public void setHardVersion(String hardVersion) {
		this.hardVersion = hardVersion;
	}


	public int getFirmwareId() {
		return firmwareId;
	}

	public void setFirmwareId(int firmwareId) {
		this.firmwareId = firmwareId;
	}

	public String getFirmwarePath() {
		return firmwarePath;
	}

	public void setFirmwarePath(String firmwarePath) {
		this.firmwarePath = firmwarePath;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public int getFirmwareSize() {
		return firmwareSize;
	}

	public void setFirmwareSize(int firmwareSize) {
		this.firmwareSize = firmwareSize;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	
	
}
