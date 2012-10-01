package gui.common;

import model.entities.Unit;

/**
 * Enumeration defining all the units of measurement (including weight,
 * volume, and count) supported by the program.
 */
public enum SizeUnits {
	
	// Weight units
	Pounds("pounds"),
	Ounces("ounces"),
	Grams("grams"),
	Kilograms("kilograms"),
	
	// Volume units
	Gallons("gallons"),
	Quarts("quarts"),
	Pints("pints"),
	FluidOunces("fluid ounces"),
	Liters("liters"),
	
	// Count units
	Count("count");
	
	/**
	 * String value to be returned by toString.
	 */
	private String _string;
	
	/**
	 * Constructor.
	 * 
	 * @param s String value to be returned by toString.
	 * 
	 * {@pre s != null}
	 * 
	 * {@post toString() == s}
	 */
	private SizeUnits(String s) {
		_string = s;
	}
	
	@Override
	public String toString() {
		return _string;
	}
	
	public Unit toUnit(){
		switch(this){
		case Pounds:
			return Unit.pounds;
		case Ounces:
			return Unit.ounces;
		case Grams:
			return Unit.grams;
		case Kilograms:
			return Unit.kilograms;
		case Gallons:
			return Unit.gallons;
		case Quarts:
			return Unit.quarts;
		case Pints:
			return Unit.pints;
		case FluidOunces:
			return Unit.fluidOunces;
		case Liters:
			return Unit.liters;
		case Count:
			return Unit.count;
		default:
			return null;
		}
	}

	public static SizeUnits fromUnit(Unit units) {
		switch(units){
		case pounds:
			return Pounds;
		case ounces:
			return Ounces;
		case grams:
			return Grams;
		case kilograms:
			return Kilograms;
		case gallons:
			return Gallons;
		case quarts:
			return Quarts;
		case pints:
			return Pints;
		case fluidOunces:
			return FluidOunces;
		case liters:
			return Liters;
		case count:
			return Count;
		default:
			return null;
		}
	}
	
}

