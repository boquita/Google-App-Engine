package com.hirirbox.Ofy4.datastore;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Parent;



/**
 * Message Entity
 * 
 * Objectify annotations
 * 
 * @author Ivan Rodriguez - Hodeian
 *
 */
@Cache @Entity
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	@Parent @Load (Message.class)
	private Ref<User> parentKey;
	
	private Date creationDate;
	
	private String subject;
	
	private String message; 
	
	@Ignore 
	private User user;
	
	
	public Message() {

	}
	
	@OnLoad
	private void getParent () {
		
		System.out.println ("doSomething ejecutado en Onload");
		setUser(parentKey.get());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * Devuelve el asunto: Reclamacion, Consulta, Sugerencia, ...
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the parentKey
	 */
	public Ref<User> getParentKey() {
		return parentKey;
	}

	/**
	 * @param parentKey the parentKey to set
	 */
	public void setParentKey(Ref parentKey) {
		this.parentKey = parentKey;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	
}
