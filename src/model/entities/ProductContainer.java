package model.entities;

import model.entities.Item;
import model.entities.Product;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;



/** ProductContainer
 * A generic term for Storage Units and Product Groups.
 * These objects can contain Products and Items, 
 * and are referred to generically as product containers.
 */
public abstract class ProductContainer{
	private Map<BarCode,Product> products;
	private Map<BarCode,Item> items;
	private Map<String,ProductGroup> productGroups;
	private Map<Product,Set<Item>> itemsByProduct;
	private ProductContainer container;
	private StorageUnit storageUnit;
	private String name; 
	
	public ProductContainer(String name, ProductContainer container) {
		assert(name != null);
		assert(!name.equals(""));
		
		this.name = name;
		this.setContainer(container);
		products = new HashMap<BarCode, Product>();
		items = new HashMap<BarCode,Item>();
		productGroups = new HashMap<String,ProductGroup>();
		itemsByProduct = new HashMap<Product,Set<Item>>();
	}
	
	/**
	 * Asks if the item can be added
	 * @param item The item that you want to add
	 * @return true if the item can be added
	 * @return false if the item cannot be added
	 */
	public boolean canAddItem(Item item) {
		if(items.containsKey(item.getBarCode())) {

			return false;
		}
		
		return true;
	}
	
	/** Add an item to the ProductContainer
	 * @param item - the Item to add
	 * 
	 * @Constraint When a new Item is added to the system, it is placed in a particular
	 * Storage Unit 
	 * (called the "target Storage Unit"). The new Item is added to the same ProductContainer 
	 * that contains the Item's Product within the target Storage Unit. 
	 * If the Item's Product is not already in a Product Container within the target 
	 * StorageUnit, the Product is placed in the Storage Unit at the root level.
	 * 
	 * @Constraint New Items are added to the Product Container within the target 
	 * Storage Unit that contains the Item's Product. If the Item's Product is not 
	 * already in the Storage Unit, it is automatically added to the Storage Unit 
	 * at the top level before the Items are added.
	 * 
	 */
	public void addItem(Item item) {
		assert(canAddItem(item));
		
		if(!canAddItem(item)) {
			throw new IllegalArgumentException("The Item is already in this container");
		}
		
		item.getProduct().addProductContainer(this);
		storageUnit.setProductForContainer(item.getProduct(), this);
		products.put(item.getProduct().getBarCode(), item.getProduct());
		items.put(item.getBarCode(), item);
		putItemByProduct(item);
		item.setContainer(this);
	}
	
	private void putItemByProduct(Item item) {
		Set<Item> items = itemsByProduct.get(item.getProduct());
		if(items == null){
			items = new HashSet<Item>();
		}
		
		items.add(item);
		itemsByProduct.put(item.getProduct(), items);
	}
	
	public Set<Item> getItemsByProduct(Product product){
		return itemsByProduct.get(product);
	}
	

	/**
	 * Asks if the item can be removed
	 * @param item The item to be removed
	 * @return true if the item can be removed
	 * @return false of the item cannot be removed
	 */
	public boolean canRemoveItem(Item item) {
		if(items.containsKey(item.getBarCode())) {

			return true;
		}
		
		return false;
	}
	 /** Removes an item from the product container
	  * 
	  * @param item the item to remove
	  */
	public void removeItem(Item item) {
		assert(canRemoveItem(item));
			
		if(!canRemoveItem(item)) {

			throw new IllegalArgumentException("The Item is not in this container");
		}
		
		items.remove(item.getBarCode());
		if(this instanceof ProductGroup){
			this.getStorageUnit().removeItem(item);
		}

	 }

	 
	/**
	 * Asks if the product can be added
	 * @param product the product to be added
	 * @return true if the Product can be added
	 * @return false if the Product cannot be added 
	 */
	public boolean canAddProduct(Product product) {
		 return true;
	}
	 
	 /** adds the product specified by product
	  * 
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
	public void addProduct(Product product) {
		assert(canAddProduct(product));
		
		if(!canAddProduct(product)) {

			throw new IllegalArgumentException();
		}
		
		products.put(product.getBarCode(), product);
	}
	
	/**
	 * Asks if the Product can be removed
	 * @param product The product to be removed
	 * @return true if the Product can be removed
	 * @return false if the Product cannot be removed
	 */
	public boolean canRemoveProduct(Product product) {
		if(products.containsKey(product.getBarCode())) {

			return true;
		}
		return false;
	}
	
