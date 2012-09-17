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
	
	/**
	 * Constructor
	 */
	private BarCodeGenerator(){
		barCodes = new HashSet<BarCode>();
	}
	
	/**
	 * Get a unique bar code
	 * @return A unique BarCode
	 */
	public BarCode generateBarCode(){
		return null;
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
