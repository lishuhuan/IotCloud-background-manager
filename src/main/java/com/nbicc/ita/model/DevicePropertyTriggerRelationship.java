package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "DevicePropertyTriggerRelationship")
@Table(name = "device_property_trigger_relationship")
public class DevicePropertyTriggerRelationship{
	

	@Id
	@Column(name = "id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "property_id")
	private DeviceProperty property;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trigger_id")
	@JsonIgnore
	private  DevicePropertyTrigger trigger;
	
	@Column(name = "rule_type")
	private int ruleType;
	
	@Column(name = "operator")
	private int operator;
	
	@Column(name = "compare_value")
	private String compareValue;
	
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

	public DeviceProperty getProperty() {
		return property;
	}

	public void setProperty(DeviceProperty property) {
		this.property = property;
	}

	public DevicePropertyTrigger getTrigger() {
		return trigger;
	}

	public void setTrigger(DevicePropertyTrigger trigger) {
		this.trigger = trigger;
	}

	public int getRuleType() {
		return ruleType;
	}

	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public String getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(String compareValue) {
		this.compareValue = compareValue;
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
