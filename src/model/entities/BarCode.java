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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4706105919749843346L;
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
	
	/** 
	 * Converts a BarCode to a string
	 * 
	 * @return the BarCode as a string
	 */
	@Override
	public String toString() {
		return barCode;
	}
	/**
	 * Method test to make sure that the barcode is a valid UPC Barcode
	 * Which it only wouldn't be if someone was using or source code it a way that 
	 * they should not be.
	 * @return boolean of whether the barcode is valid or not
	 */
	public boolean isValid()
	{
		return barCode.length()==12 && checkCheckDigit();
	}
	
	/*
	 * Private 
	 */
	private boolean checkCheckDigit()
	{
		int oddIndexDigits = 0;
		int evenIndexDigits = 0;
		for(int i = 0; i < barCode.length()-1; i+=2) {
			oddIndexDigits += Integer.parseInt(barCode.substring(i,i+1));
			if(i!=10) {
				evenIndexDigits += Integer.parseInt(barCode.substring(i+1,i+2));
			}	
		}	
		int result = oddIndexDigits * 3;
		result += evenIndexDigits;
		result = result % 10;
		if(result != 0) {
			result = 10 - result;
		}
		
		String retVal = "";
		retVal +=  result;
		
		return retVal.charAt(0) == barCode.charAt(11);
	}
}
