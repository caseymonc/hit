/*
 * 
 */
package model.controllers;

import model.CoreObjectModel;
import model.entities.*;
import model.managers.ProductGroupManager;
import model.managers.StorageUnitManager;

/** Controller that communicates with the controller in the MVC structure
 *	Acts like a facade in dealing with the rest of the model. 
 * @author davidpatty
 */
public class StorageUnitController {
	
	private CoreObjectModel COM;
	/** Oversees moving an item from one container to another
	 * 
	 */
	public void moveItem(Item item, StorageUnit targetContainer) {
		if(item.getContainer().canRemoveItem(item)){
			item.getContainer().getStorageUnit().removeItem(item);
			item.getContainer().removeItem(item);
			item.move(targetContainer);
		}
		targetContainer.addItem(item);
	}
	
	public boolean canAddStorageUnit(StorageUnit unit) {
		if(unit == null)
			return false;
		
		StorageUnitManager manager = COM.getStorageUnitManager();
		return manager.canAddStorageUnit(unit);
	}
	
	public void addStorageUnit(StorageUnit unit){
		assert(unit != null);
		assert(canAddStorageUnit(unit));
		
		if(!canAddStorageUnit(unit)){
			throw new IllegalArgumentException();
		}
		
		COM.getStorageUnitManager().addStorageUnit(unit);
	}
	
	public boolean canEditStorageUnit(StorageUnit unit, StorageUnit oldUnit) {
		StorageUnitManager manager = COM.getStorageUnitManager();
		
		
		if(oldUnit.getName().equals(unit.getName())){
			return true;
		}
		
		return manager.canAddStorageUnit(unit);
	}
	
	public void editStorageUnit(StorageUnit unit, StorageUnit oldUnit){
		assert(canEditStorageUnit(unit, oldUnit));
		
		if(!canEditStorageUnit(unit, oldUnit)){
			throw new IllegalArgumentException();
		}
		
		oldUnit = COM.getStorageUnitManager().getStorageUnitByName(oldUnit.getName());
		oldUnit.update(unit);
	}
	
	public boolean canDeleteStorageUnit(StorageUnit unit){
		return unit.isEmpty();
	}
	
	public void deleteStorageUnit(StorageUnit unit){
		assert(canDeleteStorageUnit(unit));
		
		if(!canDeleteStorageUnit(unit)){
			throw new IllegalArgumentException("This StorageUnit is not empty");
		}
		StorageUnitManager suManager = COM.getStorageUnitManager();
		ProductGroupManager pgManager = COM.getProductGroupManager();
		suManager.removeStorageUnit(unit);
		pgManager.removeProductGroups(unit);
		
	}

}
