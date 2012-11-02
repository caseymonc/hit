/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.util.Collection;
import java.util.HashMap;
import reports.visitors.ItemVisitor;
import model.entities.BarCode;
import model.entities.Item;
import model.entities.ProductContainer;
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
		
		assert(canAddItem(i));
		
		if(!canAddItem(i)){
			throw new IllegalArgumentException("Cannot add Item: " + i);
		}
		
		if(canAddItem(i)) {
			itemsByBarCode.put(i.getBarCode(), i);		
		}

	}

	/** Removes the Item from items by barcode, and puts it in the removed items index
	 * 
	 * @param itemToRemove 
	 */
	public void removeItem(Item itemToRemove) {
		
		assert(itemsByBarCode.containsKey(itemToRemove.getBarCode()));
		//tell the item to change its state
		itemToRemove.remove();
		//pull out item from itemsByBarCode
		itemsByBarCode.remove(itemToRemove.getBarCode());
		//add item to removed items history stuff.
		removedItemsByBarCode.put(itemToRemove.getBarCode(), itemToRemove);
	}
	
	public void unRemoveItem(Item item, ProductContainer container){
		item.unRemove(container);
		itemsByBarCode.put(item.getBarCode(), item);
		removedItemsByBarCode.remove(item.getBarCode());
	}
	
	public void deleteItem(Item itemToDelete){
		itemToDelete.delete();
		itemsByBarCode.remove(itemToDelete.getBarCode());
	}
	
	/**  can you add this item?
	 * 
	 * @param newItem
	 * @return true if the conditions are met and false otherwise
	 */
	public boolean canAddItem(Item newItem) {
		//is a valid item
		if(newItem == null) {
			return false;
		} else if(newItem.getProduct() == null) { //Product Must be non-empty.
			return false;
		} else if(!newItem.getBarCode().isValid()) { // Have a valid barcode
			return false;
		} else if(!newItem.hasValidEntryDate()) { // Have a valid entry date
			return false;
		} else if(newItem.getExitDate() != null) { // Can't be a removed item
			return false;
		} else if(!newItem.hasProductShelfLife() && newItem.getExpirationDate() != null) {
			return false;
		}
		//Barcode	Unique among all Items.		
		return (!itemsByBarCode.containsKey(newItem.getBarCode()) 
			   && !removedItemsByBarCode.containsKey(newItem.getBarCode()));
	}
	
	/** has the item already been removed?
	 * @param item Item
	 * @return true if it has been removed. 
	 */
	public boolean itemIsInRemovedItems(Item item) {
		return removedItemsByBarCode.containsKey(item.getBarCode());
	}
	
	public void accept(ItemVisitor visitor){
		for(Item item : itemsByBarCode.values()){
			visitor.visitItem(item);
		}
	}
	
	public void acceptRemoved(ItemVisitor visitor){
		for(Item item : removedItemsByBarCode.values()){
			visitor.visitItem(item);
		}
	}
}
