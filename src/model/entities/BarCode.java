package model.entities;

import java.io.Serializable;

/** BarCode
 * A Barcode must be a valid UPC-A barcode.  All products 
 * will have their own BarCode and HIT will generate new 
 * unique BarCodes for all items.
 * 
 * @author casey dmathis
 */
public class BarCode implements Serializable {
	
	/** The value of the BarCode.
	 * This value must be a valid UPC-A barcode and must
	 * be unique for all products and items in the system.
	 */
	private String barCode;
	
	/** Constructor
	 * 
	 * @param barCode - The value that will be assigned to barCode. 
	 */
	public BarCode(String barCode){
		this.barCode = barCode;
	}
	
	/** gets the value of the BarCode
	 * 
	 * @return barCode - the value of the BarCode
	 */
	public String getBarCode(){
		return barCode;
	}
     
	@Override
	public boolean equals(Object obj) 
	{
	    if(obj instanceof BarCode)
	    {
		   BarCode objBCode = (BarCode)obj;
		   return this.barCode.equals(objBCode.barCode);
	    }
	    else{
		   return false;
	    }
	}

	@Override
	public int hashCode() {
		return this.barCode.hashCode();
	}
}
