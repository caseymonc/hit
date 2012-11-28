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

	protected BarCodeLookupHandler next;
	private HandlerDescriptor descriptor;
	
	/**
	 * Used to make a new handler for 
	 * @param descriptor Contains information about the handler
	 */
	public BarCodeLookupHandler(HandlerDescriptor descriptor){
		this.descriptor = descriptor;
		this.next = null;
	}
	/**
	 *  The default constructor for use in loading classes from
	 *  a file. The descriptor for the handler will be set following
	 *  construction.
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
	 * Fetch the barcode description from the Internet
	 * @return 
	 */
	public abstract String lookup(String barcode);
	
	/**
	 * This method is to assist in creating a singly linked list of
	 * registry handlers
	 * @param next 
	 */
	public void setNext(BarCodeLookupHandler next){
		this.next = next;
	}
	
	/**
	 * Returns the class values for this instance
	 */
	public HandlerDescriptor getDescriptor() {
		return descriptor;
	}
	
}
