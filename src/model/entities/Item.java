package model.entities;

import java.util.Date;
import java.util.Objects;
import model.persistence.PersistentItem;

/** Item
 * A physical instance of a particular Product. An Item 
 * corresponds to a physical container with a BarCode on it.
 * For example, a case of soda might contain 24 cans of Diet 
 * Coke.  In this case, the Product is Diet Coke, 12 fl oz, 
 * while each physical can is a distinct Item.
 * 
 * @author casey dmathis
 */

public class Item implements PersistentItem{
	
	/**Unique BarCode for this Item.
	 * This BarCode is generated by the static BarCodeGenerator class, 
	 * and is different from the manufacturer's BarCode.
	 * Must be a valid UPC BarCode and unique among all Items.
	 */
	private BarCode barCode;
	
	/** The date on which the Item was entered into the system.
	 * Must be non-empty. Cannot be in the future or prior to 1/1/2000.
	 */
	private Date entryDate;
	
	/** The date and time at which the Item was removed from the system.
	 * exitDate is defined only if the Item has been removed from storage.
	 * Cannot be in the future or prior to 12 AM on the Item's Entry Date.
	 */
	private Date exitDate;
	
	/** The date on which this Item will expire. This is calculated
	 * based on this Item's Entry Date and the Product's Shelf Life.
	 * This attribute is defined only if the Product's Shelf Life 
	 * attribute has been specified.
	 */
	private Date expirationDate;
	
	/** The Product of which this Item is an instance.
	 * Must be non-empty
	 */
	private Product product;
	
	/** Product Container that contains this Item.
	 * container is empty if the Item has been removed from storage. 
	 * It is non-empty if the Item has not been removed from storage. 
	 * (Before it is removed, an Item is contained in one Product 
	 * Container. After it is removed, it is contained in no Product 
	 * Containers.)
	 */
	private ProductContainer container;
	
	/** Constructor - creates a new Item using the following params:
	 * 
	 * @param barCode
	 * @param expirationDate
	 * @param product
	 * @param container
	 */
	public Item(BarCode barCode, Date entryDate, Date expirationDate, 
			Product product, ProductContainer container){		
		this.barCode = barCode;
		this.entryDate = entryDate;
		this.expirationDate = expirationDate;
		this.product = product;
		this.container = container;
	}
	
	/**
	 * Move the Item from the current container
	 * to the moveTo container
	 * @param moveTo - the container to which the Item is moved
	 * 
	 * @throws exception if moveTo is not a valid Product Container
	 */
	public void move(ProductContainer moveTo){
		
	}
	
	/** Remove the Item from storage
	 * Removes the Item by storing its exit time, and clearing its
	 * container. The Item is retained for historical purposes.
	 *
	 * @throws exception if the item has already been removed from 
	 * a storage unit. 
	 */
	public void remove(){
		
	}
	
	
	/*public String sqlCreateStatement() {
		String query = "CREATE TABLE items(" +
				"items_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"barCode TEXT," +
				"entryDate DATETIME," +
				"exitDate DATETIME," +
				"expirationDate DATETIME," +
				"product_id INTEGER" + 
				"parent_id INTEGER" +
				");";
		return query;
	}*/
	
	/** Checks to see if newDate is a valid entry date
	 * 
	 * @param newDate
	 * @return true if newDate is non-empty, is not in the future 
	 * and is not prior to 1/1/2000. Otherwise, returns false.
	 */
	public boolean canSetEntryDate(Date newDate){
		return true;
	}
	
	/** Sets items entryDate to newDate
	 * 
	 * @param newDate - The new entry date
	 */
	public void setEntryDate(Date newDate){
		entryDate = newDate;
	}
	
	// Getters
	
	/** Get the expiration date of the item
	 * 
	 * @return the expiration date of the item
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	/** Get the exit date of the item
	 * 
	 * @return the exit date of the item
	 */
	public Date getExitDate() {
		return exitDate;
	}
	
	/** Get the entry date of the item
	 * 
	 * @return the entry date of the item
	 */
	public Date getEntryDate() {
		return entryDate;
	}
	
	/** Get the barCode of the item
	 * 
	 * @return the barCode of the item
	 */
	public BarCode getBarCode() {
		return barCode;
	}

	/** Get the item's product
	 * 
	 * @return the item's product
	 */
	public Product getProduct() {
		return product;
	}

	/** Get the item's container
	 * 
	 * @return the item's container
	 */
	public ProductContainer getContainer() {
		return container;
	}
     
	@Override
	public boolean equals(Object obj) 
	{
	    if(obj instanceof Item){
		   Item objItem = (Item)obj;
		   return this.barCode == objItem.barCode;
	    }
	    else{
		   return false;
	    }    
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.barCode);
		return hash;
	}
	
	
}