package model.persistence;

import java.io.Serializable;

import model.persistence.DataObjects.DataObject;


/**
 * Represents an item that can be saved in the persistent store
 * @author Casey Moncur
 *
 */
public interface PersistentItem extends Serializable{
	
	/**
	 * Get the data transfer object to 
	 * save this item off to the database
	 * @return
	 */
	DataObject getDataObject();
	
}
