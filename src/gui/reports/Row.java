package gui.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Casey Moncur
 * A class that represents a row for a table
 */
public class Row {
	/** List of cells*/
	private List<Cell> cells;
	
	/**
	 * Constructor
	 */
	public Row(){
		cells = new ArrayList<Cell>();
	}
	
	/**
	 * Add a cell to the row
	 * @param cell
	 */
	public void addCell(Cell cell){
		cells.add(cell);
	}
	
	/**
	 * Get the number of cells in the row
	 * @return the number of cells in the row
	 */
	public int size(){
		return cells.size();
	}
	
	/**
	 * Get a cell at i
	 * @param i index of the cell
	 * @return the cell at i
	 */
	public Cell getCell(int i){
		return cells.get(i);
	}
	
	public String toString(){
		return cells.toString();
	}
}
