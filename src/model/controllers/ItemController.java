/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.controllers;

import gui.item.ItemData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.BarCodePrinter;
import model.CoreObjectModel;
import model.Hint;
import model.entities.*;
import model.managers.ItemManager;

/** Oversees, controls or delegates everything to do with Items
 *
 * @author davidpatty
 */
public class ItemController extends ModelController {

	
	/**
	 *  the Core Object Model will interface between the controllers and the Managers.
	 */
	private CoreObjectModel COM;
	
	/**
	 * Manages Items and their indexes
	 */
	private ItemManager IM;
	
	/**
	 * Controls everything to do with Storage Units
	 */
	private StorageUnitController SC;
        
        /**
         * Controls everything to do with Products
         */
        private ProductController PC;
        
	private static ItemController instance;

	/**
	 * Constructor
	 */
	private ItemController() {
		COM = CoreObjectModel.getInstance();
		IM = COM.getItemManager();
		SC = COM.getStorageUnitController();
                PC = COM.getProductController();
	}

	public static ItemController getInstance() {
		if(instance == null){
			instance = new ItemController();
		}
		return instance;
	}
	
	/** 
	 * New Items are added to the Product Container within the target Storage 
         * Unit that contains the Item’s Product. If the Item’s Product is not already
         * in the Storage Unit, it is automatically added to the Storage Unit at the 
         * top level before the Items are added.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void addItem(Item i, StorageUnit su) throws IllegalArgumentException {
		
		if(i == null || su ==  null) {
			throw new IllegalArgumentException();
		}
		su.addItem(i);
		if(i.getContainer() == null) {
			throw new IllegalArgumentException("Item still does not have a specified container");
		}
		IM.addItem(i);
                PC.addItemToProduct(i.getProduct(), i);
        
		System.out.println("Notifying Item Observers");
		this.setChanged();
		this.notifyObservers(new Hint(i, Hint.Value.Add));
	}

	/** Moves the item to removed items 
	 * 
	 * @throws IllegalArgumentException
	 */
	public void removeItem(Item i) throws IllegalArgumentException {
		if(i == null) {
			throw new IllegalArgumentException("Null item");
		}
		
		if(!i.getContainer().canRemoveItem(i)) {
			throw new IllegalArgumentException("Cannot Remove Item");
		} else {
			i.getContainer().removeItem(i);
			IM.removeItem(i);
            PC.removeItemFromProduct(i.getProduct(), i);
		}
		this.setChanged();
		this.notifyObservers(new Hint(i, Hint.Value.Delete));
	}
	
	public void unRemoveItem(Item item, ProductContainer container){
		container.unRemoveItem(item);
		IM.unRemoveItem(item, container);
		PC.unRemoveItemFromProduct(item.getProduct(), item);
		
		this.setChanged();
		this.notifyObservers(new Hint(item, Hint.Value.Add));
	}
	
	/**
	 * Delete an item from the system completely
	 * @param item
	 */
	public void deleteItem(Item item) {
		if(item == null) {
			throw new IllegalArgumentException("Null item");
		}
		
		if(!this.canDeleteItem(item)){
			throw new IllegalArgumentException("!Can Delete Item");
		}
		
		item.getContainer().removeItem(item);
		IM.deleteItem(item);
		
		this.setChanged();
		this.notifyObservers(new Hint(item, Hint.Value.Delete));
	}
	
	private boolean canDeleteItem(Item item) {
		return item.getContainer().canRemoveItem(item);
	}

	/** The Storage Unit Controller will take care of the movement
	 * 
	 * @param i
	 * @param pc
	 * @throws CannotMoveItemException 
	 */
	public void moveItem(String barcode, ProductContainer target) {
		BarCode bc = new BarCode(barcode);
		Item i = IM.getItemByBarCode(bc);
		SC.moveItem(i,target);
		this.setChanged();
		this.notifyObservers(new Hint(i, Hint.Value.Move));
	}
	
	/**
	 * 
	 * @param i
	 * @param pc 
	 */
	public void transferItem(Item i, ProductContainer target) {
		String bc = i.getBarCode().getBarCode();
		moveItem(bc, target);
	}
	
	/**
	 * @throws CannotModifyItemException
	 */
	public void modifyItem(Item i) {// throws CannotModifyItemException {
		
		Item oldItem = IM.getItemByBarCode(i.getBarCode());
		
		if(canModifyItem(i, oldItem) == false) {
			//throw new CannotModifyItemException();
			//does this subsequently disable the ok button?
		} else {
			
			//code to remove...
		}
	}
	

	/** can we modify this item from oldItem to item
	 * @return true if valid state change
	 */
	public boolean canModifyItem(Item item, Item oldItem) {
		return true;
	}
	
	/**
	 * gets the Item Manager
	 * @return IM the item manager
	 */
	public ItemManager getItemManager() {
		return IM;
	}
	/**
	 * This method will print 
	 * After the pdf is displayed the list of items is cleared.
	 */
	public void printItemLabelsOnAddBatchClose(List<ItemData> items) {
		BarCodePrinter bcp = new BarCodePrinter(items);
	}
	
	public boolean enableAddItem(String count, Date entryDate, String productBarCode) {
		
		/*if(productBarCode.length() !=12) {
			//invalid barcodes are allowed in HIT demo, but we might not want them...
			return false;
		}*/

		try{
			int i = Integer.parseInt(count);
			if(i <= 0) {
				return false;
			}
		} catch(Exception e) {
			return false;
		}
		
		BarCode productB = new BarCode(productBarCode);
		try{
			Product dummyProduct = new Product("dummy",productB, 1, 1, new Size(Unit.count, 1));
			return Item.canCreate(null, entryDate, null, dummyProduct, null);	
		}catch(IllegalArgumentException e){
			return false;
		}
		
	}

	/**
	 * Updates the specified item with a new entry date
	 * @param i The item to be modified
	 * @param newEntryDate 
	 */
	public void updateItemsEntryDate(Item i, Date newEntryDate) {
		assert(i.canSetEntryDate(newEntryDate));
		i.setEntryDate(newEntryDate);
		i.calculateExpirationDate();
		this.setChanged();
		this.notifyObservers(new Hint(i, Hint.Value.Edit));
	}
	
	public boolean hasItem(String barcode)
	{
		BarCode b = new BarCode(barcode);
		if (!b.isValid())
			return false;
		return IM.getItemByBarCode(b) != null;
	}
        
    public Item getItemByBarCode(String barcode){
        assert(barcode.equals("") == false);
        
        if(barcode.equals("")){
            throw new IllegalArgumentException();
        }

        return IM.getItemByBarCode(new BarCode(barcode));
    }

}
