package model;

import java.io.Serializable;

public class Size implements Serializable {
	private String units;
	private float size;
	
	public void setUnits(String units) {
		this.units = units;
	}
	
	public String getUnits() {
		return units;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getSize() {
		return size;
	}
}
