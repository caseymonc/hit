package gui.reports;

/**
 * 
 * @author Casey Moncur
 * A class that represents a cell for a table
 */
public class Cell {
	private String value;
	
	public Cell(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
