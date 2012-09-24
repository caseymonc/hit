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
	
	
	
	public ProductGroupManager(){
		productGroupsByProductContainer = new HashMap<ProductContainer, Map<String,ProductGroup>>();
		productGroupsByStorageUnit = new HashMap<StorageUnit,Set<ProductGroup>>();
		productGroups = new HashSet<ProductGroup>();
	}

	public ProductGroup getProductGroupByName(String name, ProductContainer container) {
		Map<String, ProductGroup> groups = productGroupsByProductContainer.get(container);
		if(groups == null)
			return null;
		return groups.get(name);
	}
	
	public boolean canAddProductGroup(ProductGroup group) {
		return group.getContainer().canAddProductGroup(group);
	}
	
	public void addProductGroup(ProductGroup group) {
		assert(canAddProductGroup(group));
		
		if(!canAddProductGroup(group))
			throw new IllegalArgumentException();
		
		group.getContainer().addProductGroup(group);
		
		if(productGroupsByStorageUnit.get(group.getStorageUnit()) == null){
			productGroupsByStorageUnit.put(group.getStorageUnit()
											, new HashSet<ProductGroup>());
		}
		
		productGroupsByStorageUnit.get(group.getStorageUnit()).add(group);
		
		Map<String, ProductGroup> groups = productGroupsByProductContainer
																.get(group.getContainer());
		if(groups == null){
			productGroupsByProductContainer.put(group.getContainer(), 
														new HashMap<String, ProductGroup>());
			groups = productGroupsByProductContainer.get(group.getContainer());
		}
		groups.put(group.getName(), group);
		productGroups.add(group);
	}
	
	public boolean canRemoveProductGroup(ProductGroup group) {
		if(this.contains(group) && group.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public void removeProductGroup(ProductGroup group) {
		assert(canRemoveProductGroup(group));
		
		if(!canRemoveProductGroup(group))
			throw new IllegalArgumentException();
		
		ProductContainer container = group.getContainer();
		container.removeProductGroup(group);
		
		productGroupsByProductContainer.remove(group.getName());
		productGroupsByStorageUnit.get(group.getStorageUnit()).remove(group);
		productGroups.remove(group);
	}
	
	public boolean contains(ProductGroup unit) {
		if(productGroups.contains(unit)) {
			return true;
		}
		return false;
	}
	
	public Set<ProductGroup> getProductGroups(StorageUnit unit){
		return productGroupsByStorageUnit.get(unit);
	}
		
	public void removeProductGroups(StorageUnit unit){
		Set<ProductGroup> groups = productGroupsByStorageUnit.get(unit);
		if(groups == null)
			return;
		
		ProductGroup[] groupsArray = new ProductGroup[groups.size()];
		int i = 0;
		for(ProductGroup group : groups){
			groupsArray[i] = group;
			i++;
		}
		
		for(ProductGroup group : groupsArray){
			this.removeProductGroup(group);
		}
	}
}
