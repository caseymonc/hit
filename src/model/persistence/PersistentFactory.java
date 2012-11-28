package model.persistence;

import model.CoreObjectModel;
import model.entities.ProductContainer;
import model.entities.Item;
import model.entities.Product;
import model.persistence.DAO.ItemDAO;
import model.persistence.DAO.ProductDAO;
import model.persistence.DAO.ProductGroupDAO;
import model.persistence.DAO.StorageUnitDAO;
import model.persistence.DAO.ppcDAO;

import java.util.List;


/**
 * This class represents a way to save off
 * the data structure.  Any class that extends
 * PersistentStore should be able to save and
 * read back the data structure.
 * @author Casey Moncur
 *
 */
public abstract class PersistentFactory {
	
	private static PersistentFactory selectedPersistentStore;
	
	public static PersistentFactory getFactory(){
		return selectedPersistentStore;
	}
	
	public static void setSelectedStore(PersistentFactory store){
		selectedPersistentStore = store;
	}
	
	/**
	 * Get a StorageUnitDAO for accessing the StorageUnit
	 * Table
	 * @return StorageUnit Data Access Object
	 */
	public abstract StorageUnitDAO getStorageUnitDAO();
	
	/**
	 * Get a ProductGroupDAO for accessing the ProductGroup
	 * table
	 * @return ProductGroup Data Access Object
	 */
	public abstract ProductGroupDAO getProductGroupDAO();
	
	/**
	 * Get a ProductDAO for accessing the Product table
	 * @return Product Data Access Object
	 */
	public abstract ProductDAO getProductDAO();
	
	/**
	 * Get an ItemDAO for accesssing the Item table
	 * @return Item Data Access Object
	 */
	public abstract ItemDAO getItemDAO();
	
	/**
	 * Get the CoreObjectModel which has a connection to
	 * the rest of the data structure
	 * @return the root ProductContainer
	 */
	public abstract CoreObjectModel getCoreObjectModel();
	
	/**
	 * Save the PersistentItem to the PersistentStore
	 * @param item - the PersistentItem to save
	 */
	public abstract void save(PersistentItem item);
	
	public abstract ppcDAO getPpcDAO();
}
