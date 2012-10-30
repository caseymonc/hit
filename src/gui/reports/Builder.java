package gui.reports;

public abstract class Builder {	
	
	private Table tableToDraw;
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
		for(int i = 1; i < numColumns; ++i){
			printTableHeader(firstRow.getCell(i));
		}
		for(int i = 1; i < tableSize; ++i){
			printTable(table.getRow(i));
		}
	
	}
	
	public abstract void printTableHeader(Cell cell);

	public abstract void printTable(Row row);
}
