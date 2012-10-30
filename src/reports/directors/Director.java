package reports.directors;

import gui.reports.Builder;

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
	 * Run the algorithm to build the report
	 */
	public abstract void createReport();
	
	public abstract void createReport(int months);
	
	/**
	 * Get the builder
	 * @return The builder
	 */
	public Builder getBuilder(){
		return builder;
	}
}
