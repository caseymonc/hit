package model;

import java.io.Serializable;
import java.util.Set;

/** ProductContainer
 * A generic term for Storage Units and Product Groups.  
 * These objects can “contain” Products and Items, 
 * and are referred to generically as “product containers”.
 */


public abstract class ProductContainer{
	private Set<Product> products;
	private Set<Item> items;
	private Set<ProductGroup> productGroups;

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void addItem(Item item){
		
	}
	
	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setProductGroups(Set<ProductGroup> productGroups) {
		this.productGroups = productGroups;
	}

	public Set<ProductGroup> getProductGroups() {
		return productGroups;
	}
}
