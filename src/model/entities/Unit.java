package model.entities;
/**
 * Represents the units of a size
 * @author Casey Moncur
 *
 */
public enum Unit{
	count("Count"),
	pounds("Pounds"),
	ounces("Ounces"),
	grams("Grams"),
	kilograms("Kilograms"),
	gallons("Gallons"),
	quarts("Quarts"),
	pints("Pints"),
	liters("Liters"),
	fluidOunces("Fluid ounces");
        
        /**
	 * String value to be returned by toString.
	 */
	private String _string;
	
	/**
	 * Constructor.
	 * 
	 * @param s String value to be returned by toString.
	 */
	private Unit(String s) {
            _string = s;
	}
        
	/** 
	 * returns the string of the enum value
	 */
	@Override
	public String toString() {
	    return _string;
	}
}
