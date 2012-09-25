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
	/** 
	 * 
	 * @param i
	 * @return true if the conditions are met and false otherwise
	 */
	public boolean canAddItem(Item i) {

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
		} else if(!i.hasProductShelfLife() && i.getExpirationDate() != null) {
			return false;
		}
		//Barcode	Unique among all Items.
		assert(!itemsByBarCode.containsKey(i.getBarCode()));
		assert(!removedItemsByBarCode.containsKey(i.getBarCode()));
		
		return true;
	}
	
	/** the item is 
	 * @param i Item
	 */
	public boolean itemIsInRemovedItems(Item i) {
		if(itemsByBarCode.containsKey(i.getBarCode())) {
			assert(!removedItemsByBarCode.containsKey(i.getBarCode()));
			return false;
		}
		
		return (removedItemsByBarCode.containsKey(i.getBarCode())
			   && removedItemsByBarCode.containsValue(i));
	}
}
