/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.entities.Product;
import java.util.TreeMap;

/** Keeps a Map of products keyed by their descriptions.  
 * @author davidpatty
 */
public class ProductMap extends TreeMap<String,Product> {

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
	  * @param namedescription of the product
	  * @return the product whose description is equal to description.
	  */
	 public Product getProductByDescription(String description) {
		  return this.get(description);
	 }
	 
}
