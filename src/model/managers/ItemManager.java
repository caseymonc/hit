/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.util.HashMap;
import model.entities.BarCode;
import model.entities.Item;
import model.persistence.PersistentItem;


/**
 *
 * @author davidpatty
 */
public class ItemManager implements PersistentItem
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8816747283776538422L;

	/**
	 *  Maps each barcode to its item. all unremoved items belong here
	 */
	private HashMap<BarCode, Item> itemsByBarCode;
		
	/**
	 *  Maps each barcode to its item. all removed items belong here.
	 */
	private HashMap<BarCode, Item> removedItemsByBarCode;
	
	/**
	 * Constructor
	 */
	public ItemManager() {
		itemsByBarCode = new HashMap<BarCode, Item>();
		removedItemsByBarCode = new HashMap<BarCode, Item>();
	}
	
	public Item getItemByBarCode(BarCode barcode)
	{
		return itemsByBarCode.get(barcode);
	}
	
	/** adds the item
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
		//tell the item to change its state
		i.remove();
		//pull out item from itemsByBarCode
		itemsByBarCode.remove(i.getBarCode());
		//add item to removed items history stuff.
		removedItemsByBarCode.put(i.getBarCode(), i);
	}
	/**  can you add this item?
	 * 
	 * @param i
	 * @return true if the conditions are met and false otherwise
	 */
	public boolean canAddItem(Item i) {

		if(i == null) {
			return false;
		} else if(i.getProduct() == null) {
			//Product	Must be non-empty.
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
	
	/** has the item already been removed?
	 * @param i Item
	 * @return true if it has been removed. 
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
