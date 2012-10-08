package model.persistence;

import model.CoreObjectModel;
import model.entities.ProductContainer;
import model.entities.Item;
import model.entities.Product;
import java.util.List;


/**
 * This class represents a way to save off
 * the data structure.  Any class that extends
 * PersistentStore should be able to save and
 * read back the data structure.
 * @author Casey Moncur
 *
 */
public abstract class PersistentStore {
	
	private static PersistentStore selectedPersistentStore;
	
	public static PersistentStore getSelectedStore(){
		return selectedPersistentStore;
	}
	
	public static void setSelectedStore(PersistentStore store){
		selectedPersistentStore = store;
	}
	
	/**
	 * Get the CoreObjectModel which has a connection to
	 * the rest of the data structure
	 * @return the root ProductContainer
	 */
	public abstract CoreObjectModel getCoreObjectModel();
	
	/**
	 * Get all of the items that can hold Items
	 * @return a list of all ProductContainers
	 */
	public abstract List<ProductContainer> getAllContainers();
	
	/** Get a list of all of the products
	 * @return A list of all Products
	 */
	public abstract List<Product> getAllProducts();
	
	/** Get a list of all of the items
	 * @return A list of all Items
	 */
	public abstract List<Item> getAllItems();
	
	/**
	 * Save the PersistentItem to the PersistentStore
	 * @param item - the PersistentItem to save
	 */
	public abstract void save(PersistentItem item);
}
