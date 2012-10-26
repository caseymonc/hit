package gui.reports;

/**
 * 
 * @author Casey Moncur
 * Class built for the Builder Pattern
 * This class holds the algorithm for building
 * reports
 */
public abstract class Director {
	
	private Builder builder;

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
	
	/**
	 * Get the builder
	 * @return The builder
	 */
	public Builder getBuilder(){
		return builder;
	}
}
