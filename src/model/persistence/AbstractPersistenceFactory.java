package model.persistence;

import model.persistence.DAO.ItemDAO;
import model.persistence.DAO.ProductDAO;
import model.persistence.DAO.ProductGroupDAO;
import model.persistence.DAO.StorageUnitDAO;

public abstract class AbstractPersistenceFactory {
	
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
	 * Get a connection manager for managing connections to the database.
	 * @return ConnectionManager
	 */
	public abstract AbstractConnectionManager getConnectionManager();
}
