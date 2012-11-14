package model.persistence.DAO;

import model.persistence.DataObjects.DataObject;

public abstract class DataAccessObject {
	
	/**
	 * Get an array of all of this type of object
	 * @return DataObject[] of all items of this DAO type
	 */
	public abstract DataObject[] readAll();
	
	/**
	 * Create a row in the table for obj
	 * @param obj the object to insert into the database
	 */
	public abstract void create(DataObject obj);
	
	/**
	 * Delete an item from the database
	 * @param obj the object to be deleted from the database
	 */
	public abstract void delete(DataObject obj);
	
	/**
	 * Update an item in the database
	 * @param obj the object to be updated in the database
	 */
	public abstract void update(DataObject obj);
}
