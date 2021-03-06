/*
 * 
 */
package model.controllers;

import java.sql.SQLException;
import java.util.List;

import model.CoreObjectModel;
import model.Hint;
import model.entities.*;
import model.managers.ProductGroupManager;
import model.managers.StorageUnitManager;
import model.persistence.ConnectionManager;
import model.persistence.PersistentFactory;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.StorageUnitDO;

/** Controller that communicates with the controller in the MVC structure
 *	Acts like a facade in dealing with the rest of the model. 
 * @author cmoncur
 */
public class StorageUnitController extends ModelController{

	/**
	 * CoreObjectModel singleton reference
	 */
	private CoreObjectModel COM;
	private static StorageUnitController instance;
	/** 
	 * Constructor
	 */
	private StorageUnitController(){
		COM = CoreObjectModel.getInstance();
	}
	
	public static StorageUnitController getInstance(){
		if(instance == null){
			instance = new StorageUnitController();
		}
		
		return instance;
	}
	
	public static void resetInstance() {
		instance = null;
	}
	
	/** Oversees moving an item from one container to another
	 * 
	 */
	public void moveItem(Item item, ProductContainer targetContainer) {
		if(item.getContainer().canRemoveItem(item)){
			item.getContainer().removeItem(item);
			item.move(targetContainer);
		}
		targetContainer.addItem(item);
		
	}
	
	/**
	 * Get a List of all StorageUnits
	 * @return a List of all StorageUnits
	 */
	public List<StorageUnit> getAllStorageUnits(){
		return COM.getStorageUnitManager().getAllStorageUnits();
	}
	
	/**
	 * Get a StorageUnit by name
	 * @param name the index by which to get StorageUnit
	 * @return the StorageUnit associated with name
	 */
	public StorageUnit getStorageUnitByName(String name){
		return COM.getStorageUnitManager().getStorageUnitByName(name);
	}
	
	/**
	 * Asks whether unit can be added
	 * @param unit the unit to add
	 * @return true if the name of unit is unique
	 */
	public boolean canAddStorageUnit(StorageUnit unit) {
		if(unit == null) {
			return false;
		}
		
		StorageUnitManager manager = COM.getStorageUnitManager();
		return manager.canAddStorageUnit(unit);
	}
	
	/**
	 * Adds unit to the system
	 * @param unit the StorageUnit to be added
	 */
	public void addStorageUnit(StorageUnit unit){
		assert(unit != null);
		assert(canAddStorageUnit(unit));
		
		if(!canAddStorageUnit(unit)){
			throw new IllegalArgumentException();
		}
		
		//Open Database Connection and start transaction
		ConnectionManager manager = ConnectionManager.getConnectionManager();
		manager.startTransaction();
		
		try{
			COM.getStorageUnitManager().addStorageUnit(unit);
			manager.setTransactionSuccessful();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		//Close the Database and close the connection
		manager.endTransaction();
		
		this.setChanged();
		this.notifyObservers(new Hint(unit, Hint.Value.Add));
		
	}
	
	/**
	 * Asks whether a StorageUnit can be edited
	 * @param unit The new StorageUnit
	 * @param oldUnit The old StorageUnit
	 * @return true if the values of oldUnit can be changed to the
	 * values of unit
	 * @return false of not
	 */
	public boolean canEditStorageUnit(StorageUnit unit, StorageUnit oldUnit) {
		StorageUnitManager manager = COM.getStorageUnitManager();
		
		
		if(oldUnit.getName().equals(unit.getName())){
			return true;
		}
		
		return manager.canAddStorageUnit(unit);
	}
	
	/**
	 * Edit the StorageUnit
	 * @param unit
	 * @param oldUnit
	 */
	public void editStorageUnit(StorageUnit unit, StorageUnit oldUnit){
		assert(canEditStorageUnit(unit, oldUnit));
		
		if(!canEditStorageUnit(unit, oldUnit)){
			throw new IllegalArgumentException();
		}
		
		
		ConnectionManager manager = ConnectionManager.getConnectionManager();
		manager.startTransaction();
		
		try{
			oldUnit = COM.getStorageUnitManager().getStorageUnitByName(oldUnit.getName());
			COM.getStorageUnitManager().updateStorageUnitByName(unit.getName(), oldUnit.getName());
			oldUnit.update(unit);
			DataObject unitDO = oldUnit.getDataObject();
			PersistentFactory.getFactory().getStorageUnitDAO().update(unitDO);
			manager.setTransactionSuccessful();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		//Close the Database and close the connection
		manager.endTransaction();
		this.setChanged();
		this.notifyObservers(new Hint(oldUnit, Hint.Value.Edit));
	}
	
	/**
	 * Asks Whether you can remove the StorageUnit
	 * @param unit the StorageUnit to remove
	 * @return true if the StorageUnit isEmpty
	 * @return false if the StorageUnit is not Empty
	 */
	public boolean canDeleteStorageUnit(StorageUnit unit){
		return unit.isEmpty() && COM.getStorageUnitManager().canRemoveStorageUnit(unit);
	}
	
	/**
	 * Delete unit
	 * @param unit
	 */
	public void deleteStorageUnit(StorageUnit unit){
		assert(canDeleteStorageUnit(unit));
		
		if(!canDeleteStorageUnit(unit)){
			throw new IllegalArgumentException("This StorageUnit is not empty");
		}
		
		ConnectionManager manager = ConnectionManager.getConnectionManager();
		manager.startTransaction();
		
		try{
			StorageUnitManager suManager = COM.getStorageUnitManager();
			ProductGroupManager pgManager = COM.getProductGroupManager();
			suManager.removeStorageUnit(unit);
			pgManager.removeProductGroups(unit);
			manager.setTransactionSuccessful();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		//Close the Database and close the connection
		manager.endTransaction();
		this.setChanged();
		this.notifyObservers(new Hint(unit, Hint.Value.Delete));
	}
}
