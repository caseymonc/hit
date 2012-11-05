package reports.directors;

import gui.reports.Builder;
import java.util.Date;

/**
 * 
 * @author Casey Moncur
 * Class built for the Builder Pattern
 * This class holds the algorithm for building
 * reports
 */
public abstract class Director {
	
	protected Builder builder;

	/**
	 * Constructor
	 * @param builder The builder that will draw the report
	 */
	public Director(Builder builder){
		this.builder = builder;
	}
	
	/**
	 * Get the builder
	 * @return The builder
	 */
	public Builder getBuilder(){
		return builder;
	}
}
