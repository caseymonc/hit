package model;

import java.io.Serializable;

public class BarCode implements Serializable {
	private String barCode;
	
	public BarCode(String barCode){
		setBarCode(barCode);
	}
	
	public void setBarCode(String barCode){
		this.barCode = barCode;
	}
	
	public String getBarCode(){
		return barCode;
	}
}
