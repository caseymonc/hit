/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
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
import java.util.List;
import java.util.Random;
import model.entities.Item;

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
	public BarCodePrinter(List<ItemData> items) {
			Random random = new Random();
			
		if(items.size() > 0) {
			try {
				//Print the newItem barcodes to a pdf			
				BarcodeEAN codeEAN = new BarcodeEAN();
				codeEAN.setCodeType(Barcode.UPCA);
				Document document = new Document(new Rectangle(340, 842));
								String file_name = "labels-" + 
										Integer.toString(random.nextInt()) + ".pdf";
				PdfWriter writer = PdfWriter.getInstance(document, 
										new FileOutputStream(file_name));
				document.open();
				PdfContentByte cb = writer.getDirectContent();

								int numCols = 6;
								int colWidth = 200;
								PdfPTable table = new PdfPTable(numCols);
								table.setTotalWidth(numCols*colWidth);
								
								Font font = new Font(FontFamily.HELVETICA, 4);

				for(int i=0; i < items.size(); ++i)
				{
									Item item = (Item)items.get(i).getTag();
									String title = item.getProduct().getDescription();
									String entry = DateUtils.formatDate(item.getEntryDate()); 
									String exp;
									
									PdfPCell cell = new PdfPCell();
									cell.setBorder(Rectangle.NO_BORDER);
									if(item.getExpirationDate() != null){
										exp = " exp " + DateUtils.formatDate(item.getExpirationDate());
									} else {
										exp = "";
									}

									codeEAN.setCode(item.getBarCode().getBarCode());
									cell.addElement(new Paragraph(title, font));
									cell.addElement(new Paragraph(entry + exp, font));
									
									Image image = codeEAN.createImageWithBarcode(cb, null, null);
									image.setSpacingBefore(3);
									cell.addElement(image);
									
									table.addCell(cell);
				}
								
								int remainingCells = numCols - (items.size() % numCols);
								
								for(int i = 0; i < remainingCells; i++) {
									PdfPCell cell = new PdfPCell();
									cell.setBorder(Rectangle.NO_BORDER);
									table.addCell(cell);
								}
								
								document.add(table);
				document.close();
				//This will allow you to open a pdf and display it on the screen
				java.awt.Desktop.getDesktop().open(new File(file_name));


			} catch(DocumentException e) {

				System.out.println("Error: unable to create pdf: " + e.getMessage());

			} catch(IOException e) {

				System.out.println("Error: unable to write to pdf: " + e.getMessage());			

			}
		}
	}
	
}
