
package model.controllers;

import model.CoreObjectModel;
import model.entities.Product;
import model.entities.ProductContainer;
import model.entities.StorageUnit;

/** 
 *
 * @author davidmathis
 */
public class ProductController {
	
	/** The CoreObjectModel used to manage all data
	 * model implements the singleton that it can be shared
	 * between many different Controllers
	 */
	private CoreObjectModel model;
	
	/** Constructor
	 * 
	 * creates a new ProductController
	 */
	public ProductController(){
		//model = CoreObjectModel.getInstance();
	}
}
