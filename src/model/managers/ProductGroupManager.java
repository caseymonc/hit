/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.util.HashMap;
import java.util.Set;
import model.entities.BarCode;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;

/**
 *
 * @author davidpatty
 */
public class ProductGroupManager {
	
	private HashMap<BarCode, ProductGroup> productGroupsByBarCode;
	
	private HashMap<ProductGroup, Set<Product>> productsByProductGroup;

	private HashMap<ProductGroup, Set<Item>> itemsByProductGroup;
	
	public ProductGroupManager() {

		productGroupsByBarCode = new HashMap<BarCode, ProductGroup>();
		productsByProductGroup = new HashMap<ProductGroup, Set<Product>>();
		itemsByProductGroup = new HashMap<ProductGroup, Set<Item>>();
	}
	
	/**
	 * @throws CantAddItemException
	 */
	public void addItemToProductGroup(ProductGroup pg, Item i) {
		//can always add dont need to check if i can.
	}
	
	/**
	 * @throws CantAddItemException
	 */
	public void addProductToProductGroup(ProductGroup pg, Product p) {
		//can always add dont need to check if i can.
		//updates productsByProductGroup.
	}
	
	/** removes the Item from the Product Group
	 * 
	 * @throws IllegalArgumentException
	 * @param i
	 * @param pg 
	 */
	public void removeItemFromProductGroup(Item i, ProductGroup pg) {
		if(i == null || pg == null) {
			throw new IllegalArgumentException();
		}
		
		assert(itemsByProductGroup.containsKey(pg));
		assert(itemsByProductGroup.get(pg).contains(i));
		
		itemsByProductGroup.get(pg).remove(i);
	}
	
	public boolean productGroupHasProduct(ProductGroup pg, Product p) {
		return true; //dpatty requesting this function.
	}
	
	
	
	/**
	 * 
	 * @param pg
	 * @param i
	 * @return 
	 */
	public boolean productGroupHasItem(ProductGroup pg, Item i) {
		return true;
	}
}
