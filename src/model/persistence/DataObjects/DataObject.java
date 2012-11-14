package model.persistence.DataObjects;

public abstract class DataObject {
	private long id;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
}
