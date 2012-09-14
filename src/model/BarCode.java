package model;

import java.io.Serializable;

public class BarCode implements Serializable {
	private String barCode;
	
	public BarCode(String barCode){
		
	}
	
	public String generate(){
		return barCode;
	}
}
