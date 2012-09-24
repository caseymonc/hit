/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import model.entities.*;

/**
 *
 * @author davidpatty, davidmathis
 */
public class ProductManager {

    /**
     * Map of products indexed by their BarCodes
     */
    Map<BarCode, Product> productsByBarCode;
    /**
     * Map of Item Collections indexed by their product
     */
    Map<Product, Set<Item>> itemsByProduct;

    /**
     * Constructor
     *
     * Creates a new ProductManager
     */
    public ProductManager() {
        productsByBarCode = new HashMap<BarCode, Product>();
        itemsByProduct = new HashMap<Product, Set<Item>>();
    }

    /** Determines if a product is valid
     * 
     * @param p - the product being checked
     * 
     * @return true if p is valid. Otherwise, return false.
     */
    public boolean isValidProduct(Product p){
        if(p.getDescription().equals("")){
            return false;
        }
        
        if(p.getBarCode() == null || p.getBarCode().getBarCode().equals("")){
            return false;
        }
        
        if(p.getShelfLife() < 0){
            return false;
        }
        
        if(p.getThreeMonthSupply() < 0){
            return false;
        }
        
        Size size = p.getSize();
        
        if(size == null){
            return false;
        }
        
        if(!(size.getUnits() instanceof Unit)){
            return false;
        }
        
        if(size.getSize() < 1){
            return false;
        }
        
        if(size.getUnits() == Unit.count && size.getSize() != 1){
            return false;
        }
        
        return true;
    }
    
    /** Determines if a product can be added to the Product Manager
     * 
     * @param p - the product being added
     * 
     * @return true if p can be added. Otherwise, return false.
     */
    public boolean canAddProduct(Product p) {
        if(isValidProduct(p) == false){
            return false;
        }
        
        if (productsByBarCode.containsKey(p.getBarCode())) {
            return false;
        }
   
        return true;
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
        
        productsByBarCode.put(p.getBarCode(), p);
        itemsByProduct.put(p, new HashSet<Item>());
    }

    /** Checks to see if a product can be added to a container
     * 
     * @param p - Product to be added
     * @param c - The ProductContainer to which the product is being added.
     */
    public boolean canAddProductToContainer(Product p, ProductContainer c){
        
        if(p == null || c == null){
            return false;
        }
        
        if(c.canAddProduct(p) == false){
            return false;
        }      
        
        return true;
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

        assert (p != null);
        assert (c != null);
        assert (canAddProductToContainer(p, c));
        
        if (p == null || c == null) {
            throw new IllegalArgumentException();
        }

        if(canAddProductToContainer(p, c) == false) {
            throw new IllegalArgumentException("Product cannot be added to container");
        }
        
        if (productsByBarCode.containsKey(p.getBarCode()) == false) {
            if(isValidProduct(p)){
                addProduct(p);
            } else{
                throw new IllegalArgumentException("Product is not valid");
            }
        }

        // Check to see if p is already contained somewhere in c's storage unit
        Set<ProductContainer> containers = p.getContainers();
        
        for(ProductContainer container : containers){
            if(container.getStorageUnit().equals(c.getStorageUnit())){
                container.removeProduct(p);
                p.removeProductContainer(container);
            }
        }
        
        p.addProductContainer(c);
        c.addProduct(p);
    }

    /**
     * Checks to see if a product can be removed.
     *
     * @param p - The product being added to c
     * @param c - The container that p is being removed from
     * @return return true if p can be removed, else return true
     */
    public boolean canRemoveProductFromContainer(Product p, ProductContainer c){

        assert (p != null);
        assert (c != null);

        // does c have Items that point to p
        Collection<Item> items = c.getAllItems();

        for (Item item : items) {
            if (item.getProduct().equals(p)) {
                // their are items in c that point to p
                return false;
            }
        }

        return true;
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

        assert (p != null);
        assert (c != null);
        assert (canRemoveProductFromContainer(p, c));

        if (p == null || c == null) {
            throw new IllegalArgumentException();
        }

        c.removeProduct(p);
        p.removeProductContainer(c);

        if (p.getContainers().isEmpty()) {
            assert(canRemoveProduct(p));
            removeProduct(p);
        }
    }

