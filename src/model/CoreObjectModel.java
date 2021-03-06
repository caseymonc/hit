/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.Date;

import model.barcode.BarCodeLookupRegistry;
import model.controllers.*;
import model.managers.*;
import model.persistence.PersistentItem;
import model.persistence.PersistentFactory;
import model.persistence.DataObjects.DataObject;

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
	private BarCodeLookupRegistry registry;
	
	public static CoreObjectModel getInstance()
	{
		if(_instance == null)
		{
			if(PersistentFactory.getFactory() != null){
				//System.out.println("Get CoreObjectModel From Persistent Serializer");
				_instance = PersistentFactory.getFactory().getCoreObjectModel();
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
		registry = new BarCodeLookupRegistry();
	}

	public static CoreObjectModel getNewInstance() {
		_instance = new CoreObjectModel();
		return _instance;
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
	
	public Date getSinceDate(){
		return sinceDate;
	}
	
	public Date useSinceDate() {
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
	
	public boolean hasSinceDate(){
		return sinceDate != null;
	}

	public BarCodeLookupRegistry getRegistry() {
		return registry;
	}
	
	@Override
	public DataObject getDataObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLastBarCodeInt(int lastBarCode) {
		this.lastBarCode = lastBarCode;
	}

	public void setSinceDate(Date sinceDateValue) {
		sinceDate = sinceDateValue;
	}
}
