package model;

import java.util.HashSet;
import java.util.Set;
import model.entities.BarCode;

/**
 * Generates Valid UPC-A BarCodes
 * @author Casey Moncur
 *
 */
public class BarCodeGenerator {
	
	/** Singleton instance of BarCodeGenerator*/
	private static BarCodeGenerator _instance;
	private HashSet<BarCode> barCodes;
	int lastBarCode;
	
	/** 
	 * Get an instance of BarCodeGenerator
	 * @return singleton instance of BarCodeGenerator
	 */
	public static BarCodeGenerator getInstance()
	{
	   if(_instance == null)
	   {
		   _instance = new BarCodeGenerator();
	   }
	   return _instance;
	}
	
	/**
	 * Private Constructor
	 */
	private BarCodeGenerator(){
		// barCodes = new HashSet<BarCode>(); I don't think that we need this
		// because we know that every code we generate is unique 
		// because the barcodegenerator class is a singleton class and it starts
		// with 1 as the starting barcode and will go up to 100 
		lastBarCode = 0;
	}
	
	/**
	 * Get a unique bar code
	 * @return A unique BarCode
	 */
	public BarCode generateBarCode(){
          lastBarCode++; 
		// We can have ~2.1 billion products before this ever 
		// goes bad do we need a reset if it reaches that number?
		int length = (lastBarCode == 0) ? 1 : (int)Math.log10(lastBarCode) + 1;
		String barCodeBuilder= "" ;
		for (int i=0; i<(11-length); i++) {
			barCodeBuilder += "0";
		}
		barCodeBuilder += lastBarCode;
		barCodeBuilder += getCheckDigit(barCodeBuilder);
		BarCode newBarCode = new BarCode(barCodeBuilder);
		return newBarCode;
	}
	
	/**
	 * This method generates the check digit of a UPC-A Barcode
	 * @param barCodeBuilder The barcodeBuilder is the first 11 digits of the barcode in string form
	 * @return The check digit of the barCode
	 */
	private String getCheckDigit(String barCodeBuilder)
	{
		// In the UPC-A system, the check digit is calculated as follows:
		// Add the digits in the odd-numbered positions (first, third, fifth, etc.) together and multiply by three.
		// Add the digits in the even-numbered positions (second, fourth, sixth, etc.) to the result.
		// Find the result modulo 10 (i.e. the remainder when divided by 10.. 10 goes into 58 5 times with 8 leftover).
		// If the result is not zero, subtract the result from ten.
		// For example, a UPC-A barcode (in this case, a UPC for a box of tissues)
		// "03600029145X" where X is the check digit, X can be calculated by
		// adding the odd-numbered digits (0 + 6 + 0 + 2 + 1 + 5 = 14),
		// multiplying by three (14 × 3 = 42),
		// adding the even-numbered digits (42 + (3 + 0 + 0 + 9 + 4) = 58),
		// calculating modulo ten (58 mod 10 = 8),
		// subtracting from ten (10 − 8 = 2).
		int oddIndexDigits = 0;
		int evenIndexDigits = 0;
		int result = 0;
		for(int i = 0; i < barCodeBuilder.length(); i+=2) {
			oddIndexDigits += barCodeBuilder.charAt(i);
			if(i!=10) {
				evenIndexDigits += barCodeBuilder.charAt(i+1);
			}	
		}	
		result = oddIndexDigits * 3;
		result += evenIndexDigits;
		result = result % 10;
		if(result != 0) {
			result = 10 - result;
		}
		
		String retVal = "";
		retVal +=  result;
		return retVal;
	}
}
