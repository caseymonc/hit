package model.entities;

import model.entities.Item;
import model.entities.Product;
import java.io.Serializable;
import java.util.Set;
import java.util.List;



/** ProductContainer
 * A generic term for Storage Units and Product Groups.
 * These objects can contain Products and Items, 
 * and are referred to generically as product containers.
 */
public abstract class ProductContainer{

	 
	/** Add an item to the ProductContainer
	 * @param item - the Item to add
	 * 
	 * @Constraint When a new Item is added to the system, it is placed in a particular Storage Unit 
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
	public abstract void addItem(Item item);
	
	 /** Removes an item from the product container
	  * 
	  * @param item the item to remove
	  */
	 public abstract void removeItem(Item item);

	 
	 
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
	public abstract void addProduct(Product product);
	
	/** Remove a Product from the container
	 * @Constraint A Product may be deleted from a Product Container 
	 * only if there are no Items of the Product remaining in the Product Container.
	 * 
	 * @param product
	 */
	public abstract void removeProduct(Product product);

	 
	 /** determines if it can add the product container
	 * @param ProductContainer
	 */
	public abstract boolean canAddProductContainer(ProductContainer productContainer);
	  
	 /** adds the product container
	 * @param ProductContainer
	 */
	public abstract void addProductContainer(ProductContainer productContainer);
	
	/** removes the productContainer specified by productContainer
	 * @param ProductContainer
	 */
	public abstract void removeProductContainer(ProductContainer productContainer);
	 
	 
	 /** Get the product container by barcode
	  * 
	  * @param barcode
	  * @return the Item specified by barcode
	  */
	 public abstract Item getItemByBarCode(BarCode barcode);

	 /** Get the Item by name
	  * 
	  * @param name
	  * @return the product specified by name
	  */
	 public abstract Product getItemByName(String name);
	 
	 /** Gets a list of all the items in this product container
	  * 
	  * @return List<Item> all of the items in this product container
	  */
	 public abstract List<Item> getAllItems();
	 
	 /** Gets a list of all the products in this product container
	  * 
	  * @return List<Product> all of the items in this product container
	  */
	 public abstract List<Product> getAllProducts();
	 
	 /** Gets a list of all the product containers that are children of this 
	  * product container
	  * 
	  * @return List<ProductContainer> all of the items in this product container
	  */
	 public abstract List<ProductContainer> getAllProductContainers();
	 
	 
	 /** Get the product container by name
	  * 
	  * @param name String the new name for this container	  
	  * @return the ProductContainer specified by name
	  */
	 public abstract ProductContainer getProductContainerByName(String name);
	 
	 
	 
	 /** Used to get the name of the storage container
	  *   in the case of root, this is "StorageUnit"
	  * @return string name of the product Container.
	  */
	 public abstract String getName();
	 
	 /** sets the name of this product container
	  *  @param name String the new name for this container
	  */
	 public abstract void setName(String name);
	 
	 
}
