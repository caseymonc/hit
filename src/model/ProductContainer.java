package model;

import java.io.Serializable;
import java.util.Set;

/** ProductContainer
 * A generic term for Storage Units and Product Groups.��
 * These objects can �contain� Products and Items, 
 * and are referred to generically as �product containers�.
 */


public abstract class ProductContainer{
	/** Products that are in this container*/
	private ProductMap products;
	
	/** Items that are in this container*/
	private ItemMap items;
	
	/** Product groups that are in this container
	 * @Constraint may not have two top level
	 * Product Groups with the same
	 * name.*/
	private ProductGroupMap productGroups;

	/** Add an item to the ProductContainer
	 * @param item - the Item to add
	 * 
	 * @Constraint When a new Item is added to the system, it is placed in a particular Storage Unit 
	 * (called the "target Storage Unit"). The new Item is added to the same ProductContainer 
	 * that contains the Item�s Product within the target Storage Unit. 
	 * If the Item�s Product is not already in a Product Container within the target 
	 * StorageUnit, the Product is placed in the Storage Unit at the root level.
	 * 
	 * @Constraint New Items are added to the Product Container within the target 
	 * Storage Unit that contains the Item�s Product. If the Item�s Product is not 
	 * already in the Storage Unit, it is automatically added to the Storage Unit 
	 * at the top level before the Items are added.
	 */
	public abstract void addItem(Item item);
	
	/**
	 * @Constraint When a Product is dragged into a Product Container, the logic is as follows:
	 * 		Target Product Container = the Product Container the user dropped the Product on
	 * 		Target Storage Unit = the Storage Unit containing the Target Product Container
	 * 		If the Product is already contained in a Product Container in the Target Storage Unit
	 * 			Move the Product and all associated Items from their old Product Container 
	 * 			to the Target Product Container
	 * 		Else
	 * 			Add the Product to the Target Product Container
	 * @param product
	 */
	public abstract void addProduct(Product product);
	
	/**
	 * @Constraint A Product may be deleted from a Product Container 
	 * only if there are no Items of the Product remaining in the Product Container.
	 * 
	 * @param product
	 */
	public abstract void deleteProduct(Product product);
	
	public void setProducts(ProductMap products) {
		this.products = products;
	}

	public ProductMap getAllProducts() {
		return products;
	}
	
	public void setItems(ItemMap items) {
		this.items = items;
	}

	public ItemMap getAllItems() {
		return items;
	}

	public void setProductGroups(ProductGroupMap productGroups) {
		this.productGroups = productGroups;
	}

	public ProductGroupMap getAllProductGroups() {
		return productGroups;
	}
     
     
     
}
