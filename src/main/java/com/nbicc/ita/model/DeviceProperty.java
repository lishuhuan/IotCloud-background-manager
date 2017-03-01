package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "DeviceProperty")
@Table(name = "device_property")
public class DeviceProperty {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "ch_name")
	private String chName;
	
	@Column(name = "en_name")
	private String enName;
	
	@Column(name = "wr_flag")
	private int wrFlag;
	
	@Column(name = "is_major")
	private int isMajor;
	
	@Column(name = "is_standard_property")
	private int isStandardProperty;
	
	@Column(name = "format_type")
	private int formatType;
	
	@Column(name = "format_rule")
	private String formatRule;
	
	@Column(name = "property_type")
	private int propertyType;
	
	@Column(name = "is_coordinated")
	private int isCoordinated;
	
	@Column(name = "trigger_ids")
	private String triggerIds;
	
	@Column(name = "property_unit")
	private String propertyUnit;
	
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
	
	public int getIsStandardProperty() {
		return isStandardProperty;
	}
	
	public void setIsStandardProperty(int isStandardProperty) {
		this.isStandardProperty = isStandardProperty;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getWrFlag() {
		return wrFlag;
	}

	public void setWrFlag(int wrFlag) {
		this.wrFlag = wrFlag;
	}

	public int getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(int isMajor) {
		this.isMajor = isMajor;
	}

	public int getFormatType() {
		return formatType;
	}

	public void setFormatType(int formatType) {
		this.formatType = formatType;
	}

	public String getFormatRule() {
		return formatRule;
	}

	public void setFormatRule(String formatRule) {
		this.formatRule = formatRule;
	}

	public int getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(int propertyType) {
		this.propertyType = propertyType;
	}

	public int getIsCoordinated() {
		return isCoordinated;
	}

	public void setIsCoordinated(int isCoordinated) {
		this.isCoordinated = isCoordinated;
	}

	public String getTriggerIds() {
		return triggerIds;
	}

	public void setTriggerIds(String triggerIds) {
		this.triggerIds = triggerIds;
	}

	public String getPropertyUnit() {
		return propertyUnit;
	}

	public void setPropertyUnit(String propertyUnit) {
		this.propertyUnit = propertyUnit;
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
