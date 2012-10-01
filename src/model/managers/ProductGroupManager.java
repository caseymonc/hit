/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import model.entities.BarCode;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.entities.StorageUnit;

/**
 *
 * @author davidpatty
 */
public class ProductGroupManager {
	
	private Map<ProductContainer, Map<String,ProductGroup>> productGroupsByProductContainer;
	private Map<StorageUnit,Set<ProductGroup>> productGroupsByStorageUnit;
	private Set<ProductGroup> productGroups;
	
	
	/**
	 * Constructor
	 */
	public ProductGroupManager(){
		productGroupsByProductContainer = new HashMap<ProductContainer, Map<String,ProductGroup>>();
		productGroupsByStorageUnit = new HashMap<StorageUnit,Set<ProductGroup>>();
		productGroups = new HashSet<ProductGroup>();
	}

	/**
	 * Get the ProductGroup associated with name in the container
	 * @param name the name index
	 * @param container the container index
	 * @return the ProductGroup associated with name in the container
	 */
	public ProductGroup getProductGroupByName(String name, ProductContainer container) {
		Map<String, ProductGroup> groups = productGroupsByProductContainer.get(container);
		if(groups == null) {
			return null;
		}
		return groups.get(name);
	}
	
	/**
	 * Asks whether group can be added
	 * @param group the group to be added
	 * @return true if the group can be added
	 * @return false if the group cannot be added
	 */
	public boolean canAddProductGroup(ProductGroup group) {
		return group.getContainer().canAddProductGroup(group);
	}
	
	/**
	 * Adds the group to the manager
	 * @param group the group to be added
	 */
	public void addProductGroup(ProductGroup group) {
		assert(canAddProductGroup(group));
		
		if(!canAddProductGroup(group)) {
			throw new IllegalArgumentException();
		}
		
		group.getContainer().addProductGroup(group);
		
		if(productGroupsByStorageUnit.get(group.getStorageUnit()) == null) {
			productGroupsByStorageUnit.put(group.getStorageUnit(), new HashSet<ProductGroup>());
		}
		
		productGroupsByStorageUnit.get(group.getStorageUnit()).add(group);
		
		Map<String, ProductGroup> groups = 
			   productGroupsByProductContainer.get(group.getContainer());
		if(groups == null) {
			productGroupsByProductContainer.put(group.getContainer(), 
				   new HashMap<String, ProductGroup>());
			groups = productGroupsByProductContainer.get(group.getContainer());
		}
		groups.put(group.getName(), group);
		productGroups.add(group);
	}
	
	/**
	 * Asks if the group can be removed
	 * @param group The group to be removed
	 * @return true if the group can be removed
	 * @return false if the group cannot be removed
	 */
	public boolean canRemoveProductGroup(ProductGroup group) {
		if(this.contains(group) && group.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Remove the group from the system
	 * @param group The group to remove
	 */
	public void removeProductGroup(ProductGroup group) {
		assert(canRemoveProductGroup(group));
		
		if(!canRemoveProductGroup(group)) {
			throw new IllegalArgumentException();
		}

		ProductContainer container = group.getContainer();
		container.removeProductGroup(group);
		
		productGroupsByProductContainer.remove(group.getName());
		productGroupsByStorageUnit.get(group.getStorageUnit()).remove(group);
		productGroups.remove(group);
	}
	
	/**
	 * Asks whether group is in the system
	 * @param group
	 * @return true if group is in the system
	 * @return false if group in not in the system
	 */
	public boolean contains(ProductGroup group) {
		if(productGroups.contains(group)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get all of the product groups in unit
	 * @param unit The unit to get the groups from
	 * @return All of the ProductGroups in unit
	 */
	public Set<ProductGroup> getProductGroups(StorageUnit unit){
		return productGroupsByStorageUnit.get(unit);
	}
		
	/**
	 * Remove all of the ProductGroups in unit
	 * @param unit The unit to remove all of the ProductGroups from
	 */
	public void removeProductGroups(StorageUnit unit){
		Set<ProductGroup> groups = productGroupsByStorageUnit.get(unit);
		if(groups == null) {
			return;
		}
		
		ProductGroup[] groupsArray = new ProductGroup[groups.size()];
		int i = 0;
		for(ProductGroup group : groups) {
			groupsArray[i] = group;
			i++;
		}
		
		for(ProductGroup group : groupsArray) {
			this.removeProductGroup(group);
		}
	}

	private void updateProductGroupByName(String newName, String oldName,
			ProductContainer container) {
		Map<String, ProductGroup> groups = productGroupsByProductContainer.get(container);
		if(groups == null) {
			throw new IllegalArgumentException("The ProductContainer does not " +
											"have any ProductGroups in it.");
		}
		
		ProductGroup group = groups.get(oldName);
		groups.remove(oldName);
		groups.put(newName, group);
		
		
	}

	public void updateProductGroup(ProductGroup group, ProductGroup oldGroup) {
		updateProductGroupByName(group.getName(), oldGroup.getName() , oldGroup.getContainer());
		oldGroup.getContainer().updateProductGroupByName(group.getName(), oldGroup.getName());
		productGroups.remove(oldGroup);
		productGroupsByStorageUnit.get(oldGroup.getStorageUnit()).remove(oldGroup);
		oldGroup.update(group);
		productGroupsByStorageUnit.get(oldGroup.getStorageUnit()).add(oldGroup);
		productGroups.add(oldGroup);
		
	}
}