	/** Remove a Product from the container
	 * @Constraint A Product may be deleted from a Product Container 
	 * only if there are no Items of the Product remaining in the Product Container.
	 * 
	 * @param product
	 */
	public void removeProduct(Product product) {
		assert(canRemoveProduct(product));
		
		if(!canRemoveProduct(product)) {

			throw new IllegalArgumentException("The Product is not in this container");
		}
		
		products.remove(product);
	}

	 
	 /** determines if it can add the product container
	 * @param ProductContainer
	 */
	public boolean canAddProductGroup(ProductGroup productGroup) {
		assert(productGroup != null);
		if(productGroups.containsKey(productGroup.getName())) {

			return false;
		}
		
		return true;
	}
	  
	 /** adds the product container
	 * @param ProductContainer
	 */
	public void addProductGroup(ProductGroup productGroup) {
		assert(productGroup != null);
		assert(canAddProductGroup(productGroup));
		
		if(!canAddProductGroup(productGroup)) {

			throw new IllegalArgumentException("There is already a ProductGroup in this container" +
												"with name: " + productGroup.getName());
		}
		
		
		productGroup.setStorageUnit(this.getStorageUnit());

		productGroups.put(productGroup.getName(), productGroup);
	}
	
	/** removes the productContainer specified by productContainer
	 * @param ProductContainer
	 */
	public void removeProductGroup(ProductGroup productGroup) {

		productGroups.remove(productGroup.getName());
	}
	 
	 
	 /** Get the product container by barcode
	  * 
	  * @param barcode
	  * @return the Item specified by barcode
	  */
	 public Item getItemByBarCode(BarCode barcode) {
		 assert(barcode != null);
		 
		 if(barcode == null) {

			 throw new IllegalArgumentException("Null Barcode");
		 }
		 
		 return items.get(barcode);
	 }

	 
	 /** Gets a list of all the items in this product container
	  * 
	  * @return List<Item> all of the items in this product container
	  */
	 public Collection<Item> getAllItems() {

		 return items.values();
	 }
	 
	 /** Gets a list of all the products in this product container
	  * 
	  * @return List<Product> all of the items in this product container
	  */
	 public Collection<Product> getAllProducts() {

		 return products.values();
	 }
	 
	 /** Gets a list of all the product containers that are children of this 
	  * product container
	  * 
	  * @return List<ProductContainer> all of the items in this product container
	  */
	 public Collection<ProductGroup> getAllProductGroup() {

		 return productGroups.values();
	 }
	 
	 
	 /** Get the product container by name
	  * 
	  * @param name String the new name for this container	  
	  * @return the ProductContainer specified by name
	  */
	 public ProductGroup getProductGroupByName(String name) {

		 return productGroups.get(name);
	 }
	 
	 
	 
	 /** Used to get the name of the storage container
	  *   in the case of root, this is "StorageUnit"
	  * @return string name of the product Container.
	  */
	 public String getName() {

		 return name;
	 }
	 
	 /** sets the name of this product container
	  *  @param name String the new name for this container
	  */
	 public void setName(String name) {
		 this.name = name;
	 }
	 
	 /**
	  * Get the ProductContainer in which this ProductContainer resides
	  * @return container
	  */
	 public ProductContainer getContainer() {
		 return container;
	 }
	 
	 /**
	  * Sets the ProductContainer in which this ProductContainer resides
	  * @param container
	  */
	 public void setContainer(ProductContainer container){
		 this.container = container;
	 }
	 
	 /**
	  * @return true if obj is equal to this
	  */
	 public boolean equals(Object obj) {
		 if(obj instanceof ProductContainer) {

			 ProductContainer container = (ProductContainer)obj;
			 return this.getName().equals(container.getName());
		 }
		 
		 return false;
	 }
	 
	 /**
	  * Is this Container and it's children empty
	  * @return
	  */
	 public boolean isEmpty() {
		 boolean isEmpty = items.size() == 0;
		 if(!isEmpty)
			 return false;
		 for(ProductGroup group : productGroups.values()){
			 isEmpty |= group.isEmpty();
			 if(!isEmpty)
				 return false;
		 }
		 return items.size() == 0;
	}

	/**
	 * Set the StorageUnit in which this ProductContainer resides
	 * @param storageUnit
	 */
	public void setStorageUnit(StorageUnit storageUnit) {
		this.storageUnit = storageUnit;
	}

	/**
	 * Get the StorageUnit in which this ProductContainer resides
	 * @return
	 */
	public StorageUnit getStorageUnit() {
		return storageUnit;
	}

	/**
	 * Update this ProductContainer
	 * @param unit
	 */
	public void update(ProductContainer unit) {
		this.name = unit.getName();
		
	}

}
