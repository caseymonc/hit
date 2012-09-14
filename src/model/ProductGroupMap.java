/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.TreeMap;

/** ProductGroupMap
 * Maps the name of the product Group to the Product Group.  
 * This class will contain all of the ProductGroups in a specific container
 *
 * @author davidpatty
 */
public class ProductGroupMap extends TreeMap<String, ProductGroup> {

	 /**
	  * Constructor
	  */
	 public ProductGroupMap() {
	 
	 }
		
	 /**
	  * @throws Exception if the product is already in the map.
	  * @param pg ProductGroup to be added
	  */
	 public void addProduct(String name, ProductGroup pg) {
		  this.put(name, pg);
	 }
	 /**
	  * @throws Exception if the product is not in the map.
	  * @param name 
	  */
	 public void removeProduct(String name) {
		  
	 }  
	 /**
	  * Returns true if the product is contained in the Map else false.
	  * @param name
	  * @return 
	  */
	 public boolean containsProduct(String name) {
		  return this.containsKey(name);
	 }
	 
	 
	 /**
	  * @param name
	  * @return the Product Group else null
	  */
	 public ProductGroup getProductByName(String name) {
		  return this.get(name);
	 }
}
