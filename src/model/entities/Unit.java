package model.entities;
/**
 * Represents the units of a size
 * @author Casey Moncur
 *
 */
public enum Unit{
	count("count"),
	pounds("pounds"),
	ounces("ounces"),
	grams("grams"),
	kilograms("kilograms"),
	gallons("gallons"),
	quarts("quarts"),
	pints("pints"),
	liters("liters"),
	fluidOunces("fluid ounces");
        
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
