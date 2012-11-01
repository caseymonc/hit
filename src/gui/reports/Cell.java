package gui.reports;

/**
 * 
 * @author Casey Moncur
 * A class that represents a cell for a table
 */
public class Cell {
	private String value;
	private Cell partnerCell;
	
	public Cell(String value){
		this.value = value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
