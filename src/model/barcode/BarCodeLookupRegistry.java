/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author davidpatty
 */
public class BarCodeLookupRegistry {

	private Map<String, BarCodeLookupHandler> handlers;
	private Map<String, HandlerDescriptor> descriptors;

	/**
	 * Constructor
	 */
	public BarCodeLookupRegistry() {
		handlers = new HashMap<String, BarCodeLookupHandler>();
		descriptors = new HashMap<String, HandlerDescriptor>();
	}
	
	/**
	 *	registers a handler to use in the lookup process
	 * @param descriptor 
	 */
	public void RegisterHandler(HandlerDescriptor descriptor) {
		BarCodeLookupHandler handler = CreateHandler(descriptor);
		
		if (handler != null) {
			handlers.put(descriptor.getClassName(), handler);
			descriptors.put(descriptor.getClassName(), descriptor);
		} else {
			System.err.println("Unable to Register" + descriptor.getClassName());
		}
	}
	
	/**
	 *	removes the handler from the available handlers list
	 * @param descriptor 
	 */
	public void UnregisterHandler(HandlerDescriptor descriptor) {
		String className = descriptor.getClassName();
		if (handlers.containsKey(className)) {
			handlers.remove(className);
			descriptors.remove(className);
		} else {
			System.err.println("Unable to Remove " + className + ": Does Not Exist");
		}
	}
	
	/**
	 * gets the registered handlers for use in looking up a product
	 * @return all the registered handlers
	 */
	public Map<String, BarCodeLookupHandler> GetAvailableHandlers() {
		return handlers;
	}
	
	/**
	 *	Creates a new handler to be used
	 * @return the created Handler?
	 */
	public BarCodeLookupHandler CreateHandler(HandlerDescriptor descriptor) {
		
		String name = descriptor.getName();
		String className = descriptor.getClassName();
		String description = descriptor.getDescription();
		BarCodeLookupHandler handler;

		try {
		
			Class cls = Class.forName(className);
			handler = (BarCodeLookupHandler) cls.newInstance();
		
		} catch(Exception e) {
			
			System.err.println(e.getMessage());
			return null;
		}

		return handler;
	}
	
	/**
	 * loads the registered handlers from previous runs
	 */
	public void LoadConfig() {
		//TODO: finish this method
//		foreach (classname in the file) {
//			HandlerDescriptor descriptor = new HandlerDescriptor(name,className,description);
//			RegisterHandler(descriptor);
//		}
	}
	
	/**
	 *  saves the registered handlers for persistence
	 */
	public void SaveConfig() {
		//TODO: finish this method
//		foreach (HandlerDescriptor descriptor : descriptors.values()) {
//			store into a file	
//			name,className,description
//		}
	}
}
