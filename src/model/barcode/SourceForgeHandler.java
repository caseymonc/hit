/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

/**
 *
 * @author davidpatty
 */
public class SourceForgeHandler extends BarCodeLookupHandler {

	public SourceForgeHandler() {
		super();
	}
	
	public SourceForgeHandler(HandlerDescriptor descriptor) {
		super(descriptor);
	}

	@Override
	public String lookup(String barcode) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}