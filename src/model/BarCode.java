package model;

import java.io.Serializable;

public class BarCode implements Serializable {
	private String barCode;

    
	
        /**
         *  The purpose of the barcode class is to generate a UPC-A barcode and
         *  store for the barcode
         */
	public BarCode(){
		
	}
	
        /**
         *  This method creates a UPC-A barcode
         */
	private void generate(){
	
	}
        
        /**
         * 
         * @return The UPC-A barcode of the item
         */
        public String getBarCode() 
        {
             return barCode;
        }
        
     
}
