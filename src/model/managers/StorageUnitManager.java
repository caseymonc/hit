/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.StorageUnit;

/**
 *
 * @author davidpatty
 */
public class StorageUnitManager {
	
	private Map<String,StorageUnit> storageUnits;
	private Map<Item,StorageUnit> storageUnitByItem;
	private Map<Product,Set<StorageUnit>> storageUnitsByProduct;
	private Map<Product, ProductGroup> productByProductGroup;
	
	public StorageUnit getStorageUnitByName(String name) {
		return storageUnits.get(name);
	}
	
	public boolean canAddStorageUnit(StorageUnit unit) {
		if(contains(unit)) {
			return false;
		}
		return true;
	}
	
	public void addStorageUnit(StorageUnit unit) {
		assert(canAddStorageUnit(unit));
		
		if(!canAddStorageUnit(unit))
			throw new IllegalArgumentException();
		
		storageUnits.put(unit.getName(), unit);
	}
	
	public boolean canRemoveStorageUnit(StorageUnit unit) {
		if(contains(unit)) {
			return true;
		}
		return false;
	}
	
	public void removeStorageUnit(StorageUnit unit) {
		assert(canRemoveStorageUnit(unit));
		
		if(!canRemoveStorageUnit(unit))
			throw new IllegalArgumentException();
		
		storageUnits.remove(unit.getName());
	}
	
	public boolean contains(StorageUnit unit) {
		if(storageUnits.keySet().contains(unit.getName())) {
			return true;
		}
		return false;
	}
}
