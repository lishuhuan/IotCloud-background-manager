package com.nbicc.ita.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "DevicePropertyStandard")
@Table(name = "device_property_standard")
public class DevicePropertyStandard {
	
	@Id
	@Column(name = "id")
	private String id;
	
	
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
	
	
	@Column(name = "format_type")
	private int formatType;
	
	@Column(name = "format_rule")
	private String formatRule;
	
	@Column(name = "property_type")
	private int propertyType;
	
	@Column(name = "is_coordinated")
	private int isCoordinated;
	
	
	@Column(name = "property_unit")
	private String propertyUnit;
	
	@Column(name = "entity_version")
	private long entityVersion;
	

	@Column(name = "del_flag")
	private int delFlag;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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


	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	
	

}
