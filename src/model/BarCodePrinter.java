/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import common.util.DateUtils;
import gui.item.ItemData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import model.entities.Item;

/**
 * Class that manages BarCode printing.
 */
public class BarCodePrinter {

	/**
	 * When constructed, this class creates a PDF document that contains the BarCodes 
	 * of all of the Items in items. After this PDF is created it is opened and displayed.
	 *
	 * @param items - a list of Items whose BarCodes need to be printed.
	 */
	public BarCodePrinter(List<ItemData> items) {		
		Random random = new Random();
		String file_name = "labels-" + Integer.toString(random.nextInt()) + ".pdf";
		
		if (items.size() < 1) {
			return;
		}
		
		try {	
			// Create the PDF document
			Document document = new Document(new Rectangle(340, 468));
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file_name));
			
			document.open();
			PdfPTable table = buildTable(items, writer);
			document.add(table);
			document.close();
			
			// Display the PDF
			java.awt.Desktop.getDesktop().open(new File(file_name));
		} catch (DocumentException e) {

			System.out.println("Error: unable to create pdf: " + e.getMessage());

		} catch (IOException e) {

			System.out.println("Error: unable to write to pdf: " + e.getMessage());

		}
	}
	
	private PdfPTable buildTable(List<ItemData> items, PdfWriter writer){	
		int numCols = 6;
		int colWidth = 200;
		Font font = new Font(FontFamily.HELVETICA, 4);
		PdfContentByte cb = writer.getDirectContent();
		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(Barcode.UPCA);
		
		PdfPTable table = new PdfPTable(numCols);
		table.setTotalWidth(numCols * colWidth);
		
		for (int i = 0; i < items.size(); ++i) {
			Item item = (Item) items.get(i).getTag();
			String title = item.getProduct().getDescription();
			String entry = DateUtils.formatDate(item.getEntryDate());
			String exp = expDateToString(item.getExpirationDate());

			PdfPCell cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);

			codeEAN.setCode(item.getBarCode().toString());
			cell.addElement(new Paragraph(title, font));
			cell.addElement(new Paragraph(entry + exp, font));

			Image image = codeEAN.createImageWithBarcode(cb, null, null);
			image.setSpacingBefore(3);
			cell.addElement(image);

			table.addCell(cell);
		}

		int remainingCells = numCols - (items.size() % numCols);

		for (int i = 0; i < remainingCells; i++) {
			PdfPCell cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
		}
		
		return table;
	}
	
	private String expDateToString(Date expDate){
		String result;
		
		if (expDate != null) {
			result = " exp " + DateUtils.formatDate(expDate);
		} else {
			result = "";
		}
		
		return result;
	}
}
