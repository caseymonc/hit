/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.controllers;

import model.CoreObjectModel;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.managers.ProductGroupManager;

/**
 *
 * @author davidpatty
 */
public class ProductGroupController {
	private CoreObjectModel COM;
	/** Oversees moving an item from one container to another
	 * 
	 */
	
	public boolean canAddProductGroup(ProductGroup group, ProductContainer container) {
		if(group == null)
			return false;
		
		if(container == null)
			return false;
		
		return container.canAddProductGroup(group);
	}
	
	public void addProductGroup(ProductGroup group, ProductContainer container){
		assert(group != null);
		assert(container != null);
		assert(canAddProductGroup(group, container));
		
		if(!canAddProductGroup(group, container)){
			throw new IllegalArgumentException();
		}
		
		container.addProductGroup(group);
		COM.getProductGroupManager().addProductGroup(group, container);
	}
	
	public boolean canEditProductGroup(ProductGroup unit, ProductGroup oldUnit) {
		ProductGroupManager manager = COM.getProductGroupManager();
		
		
		if(oldUnit.getName().equals(unit.getName())){
			return true;
		}
		
		return manager.canAddProductGroup(unit);
	}
	
	public void editProductGroup(ProductGroup unit, ProductGroup oldUnit){
		assert(canEditProductGroup(unit, oldUnit));
		
		if(!canEditProductGroup(unit, oldUnit)){
			throw new IllegalArgumentException();
		}
		
		oldUnit = COM.getProductGroupManager().getProductGroupByName(oldUnit.getName());
		oldUnit.update(unit);
	}
	
	public boolean canDeleteProductGroup(ProductGroup unit){
		return unit.isEmpty();
	}
	
	public void deleteProductGroup(ProductGroup unit){
		assert(canDeleteProductGroup(unit));
		
		if(!canDeleteProductGroup(unit)){
			throw new IllegalArgumentException("This ProductGroup is not empty");
		}
		ProductGroupManager suManager = COM.getProductGroupManager();
		ProductGroupManager pgManager = COM.getProductGroupManager();
		suManager.removeProductGroup(unit);
		//pgManager.removeProductGroups(unit);
		
	}
}
