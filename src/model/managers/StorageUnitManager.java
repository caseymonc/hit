/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.StorageUnit;
import model.persistence.PersistentItem;

/**
 *
 * @author davidpatty
 */
public class StorageUnitManager implements PersistentItem{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7094239995948263377L;
	private Map<String,StorageUnit> storageUnits;
	
	/** Constructor */
	public StorageUnitManager(){
		storageUnits = new HashMap<String, StorageUnit>();
	}
	
	/**
	 * Gets a list of all the StorageUnits in the system
	 * @return list of all StorageUnits
	 */
	public List<StorageUnit> getAllStorageUnits(){
		List<StorageUnit> units = new ArrayList<StorageUnit>();
		
		for(StorageUnit unit : storageUnits.values())
			units.add(unit);
		
		return units;
	}
	
	/**
	 * Get a StorageUnit by it's name
	 * @param name The name of the StorageUnit
	 * @return the StorageUnit associated with name
	 */
	public StorageUnit getStorageUnitByName(String name) {
		return storageUnits.get(name);
	}
	
	/**
	 * Ask whether the StorageUnit can be added
	 * @param unit The StorageUnit to add
	 * @return true if the StorageUnit can be added
	 * @return false if the StorageUnit cannot be added
	 */
	public boolean canAddStorageUnit(StorageUnit unit) {
		if(contains(unit)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Add a StorageUnit to the system
	 * @param unit The StorageUnit to be added
	 * @throws IllegalArgumentException if the StorageUnit cannot be added
	 */
	public void addStorageUnit(StorageUnit unit) {
		assert(canAddStorageUnit(unit));
		
		if(!canAddStorageUnit(unit))
			throw new IllegalArgumentException();
		
		storageUnits.put(unit.getName(), unit);
	}
	
	/**
	 * Asks whether the StorageUnit can be removed
	 * @param unit The StorageUnit to be removed
	 * @return true if the StorageUnit can be removed
	 * @return false if the StorageUnit cannot be removed
	 */
	public boolean canRemoveStorageUnit(StorageUnit unit) {
		if(unit == null)
			return false;
		
		if(contains(unit)) {
			return unit.isEmpty();
		}
		return false;
	}
	
	/**
	 * Remove the StorageUnit from the system
	 * @param unit The StorageUnit to be removed
	 * @throws IllegalArgumentException if the StorageUnit cannot be removed
	 */
	public void removeStorageUnit(StorageUnit unit) {
		assert(canRemoveStorageUnit(unit));
		
		if(!canRemoveStorageUnit(unit))
			throw new IllegalArgumentException();
		
		storageUnits.remove(unit.getName());
	}
	
	/**
	 * Asks whether the Manager contains the StorageUnit
	 * @param unit The unit we are asking about
	 * @return true if the StorageUnit is in the manager
	 * @return false if the StorageUnit is not in the manager
	 */
	public boolean contains(StorageUnit unit) {
		if(storageUnits.keySet().contains(unit.getName())) {
			return true;
		}
		return false;
	}

	public void updateStorageUnitByName(String newName, String oldName) {
		StorageUnit unit = storageUnits.get(oldName);
		storageUnits.remove(oldName);
		storageUnits.put(newName, unit);
	}
}
