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

/** 
 * @author zhuolin(zl@nbicc.com) 
 * @date 2015年11月27日
 * 自定义消息协议字段配置
 */
@Entity(name = "MessageProtocolItem")
@Table(name = "message_protocol_item")
public class MessageProtocolItem implements CloudEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6182645964912425600L;

	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemId;
	
	@ManyToOne
	@JoinColumn(name = "mp_id")
	private MessageProtocol mProtocol;
	
	@Column(name = "item_type")
	private int itemType;
	
	@Column(name = "item_content")
	private String itemContent;
	
	@Column(name = "item_title")
	private String itemTitle;
	
	@Column(name = "item_description")
	private String itemDescription;
	
	@Column(name = "item_seq")
	private Integer itemSeq;
	
	@Column(name = "bit_length")
	private Integer bitLength;
	
	@Column(name = "start_index")
	private Integer startIndex;
	
	@Column(name = "end_index")
	private Integer endIndex;
	
	@ManyToOne
	@JoinColumn(name = "owner_message_protocol_id")
	private MessageProtocol ownMProtocol;
	
	@Column(name = "owner_type")
	private Integer ownerType;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "insert_at")
	private Date insertAt;
	
	@Column(name = "item_operation")
	private int itemOperation;
	
	@Column(name = "resolving")
	private float resolving;
	
	@Column(name = "increment")
	private float increment;
	
	
	
	

	public float getResolving() {
		return resolving;
	}

	public void setResolving(float resolving) {
		this.resolving = resolving;
	}

	public float getIncrement() {
		return increment;
	}

	public void setIncrement(float increment) {
		this.increment = increment;
	}

	public int getItemOperation() {
		return itemOperation;
	}

	public void setItemOperation(int itemOperation) {
		this.itemOperation = itemOperation;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public MessageProtocol getmProtocol() {
		return mProtocol;
	}

	public void setmProtocol(MessageProtocol mProtocol) {
		this.mProtocol = mProtocol;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}

	public int getBitLength() {
		return bitLength;
	}

	public void setBitLength(int bitLength) {
		this.bitLength = bitLength;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public MessageProtocol getOwnMProtocol() {
		return ownMProtocol;
	}

	public void setOwnMProtocol(MessageProtocol ownMProtocol) {
		this.ownMProtocol = ownMProtocol;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public Date getInsertAt() {
		return insertAt;
	}

	public void setInsertAt(Date insertAt) {
		this.insertAt = insertAt;
	}
}
