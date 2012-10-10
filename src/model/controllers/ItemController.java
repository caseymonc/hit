/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.controllers;

import model.CoreObjectModel;
import model.entities.*;
import model.managers.ItemManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import model.BarCodePrinter;
import model.Hint;

/** Oversees, controls or delegates everything to do with Items
 *
 * @author davidpatty
 */
public class ItemController extends ModelController{

	
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
	private static ItemController instance;
	private List<String> newItemBarCodes;

	/**
	 * Constructor
	 */
	private ItemController() {
		COM = CoreObjectModel.getInstance();
		IM = COM.getItemManager();
		SC = COM.getStorageUnitController();
		newItemBarCodes = new ArrayList<String>();
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
		newItemBarCodes.add(i.getBarCode().getBarCode());
        
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
		}
	}
	
	/** The Storage Unit Controller will take care of the movement
	 * 
	 * @param i
	 * @param pc
	 * @throws CannotMoveItemException 
	 */
	public void moveItem(Item i, ProductContainer target) {
		SC.moveItem(i,target.getStorageUnit());
		this.setChanged();
		this.notifyObservers(new Hint(i, Hint.Value.Move));
	}
	
	/**
	 * 
	 * @param i
	 * @param pc 
	 */
	public void transferItem(Item i, ProductContainer target) {
		moveItem(i, target);
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
	public void printItemLabelsOnAddBatchClose() {
		BarCodePrinter bcp = new BarCodePrinter(newItemBarCodes);
		newItemBarCodes.clear();
	}
	
	public boolean enableAddItem(String count, Date entryDate, String productBarCode) {
		
		if(productBarCode.length() !=12) {
			//invalid barcodes are allowed in HIT demo, but we might not want them...
			return false;
		}

		try{
			Integer.parseInt(count);
		} catch(Exception e) {
			return false;
		}
		
		BarCode productB = new BarCode(productBarCode);
		Product dummyProduct = new Product("dummy",productB, 1,1, new Size(Unit.count, 1));

		return Item.canCreate(null, entryDate, null, dummyProduct, null);	
	}

	

}
