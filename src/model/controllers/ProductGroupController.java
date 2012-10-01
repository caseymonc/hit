/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.controllers;

import model.CoreObjectModel;
import model.Hint;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.managers.ProductGroupManager;

/**
 *
 * @author davidpatty
 */
public class ProductGroupController extends ModelController{
	private CoreObjectModel COM;
	private static ProductGroupController instance;
	
	/** Oversees moving an item from one container to another
	 * 
	 */
	
	private ProductGroupController(){
		COM = CoreObjectModel.getInstance();
	}
	
	public static ProductGroupController getInstance(){
		if(instance == null){
			instance = new ProductGroupController();
		}
		return instance;
	}
	
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
		
		COM.getProductGroupManager().addProductGroup(group);
		this.setChanged();
		this.notifyObservers(new Hint(group, Hint.Value.Add));
	}
	
	public boolean canEditProductGroup(ProductGroup group, ProductGroup oldUnit) {
		ProductGroupManager manager = COM.getProductGroupManager();
		
		
		if(oldUnit.getName().equals(group.getName())){
			return true;
		}
		
		return manager.canAddProductGroup(group);
	}
	
	public void editProductGroup(ProductGroup unit, ProductGroup oldUnit) 
		   throws IllegalArgumentException {

		assert(canEditProductGroup(unit, oldUnit));
		
		if(!canEditProductGroup(group, oldUnit)){
			throw new IllegalArgumentException();
		}
		
		oldUnit = COM.getProductGroupManager()
					.getProductGroupByName(oldUnit.getName(), oldUnit.getContainer());
		COM.getProductGroupManager().updateProductGroup(group, oldUnit);
		
		this.setChanged();
		this.notifyObservers(new Hint(group, Hint.Value.Edit));
	}
	
	public boolean canDeleteProductGroup(ProductGroup group){
		return group.isEmpty() && COM.getProductGroupManager().canRemoveProductGroup(group);
	}
	
	public void deleteProductGroup(ProductGroup group){
		assert(canDeleteProductGroup(group));
		
		if(!canDeleteProductGroup(group)){
			throw new IllegalArgumentException("This ProductGroup is not empty");
		}
		ProductGroupManager pgManager = COM.getProductGroupManager();
		pgManager.removeProductGroup(group);
		//pgManager.removeProductGroups(unit);
		this.setChanged();
		this.notifyObservers(new Hint(group, Hint.Value.Delete));
		
	}
}
