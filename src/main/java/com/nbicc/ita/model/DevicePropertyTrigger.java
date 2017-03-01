package com.nbicc.ita.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "DevicePropertyTrigger")
@Table(name = "device_property_trigger")
public class DevicePropertyTrigger{
	


	@Id
	@Column(name = "id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@Column(name = "trigger_event")
	private int triggerEvent;
	
	@Column(name = "trigger_name")
	private String triggerName;
	
	@Column(name = "priority")
	private int priority;
	
	@Column(name = "push_rule")
	private String pushRule;
	
	@Column(name = "status")
	private int status;
	
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
	
	@OneToMany(mappedBy = "trigger", fetch = FetchType.LAZY)
	private  Set<DevicePropertyTriggerRelationship> relationships;
	
	
	public void setRelationships(Set<DevicePropertyTriggerRelationship> relationships) {
		this.relationships = relationships;
	}
	
	public Set<DevicePropertyTriggerRelationship> getRelationships() {
		return relationships;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
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

	public int getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(int triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getPushRule() {
		return pushRule;
	}

	public void setPushRule(String pushRule) {
		this.pushRule = pushRule;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
