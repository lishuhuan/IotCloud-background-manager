package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "GatewayRelationship")
@Table(name = "gateway_relationship")
public class GatewayRelationship {

	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "gateway_id")
	private String gatewayId;
	
	@Column(name = "sub_equipment")
	private String subEquipment;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "created_by")
	private String createdBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getSubEquipment() {
		return subEquipment;
	}

	public void setSubEquipment(String subEquipment) {
		this.subEquipment = subEquipment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
}
