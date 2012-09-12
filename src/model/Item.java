package model;

import java.io.Serializable;
import java.util.Date;

/** Item
 * A physical instance of a particular Product.��
 * An Item corresponds to a physical container
 * with a barcode on it.��For example, a case of 
 * soda might contain 24 cans of Diet Coke.��In
 * this case, the Product is �Diet Coke, 12 fl oz�, 
 * while each physical can is a distinct Item.
 */

public class Item implements PersistentItem{
	
	/**Unique barcode for this Item.
	 * This barcode is generated by
	 * Inventory Tracker, and is
	 * different from the
	 * manufacturer's barcode.
	 * @Constraint Must be a valid UPC barcode and unique among all Items.
	 */
	private String barCode;
	
	/** The date on which the Item was entered into the system.
	 * @Constraint Must be non-empty. Cannot be in the future or prior to 1/1/2000.*/
	private Date entryDate;
	
	/** The date and time at which the Item was removed from the system.
	 * @Constraint This attribute is defined only if the Item has been removed from storage.
	 * Cannot be in the future or prior to 12 AM on the Item�s Entry Date.*/
	private Date exitDate;
	
	/** The date on which this Item will expire. This is calculated
	 * based on this Item�s Entry Date and the Product�s Shelf Life.
	 * @Constraint This attribute is defined only if the Product�s 
	 * Shelf Life attribute has been specified.*/
	private Date expirationDate;
	
	/** The Product of which this Item is an instance.
	 * @Constraint Must be non-empty*/
	private Product product;
	
	/** Product Container that contains this Item.
	 * @Constraint Empty if the Item has been removed from storage. Non-empty if the Item has
	 * not been removed from storage. (Before it is removed, an Item is 
	 * contained in one Product Container. After it is removed, it is contained in no
	 * Product Containers.)*/
	private ProductContainer container;
	
	public Item(String barCode, Date expirationDate, Product product, 
										ProductContainer container){
		this.barCode = barCode;
		this.setEntryDate(new Date());
		this.setExpirationDate(expirationDate);
		this.setProduct(product);
		this.setContainer(container);
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	/**
	 * Move the Item from the current container
	 * to the moveTo container
	 * @param moveTo - the container to move this item to
	 * 
	 * @Constraint An Item is contained in exactly one Product Container at a time 
	 * (until it is removed, at which point it belongs to no Product Container at all).
	 * 
	 * @Constraint When an Item is dragged into a Product Container, the logic is as follows:
	 * 		Target Product Container = the Product Container the user dropped the Item on
	 * 		Target Storage Unit = the Storage Unit containing the Target Product Container
	 * 		If the Item�s Product is already in a Product Container in the Target Storage Unit
	 * 				Move the Product and all associated Items from their 
	 * 				old Product Container to the Target Product Container
	 * 		Else
	 * 			Add the Product to the Target Product Container
	 * 			Move the selected Item from its old Product Container to 
	 * 			the Target Product Container
	 */
	public void move(ProductContainer moveTo){
		
	}
	
	public void scanIn(){
		
	}
	
	public void scanOut(){
		
	}
	
	/** Remove the item from storage
	 * @Constraint When an Item is removed,
	 * 1. The Item is removed from its containing Storage Unit.
	 * 2. The Exit Time is stored in the Item.
	 * 3. The Item is retained for historical purposes (i.e., for calculating statistics and reporting).
	 * @param store
	 */
	public void delete(PersistentStore store){
		
	}
	
	
	public String sqlCreateStatement() {
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
	}
	
	/*
	 * Getters and Setters
	 */
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}
	
	public Date getExitDate() {
		return exitDate;
	}
	
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	public Date getEntryDate() {
		return entryDate;
	}
	
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	public String getBarCode() {
		return barCode;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setContainer(ProductContainer parent) {
		this.container = parent;
	}

	public ProductContainer getContainer() {
		return container;
	}

	
}
