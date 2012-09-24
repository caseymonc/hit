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
		if(i == null) {
			throw new IllegalArgumentException();
		}
		if(canAddItem(i)) {
			itemsByBarCode.put(i.getBarCode(), i);		
		}

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
	
	public boolean canAddItem(Item i) {
		/*	
//Entry Date	Must be non‐empty.
//Entry Date	Cannot be in the future
//Entry Date	Cannot be prior to 1/1/2000
//Exit Time	This attribute is defined only if the Item has been removed from storage.
//Exit Time	Cannot be in the future or prior to 12 AM on the Item’s Entry Date
//Expiration Date	This attribute is defined only if the Product’s Shelf Life attribute has been specified.
//Container	Empty if the Item has been removed from storage.
//Container	Non‐empty if the Item has not been removed from storage. (Before it is removed, an Item is contained in one Product Container. After it is removed, it is contained in no Product Containers.)
*/		
		if(i == null) {
			return false;
		} else if(i.getProduct() == null) {
			//Product	Must be non‐empty.
			return false;
		} else if(!i.getBarCode().isValid()) {
			return false;
		} else if(!i.hasValidEntryDate()) {
			return false;
		} else if(i.getExitDate() != null) {
			return false;
		} else if() {
			
		}
		//Barcode	Unique among all Items.
		assert(!itemsByBarCode.containsKey(i.getBarCode()));
		assert(!removedItemsByBarCode.containsKey(i.getBarCode()));
		
		
		return true;
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
