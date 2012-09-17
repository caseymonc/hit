package reports;

import java.util.Date;
import java.util.List;

import model.entities.Item;

public abstract class Report {
	private String name;
	private Date date;
	
	public Report(){
		
	}
	
	public abstract List<Item> getItems();
	public abstract void print();
}
