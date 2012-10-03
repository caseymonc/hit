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
 * 
 */
public class ProductGroupController extends ModelController{
	/**
	 * Reference to the Singleton Core Object Model used for accessing the managers
	 */
	private CoreObjectModel COM;
	
	/**
	 * Singleton instance of the ProductGroupController
	 */
	private static ProductGroupController instance;
	
	/**
	 * Constructor
	 */
	private ProductGroupController(){
		COM = CoreObjectModel.getInstance();
	}
	
	/**
	 * Singleton implementation
	 * @return The singleton instance of the class
	 */
	public static ProductGroupController getInstance(){
		if(instance == null){
			instance = new ProductGroupController();
		}
		return instance;
	}
	
	/**
	 * Check method for determining if a product group can be added to a specific 
	 * product container
	 * @param group A ProductGroup that the user wants to add to a specific container
	 * @param container A ProductContainer that will be the destination of param group
	 * @return A boolean representing a success meaning that group can be added to 
	 * container or false indicating that there is something wrong with the container
	 * or the group. Mainly uses ProductContainer's canAddProductGroup to make the 
	 * decision
	 */
	public boolean canAddProductGroup(ProductGroup group, ProductContainer container) {
		if(group == null)
			return false;
		
		if(container == null)
			return false;
		
		return container.canAddProductGroup(group);
	}
	
	/**
	 * Method to add a ProductGroup to a container. Method asserts that all parameters
	 * are valid and should not be called this->canAddProductGroup is called. Uses 
	 * the core object model to get the product group manager and adds the product
	 * using the managers addProductGroup method. After adding the group the observer
	 * is notified of an added product group.
	 * @param group The group that will be added to the container
	 * @param container The container the group will be added to 
	 * @throws IllegalArgumentException if canAddProductGroup returns false
	 */
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
	
	/**
	 * 
	 * @param newUnit A product group with an edited name
	 * @param oldUnit An existing ProductGroup
	 * @return A boolean that defines whether a user can change/edit a productGroup
	 * name. False indicates that the product group name already exists. 
	 */
	public boolean canEditProductGroup(ProductGroup newUnit, ProductGroup oldUnit) {
		ProductGroupManager manager = COM.getProductGroupManager();
		
		//If the names are the same than the productGroups are the exact same
		//ProductGroup because it is imppossible to have ProductGroups with the 
		//same name
		if(oldUnit.getName().equals(newUnit.getName())){
			return true;
		}
		
		return manager.canAddProductGroup(newUnit);
	}
	
	/**
	 * This method is primarily for changing the name or location of a productGroup.
	 * When the ProductGroup is change the observer is notified.
	 * @param editedUnit A storage unit with the same name or an edited name
	 * @param oldUnit An existing storage unit
	 * @throws IllegalArgumentException If canEditProductGroup returns false (meaning
	 * there is an invalid new name for a product group)
	 */
	public void editProductGroup(ProductGroup editedUnit, ProductGroup oldUnit) 
		   throws IllegalArgumentException {

		assert(canEditProductGroup(editedUnit, oldUnit));
		
		if(!canEditProductGroup(editedUnit, oldUnit)){
			throw new IllegalArgumentException();
		}
		
		oldUnit = COM.getProductGroupManager()
					.getProductGroupByName(oldUnit.getName(), oldUnit.getContainer());
		COM.getProductGroupManager().updateProductGroup(editedUnit, oldUnit);
		
		this.setChanged();
		this.notifyObservers(new Hint(editedUnit, Hint.Value.Edit));
	}
	
	/**
	 * Method to check whether a ProductGroup can be deleted. A product can be
	 * deleted if it has no items.
	 * @param group The group that is being checked for ability to delete
	 * @return If the product group has items in it, it can't be deleted.
	 */
	public boolean canDeleteProductGroup(ProductGroup group){
		return group.isEmpty() && COM.getProductGroupManager().canRemoveProductGroup(group);
	}
	
	/**
	 * Deletes a ProductGroup.
	 * @param group  The group that will be deleted
	 * @throws IllegalArgumentException If the ProductGroup is not empty
	 */
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
