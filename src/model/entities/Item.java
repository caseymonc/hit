package model.entities;

import common.util.DateUtils;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 599535635271429952L;

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
	
	/**
	 * When an item is removed this flag is set
	 */
	private boolean removed;
	
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
			Product product, ProductContainer container) throws IllegalArgumentException {		
		this.entryDate = entryDate;
		//validate entryDate constraints
		if(!hasValidEntryDate() || product == null ) {
			throw new IllegalArgumentException();
		}
		
		//test constraint: Expiration Date only defined if the Product's Shelf life is defined		
		if(product.getShelfLife() != 0) { 
			this.expirationDate = expirationDate;
		}
		this.barCode = barCode;
		this.product = product;
		this.container = container;
		this.removed = false;
	}
	
	/**
	 * Move the Item from the current container
	 * to the moveTo container
	 * @param moveTo - the container to which the Item is moved
	 * 
	 * @throws exception if moveTo is not a valid Product Container
	 */
	public void move(ProductContainer moveTo){
		if(moveTo == null) {
			throw new IllegalArgumentException();
		}
		assert(this.container != null);
		this.container = moveTo;
	}
	
	/** Remove the Item from storage
	 * Removes the Item by storing its exit time, and clearing its
	 * container. The Item is retained for historical purposes.
	 *
	 * @throws exception if the item has already been removed from 
	 * a storage unit. 
	 */
	public void remove(){
		//constraint exitTime is only defined if the Item has been removed
		assert(exitDate == null);
		//Cannot be in the future
		exitDate = new Date(); 
		//Cannot be prior to 12AM on the Items Entry Date
		assert(!exitDate.before(this.entryDate)); 
		assert(this.container != null);// Non-empty if the item has not been removed from storage
		this.container = null;
		this.removed = true;
	}
	
	public void delete(){
		//this.container = null;
	}
	
	public void unRemove(ProductContainer container){
		exitDate = null;
		this.container = container;
		removed = false;
	}
	/** does this item have a product shelf life?
	 * 
	 * @return true if the shelf life is set > 0
	 */
	public boolean hasProductShelfLife() {
		assert(product != null);
		if(product.getShelfLife() == 0) {
			return false;
		}
		assert(product.getShelfLife() > 0);
		return true;
	}
	
	/**	Entry Date	Must be non-empty.
	 *	Entry Date	Cannot be in the future
	 *	Entry Date	Cannot be prior to 1/1/2000
	 * 
	 * @return true if pass constraints 
	 */
	public boolean hasValidEntryDate() {
		
		if(entryDate == null) {
			return false;
		}
		if(entryDate.after(new Date())) {
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.set(2000,0,0);
		if(entryDate.before(c.getTime())) {
			return false;
		}
		
		return true;
	}
	/**	Exit Time	This attribute is defined only if the Item has been removed from storage.
	 *	Exit Time	Cannot be in the future or prior to 12 AM on the Item’s Entry Date
	 * 
	 * @return true if pass constraints
	 */
	public boolean hasValidExitDate() {
		if(this.removed) {
			if(this.exitDate == null) {
				return false; 
			} else if(this.exitDate.after(new Date())) {
				return false;
			} else if(this.exitDate.before(this.entryDate)) {
				return false;
			}
		} else if(this.exitDate != null) {
			return false;
		}
		return true;
	}
	
	/** used in testing - can this item be created with these parameters?
	 * 
	 * @param barCode
	 * @param entryDate
	 * @param expirationDate
	 * @param product
	 * @param container
	 * @return true if creation is allowed
	 */
	public static boolean canCreate(BarCode barCode, Date entryDate, Date expirationDate, 
			Product product, ProductContainer container){
		try{
			new Item(barCode, entryDate, expirationDate, product, container);
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}
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
	
	/**
	 * calculates the expiration date from the shelf life and the entry date.  
	 *
	 */
	public void calculateExpirationDate() {
		if(this.hasProductShelfLife()){
			GregorianCalendar calendar = new GregorianCalendar();
	
			calendar.setTime(this.entryDate);
	
			calendar.add(GregorianCalendar.MONTH, product.getShelfLife());
	
			this.expirationDate = DateUtils.removeTimeFromDate(calendar.getTime());
		}
	}

	/**
	 * sets the items container to container
	 * @param container
	 */
	public void setContainer(ProductContainer container){
		this.container = container;
	}

	// Getters
	/** Get the expiration date of the item
	 * 
	 * @return the expiration date of the item
	 */
	public Date getExpirationDate() {
		calculateExpirationDate();
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
	
	/** Get the barCode of the item's product
	 * 
	 * @return the BarCode of the item's product
	 */
	public BarCode getProductBarCode() {
		return product.getBarCode();
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

	public String toString(){
		return "Item: " + this.getProduct().getDescription() 
							+ " BarCode: " + this.getBarCode();
	}
	
	/*@Override
	public int hashCode() {
		return barCode.hashCode();
	}*/
	
	/**
	 *
	 * @author davidpatty
	 */
	public static class ItemComparator implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			if ( !(o1 instanceof Item && o2 instanceof Item)) {
				throw new ClassCastException();
			}
			
			Item e1 = (Item) o1;
			Item e2 = (Item) o2;
			Date d1 = e1.getEntryDate();
			Date d2 = e2.getEntryDate();

			int ret = -99;
			if (d1.before(d2)) {
				ret = -1;
			} else if (d1.after(d2)) {
				ret = 1;
			} else {
				ret = 0;
			}
			return ret;
		}
	}
	
}