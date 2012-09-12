/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.TreeMap;

/**
 *
 * @author davidpatty
 */
public class ProductMap extends TreeMap<String,Product> {

	/**
	 * 
	 */
	public ProductSet() {
          
     }
     
     /**
      * 
      * @param p Product to be added
      */
     public void addProduct(String name, Product p) {
          this.put(name,p);
     }
     
     public boolean containsProduct(String name) {
          return this.containsKey(name);
     }
     /**
      * 
      * @param name
      * @return 
      */
     public Product getProductByName(String name) {
          return this.get(name);
     }
     
}
