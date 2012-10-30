package gui.reports;

/**
 * 
 * @author Casey Moncur
 * A class that represents a cell for a table
 */
public class Cell {
	private Object value;
	private Cell partnerCell;
	
	public Cell(Object value){
		this.value = value;
		this.partnerCell = null;
	}
	
	public Cell(Object value, Cell partnerCell){
		this.value = value;
		this.partnerCell = partnerCell;
	}
	
	public Cell(Object value, Object partnerValue){
		this.value = value;
		this.partnerCell = new Cell(partnerValue);
	}
	
	public void setValue(Object value){
		this.value = value;
	}
	
	public void setPartnerCell(Cell partnerCell){
		this.partnerCell = partnerCell;
	}
	
	public Object getValue(){
		return value;
	}
	
	public Cell getPartnerCell(){
		return partnerCell;
	}
	
	@Override
	public String toString(){
		String result = "";
		if(value instanceof Long) {
			result = Long.toString((long)value);
		} else if(value instanceof Integer) {
			result = Integer.toString((int)value);
		} else if(value instanceof String) {
			result = (String)value;
		}
		
		if(partnerCell != null){
			result += "/" + partnerCell.toString();
		}
		
		return result;
	}
}
