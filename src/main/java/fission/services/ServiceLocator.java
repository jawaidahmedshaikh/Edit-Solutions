/*
 * ServiceLocator.java      Version 1.1  08/06/2001
 * 
 * Copyright (c) 2000 Systems
 Engineering Group, LLC. All Rights Reserved. 
 * 
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part 
 * without the written permission ofSystems Engineering Group, LLC.
 */
package fission.services;

import javax.naming.NameAlreadyBoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * ServiceLocator provides a lookup service to bind and retrieve
 * common services such as CommonGlobal or StorageManager
 */
public class ServiceLocator {

	private Map map;
    
    public ServiceLocator() {
    
        init();
    }
	
	private final void init() {
	
		map = new HashMap();
	}
	
	/**
	 * Binds a name to an object.
	 *
	 * @param name ServiceLocator name of the object
	 * @param obj  The bound object
	 * @exception  NameAlreadyBoundException
	 */
	public void bind(String name, Object obj) throws
		  			  NameAlreadyBoundException {
	
		if (map.containsKey(name))  {
		
			throw new NameAlreadyBoundException(name + " already bound.");
		}
		
		map.put(name, obj);
	}
	
	/**
	 * Unbinds a name from an object.
	 *
	 * @param name The name of the object to unbind
	 * @param obj  The bound object
	 */
	public void unbind(String name) {
	
		map.remove(name);
	}
	
	/**
	 * Unbinds a name from an object.
	 *
	 * @param name The name of the object to unbind
	 * @param obj  The bound object
	 */
	public void rebind(String name, Object obj) {
	
		map.put(name, obj);
	}
	
	/**
	 * Locates an object by name.
	 * 
	 * @param name The name of the object to lookup
	 * @return The bound object, or null if the object is not found.
	 */
	public Object lookup(String name)  {
	
		return map.get(name);
	}
}