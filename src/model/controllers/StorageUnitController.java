/*
 * 
 */
package model.controllers;

import model.entities.*;

/** Controller that communicates with the controller in the MVC structure
 *	Acts like a facade in dealing with the rest of the model. 
 * @author davidpatty
 */
public abstract class StorageUnitController {
	
	/** Oversees moving an item from one container to another
	 * 
	 */
	public abstract void moveItem(Item i, ProductContainer targetContainer);
}