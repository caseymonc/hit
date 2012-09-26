/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.controllers;

import model.CoreObjectModel;
import model.entities.*;
import model.managers.*;


/**
 *
 * @author davidpatty
 */
public class ItemController {

	
	/**
	 *  the Core Object Model will interface between the controllers and the Managers.
	 */
	private CoreObjectModel COM;
	
	/**
	 * Manages Products and their indexes
	 */
	private ProductManager PM;

	/**
	 * Manages Items and their indexes
	 */
	private ItemManager IM;

	/**
	 * Manages the StorageUnits and their indexes
	 */
	private StorageUnitManager SM;
	
	/**
	 * Manages the ProductGroups and their indexes
	 */
	private ProductGroupManager PGM;
	
	/**
	 * Controls everything to do with products
	 */
	private ProductController PC;
	
	/**
	 * Controls everything to do with Storage Units
	 */
	private StorageUnitController SC;
	
	/**
	 * Controls everything to do with Product Groups
	 */
	private ProductGroupController PGC;

	/**
	 * Constructor
	 */
	public ItemController() {
		COM = CoreObjectModel.getInstance();
		PM = COM.getProductManager();
		IM = COM.getItemManager();
//		SM = COM.getStorageUnitManager();
//		PGM = COM.getProductGroupManager();
//		
//		PC = COM.getProductController();
		SC = COM.getStorageUnitController();
//		PGC = COM.getProductGroupController();
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
	}

	/** Moves the item to removed items 
	 * 
	 * @throws IllegalArgumentException
	 */
	public void removeItem(Item i) throws IllegalArgumentException {
		if(i == null) {
			throw new IllegalArgumentException();
		}
		i.getContainer().removeItem(i);
		IM.removeItem(i);
	}
	
	/** The Storage Unit Controller will take care of the movement
	 * 
	 * @param i
	 * @param pc
	 * @throws CannotMoveItemException 
	 */
	public void moveItem(Item i, ProductContainer target) {
		SC.moveItem(i,target.getStorageUnit());
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
	
	/**
	 * 
	 */
	public boolean productContainerAlreadyHasItem(ProductContainer pc, Item i) {
		return true;
	}

	/**
	 * 
	 */
	public boolean canModifyItem(Item item, Item oldItem) {
		return true;
	}
	
	public ItemManager getItemManager() {
		return IM;
	}

}
