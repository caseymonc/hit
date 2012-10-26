package gui.reports;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private List<Row> rows;
	
	public Table(){
		rows = new ArrayList<Row>();
	}
	
	public void addRow(Row row){
		this.rows.add(row);
	}
	
	public int size(){
		return rows.size();
	}
	
	public Row getRow(int i){
		return rows.get(i);
	}
	
	
}
