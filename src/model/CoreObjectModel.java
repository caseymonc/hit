/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.Date;

import common.util.DateUtils;

import model.controllers.*;
import model.entities.*;
import model.managers.*;
import model.persistence.PersistentItem;
import model.persistence.PersistentStore;

/** CoreObjectModel
 * This class stores the rootUnit which is the root of a tree 
 * structure that points to every object in the HIT system. It
 * also contains a Map of all Products and a Map of all Items.
 * These maps make is easier to iterate through all products
 * and items.
 *
 * @author davidpatty
 */
public class CoreObjectModel implements PersistentItem{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7518635251729938948L;
	private static CoreObjectModel _instance;
	private int lastBarCode;

	private ProductManager productManager;
	private ProductGroupManager productGroupManager;
	private StorageUnitManager storageUnitManager;
	private ItemManager itemManager;
	private Date sinceDate;
	  
	public static CoreObjectModel getInstance()
	{
		if(_instance == null)
		{
			if(PersistentStore.getSelectedStore() != null){
				//System.out.println("Get CoreObjectModel From Persistent Serializer");
				_instance = PersistentStore.getSelectedStore().getCoreObjectModel();
			}
			
			if(_instance == null){
				//System.out.println("Get new CoreObjectModel");
				_instance = new CoreObjectModel();
			}
		}
		return _instance;
	}
	private CoreObjectModel()
	{
		productManager = new ProductManager();
		productGroupManager = new ProductGroupManager();
		storageUnitManager = new StorageUnitManager();
		itemManager = new ItemManager();
		lastBarCode = 0;
	}

	public void resetInstance(){
		_instance = null;
		ProductController.resetInstance();
		ItemController.resetInstance();
		ProductGroupController.resetInstance();
		StorageUnitController.resetInstance();
	}
	
	public ProductManager getProductManager() {
		return productManager;
	}

	public ProductGroupManager getProductGroupManager() {
		return productGroupManager;
	}

	public StorageUnitManager getStorageUnitManager() {
		return storageUnitManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}
	
	public ProductController getProductController() {
		return ProductController.getInstance();
	}

	public ProductGroupController getProductGroupController() {
		return ProductGroupController.getInstance();
	}

	public StorageUnitController getStorageUnitController() {
		return StorageUnitController.getInstance();
	}

	public ItemController getItemController() {
		return ItemController.getInstance();
	}
	
	public int getLastBarCode() {
		return lastBarCode;
	}
	
	public void incLastBarCode(){
		lastBarCode++;
	}
	
	public Date getSinceDate() {
		Date toReturn;
		if(sinceDate == null){
			sinceDate = new Date();
			sinceDate.setYear(0);
			toReturn = sinceDate;
		}else{
			toReturn = sinceDate;
			sinceDate = new Date();
		}
		return toReturn;
	}
}
