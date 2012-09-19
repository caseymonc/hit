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
	private static BarCodeGenerator instance;
	
	/** The set that contains all used bar codes*/
	private Set<BarCode> barCodes;
        
        private int lastBarCode;
	
	/**
	 * Constructor
	 */
	private BarCodeGenerator(){
		barCodes = new HashSet<BarCode>();
                int lastBarCode = 0;
	}
	
	/**
	 * Get a unique bar code
	 * @return A unique BarCode
	 */
	public BarCode generateBarCode(){
//        In the UPC-A system, the check digit is calculated as follows:
//        Add the digits in the odd-numbered positions (first, third, fifth, etc.) together and multiply by three.
//        Add the digits in the even-numbered positions (second, fourth, sixth, etc.) to the result.
//        Find the result modulo 10 (i.e. the remainder when divided by 10.. 10 goes into 58 5 times with 8 leftover).
//        If the result is not zero, subtract the result from ten.
//        For example, a UPC-A barcode (in this case, a UPC for a box of tissues) "03600029145X" where X is the check digit, X can be calculated by
//        adding the odd-numbered digits (0 + 6 + 0 + 2 + 1 + 5 = 14),
//        multiplying by three (14 × 3 = 42),
//        adding the even-numbered digits (42 + (3 + 0 + 0 + 9 + 4) = 58),
//        calculating modulo ten (58 mod 10 = 8),
//        subtracting from ten (10 − 8 = 2).
		return null;
	}
	
        private String getCheckDigit()
        {
            return "";
        }
        
	/** 
	 * Get an instance of BarCodeGenerator
	 * @return singleton instance of BarCodeGenerator
	 */
	public static BarCodeGenerator instance(){
		if(instance != null)
			instance = new BarCodeGenerator();
		return instance;
	}
}
