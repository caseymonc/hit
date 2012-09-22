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
	
	private Map<String,ProductGroup> productGroups;
	private Map<StorageUnit,Set<ProductGroup>> productGroupsByStorageUnit;
	
	
	
	public ProductGroupManager(){
		productGroups = new HashMap<String,ProductGroup>();
		productGroupsByStorageUnit = new HashMap<StorageUnit,Set<ProductGroup>>();
	}

	public ProductGroup getProductGroupByName(String name) {
		return productGroups.get(name);
	}
	
	public boolean canAddProductGroup(ProductGroup unit) {
		if(contains(unit)) {
			return false;
		}
		return true;
	}
	
	public void addProductGroup(ProductGroup group, ProductContainer container) {
		assert(canAddProductGroup(group));
		
		if(!canAddProductGroup(group))
			throw new IllegalArgumentException();
		
		if(productGroupsByStorageUnit.get(group.getStorageUnit()) != null){
			productGroupsByStorageUnit.put(group.getStorageUnit()
											, new HashSet<ProductGroup>());
		}
		productGroupsByStorageUnit.get(group.getStorageUnit()).add(group);
		productGroups.put(group.getName(), group);
	}
	
	public boolean canRemoveProductGroup(ProductGroup unit) {
		if(contains(unit)) {
			return true;
		}
		return false;
	}
	
	public void removeProductGroup(ProductGroup group) {
		assert(canRemoveProductGroup(group));
		
		if(!canRemoveProductGroup(group))
			throw new IllegalArgumentException();
		
		productGroups.remove(group.getName());
		productGroupsByStorageUnit.get(group.getStorageUnit()).remove(group);
	}
	
	public boolean contains(ProductGroup unit) {
		if(productGroups.keySet().contains(unit.getName())) {
			return true;
		}
		return false;
	}
	
	public Set<ProductGroup> getProductGroups(StorageUnit unit){
		return productGroupsByStorageUnit.get(unit);
	}
	
	public void removeProductGroups(StorageUnit unit){
		Set<ProductGroup> groups = productGroupsByStorageUnit.get(unit);
		for(ProductGroup group : groups){
			this.removeProductGroup(group);
		}
	}
}
