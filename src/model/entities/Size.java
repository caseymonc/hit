package model.entities;

import java.io.Serializable;
/**
 * Represents the size of an Item, Product, or three month supply
 * 
 * @author Casey Moncur
 */
public class Size implements Serializable {
	
	/** The unit of measurement.
	 * Can be any of the following: count, pounds, ounces, grams, kilograms, 
	 * gallons, quarts, pints, fluid, ounces, liters.
	 */
	private Unit units;
	
	/** The float value of the size
	 * Can be any positive float value (zero is not allowed). 
	 * If the unit of measurement is "count", the magnitude must be "1".
	 */
	private float size;
	
	/** Constructor
	 * Create a size object
	 * 
	 * @param units - the units that this size object
	 * @param size - the float value in units
	 */
	public Size(Unit units, float size){
		assert(size >= 0);
		
		if(units == Unit.count){
			assert(isInteger(size));
			
			if(!isInteger(size))
				throw new IllegalArgumentException();
		}
		
		if(size < 0) {
			throw new IllegalArgumentException("Size must be non-negative");
		}
		
		this.setUnits(units);
		this.setSize(size);
	}
	
	/**
	 * Tests whether size is an integer
	 * @param size
	 * @return
	 */
	private boolean isInteger(float size){
		int intSize = (int)size;
		size -= intSize;
		return size == 0;
	}
	
	/** Set the units of the size object
	 * 
	 * @param units - the units that this size object
	 */
	public void setUnits(Unit units) {
		this.units = units;
	}
	
	/** Get the units of the size object
	 * 
	 * @return the units of the size
	 */
	public Unit getUnits() {
		return units;
	}

	/** Set the float value of the size
	 * 
	 * @param size - the float value of the size
	 */
	public void setSize(float size) {
		assert(size >= 0);
		
		if(units == Unit.count){
			assert(isInteger(size));
			
			if(!isInteger(size))
				throw new IllegalArgumentException();
		}
		
		if(size < 0) {
			throw new IllegalArgumentException("Size must be non-negative");
		}
		
		this.size = size;
	}

	/** Get the float value of the size
	 * 
	 * @return the float value of the size
	 */
	public float getSize() {
		return size;
	}
}
