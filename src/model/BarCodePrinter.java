/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author davidpatty
 */
public class BarCodePrinter {
	
	/**
	 *  When constructed this class iterates through all the added items and adds their barcodes 
	 * to a pdf that is then displayedon the screen.
	 * 
	 * @param newItemBarCodes
	 */
	public BarCodePrinter(List<String> newItemBarCodes) {
		
		try {
			
			//Print the newItem barcodes to a pdf			
			BarcodeEAN codeEAN = new BarcodeEAN();
			codeEAN.setCodeType(Barcode.UPCA);
			Document document = new Document(new Rectangle(340, 842));
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("ItemsAddedBarcodes.pdf"));
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			
			//For all of the barcodes that need to be printed
			for(int i=0; i < newItemBarCodes.size(); ++i)
			{
				codeEAN.setCode(newItemBarCodes.get(i)); 
				document.add(codeEAN.createImageWithBarcode(cb, null, null));
			}
			
			document.close();
			//This will allow you to open a pdf and display it on the screen
			java.awt.Desktop.getDesktop().open(new File("ItemsAddedBarcodes.pdf"));
			
			
		} catch(DocumentException e) {
			
			System.out.println("Error: unable to create pdf: " + e.getMessage());
		
		} catch(IOException e) {
			
			System.out.println("Error: unable to write to pdf: " + e.getMessage());			
		
		}
	}
	
}
