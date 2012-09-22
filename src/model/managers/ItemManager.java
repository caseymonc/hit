/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.util.HashMap;

import model.entities.Item;
import model.entities.BarCode;
import model.entities.ProductGroup;
import model.entities.ProductContainer;
import model.entities.StorageUnit;


/**
 *
 * @author davidpatty
 */
public class ItemManager 
{
	/**
	 *  Maps each barcode to its item. all unremoved items belong here
	 */
	private HashMap<BarCode, Item> itemsByBarCode;
		
	/**
	 *  Maps each barcode to its item. all removed items belong here.
	 */
	private HashMap<BarCode, Item> removedItemsByBarCode;
	
	public ItemManager() {
		itemsByBarCode = new HashMap<BarCode, Item>();
		removedItemsByBarCode = new HashMap<BarCode, Item>();
	}
	
	public Item getItemByBarCode(BarCode barcode)
	{
		return null;
	}
	
	/**
	 * @throws CantAddItemException
	 */
	public void addProductGroupToItem(Item i, ProductGroup pg) {
		//can always add dont need to check if i can.
	}
	
	/**
	 * @throws CantAddItemException
	 */
	public void addItem(Item i) {
		itemsByBarCode.put(i.getBarCode(), i);
	}

	/** Removes the Item from items by barcode, and puts it in the removed items index
	 * 
	 * @param i 
	 */
	public void removeItem(Item i) {
		
		assert(itemsByBarCode.containsKey(i.getBarCode()));
		
		//pull out item from itemsByBarCode
		itemsByBarCode.remove(i.getBarCode());
		//add item to removed items history stuff.
		removedItemsByBarCode.put(i.getBarCode(), i);
	}
	
	
	
	/** Determines if the specified Item is contained by the ProductGroup
	 * 
	 * @param i Item 
	 * @param pg ProductGroup
	 * @return 
	 */
	public boolean itemIsInProductGroup(Item i, ProductGroup pg) {
		return true;
	}
	
	/** Determines if the specified Item is contained by the StorageUnit
	 * 
	 * @param i Item 
	 * @param su StorageUnit
	 * @return 
	 */
	public boolean itemIsInStorageUnit(Item i, StorageUnit su) {
		return true;
	}
	
	/**
	 * @param i Item
	 */
	public boolean itemIsInRemovedItems(Item i) {
		return (removedItemsByBarCode.containsKey(i.getBarCode())
			   && removedItemsByBarCode.containsValue(i));
	}
}
