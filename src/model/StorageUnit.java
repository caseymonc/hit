package model;

import java.io.Serializable;

/* StorageUnit
 * A Storage Unit is a room, closet, pantry, cupboard, 
 * or some other enclosed area where 
 * items can be stored.
 */

public class StorageUnit extends ProductContainer implements Serializable{
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
