package model.entities;


import java.util.HashMap;
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

	/**
	 * Asks whether a StorageUnit can be created with these values
	 * @param name The name of the StorageUnit
	 * @return true if a StorageUnit can be created with these values
	 * @return false if a StorageUnit cannot be created with these values
	 */
	public static boolean canCreate(String name){
		try{
			new StorageUnit(name);
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/**
	 * @return this
	 */
	@Override
	public StorageUnit getStorageUnit() {
		return this;
	}
	
	/**
	 * @throws UnsupportedOperationException
	 * because a StorageUnit cannot be in a StorageUnit
	 */
	@Override
	public void setStorageUnit(StorageUnit unit) {
		throw new UnsupportedOperationException("StorageUnits cannot be in a StorageUnit");
	}
	
	/**
	 * Add an Item to this StorageUnit
	 * @param The Item to be added
	 */
	@Override
	public void addItem(Item item){
		ProductContainer container = productContainerByProduct.get(item.getProduct());
		if(container == null || container == this){
			super.addItem(item);
		}else{
			container.addItem(item);
		}
	}

	/**
	 * Sets the productByContainer index
	 * @param product
	 * @param productContainer
	 */
	public void setProductForContainer(Product product, ProductContainer productContainer) {
		productContainerByProduct.put(product, productContainer);
	}

	/**
	 * Get the ProductContainer that is indexed by Product
	 * @param product the index
	 * @return the ProductContainer that is indexed by product
	 */
	public ProductContainer getProductGroupByProduct(Product product){
		assert(product != null);
		
		return productContainerByProduct.get(product);
	}
	
	
}
