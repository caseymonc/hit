package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/* Product
 * A bar-coded product that can be stored in a Storage Unit.  
 * Example Products are:
 * Town House Light Buttery Crackers, 13 oz, Barcode 030100169079
 * Colgate Toothpaste - Cavity Protection, 6.40 oz, Barcode 035000509000
 * Alcon Lens Drops, 0.17 fl oz, Barcode 300650192057
 */

public class Product implements Serializable{
	private String description;
	private Date creationDate;
	private String barCode;
	private int shelfLife;
	private Size threeMonthSupply;
	private Size size;
	private Set<ProductContainer> parents;
	
	
	
	public void create(){
		
	}
	
	public void edit(){
		
	}
	
	public void delete(){
		
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
