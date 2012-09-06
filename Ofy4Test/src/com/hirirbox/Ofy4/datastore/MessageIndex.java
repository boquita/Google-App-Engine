package com.hirirbox.Ofy4.datastore;

import java.util.Date;
import java.util.List;


import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

/**
 * Entity Group  {@link Message}.
 * 
 * 
 * @author Ivan Rodriguez - Hodeian
 *
 */
@Entity
public class MessageIndex {

	@Id 
	private Long id;
	@Parent @Load (User.class)
	private Ref<Message> parentKey;
	@Index
	private List<Key<User>> receiverKeyList;
	@Index
	private Date date;
	
	public MessageIndex() {
		super();
	}
	

	/**
	 * @return the receiverKeyList
	 */
	public List<Key<User>> getReceiverKeyList() {
		return receiverKeyList;
	}
	/**
	 * @param receiverKeyList the receiverKeyList to set
	 */
	public void setReceiverKeyList(List<Key<User>> receiverKeyList) {
		this.receiverKeyList = receiverKeyList;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	/**
	 * @return the parentKey
	 */
	public Ref<Message> getParentKey() {
		return parentKey;
	}


	/**
	 * @param parentKey the parentKey to set
	 */
	public void setParentKey(Ref<Message> parentKey) {
		this.parentKey = parentKey;
	}
	
}
