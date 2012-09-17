package model.persistence;

import java.io.Serializable;


/**
 * Represents an item that can be saved in the persistent store
 * @author Casey Moncur
 *
 */
public interface PersistentItem extends Serializable{
	
	/**
	 * @return A query string that will
	 * create an SQLite table that represents
	 * the PersistentItem
	 */
	String sqlCreateStatement();
	
	
}
