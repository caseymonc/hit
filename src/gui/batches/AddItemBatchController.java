package gui.batches;

import com.itextpdf.text.Document;
import gui.common.*;
import gui.inventory.*;
import gui.product.*;
import java.io.IOException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.entities.Item;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
		IAddItemBatchController {
	
	private List<Item> newItems;

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
		
		construct();
		newItems = new ArrayList<>();
	}

	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IAddItemBatchView getView() {
		return (IAddItemBatchView) super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
	}

	/**
	 * This method is called when the "Entry Date" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void entryDateChanged() {
	}

	/**
	 * This method is called when the "Count" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void countChanged() {
	}

	/**
	 * This method is called when the "Product Barcode" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void barcodeChanged() {
	}

	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
	}

	/**
	 * This method is called when the selected product changes
	 * in the add item batch view.
	 */
	@Override
	public void selectedProductChanged() {
	}

	/**
	 * This method is called when the user clicks the "Add Item" button
	 * in the add item batch view.
	 */
	@Override
	public void addItem() {
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the add item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view. The method itself iterates through all
	 * the added items and adds their barcodes to a pdf that is then displayed
	 * on the screen. After the pdf is displayed the list of items is cleared.
	 */
	@Override
	public void done()throws DocumentException, IOException{		
		//Print the newItem barcodes to a pdf
		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(Barcode.UPCA);
		Document document = new Document(new Rectangle(340, 842));
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("ItemsAddedBarcodes.pdf"));
		PdfContentByte cb = writer.getDirectContent();
		document.open();
		//For all of the barcodes that need to be printed
		for(int i=0; i < newItems.size(); ++i)
		{
			codeEAN.setCode(newItems.get(i).getBarCode().getBarCode()); 
			document.add(codeEAN.createImageWithBarcode(cb, null, null));
		}

		java.awt.Desktop.getDesktop().open(new File("ItemsAddedBarcodes.pdf"));
		//The above command will allow you to open a pdf and display it on the screen

		newItems.clear();
	}
	
}

