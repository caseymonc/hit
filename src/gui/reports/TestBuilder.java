package gui.reports;

import java.util.ArrayList;

public class TestBuilder extends Builder {
	
	private ArrayList<Object> list;
	@Override
	public void drawTitle(String title) {
		// TODO Auto-generated method stub
		list.add(title);

	}

	@Override
	public void drawTable(Table table) {
		list.add(table);
		// TODO Auto-generated method stub

	}

	@Override
	public void drawText(String text, int fontSize) {
		list.add(text);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printTableHeader(Cell cell) {
		list.add(cell);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printTable(Row row) {
		// TODO Auto-generated method stub
		list.add(row);
	}
	public boolean hasMoreObjects()
	{
		return list.size()>0;
	}
	public Object getNextObject()
	{
		return list.remove(list.size()-1);
	}

	@Override
	public void endHeader() {
		return;
	}

	@Override
	public void endTable() {
		return;
		
	}

	@Override
	public void startTable() {
		return;
	}

	@Override
	public void endDocument() {
		
	}

}
