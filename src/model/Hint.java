package model;

/**
 * This class is used for the Observer Design Pattern to send
 * Hints back up to the UI Controllers when a change is made
 * to the Data Model
 * @author Casey Moncur
 *
 */
public class Hint {
	public enum Value{
		Add,
		Edit,
		Delete, 
		Move
	}
	private Object extra;
	private Value hint;
	
	public Hint(Object extra, Value hint){
		this.setExtra(extra);
		this.setHint(hint);
	}

	public void setExtra(Object extra) {
		this.extra = extra;
	}

	public Object getExtra() {
		return extra;
	}

	public void setHint(Value hint) {
		this.hint = hint;
	}

	public Value getHint() {
		return hint;
	}
}
