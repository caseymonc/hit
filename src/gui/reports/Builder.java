package gui.reports;

public abstract class Builder {	
	
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
	public abstract void drawTable(Table table);
}
