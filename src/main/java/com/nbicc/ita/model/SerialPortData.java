package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "SerialPortData")
@Table(name = "serial_port_data")
public class SerialPortData {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "device_key")
	private String deviceKey;
	
	@Column(name = "tag_name")
	private String tagName;
	
	@Column(name = "format_type")
	private String formatType;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "wr_flag")
	private int wrFlag;
	
	@Column(name = "ch_name")
	private String chName;
	
	@Column(name = "en_name")
	private String enName;
	
	@Column(name = "format_rule")
	private String formatRule;
	
	@Column(name = "dp_id")
	private String dpId;
	
	@Column(name = "property_unit")
	private String propertyUnit;
	
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
	
	public void setDpId(String dpId) {
		this.dpId = dpId;
	}
	
	public String getDpId() {
		return dpId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getFormatType() {
		return formatType;
	}
	
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWrFlag() {
		return wrFlag;
	}

	public void setWrFlag(int wrFlag) {
		this.wrFlag = wrFlag;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getFormatRule() {
		return formatRule;
	}

	public void setFormatRule(String formatRule) {
		this.formatRule = formatRule;
	}

	public String getPropertyUnit() {
		return propertyUnit;
	}

	public void setPropertyUnit(String propertyUnit) {
		this.propertyUnit = propertyUnit;
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
