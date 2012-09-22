package model.entities;

import model.entities.ProductContainer;
import model.entities.Item;
import model.entities.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.persistence.PersistentItem;

/** StorageUnit
 * A Storage Unit is a room, closet, pantry, cupboard, 
 * or some other enclosed area where 
 * items can be stored.
 */

public class StorageUnit extends ProductContainer implements PersistentItem{
	
private Map<Product, ProductContainer> productContainerByProduct;
	
	/** Constructor
	 * @param name The name of the StorageUnit
	 */
	public StorageUnit(String name) {
		super(name, null);
		productContainerByProduct = new HashMap<Product, ProductContainer>();
	}

	public StorageUnit getStorageUnit() {
		return this;
	}
	
	public void setStorageUnit(StorageUnit unit) {
		throw new IllegalStateException();
	}
	
	public void addItem(Item item){
		ProductContainer container = productContainerByProduct.get(item.getProduct());
		if(container == null){
			super.addItem(item);
		}else{
			container.addItem(item);
		}
	}

	public void setProductForContainer(Product product, ProductContainer productContainer) {
		productContainerByProduct.put(product, productContainer);
	}

	public void update(StorageUnit unit) {
		super.update(unit);
		
	}
	
	public ProductContainer getProductGroupByProduct(Product product){
		assert(product != null);
		
		return productContainerByProduct.get(product);
	}
	
	
}
