package com.hirirbox.Ofy4;

import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.hirirbox.Ofy4.datastore.Message;
import com.hirirbox.Ofy4.datastore.MessageIndex;
import com.hirirbox.Ofy4.datastore.User;


/**
 */
public class DSConnection  {

    static {

    	ObjectifyService.register(Message.class);
    	ObjectifyService.register(MessageIndex.class);
    	ObjectifyService.register(User.class);
    
    }
   
    
	static {
		ObjectifyService.factory(); //setFactory(new ObjectifyFactory());
	
	}
		
	/**
	* @return our extension to Objectify
	*/
	public Objectify ofy() {
		return (Objectify)ObjectifyService.begin();
	}
		
	/**
	* @return our extension to ObjectifyFactory
	*/
	public static ObjectifyFactory factory() {
		return (ObjectifyFactory)ObjectifyService.factory();
	}
		
		
    private static DSConnection instance;

    /**
     * Devuelve la instancia estática de DSConnection, encargada de realizar las operaciones de DataStore
     * @return Objeto DSConnection encargado de realizar las operaciones de DataStore
     */
    public static DSConnection getInstance () {
    	if (instance == null) {
    		System.out.println("DSConnection::getInstance() --> instance == null");	    		
    		instance = new DSConnection();
    		System.out.println("DSConnection::getInstance() --> instance created SUCCESSFULLY!");
    	}
    	return instance;
    }
    
    /**
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public Object getEntity (Key entityKey){
    	return ofy().load().key(entityKey).get();
    }
    
    /**
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map getEntities (Iterable entities){
		return ofy().load().keys(entities);
    }
    
    
    /**
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map getEntities (Key... entities){
    	return ofy().load().keys(entities);
    }
    
    
    /**
     * Persiste una entidad en el DataStore.
     * 
     * Es un wrapper que desacopla la v3 de Objectify para v4.
     * 
     * @param entity
     * @return
     */
 	public Key putEntity (Object entity){
     	return ofy().save().entity(entity).now();
    }
     
    
    /**
     * Persiste una entidad en el DataStore.
     * 
     * Es un wrapper que desacopla la v3 de Objectify para v4.
     * 
     * @param entity
     * @return
     */
 	public Object putEntity (Iterable entities){
 		
 		return ofy().save().entities(entities).now();
    }
 	
 	/**
 	 * Pertiste varias entidades en el DataStore
 	 * 
 	 * Es un wrapper que desacopla la v3 de Objectify para v4.
 	 * 
 	 * @param entitys
 	 * @return
 	 */
    @SuppressWarnings("unchecked")
 	public Result<Map<Key<Object>, Object>> putEntities (Object... entitys){
     	return ofy().save().entities(entitys);
    }
    
    /**
     * Elimina una entidad a través de su Key.
     * 
     * Es un wrapper que desacopla la v3 de Objectify para v4.
     * 
     * @param entityKey
     * @return
     */
    public Result<Void> deleteEntity (Key entityKey){
    	return ofy().delete().key(entityKey);
    }
    /**
     * Elimina un "grupo" de entidades a través de su Key.
     * 
     * Es un wrapper que desacopla la v3 de Objectify para v4.
     * @param entitys
     * @return
     */
    public Result<Void> deleteEntity (Iterable entities) {
    	return ofy().delete().entities(entities);
    }
    /**
     * Elimina una entidad a través de su Key.
     * 
     * Es un wrapper que desacopla la v3 de Objectify para v4.
     * 
     * @param entityKey
     * @return
     */
    public Result<Void> deleteEntity (Object entity){
    	return ofy().delete().entity(entity);
    }
    
    /**
     * Elimina un "grupo" de entidades a través de su Key.
     * 
     * Es un wrapper que desacopla la v3 de Objectify para v4.
     * @param entitys
     * @return
     */
    public Result<Void> deleteEntities (Object... entities) {
    	return ofy().delete().entities(entities);
    }
    
    
    
    public DSConnection() {
    	super();
    }
     
}
