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
public class ItemMap extends TreeMap<String, Item> {
     /**
      * 
      */
     public ItemMap() {
          
     }
     /**
      * 
      * @param i Item to be added
      */
     public void addItem(String name, Item i) {
         this.put(name,i);

     }

     public boolean containsProduct(String name) {
          return this.containsKey(name);
     }
     
     /**
      * 
      * @param name
      * @return 
      */
     public Item getProductByName(String name) {
          return this.get(name);
     }
}
