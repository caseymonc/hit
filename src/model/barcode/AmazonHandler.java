/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

/**
 *
 * @author davidpatty
 */
public class AmazonHandler extends BarCodeLookupHandler {

	public AmazonHandler() {
		super();
	}
	
	public AmazonHandler(HandlerDescriptor descriptor) {
		super(descriptor);
	}

	@Override
	public String lookup(String barcode) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}