
package model.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import model.CoreObjectModel;
import model.Hint;
import model.entities.*;
import model.BarCodeGenerator;
import model.managers.ProductManager;

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
        
        private ProductManager productManager;
	
	/** Constructor
	 * 
	 * creates a new ProductController
	 */
	private ProductController(){
		model = CoreObjectModel.getInstance();
                productManager = model.getProductManager();
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
            return productManager.getProductByBarCode(barcode);
        }
        
        /** Determines if a product can be added to the Product Manager
        * 
        * @param p - the product being added
        * 
        * @return true if p can be added. Otherwise, return false.
        */
       public boolean canAddProduct(Product p) {        
           return productManager.canAddProduct(p);
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

           productManager.addProduct(p);
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
            productManager.addProductToContainer(p, c);
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
        * Checks to see if a product can be removed.
        *
        * @param p - The product being added to c
        * @param c - The container that p is being removed from
        * @return return true if p can be removed, else return true
        */
        public boolean canRemoveProductFromContainer(Product p, ProductContainer c){
            return productManager.canRemoveProductFromContainer(p, c);
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
            productManager.removeProductFromContainer(p, c);
        }
        
        public void EditProduct(BarCode productBarCode, Product newProduct) 
                throws IllegalArgumentException {
            productManager.editProduct(productBarCode, newProduct);
            
            Product p = productManager.getProductByBarCode(productBarCode);
            System.out.println("Notifying observers...");
            this.setChanged();
            this.notifyObservers(new Hint(p, Hint.Value.Edit));
        }
        
        /**
        * Get the products in a ProductContainer
        *
        * @param c - The ProductContainer whose products will be returned
        * @return the Products that belong to c
        */
        public Collection<Product> getProductsByContainer(ProductContainer c) {
            return productManager.getProductsByContainer(c);
        }

        /**
         * Get the Containers that contain a Product
         *
         * @param p - The Product whose containers will be returned
         * @return the ProductContainers that contain p
         */
        public Set<ProductContainer> getContainersByProduct(Product p) {
            return productManager.getContainersByProduct(p);
        }
        
        /**
         * Tests whether or not a product can be edited.
         * 
         * @param _barcode
         * @param _desc
         * @param _sizeVal
         * @param _sizeUnit
         * @param _supply
         * @param _shelfLife
         * 
         * @return true if a product can be edited. Otherwise return false.
         */
        public boolean canEditProduct(String _barcode, String _desc, String _sizeVal, 
                Unit _sizeUnit, String _supply, String _shelfLife) {
            
            Product product = createProduct(_barcode, _desc, _sizeVal, 
                    _sizeUnit, _supply, _shelfLife);
            
            if(product == null) {
                return false;
            }
            
            return true;
        }
        
        /**
         * Tests whether or not a product can be added.
         * 
         * @param _barcode
         * @param _desc
         * @param _sizeVal
         * @param _sizeUnit
         * @param _supply
         * @param _shelfLife
         * 
         * @return true if a product can be added. Otherwise return false.
         */
        public boolean canAddProduct(String _barcode, String _desc, String _sizeVal, 
                Unit _sizeUnit, String _supply, String _shelfLife) {
            
            Product product = createProduct(_barcode, _desc, _sizeVal, 
                    _sizeUnit, _supply, _shelfLife);
            
            if(product == null){
                return false;
            }
            
            if(canAddProduct(product) == false) {
                return false;
            }
            
            return true;
        }
        
        /**
         * Tests whether or not a product can be created.
         * 
         * @param _barcode
         * @param _desc
         * @param _sizeVal
         * @param _sizeUnit
         * @param _supply
         * @param _shelfLife
         * 
         * @return a product if the product can be created.  Otherwise it returns null.
         */
        public Product createProduct(String _barcode, String _desc, String _sizeVal, 
                Unit _sizeUnit, String _supply, String _shelfLife) {
            
            String description = _desc;
            BarCode barCode = new BarCode(_barcode);
            float sizeVal;
            int supply;
            int shelfLife;
            boolean canAddProduct = true;
            
            try {
               sizeVal = Float.parseFloat(_sizeVal); 
            }
            catch(Exception e) {
                sizeVal = 0;
                canAddProduct = false;
            }
            
            try {
                shelfLife = Integer.parseInt(_shelfLife);
            }
            catch(Exception e) {
                shelfLife = 0;
                canAddProduct = false;
            }
            
            try {
                supply = Integer.parseInt(_supply);
            }
            catch(Exception e) {
                supply = 0;
                canAddProduct = false;
            }
            
            Size size = new Size(_sizeUnit, sizeVal);
            
            if(canAddProduct == false){
                return null;
            }
            
            if(Product.canCreate(description, barCode, shelfLife, supply, size) == false){
                return null;
            }
            
            Product product = new Product(description, barCode, shelfLife, supply, size);
            
            return product;
        }
        
        public Collection<Product> getAllProducts() {
            return productManager.getAllProducts();
        }
        
        public Collection<Item> getItemsByProduct(Product p) throws IllegalArgumentException {
            return productManager.getItemsByProduct(p);
        }
        
        public void addItemToProduct(Product p, Item i){
            productManager.addItemToProduct(p, i);
        }
        
        public void removeItemFromProduct(Product p, Item i) {
            productManager.removeItemFromProduct(p, i);
        }
}
