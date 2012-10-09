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
import model.persistence.PersistentItem;

/**
 *
 * @author davidpatty, davidmathis
 */
public class ProductManager implements PersistentItem{

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
    
    /** Determines if a product can be added to the Product Manager
     * 
     * @param p - the product being added
     * 
     * @return true if p can be added. Otherwise, return false.
     */
    public boolean canAddProduct(Product p) {        
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
            addProduct(p);
        }

        // Check to see if p is already contained somewhere in c's storage unit
        Set<ProductContainer> containers = p.getContainers();
        
        for(ProductContainer container : containers){
            if(container.getStorageUnit().equals(c.getStorageUnit())){
                container.removeProduct(p);
                p.removeProductContainer(container);
            }
        }
        assert(!p.getContainers().contains(c));
        
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
     * Adds an Item-Product relationship to itemsByProduct
     *
     * @param p
     * @param i
     *
     * @throws IllegalArgumentException
     */
    public void addItemToProduct(Product p, Item i) 
            throws IllegalArgumentException {
        
        assert(p != null);
        assert(i != null);
        assert(itemsByProduct.containsKey(p));
        
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
            assert(p.getCreationDate().compareTo(i.getEntryDate()) <= 0);
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
        assert(barcode != null);
        
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
