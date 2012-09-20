/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;
import java.util.HashMap;
import java.util.Set;
import model.entities.Item;
import model.entities.Product;
import model.entities.StorageUnit;

/**
 *
 * @author davidpatty
 */
public class StorageUnitManager {
	
	/**
	 * Maps each StorageUnit to its Name.
	 */
	private HashMap<String, StorageUnit> storageUnitsByName;

	/**
	 * Maps each Product to its StorageUnits.
	 */
	private HashMap<Product, Set<StorageUnit>> storageUnitsByProduct;

	/**
	 * Maps each StorageUnit to its Items.
	 */
	private HashMap<StorageUnit, Set<Item>> itemsByStorageUnit;
	
	public StorageUnitManager() {
		storageUnitsByName =  new HashMap<String, StorageUnit>();
		storageUnitsByProduct =  new HashMap<Product, Set<StorageUnit>>();
		itemsByStorageUnit =  new HashMap<StorageUnit, Set<Item>>();
	}
	
	
	/**
	 * @throws CantAddItemException
	 */
	public void addItemToStorageUnit(StorageUnit su, Item i) {
		
	}
	
	/**
	 * @throws CantAddProductException
	 */
	public void addProductToStorageUnit(StorageUnit su, Product p) {
		
	}
	
	/** Removes the item from the storage unit
	 * @throws IllegalArgumentException
	 * @param i
	 * @param su 
	 */
	public void removeItemFromStorageUnit(Item i, StorageUnit su) {
		if(i == null || su == null) {
			throw new IllegalArgumentException();
		}
		//make sure the storage unit is in the index
		assert(itemsByStorageUnit.containsKey(su));
		//make sure the Item is mapped to the storage Unit
		assert((itemsByStorageUnit.get(su)).contains(i));

		itemsByStorageUnit.get(su).remove(i);
	}
	
	/** Determines if the Storage Unit contains the item
	 * 
	 * @param su
	 * @param i
	 * @return true if contained else false
	 */
	public boolean storageUnitHasItem(StorageUnit su, Item i) {
		return true;
	}
	
	/** Determines if the Storage Unit contains the item
	 * 
	 * @param su
	 * @param p
	 * @return true if contained else false
	 */
	public boolean storageUnitHasProduct(StorageUnit su, Product p) {
		return true;
	}
}
