package model.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import model.persistence.PersistentItem;

/**
 * Product A bar-coded product that can be stored in a Storage Unit. Example
 * Products are: Town House Light Buttery Crackers, 13 oz, Barcode 030100169079
 * Colgate Toothpaste-Cavity Protection, 6.40 oz, Barcode 035000509000
 *
 * A Product may be in any number of Storage Units. However, a Product may not
 * be in multiple different Product Containers within the same Storage Unit at
 * the same time. That is, a Product may appear at most once in a particular
 * Storage Unit.
 *
 * @author casey dmathis
 */
public class Product implements PersistentItem {

	/**
	 * Description of the Product. Must be non-empty.
	 */
	private String description;
	/**
	 * The date this Product was added to the system. The creation date is equal
	 * to the earliest Entry Date for any Item of this Product.
	 */
	private Date creationDate;
	/**
	 * Manufacturer's barcode for the Product. Must be non-empty.
	 */
	private BarCode barCode;
	/**
	 * The Product's shelf life, measured in months. Must be non-negative.
	 */
	private int shelfLife;
	/**
	 * The number of this Product required for a 3-month supply. A value of zero
	 * means "unspecified".
	 */
	private int threeMonthSupply;
	/**
	 * The size of the Product. A size has a quantity and a unit. For example,
	 * "13 oz", "5 lbs", "0.17 fl oz".
	 */
	private Size size;
	/**
	 * Product Containers that contain this Product. At most one Product
	 * Container in a Storage Unit may contain a particular Product.
	 */
	private Set<ProductContainer> containers;

	/**
	 * Constructor
	 *
	 * @param description
	 * @param barCode
	 * @param shelfLife
	 * @param threeMonthSupply
	 * @param size
	 */
	public Product(String description, BarCode barCode, 
			int shelfLife, int threeMonthSupply, Size size) {

		assert (!description.equals(""));
		assert (barCode != null);
		assert (shelfLife >= 0);
		assert (threeMonthSupply >= 0);
		assert (size != null);

		if (description.equals("")) {
			throw new IllegalArgumentException("Description cannot be empty");
		}

		if (barCode == null || barCode.getBarCode().equals("")) {
			throw new IllegalArgumentException("Barcode is not valid");
		}

		if (shelfLife < 0) {
			throw new IllegalArgumentException("Shelf Life is negative");
		}

		if (threeMonthSupply < 0) {
			throw new IllegalArgumentException("3 Month Supply is negative");
		}

		if(!isValidProductSize(size)){
			throw new IllegalArgumentException("Size is not valid");
		}
		
		this.description = description;
		this.barCode = barCode;
		this.shelfLife = shelfLife;
		this.threeMonthSupply = threeMonthSupply;
		this.size = size;
		this.creationDate = new Date();
		this.containers = new HashSet<ProductContainer>();

	}

	public static boolean canCreate(String description, BarCode barCode, 
			int shelfLife, int threeMonthSupply, Size size){
		try{
			Product p = new Product(description, barCode, shelfLife, threeMonthSupply, size);
			return true;
		}
		catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/**
	 * Add a container to the product's set of containers
	 *
	 * @param container - The ProductContainer to add to the set of containers
	 */
	public void addProductContainer(ProductContainer container) {
		this.containers.add(container);
	}

	/**
	 * Removes a container from the product's set of containers
	 *
	 * @param container - The ProductContainer to remove from the set of
	 * containers
	 */
	public void removeProductContainer(ProductContainer container) {
		this.containers.remove(container);
	}

	// Getters
	/**
	 * Get the description of the product
	 *
	 * @return the description of the product
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the creation date of the product
	 *
	 * @return the creation date of the product
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Get the BarCode of the product
	 *
	 * @return the BarCode of the product
	 */
	public BarCode getBarCode() {
		return barCode;
	}

	/**
	 * Get the shelf life of the product
	 *
	 * @return the shelf life of the product
	 */
	public int getShelfLife() {
		return shelfLife;
	}

	/**
	 * Get the three month supply of the product
	 *
	 * @return the three month supply of the product
	 */
	public int getThreeMonthSupply() {
		return threeMonthSupply;
	}

	/**
	 * Get the size of the product
	 *
	 * @return the size of the product
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * Get the containers of the product
	 *
	 * @return the containers of the product
	 */
	public Set<ProductContainer> getContainers() {
		return containers;
	}

	// Setters
	/**
	 * set the description of the product
	 *
	 * @param description - the description of the product
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * set the shelf life of the product
	 *
	 * @param shelf life - the shelf life of the product
	 */
	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	/**
	 * set the size of the product
	 *
	 * @param size - the size of the product
	 */
	public void setSize(Size size) {
		this.size = size;
	}

	/**
	 * set the three month supply of the product
	 *
	 * @param three month supply - the three month supply of the product
	 */
	public void setThreeMonthSupply(int threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	/**
	 * Sets the creation date of the product
	 *
	 * @param date - the new creation date of the product
	 *
	 * @throws IllegalArgumentException if date is null or in the future.
	 */
	public void setCreationDate(Date date) throws IllegalArgumentException {
		assert (date != null);

		Calendar calendar = new GregorianCalendar();

		Date curDate = calendar.getTime();

		if (date.compareTo(curDate) >= 0) {
			throw new IllegalArgumentException("creation date is in the future");
		}

		this.creationDate = date;
	}
	
	/** is the product size valid?
	* 
	* @param size
	* @return true if product size is valid 
	*/
	private static boolean isValidProductSize(Size size){
		if(size == null){
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

	/**
	 * determines if an Object is equal to this Product
	 *
	 * @param obj - the Object being compared with this Product
	 *
	 * @return true if obj is equal to this Product, otherwise return false
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
			Product prod = (Product) obj;
			return this.barCode == prod.barCode;
		} else {
			return false;
		}
	}

	/*@Override
	public int hashCode() {
		return this.barCode.hashCode();
	}*/

}