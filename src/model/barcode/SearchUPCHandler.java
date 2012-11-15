/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

/**
 *
 * @author davidpatty
 */
public class SearchUPCHandler extends BarCodeLookupHandler {

	public SearchUPCHandler() {
		super();
	}
	
	public SearchUPCHandler(HandlerDescriptor descriptor) {
		super(descriptor);
	}

	@Override
	public String lookup(String barcode) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
