package gui.product;

import gui.common.Tagable;
import model.entities.Product;

/**
 * Display data class for products.
 */
public class ProductData extends Tagable {

	/**
	 * Description attribute.
	 */
	private String _description;
	
	/**
	 * Size attribute.
	 */
	private String _size;
	
	/**
	 * Count attribute.
	 */
	private String _count;
	
	/**
	 * Shelf Life attribute
	 */
	private String _shelfLife;
	
	/**
	 * Supply attribute.
	 */
	private String _supply;
	
	/**
	 * Barcode attribute.
	 */
	private String _barcode;

	/**
	 * Constructor.
	 * 
	 * {@pre None}
	 * 
	 * {@post getDescription() == ""}
	 * {@post getSize() == ""}
	 * {@post getCount() == ""}
	 * {@post getShelfLife() == ""}
	 * {@post getSupply() == ""}
	 * {@post getBarcode() == ""}
	 */
	public ProductData() {
		_description = "";
		_size = "";
		_count = "";
		_shelfLife = "";
		_supply = "";
		_barcode = "";
	}

	public ProductData(Product product) {
		if(product == null){
			_description = "";
			_size = "";
			_count = "";
			_shelfLife = "";
			_supply = "";
			_barcode = "";	
		} else {
			_barcode = product.getBarCode().getBarCode();
			_count = "1";
			_description = product.getDescription();
			_shelfLife = Integer.toString(product.getShelfLife());
			_supply = Integer.toString(product.getThreeMonthSupply());
			_size = product.getSize().toString();
			this.setTag(product);
		}
	}

	public void incrementCount() {
	    int count;
	    try{
		   count = Integer.valueOf(_count);
	    }
	    catch(Exception e){
		   count = 0;
	    }

	    count++;

	    _count = Integer.toString(count);
	}

	/**
	 * Returns the value of the Barcode attribute.
	 */
	public String getBarcode() {
		return _barcode;
	}

	/**
	 * Sets the value of the Barcode attribute.
	 * 
	 * @param barcode New Barcode value
	 * 
	 * {@pre barcode != null}
	 * 
	 * {@post getBarcode() == barcode}
	 */
	public void setBarcode(String barcode) {
		this._barcode = barcode;
	}

	/**
	 * Returns the value of the Description value.
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Sets the value of the Description value.
	 * 
	 * @param description New Description value
	 * 
	 * {@pre description != null}
	 * 
	 * {@post getDescription() == description}
	 */
	public void setDescription(String description) {
		this._description = description;
	}

	/**
	 * Returns the value of the Size attribute.
	 */
	public String getSize() {
		return _size;
	}

	/**
	 * Sets the value of the Size attribute.
	 * 
	 * @param size New Size value
	 * 
	 * {@pre size != null}
	 * 
	 * {@post getSize() == size}
	 */
	public void setSize(String size) {
		this._size = size;
	}

	/**
	 * Returns the value of the Count attribute.
	 */
	public String getCount() {
		return _count;
	}

	/**
	 * Sets the value of the Count attribute.
	 * 
	 * @param count New Count value
	 * 
	 * {@pre count != null}
	 * 
	 * {@post getCount() == count}
	 */
	public void setCount(String count) {
		this._count = count;
	}

	/**
	 * Returns the value of the Shelf Life attribute.
	 */
	public String getShelfLife() {
		return _shelfLife;
	}

	/**
	 * Sets the value of the Shelf Life attribute.
	 * 
	 * @param shelfLife New Shelf Life value
	 * 
	 * {@pre shelfLife != null}
	 * 
	 * {@post getShelfLife() == shelfLife}
	 */
	public void setShelfLife(String shelfLife) {
		this._shelfLife = shelfLife;
	}

	/**
	 * Returns the value of the Supply attribute.
	 */
	public String getSupply() {
		return _supply;
	}

	/**
	 * Sets the value of the Supply attribute.
	 * 
	 * @param supply New Supply value
	 * 
	 * {@pre supply != null}
	 * 
	 * {@post getSupply() == supply}
	 */
	public void setSupply(String supply) {
		this._supply = supply;
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
		if (obj instanceof ProductData) {
			ProductData prod = (ProductData) obj;
			return _barcode.equals(prod.getBarcode());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return _barcode.hashCode();
	}
}

