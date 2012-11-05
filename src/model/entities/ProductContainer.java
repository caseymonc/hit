package model.entities;

import model.entities.Item;
import model.entities.Product;
import model.persistence.PersistentItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import reports.visitors.ItemVisitor;
import reports.visitors.ProductGroupVisitor;
import reports.visitors.Visitor;



/** ProductContainer
 * A generic term for Storage Units and Product Groups.
 * These objects can contain Products and Items, 
 * and are referred to generically as product containers.
 */
public abstract class ProductContainer implements PersistentItem{
	/** Index from BarCode to Product
	 * Stores the Products that have items in this Container*/	
	private Map<BarCode,Product> products;
	
	/** Index from BarCode to Item
	 * Stores the Items in this Container*/
	private Map<BarCode,Item> items;
	
	/** Index from ProductGroup name to ProductGoup
	 * Stores the ProductGroups in this Container*/
	private Map<String,ProductGroup> productGroups;
	
	/** Index from Product to set of Items associated 
	 * with it in this Container*/
	private Map<Product,Set<Item>> itemsByProduct;
	
	/** The ProductContainer in which this resides*/
	private ProductContainer container;
	
	/** The StorageUnit in which this ProductContainer resides*/
	private StorageUnit storageUnit;
	
	/** The name of this ProductContainer*/
	private String name; 
	
	/**
	 * Constructor
	 * @param name the name of ProductContainer
	 * @param container the ProductContainer in which this container will reside
	 */
	public ProductContainer(String name, ProductContainer container) {
		assert(name != null);
		assert(!name.equals(""));
		
		if(name == null || name.equals(""))
			throw new IllegalArgumentException("Name cannot be empty");
		
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
		assert(item != null);
		assert(canAddItem(item));
		
		if(!canAddItem(item)) {
			throw new IllegalArgumentException("The Item is already in this container");
		}
		
		putItem(item);
	}
	
	/**
	 * Do the actual add
	 * @param item The item to add
	 */
	private void putItem(Item item){
		item.getProduct().addProductContainer(this);
		getStorageUnit().setContainerByProduct(item.getProduct(), this);
		products.put(item.getProduct().getBarCode(), item.getProduct());
		items.put(item.getBarCode(), item);
		putItemByProduct(item);
		item.setContainer(this);
		
	}
	
	/**
	 * Index the item by 
	 * @param item
	 */
	private void putItemByProduct(Item item) {
		Set<Item> items = itemsByProduct.get(item.getProduct());
		if(items == null){
			
			items = new HashSet<Item>();
		}
		
		items.add(item);
		itemsByProduct.put(item.getProduct(), items);
	}
	
	public Set<Item> getItemsByProduct(Product product){
		assert(product != null);	
		if(product == null)
			throw new IllegalArgumentException("Product cannot be null");
		if(itemsByProduct.containsKey(product)) {
			return itemsByProduct.get(product);
		} else {
			return new HashSet<Item>();
		}
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
		assert(item != null);
		
		if(item == null){
			throw new IllegalArgumentException("Item cannot be null");
		}
		
		assert(canRemoveItem(item));
			
		if(!canRemoveItem(item)) {
			throw new IllegalArgumentException("The Item is not in this container" + this.name);
		}
		
		if(itemsByProduct.get(item.getProduct()) != null){
			itemsByProduct.get(item.getProduct()).remove(item);
		}
		items.remove(item.getBarCode());
	 }
	
	public void unRemoveItem(Item item){
		this.putItemByProduct(item);
		this.putItem(item);
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
		assert(product != null);
		
		if(product == null){
			throw new IllegalArgumentException("Product cannot be null");
		}
		
		assert(canAddProduct(product));
		
		if(!canAddProduct(product)) {

			throw new IllegalArgumentException();
		}
		
		ProductContainer targetContainer = this;
		StorageUnit targetUnit = this.getStorageUnit();
		ProductContainer oldContainer = targetUnit.getContainerByProduct(product);
		if(oldContainer != null)
			oldContainer.moveProduct(product, this);
		products.put(product.getBarCode(), product);
	}
	
	public void removeProductFromContainer(Product product, ProductContainer container){
		this.itemsByProduct.remove(product);
		this.products.remove(product.getBarCode());
	}
	
	public void moveProduct(Product product, ProductContainer container){
		Set<Item> items = this.getItemsByProduct(product);
		this.itemsByProduct.remove(product);
		this.products.remove(product.getBarCode());
		if(items != null)
			for(Item item : items){
				container.putItem(item);
			}
	}
	
