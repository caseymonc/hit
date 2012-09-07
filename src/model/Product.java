package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/** Product
 * A bar-coded product that can be stored in a Storage Unit.  
 * Example Products are:
 * Town House Light Buttery Crackers, 13 oz, Barcode 030100169079
 * Colgate Toothpaste - Cavity Protection, 6.40 oz, Barcode 035000509000
 * Alcon Lens Drops, 0.17 fl oz, Barcode 300650192057
 * 
 * Singleton
 */

public class Product implements PersistentItem{
	private static HashMap<String,Product> singletons;
	
	private String description;
	private Date creationDate;
	private String barCode;
	private int shelfLife;
	private Size threeMonthSupply;
	private Size size;
	private Set<ProductContainer> parents;
	
	/**
	 * @param description - a String description of the Product
	 * @param barCode - the bar code that represents this Product
	 * @param shelfLife - the time in seconds that this Product lasts
	 * @param threeMonthSupply - how much of the Product that you need for a
	 * 							 period of 3 months
	 * @param size - The size of one Item of this Product
	 */
	private Product(String description, String barCode, int shelfLife,
								Size threeMonthSupply, Size size){
		this.setDescription(description);
		this.setBarCode(barCode);
		this.setShelfLife(shelfLife);
		this.setThreeMonthSupply(threeMonthSupply);
		this.setSize(size);
		this.setCreationDate(new Date());
		
	}
	
	/**
	 * 
	 * @param description
	 * @param barCode
	 * @param shelfLife
	 * @param threeMonthSupply
	 * @param size
	 * @return a singleton instanceof the Product
	 */
	public static Product getSingleton(String description, String barCode, int shelfLife,
														Size threeMonthSupply, Size size){
		return null;
	}
	
	/**
	 * Delete the Product from the system
	 * @param store
	 */
	public void delete(PersistentStore store){
		
	}
	
	
	/**
	 * Add an item to the parents set
	 * @param parent the ProductContainer to add to the parents set
	 */
	public void addProductContainer(ProductContainer parent){
		
	}
	
	public String sqlCreateStatement() {
		String query = "CREATE TABLE products(" +
				"product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"description TEXT," +
				"barCode TEXT," +
				"creationDate DATETIME," +
				"shelfLife INTEGER," +
				"threeMonthSupply TEXT," +
				"size TEXT" + 
				");";
		return query;
	}
	
	
	/*
	 * Getters and Setters
	 */
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setThreeMonthSupply(Size threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	public Size getThreeMonthSupply() {
		return threeMonthSupply;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Size getSize() {
		return size;
	}

	public void setParents(Set<ProductContainer> parents) {
		this.parents = parents;
	}

	public Set<ProductContainer> getParents() {
		return parents;
	}
	
}
