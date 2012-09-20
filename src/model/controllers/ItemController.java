/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.controllers;

import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.ProductContainer;
import model.entities.StorageUnit;
import model.CoreObjectModel;

import model.controllers.*;
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
	 * Constructor
	 */
	public ItemController() {
		COM = CoreObjectModel.getInstance();
		PM = COM.getProductManager();
		IM = COM.getItemManager();
		SM = COM.getStorageUnitManager();
		PGM = COM.getProductGroupManager();
	}

	/** 
	 * New Items are added to the Product Container within the target Storage Unit that contains the Item’s
	 * Product. If the Item’s Product is not already in the Storage Unit, it is automatically added to the Storage
	 * Unit at the top level before the Items are added.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void addItem(Item i, ProductContainer pc) throws IllegalArgumentException {
		
		if(i == null || pc ==  null) {
			throw new IllegalArgumentException();
		}

		Product p = i.getProduct();
		if(pc instanceof ProductGroup) {
			ProductGroup pg = (ProductGroup) pc;
			assert(PM.productIsInProductGroup(p,pg) == PGM.productGroupHasProduct(pg, p));

			//ask PM if it knows p is in pg
			if(!PM.productIsInProductGroup(p, pg)) {
				//add Product to PGM first
				//update _indexProductGroupByProduct
				PM.addProductGroupToProductsLocations(p, pg);
				//update _indexProductByProductGroup
				PGM.addProductToProductGroup(pg, p);
				//Make sure the SM and PM are synced
				assert(PM.productIsInProductGroup(p,pg) == PGM.productGroupHasProduct(pg, p));
			}
			
			//Make sure they are synced before
			assert(IM.itemIsInProductGroup(i,pg) == PGM.productGroupHasItem(pg,i));

			//add Item to SU next
			//uses _indexItemByStorageUnit
			PGM.addItemToProductGroup(pg, i);

			//uses _indexStorageUnitByItem
			IM.addProductGroupToItem(i, pg);

			//Make sure they are synced after adding
			assert(IM.itemIsInProductGroup(i,pg) == PGM.productGroupHasItem(pg,i));
		
		} else {
			
			assert(pc instanceof StorageUnit);

			StorageUnit su = (StorageUnit) pc;
			assert(PM.productIsInStorageUnit(p,su) == SM.storageUnitHasProduct(su, p));
			
			//ask PM if it knows p is in su
			if(!PM.productIsInStorageUnit(p, su)) {
				//add Product to SU first
				//update _indexStorageUnitByProduct
				PM.addStorageUnitToProductsLocations(p, su);
				//update _indexProductByStorageUnit
				SM.addProductToStorageUnit(su, p);
				//Make sure the SM and PM are synced
				assert(PM.productIsInStorageUnit(p,su) == SM.storageUnitHasProduct(su, p));
			}

			//Make sure they are synced before
			assert(IM.itemIsInStorageUnit(i,su) == SM.storageUnitHasItem(su,i));

			//add Item to SU next
			//uses _indexItemByStorageUnit
			SM.addItemToStorageUnit(su, i);

			//uses _indexStorageUnitByItem
			IM.addStorageUnitToItem(i, su);

			//Make sure they are synced after adding
			assert(IM.itemIsInStorageUnit(i,su) == SM.storageUnitHasItem(su,i));
		}
	}
	

	/** 
	 * 
	 */
	public void removeItem(Item i) throws Exception {
		//where is the item?
		//tell the IM to remove the item
		IM.removeItem(i);
		assert(IM.itemIsInRemovedItems(i));

		StorageUnit su = IM.getStorageUnitByItem(i);
		if(su != null) {
			//if the SM has the 
			SM.removeItemFromStorageUnit(i, su);			
		}
		//regardless if these are null they still return same fals==false
		assert(IM.itemIsInStorageUnit(i, su) == SM.storageUnitHasItem(su, i));

		ProductGroup pg = IM.getProductGroupByItem(i);
		if(pg != null) {
			//if the PGM has the 
			PGM.removeItemFromProductGroup(i, pg);
		}
		//regardless if these are null they still return same fals==false
		assert(IM.itemIsInProductGroup(i, pg) == PGM.productGroupHasItem(pg, i));
		//who else needs to know about this change?
	}
	
	/**
	 *
	 */
	public void moveItem(Item i, ProductContainer pc) throws Exception {

		//have to look this one up, but it seems
//		if(pc instanceof StorageUnit) {
//			if(!PM.productIsInStorageUnit(p, su)) {
//				//add Product to SU first
//				//uses _indexProductToStorageUnit
//				PM.addProduct(p, su);
//			}
//			
//		}
//
//		//add Item to SU next
//		//uses _indexItemToStorageUnit
//		IM.addItem(i, su);
	}
	
	/**
	 * @throws CannotModifyItemException
	 */
	public void modifyItem(Item i) throws Exception {
		
		Item oldItem = IM.getItemByBarCode(i.getBarCode());
		
		if(this.canModifyItem(i, oldItem) == false) {
			
		} else {
			//code to remove...
		}
	}

//	/**
//	 *  i dont think we need this.
//	 */
//	public boolean canRemoveItem(Item i) {
//		if(COM.)
//	}
	
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

}
