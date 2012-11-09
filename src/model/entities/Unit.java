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
	fluidOunces("FluidOunces");
		
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
	
	public float getConversionFactor(Unit unit){
		if(this == unit){
			return 1;
		}
		
		if(unit.isVolume() && this.isVolume()){
			return this.getFactor()/unit.getFactor();
		}
		
		if(unit.isWeight() && this.isWeight()){
			return this.getFactor()/unit.getFactor();
		}
		
		return 0;
	}
	
	public float getFactor(){
		switch(this){
		case grams:
			return 1;
		case kilograms:
			return 1000;
		case ounces:
			return .03527396211f;
		case pounds:
			return 453.59233f;
		case fluidOunces:
			return 1;
		case pints:
			return 16;
		case quarts:
			return 32;
		case gallons:
			return 128;
		case liters:
			return 33.814022702f;
		default:
			return -1;
		}
		
		
	}

	public boolean isWeight(){
		return this == pounds
			|| this == ounces
			|| this == kilograms
			|| this == grams;
	}
	
	public boolean isVolume(){
		return !isWeight() && this != count;
	}
}
