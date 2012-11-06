package gui.common;

import model.entities.Size;

public class SizeFormatter {
	public static String format(Size size){
		if(size.getSize() == 0)
			return "Unknown";
		return size.toString();
	}
}