	/**
	 * Asks if the Product can be removed
	 * @param product The product to be removed
	 * @return true if the Product can be removed
	 * @return false if the Product cannot be removed
	 */
	public boolean canRemoveProduct(Product product) {
		if(products.containsKey(product.getBarCode())) {
			Set<Item> items = this.getItemsByProduct(product);
			if(items != null && items.size() > 0)
				return false;
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
		assert(product != null);
		
		if(product == null){
			throw new IllegalArgumentException("Product cannot be null");
		}
		
		assert(canRemoveProduct(product));
		
		if(!canRemoveProduct(product)) {

			throw new IllegalArgumentException("The Product is not in this container " + getName());
		}
		
		products.remove(product.getBarCode());
		itemsByProduct.remove(product);
	}
	
	public void moveProduct(Product product){
		products.remove(product.getBarCode());
		itemsByProduct.remove(product);
	}

	 
	 /** determines if it can add the product container
	 * @param ProductContainer
	 */
	public boolean canAddProductGroup(ProductGroup productGroup) {
		if(productGroup == null)
			return false;
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
		
		if(productGroup == null){
			throw new IllegalArgumentException("ProductGroup cannot be null");
		}
		
		assert(canAddProductGroup(productGroup));
		
		if(!canAddProductGroup(productGroup)) {

			throw new IllegalArgumentException("There is already a ProductGroup in this container" +
												"with name: " + productGroup.getName());
		}
		
		productGroup.setStorageUnit(this.getStorageUnit());
		productGroups.put(productGroup.getName(), productGroup);
	}
	
	/**
	 * Ask whether the ProductGroup can be removed from it's parent
	 * @param group the group to be removed
	 * @return true if the ProductGroup can be removed
	 * @return false if group is null
	 * @return false if group is not in this
	 * @return false if group is not empty
	 */
	public boolean canRemoveProductGroup(ProductGroup group){
		if(group == null)
			return false;
		
		if(!productGroups.containsKey(group.getName()))
			return false;
		
		if(!group.isEmpty())
			return false;
		
		return true;
	}
	
	/** removes the productContainer specified by productContainer
	 * @param ProductContainer
	 */
	public void removeProductGroup(ProductGroup productGroup) {
		assert(productGroup != null);
		
		if(productGroup == null){
			throw new IllegalArgumentException("ProductGroup cannot be null");
		}
		
		assert(canRemoveProductGroup(productGroup));
		
		if(!canRemoveProductGroup(productGroup)){
			throw new IllegalArgumentException("This group cannot be removed");
		}
		
		productGroups.remove(productGroup.getName());
	}
	 
	 
	 /** Get the product container by barcode
	  * 
	  * @param barcode
	  * @return the Item specified by barcode
	  */
	 public Item getItemByBarCode(BarCode barcode) {
		 assert(barcode != null);
		 
		 if(barcode == null){
				throw new IllegalArgumentException("barcode cannot be null");
		 } 
		 
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
		 assert(name != null);
		 
		 if(name == null){
			 throw new IllegalArgumentException("Name cannot be null");
		 }
		 
		 return productGroups.get(name);
	 }
	 
	 
	 
	 /** Used to get the name of the storage container
	  *	in the case of root, this is "StorageUnit"
	  * @return string name of the product Container.
	  */
	 public String getName() {

		 return name;
	 }
	 
	 public boolean canSetName(String name){
		 if(name == null || name.equals(""))
			 return false;
		 return true;
	 }
	 
	 /** sets the name of this product container
	  *  @param name String the new name for this container
	  */
	 public void setName(String name) {
		 assert(canSetName(name));
		 
		 if(!canSetName(name)){
			 throw new IllegalArgumentException("Name cannot be empty");
		 }
		 
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
		 if(container == this)
			 throw new IllegalArgumentException("ProductContainer cannot be a parent of itself");
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
		 
		 if(!items.isEmpty()) {
			 return false;
		 }

		 for(ProductGroup group : productGroups.values()) {
			 if(!group.isEmpty()) {
				 return false;
			 }
		 }
		 return true;
	}

	/**
	 * Set the StorageUnit in which this ProductContainer resides
	 * @param storageUnit
	 */
	public void setStorageUnit(StorageUnit storageUnit) {
		
		if(storageUnit == null){
			throw new IllegalArgumentException("StorageUnit cannot be null");
		}
		
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
		assert(unit != null);
		
		if(unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
		
		setName(unit.getName());
		
	}

	/*public int hashCode(){
		if(container == null)
			return getName().hashCode();
		return getName().hashCode() + getContainer().hashCode();
	}*/
	
	public String toString(){
		return name;
	}

	
	public void updateProductGroupByName(String newName, String oldName) {
		ProductGroup group = productGroups.get(oldName);
		productGroups.remove(oldName);
		productGroups.put(newName, group);
	}
	
	//public abstract void accept(ProductGroupVisitor visitor);
	
	public abstract void accept(Visitor visitor);
	
	public void acceptOrder(Visitor visitor, boolean preOrder){
				
		Collection<ProductGroup> groupsCollection = this.getAllProductGroup();
		List<ProductGroup> groups = new ArrayList<ProductGroup>();
		groups.addAll(groupsCollection);
		Collections.sort(groups, new Comparator<ProductGroup>(){
			public int compare(ProductGroup group1, ProductGroup group2) {
				return group1.getName().compareTo(group2.getName());
			}
		});
		
		if(preOrder)
			accept(visitor);
		
		for(ProductGroup group : groups){
			group.acceptOrder(visitor, preOrder);
		}
		
		if(!preOrder)
			accept(visitor);
	}
}