    /**
     * Determines if a product can be removed from the ProductManager
     *
     * @param p
     * @return true if no items belong to p, otherwise return false.
     */
    public boolean canRemoveProduct(Product p) {
        assert(p != null);
        
        Set<Item> items = itemsByProduct.get(p);

        for (Item item : items) {
            if (item.getExitDate() == null) {
                // active items still belong to p so it cannot be 
                // removed from the system
                return false;
            }
        }

        return true;
    }

    /**
     * Removes a product from the ProductManager
     *
     * @param p - the product being removed
     * @throws IllegalArgumentException
     */
    public void removeProduct(Product p) throws IllegalArgumentException {
        assert (p != null);
        assert (canRemoveProduct(p));

        if (p == null) {
            throw new IllegalArgumentException();
        }

        productsByBarCode.remove(p.getBarCode());
        itemsByProduct.remove(p);
    }

    /**
     * Checks to see if the new information being used to edit a product is
     * valid
     *
     * @param product
     * @param newProduct
     * @return true if oldProduct can be updated to newProduct
     */
    public boolean canEditProduct(Product product, Product newProduct) {

        assert (product != null);
        assert (newProduct != null);

        // the description must be non-empty
        if (newProduct.getDescription().equals("")) {
            return false;
        }

        // the shelfLife must be non-negative
        if (newProduct.getShelfLife() < 0) {
            return false;
        }

        // the threeMonthSupply must be non-negative
        if (newProduct.getThreeMonthSupply() < 0) {
            return false;
        }

        Size size = newProduct.getSize();
        
        if(size == null){
            return false;
        }
        
        // the size must be positive and cannot be zero
        if (size.getSize() <= 0) {
            return false;
        }

        // if the unit of the size is count, the magnitude must be 1
        if (size.getUnits() == Unit.count && size.getSize() != 1) {
            return false;
        }

        return true;
    }

    /**
     * Edits a product by setting oldProduct to newProduct
     *
     * @param product - the product being updated
     * @param newProduct - the new product with the updated values
     */
    public void editProduct(Product product, Product newProduct)
            throws IllegalArgumentException {
        assert (product != null);
        assert (newProduct != null);
        assert (canEditProduct(product, newProduct));

        if (product == null || newProduct == null) {
            throw new IllegalArgumentException();
        }

        product.setDescription(newProduct.getDescription());
        product.setShelfLife(newProduct.getShelfLife());
        product.setSize(newProduct.getSize());
        product.setThreeMonthSupply(newProduct.getThreeMonthSupply());
    }

    /**
     * Adds an Item-Product relationship to itemsByProduct
     *
     * @param p
     * @param i
     *
     * @throws IllegalArgumentException
     */
    public void addItemToProduct(Product p, Item i) 
            throws IllegalArgumentException {
        if (p == null || i == null) {
            throw new IllegalArgumentException();
        }
 
        if(itemsByProduct.containsKey(p)){
            itemsByProduct.get(p).add(i);
            
            // A product's creation date is equal to the earliest entry
            // date of any its items.
            if(p.getCreationDate().compareTo(i.getEntryDate()) > 0){
                    p.setCreationDate(i.getEntryDate());
            }
            
        } else{
            throw new IllegalArgumentException("The product doesn't exist");
        }
    }

    /**
     * Determines if a product exists
     *
     * @param barcode - the BarCode of the product being searched for.
     * @return true if the product is contained in the ProductMap. Otherwise,
     * return false.
     */
    public boolean productExists(BarCode barcode) {
        return productsByBarCode.containsKey(barcode);
    }

    /**
     * Get the product by its BarCode
     *
     * @param barcode - The BarCode of the product being retrieved
     * @return the product whose BarCode is equal to barcode or null if such a
     * product doesn't exist.
     */
    public Product getProductByBarCode(BarCode barcode) {

        assert (barcode != null);

        return productsByBarCode.get(barcode);
    }

    /**
     * Get the products in a ProductContainer
     *
     * @param c - The ProductContainer whose products will be returned
     * @return the Products that belong to c
     */
    public Collection<Product> getProductsByContainer(ProductContainer c) {

        assert (c != null);

        return c.getAllProducts();
    }

    /**
     * Get the Containers that contain a Product
     *
     * @param p - The Product whose containers will be returned
     * @return the ProductContainers that contain p
     */
    public Set<ProductContainer> getContainersByProduct(Product p) {

        assert (p != null);

        return p.getContainers();
    }
    
    public boolean hasProduct(Product p){
        return productsByBarCode.containsKey(p.getBarCode());
    }
}
