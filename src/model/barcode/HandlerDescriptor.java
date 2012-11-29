/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

import java.io.Serializable;

/**
 *
 * @author davidpatty
 */
public class HandlerDescriptor implements Serializable {
	
	/**
	 *	
	 */
	private String name;
	
	/**
	 *	Determines the type of handler to use 
	 *		e.g. GoogleHandler or AmazonHandler
	 */
	private String className;
	
	/**
	 *	
	 */
	private String description;
	
	public HandlerDescriptor(String name, String className, String description) {
		this.name = name;
		this.className = className;
		this.description = description;
	}
	
	
	/**
	 * Gets the name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the className
	 * @return className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Gets the description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
}
