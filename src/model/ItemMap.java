/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.TreeMap;


/** ItemMap
 * Storage for all Items in all locations or Storage Units within
 * the inventory tracker.  ItemMap implements the storage of items,
 * mapping the barcode of the item to the actual item in the storage.
 * 
 * This class should never be a public object.  always use this inside
 * a wrapper or in another class.
 * @author dpatty
 */
public class ItemMap extends TreeMap<BarCode, Item> {

     /**
      * Constructor
      */
     public ItemMap() {
          
     }
     
     /**
      * @Constraint barcode must be unique
      * @param i Item to be added
      */
     public void addItem(BarCode barcode, Item i) {
         this.put(barcode,i);

     }
     /**
      * @Constraint barcode must be already stored in the ItemMap
      *   before using this method, you must first determine if
      *   the item is contained by using containsProduct(String)
      * @param barcode the barcode of the item to be removed
      */
     public void removeItem(BarCode barcode) {
          this.remove(barcode);
     }
     
     /** Determines if the Item is in the Map.
      * @param barcode
      * @return 
      */
     public boolean containsProduct(BarCode barcode) {
          return this.containsKey(barcode);
     }
     
     /**
      * @Constraint barcode must be already stored in the ItemMap
      *   before using this method, you must first determine if
      *   the item is contained by using containsProduct(String)
      * @param barcode
      * @return 
      */
     public Item getItemByBarCode(BarCode barcode) {
//          return this.get(barcode).;
          return null;
     }
     
     /**
      * @returns number of Items stored in the ItemMap
      */
     public int getSize() {
          return this.size();
     }
     
     
     
}
