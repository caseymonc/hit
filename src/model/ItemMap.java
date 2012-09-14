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
	 
	 /** Adds an item to the Map.
	  * 
	  * @param barcode - the BarCode of the item to be added
	  * @param i - Item to be added
	  * @throws Exception if BarCode is not unique
	  */
	 public void addItem(BarCode barcode, Item i) {
		 this.put(barcode,i);

	 }
	 /** Removes an item from the Map.
	  *	before using this method, you must first determine if the item is 
	  * contained by using containsProduct(String).
	  * 
	  * @param barcode the barcode of the item to be removed
	  * @throws exception if barcode is not already stored in the ItemMap.
	  */
	 public void removeItem(BarCode barcode) {
		  this.remove(barcode);
	 }
	 
	 /** Determines if the Item is in the Map.
	  * 
	  * @param barcode
	  * @return true if Item is in the Map else false
	  */
	 public boolean containsItem(BarCode barcode) {
		  return this.containsKey(barcode);
	 }
	 
	 /** Gets an Item using its BarCode
	  * Before using this method, you must first determine if
	  * the item is contained by using containsProduct(String)
	  * 
	  * @param barcode
	  * @return The Item associated with barcode
	  */
	 public Item getItemByBarCode(BarCode barcode) {
		  return null;
	 }
	 
	 /** Gets the size of the ItemMap
	  * 
	  * @returns number of Items stored in the ItemMap
	  */
	 public int getSize() {
		  return this.size();
	 }
	 
	 
	 
}
