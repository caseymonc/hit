
package model.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import model.CoreObjectModel;
import model.Hint;
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
	
	private static ProductController instance;
	private CoreObjectModel model;
	
	/** Constructor
	 * 
	 * creates a new ProductController
	 */
	private ProductController(){
		model = CoreObjectModel.getInstance();
	}
	
	public static ProductController getInstance(){
		if(instance == null){
			instance = new ProductController();
		}
		
		return instance;
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
        
        /** Determines if a product can be added to the Product Manager
        * 
        * @param p - the product being added
        * 
        * @return true if p can be added. Otherwise, return false.
        */
       public boolean canAddProduct(Product p) {        
           return model.getProductManager().canAddProduct(p);
       }

       /** Adds a product to the Product Manager
        * 
        * @param p - the product being added
        * 
        * @throws IllegalArgumentException
        */
       public void addProduct(Product p) throws IllegalArgumentException {
           assert (p != null);
           assert (canAddProduct(p));

           if(p == null || (canAddProduct(p) == false)){
               throw new IllegalArgumentException("Not a valid Product");
           }

           model.getProductManager().addProduct(p);
           this.setChanged();
           this.notifyObservers(new Hint(p, Hint.Value.Add));
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
            model.getProductManager().addProductToContainer(p, c);
        }
        
        public void moveProductToContainer(Product product, ProductContainer targetContainer){
        	StorageUnit targetUnit = targetContainer.getStorageUnit();
        	ProductContainer currentContainer = targetUnit.getProductGroupByProduct(product);
        	if(currentContainer == null){
        		addProductToContainer(product, targetContainer);
        	}else{
        		//Move the Product and all associated Items from 
        		//their old Product Container to the Target
        		//Product Container
        		targetUnit.moveProduct(product, targetContainer);
        	}
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
            model.getProductManager().removeProductFromContainer(p, c);
        }
        
        /**
        * Get the products in a ProductContainer
        *
        * @param c - The ProductContainer whose products will be returned
        * @return the Products that belong to c
        */
        public Collection<Product> getProductsByContainer(ProductContainer c) {
            return model.getProductManager().getProductsByContainer(c);
        }

        /**
         * Get the Containers that contain a Product
         *
         * @param p - The Product whose containers will be returned
         * @return the ProductContainers that contain p
         */
        public Set<ProductContainer> getContainersByProduct(Product p) {
            return model.getProductManager().getContainersByProduct(p);
        }
}
