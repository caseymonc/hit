
package model.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import model.CoreObjectModel;
import model.entities.*;
import model.BarCodeGenerator;

/** 
 * @author davidmathis
 */
public class ProductController extends ModelController{
	
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
		model = CoreObjectModel.getInstance();
	}
        
        /**
        * Get the product by its BarCode
        *
        * @param barcode - The BarCode of the product being retrieved
        * @return the product whose BarCode is equal to barcode or null if such a
        * product doesn't exist.
        */
        public Product getProductByBarCode(BarCode barcode){
            return model.getProductManager().getProductByBarCode(barcode);
        }
        
        /**
        * Adds a product to a container
        *
        * @param p - Product to be added
        * @param c - The ProductContainer to which the product is being added.
        *
        * @throws IllegalArgumentException
        */
        public void addProductToContainer(Product p, ProductContainer c)
               throws IllegalArgumentException {
        
        }
        
        /**
        * Removes a product from a container.
        *
        * @param p - the product being removed from c
        * @param c - the ProductContainer from which p is being removed
        * @return return true if p can be removed, else return true
        *
        * @throws IllegalArgumentException if p or c are null
        */
        public void removeProductFromContainer(Product p, ProductContainer c)
                throws IllegalArgumentException {

        }
        
        /**
        * Get the products in a ProductContainer
        *
        * @param c - The ProductContainer whose products will be returned
        * @return the Products that belong to c
        */
        public Collection<Product> getProductsByContainer(ProductContainer c) {

            return null;
        }

        /**
         * Get the Containers that contain a Product
         *
         * @param p - The Product whose containers will be returned
         * @return the ProductContainers that contain p
         */
        public Set<ProductContainer> getContainersByProduct(Product p) {

            return null;
        }
}
