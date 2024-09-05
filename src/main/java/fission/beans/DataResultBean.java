/*
 * DataResultBean.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package fission.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * DataResultBean is a property-based container class used
 * to hold serializable data for transport as a binary
 * stream.
 */
public class DataResultBean implements Serializable {


	// -------------------------------------------------
	// 				instance / class variables
	// -------------------------------------------------	

	private Map hash;
	
	
	/**
	 * DataResultBean Default constructor
	 */
	public DataResultBean() {
	
		initVariables();
	
	}
	
	// -------------------------------------------------
	// 				private methods
	// -------------------------------------------------		
	
	/**
	 * Helper method for variable initialization 
	 */
	private void initVariables() {
	
		hash = new HashMap();
	}
	
	// -------------------------------------------------
	// 				public methods
	// -------------------------------------------------

	/**
	 * Returns a Serializable object indexed by key
     * <p>
	 * @param key A String containing the lookup key
	 * @return Object The serialized object retrieved
     *         from hash or null if key value is not found
	 */
	public Object getObjectBy(String key) {	
		
		return hash.get(key);	
	}
	
	/**
	 * Maps the specified object with the given key
     *<p>
     * @param key A String containing an Object's key
     * @param value An Object containing script and table 
     *        information retrieved from the database
	 * @exception NullPointerException if the key or value 
     *            is null
	 * @exception IllegalArgumentException if the object is 
     *            not Serializable
	 */
	public void setObjectBy(String key, Object value)  {
	
		if ( !(value instanceof Serializable) ) {
			
			throw new IllegalArgumentException("object must be Serializable");
		}
		hash.put(key, value); 
	}
}