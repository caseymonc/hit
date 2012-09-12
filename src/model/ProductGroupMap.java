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
public class ProductGroupMap extends TreeMap<String, ProductGroup> {

     /**
      * 
      */
     public ProductGroupMap() {
     
     }
        
     /**
      * 
      * @param pg ProductGroup to be added
      */
     public void addProduct(String name, ProductGroup pg) {
          this.put(name, pg);
     }
     
     public boolean containsProduct(String name) {
          return this.containsKey(name);
     }
     /**
      * 
      * @param name
      * @return 
      */
     public ProductGroup getProductByName(String name) {
          return this.get(name);
     }
}
