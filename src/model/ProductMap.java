/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.entities.Product;
import model.entities.BarCode;
import java.util.TreeMap;
import model.persistence.PersistentItem;


/** Keeps a Map of products keyed by their descriptions.  
 * @author davidpatty
 */
public class ProductMap extends TreeMap<String,Product> implements PersistentItem{

	/**
	 * Constructor
	 */
	public ProductMap() {
		  
	 }
	 
	 /** Adds a product to the ProductMap
	  * 
	  * @param description - the description of the product to be added
	  * @param p - Product to be added
	  */
	 public void addProduct(String description, Product p) {
		  this.put(description,p);
	 }
	 
	 /** Determines if a product is contained in the ProductMap
	  * 
	  * @param description - the description of the product being searched for.
	  * @return true if the product is contained in the ProductMap.
	  * Otherwise, return false.
	  */
	 public boolean containsProduct(String description) {
		  return this.containsKey(description);
	 }
	 
	 /** Get the product by its description
	  * 
	  * @param description of the product
	  * @return the product whose description is equal to description.
	  */
	 public Product getProductByDescription(String description) {
		  return this.get(description);
	 }
	 
	 /** Get the product by its BarCode
	  * 
	  * @param barcode - The BarCode of the product being retrieved
	  * @return the product whose BarCode is equal to barcode. Otherwise, return null.
	  */
	 public Product getProductByBarCode(BarCode barcode){
		  for(Product p : this.values()){
			  if(p.getBarCode().getBarCode() == barcode.getBarCode()){
				  return p;
			  }
		  }
		  
		  return null;
	 }
	 
}
