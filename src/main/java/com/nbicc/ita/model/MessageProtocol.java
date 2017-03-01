package com.nbicc.ita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "MessageProtocol")
@Table(name = "message_protocol")
public class MessageProtocol {
	
	/**
	 * 
	 */

	@Id
	@Column(name = "mp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mpId;
	
	@Column(name = "sdk_key")
	private String sdkKey;
	
	@Column(name = "tag_name")
	private String tagName;
	
	@Column(name = "mp_state")
	private int mpState;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "mp_type")
	private int mpType;
	
	@Column(name = "pass_orientation")
	private int passOrientation;
	
	@Column(name = "ip_port")
	private String ipPort;
	
	@Column(name = "method")
	private String method;
	
	@Column(name = "insert_by_id")
	private String insertById;
	
	@Column(name = "insert_by_name")
	private String insertByName;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "insert_at")
	private Date insertAt;
	
	
	
	@ManyToOne
	@JoinColumn(name = "dmp_id")
	private DeviceMessageProtocol deviceMessageProtocol;

	public int getMpId() {
		return mpId;
	}

	public void setMpId(int mpId) {
		this.mpId = mpId;
	}

	public String getSdkKey() {
		return sdkKey;
	}

	public void setSdkKey(String sdkKey) {
		this.sdkKey = sdkKey;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getMpState() {
		return mpState;
	}

	public void setMpState(int mpState) {
		this.mpState = mpState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMpType() {
		return mpType;
	}

	public void setMpType(int mpType) {
		this.mpType = mpType;
	}

	public int getPassOrientation() {
		return passOrientation;
	}

	public void setPassOrientation(int passOrientation) {
		this.passOrientation = passOrientation;
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getInsertById() {
		return insertById;
	}

	public void setInsertById(String insertById) {
		this.insertById = insertById;
	}

	public String getInsertByName() {
		return insertByName;
	}

	public void setInsertByName(String insertByName) {
		this.insertByName = insertByName;
	}

	public Date getInsertAt() {
		return insertAt;
	}

	public void setInsertAt(Date insertAt) {
		this.insertAt = insertAt;
	}



	public DeviceMessageProtocol getDeviceMessageProtocol() {
		return deviceMessageProtocol;
	}

	public void setDeviceMessageProtocol(DeviceMessageProtocol deviceMessageProtocol) {
		this.deviceMessageProtocol = deviceMessageProtocol;
	}
	
	

}
