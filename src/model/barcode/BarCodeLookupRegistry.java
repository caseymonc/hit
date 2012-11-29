/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.Serializable;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author davidpatty
 */
public class BarCodeLookupRegistry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7271944782536415034L;
	private Map<String, BarCodeLookupHandler> handlers;
	private Map<String, HandlerDescriptor> descriptors;
	private BarCodeLookupHandler last;
	private BarCodeLookupHandler first;
	/**
	 * Constructor
	 */
	public BarCodeLookupRegistry() {
		handlers = new HashMap<String, BarCodeLookupHandler>();
		descriptors = new HashMap<String, HandlerDescriptor>();
		last = null;
		first = null;
		LoadConfig();
	}
	
	/**
	 *	registers a handler to use in the lookup process
	 * @param descriptor 
	 */
	public void RegisterHandler(HandlerDescriptor descriptor) {
		BarCodeLookupHandler handler = CreateHandler(descriptor);

		if (handler != null) {
			if(first == null)
				first = handler;
			if(last != null)
				last.setNext(handler);
			last = handler;
			handlers.put(descriptor.getClassName(), handler);
			descriptors.put(descriptor.getClassName(), descriptor);
			//register the next for the previous one?
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
	public BarCodeLookupHandler GetFirstHandler() {
		return first;
	}
	/**
	 * Creates a new handler to be used
	 * @return the created Handler?
	 */
	public BarCodeLookupHandler CreateHandler(HandlerDescriptor descriptor) {
		
//		String name = descriptor.getName();
		String className = descriptor.getClassName();
//		String description = descriptor.getDescription();
		BarCodeLookupHandler handler;

		try {
		
			Class cls = Class.forName(className);
			handler = (BarCodeLookupHandler) cls.newInstance();
			handler.setDescriptor(descriptor);
			
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
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("registry.txt"));
			String line;
			while ((line = br.readLine()) != null)
			{
				String[] l = line.split(",");
				HandlerDescriptor descriptor = new HandlerDescriptor(l[0],l[1],l[2]);
				RegisterHandler(descriptor);
			}
		} catch (IOException ex) {
			HandlerDescriptor descriptor1 = new HandlerDescriptor("GoogleHandler",
											"model.barcode.GoogleHandler","Google");
			HandlerDescriptor descriptor2 = new HandlerDescriptor("SearchUPCHandler",
										"model.barcode.SearchUPCHandler","Search Upc");
			HandlerDescriptor descriptor4 = new HandlerDescriptor("UPCDatabase",
									"model.barcode.UPCDatabaseHandler","UPC database");
			//screen scraper so it should go last...
			HandlerDescriptor descriptor3 = new HandlerDescriptor("UPCDatabaseCom",
								"model.barcode.UPCDatabaseComHandler","UPCDatabaseCom");
			RegisterHandler(descriptor1);
			RegisterHandler(descriptor2);
			RegisterHandler(descriptor3);
			RegisterHandler(descriptor4);
		} finally {
			try {
				if(br != null)
					br.close();
			} catch (IOException ex) {
				System.out.println(BarCodeLookupRegistry.class.getName() + ex.getMessage());
			}
		}
	}
	
	/**
	 *  saves the registered handlers for persistence
	 */
	public void SaveConfig() {
		BufferedWriter bw = null;
		try {
			String output = "";
			for (HandlerDescriptor descriptor : descriptors.values()) {
				
				output+=descriptor.getName() +",";
				output+=descriptor.getClassName()+",";
				output+=descriptor.getDescription()+"\n";
			}
			System.out.println("output"+output);
			
			bw = new BufferedWriter(new FileWriter("registry.txt"));
			bw.write(output);

		} catch (IOException ex) {
			System.out.println(BarCodeLookupRegistry.class.getName() + ex.getMessage());
		} finally {
			try {
				bw.close();
			} catch (IOException ex) {
				System.out.println(BarCodeLookupRegistry.class.getName() + ex.getMessage());
			}
		}
	}
	
}
