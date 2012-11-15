/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

/**
 *
 * @author davidpatty
 */
public abstract class BarCodeLookupHandler {

	private BarCodeLookupHandler next;
	private HandlerDescriptor descriptor;
	
	/**
	 * 
	 * @param descriptor 
	 */
	public BarCodeLookupHandler(HandlerDescriptor descriptor){
		this.descriptor = descriptor;
		this.next = null;
	}
	/**
	 *  set Descriptor method should be called later.
	 */
	public BarCodeLookupHandler(){
		descriptor = null;
		next = null;
	}
	/**
	 *	sets the descriptor
	 * @param descriptor 
	 */
	public void setDescriptor(HandlerDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	/**
	 * fetch the barcode description from the internet
	 * @return 
	 */
	public abstract String lookup(String barcode);
	
	/**
	 * 
	 * @param next 
	 */
	public void setNext(BarCodeLookupHandler next){
		this.next = next;
	}
	
}
