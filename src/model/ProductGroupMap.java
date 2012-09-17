/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.entities.ProductGroup;
import java.util.TreeMap;
import model.persistence.PersistentItem;


/** ProductGroupMap
 * Maps the name of the product Group to the Product Group.  
 * This class will contain all of the ProductGroups in a specific container
 *
 * @author davidpatty
 */
public class ProductGroupMap extends TreeMap<String, ProductGroup> implements PersistentItem{

	 /**
	  * Constructor
	  */
	 public ProductGroupMap() {
	 
	 }
		
	 /** Adds a Product Group to the map
	  * 
	  * @param name - The name of the ProductGroup to be added
	  * @param pg - ProductGroup to be added
	  * @throws Exception if the product is already in the map.
	  */
	 public void addProductGroup(String name, ProductGroup pg) {
		  this.put(name, pg);
	 }
	 /** Removes a ProductGroup from the Map
	  * 
	  * @param name - the name of the ProductGroup
	  * @throws Exception if the product is not in the map. 
	  */
	 public void removeProductGroup(String name) {
		  
	 }  
	 /** Determines if a ProductGroup is contained in the Map.
	  * 
	  * @param name
	  * @return Returns true if the product is contained in the Map else false.
	  */
	 public boolean containsProductGroup(String name) {
		  return this.containsKey(name);
	 }
	 
	 
	 /** Gets a ProductGroup by its name
	  * @param name
	  * @return the Product Group else null
	  */
	 public ProductGroup getProductGroupByName(String name) {
		  return this.get(name);
	 }
}
