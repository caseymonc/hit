package model;

import java.io.Serializable;
import java.util.Date;

/* Item
 * A physical instance of a particular Product.  
 * An Item corresponds to a physical container
 * with a barcode on it.  For example, a case of 
 * soda might contain 24 cans of Diet Coke.  In
 * this case, the Product is “Diet Coke, 12 fl oz”, 
 * while each physical can is a distinct Item.
 */

public class Item implements Serializable{
	private String barCode;
	private Date entryDate;
	private Date exitDate;
	private Date expirationDate;
	private Product product;
	private ProductContainer parent;
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public void scanIn(){
		
	}
	
	public void scanOut(){
		
	}
	
	public void create(){
		
	}
	
	public void edit(){
		
	}
	
	public void delete(){
		
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

	public void setParent(ProductContainer parent) {
		this.parent = parent;
	}

	public ProductContainer getParent() {
		return parent;
	}
}
