package gui.reports;

import java.io.File;
import java.io.IOException;

public abstract class Builder {	
	protected String fileName;
	/**
	 * Draw the title of the report
	 * @param title The title of the Report
	 */

	public abstract void drawTitle(String title);
	
	/**
	 * Draw text in the report
	 * @param subTitle The text to draw
	 * @param fontSize The font size of the text
	 */
	public abstract void drawText(String text, int fontSize);
	
	
	/**
	 * Draw a table
	 * @param table The table to draw
	 */
	public void drawTable(Table table)
	{
		int tableSize = table.size();
		int numColumns = table.getRow(0).size();
		Row firstRow = table.getRow(0);
		// This assumes that the first line of the table is ALWAYS
		// the header information for the table
		startTable();
		for(int i = 0; i < numColumns; ++i){
			printTableHeader(firstRow.getCell(i));
		}
		endHeader();
		for(int i = 1; i < tableSize; ++i){
			printTable(table.getRow(i));
		}
		endTable();
	}
	public void display()
	{
		// Display the PDF
		try {
			java.awt.Desktop.getDesktop().open(new File(fileName));
		} catch (IOException e) {
			System.out.println("Unable to display the file " + fileName +  " ." + 
					" Please close the file and try to run the report again.");
			e.printStackTrace();
		}
	}
	
	public abstract void finish();
	
	public abstract void endHeader();
	public abstract void endTable();
	public abstract void startTable();
	public abstract void printTableHeader(Cell cell);
	public abstract void printTable(Row row);
	public abstract void endDocument();

}
