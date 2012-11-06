package gui.reports;

import com.itextpdf.text.BaseColor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfBuilder extends Builder {
	private Document document;
	private PdfWriter writer;
	
	
	public PdfBuilder(String reportName) {
		reportName += ".pdf";
		fileName = reportName;
		document = new Document(new Rectangle(340, 468));
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(reportName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("This should never happen because it overwrites the report.");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Unable to write to a pdf document.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.open();		
	}

	@Override
	public void drawTitle(String title) {
		// TODO Auto-generated method stub
		Font font = new Font(FontFamily.HELVETICA, 7);
		font.setStyle(1);
		try {
			Paragraph p = new Paragraph(title,font);
			p.setExtraParagraphSpace(6);
			p.setSpacingAfter(3);
			p.setAlignment(1);
			document.add(p);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't draw a title in pdf");
			e.printStackTrace();
		}
		
	}

	@Override
	public void drawTable(Table table) {
		PdfPTable tbl = buildTable(table);
		tbl.setSpacingAfter(10);
		tbl.setSpacingBefore(3);

		try {
			document.add(tbl);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void drawText(String text, int fontSize) {
		// TODO Auto-generated method stub
		Font font = new Font(FontFamily.HELVETICA, fontSize);
		font.setStyle(1);
		try {
			Paragraph p = new Paragraph(text,font);
			p.setExtraParagraphSpace(10);
			document.add(p);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't draw text to a pdf");
			e.printStackTrace();
		}
		
	}

	@Override
	public void printTableHeader(Cell cell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printTable(Row row) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTable() {
		// TODO Auto-generated method stub
	}

	@Override
	public void endHeader() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTable() {
		// TODO Auto-generated method stub
		
	}
	private PdfPTable buildTable(Table table){	
		int numCols = table.getRow(0).size(); //The number of (cells in a row) == columns
		int colWidth = 1200/numCols;
		Font font = new Font(FontFamily.HELVETICA, 4);
		//PdfContentByte cb = writer.getDirectContent();
		PdfPTable pdfTable = new PdfPTable(numCols);
		pdfTable.setWidthPercentage(100);
		//pdfTable.setTotalWidth(numCols * colWidth);
		
		BaseColor headerColor = new BaseColor(92,68,58);
		BaseColor oddColor = new BaseColor(211,228,229);
		BaseColor evenColor = new BaseColor(255,255,255);

		for (int i = 0; i < table.size(); ++i) {
		
			for(int j = 0; j < table.getRow(i).size(); ++j)
			{
				
				PdfPCell cell = new PdfPCell();
				Cell cellValue = table.getRow(i).getCell(j);
				cell.setBorder(Rectangle.BOX);
				Paragraph par;
				if(i % 2 == 0) //even rows
				{
					if (i == 0) {
						cell.setBackgroundColor(headerColor);
						Font headerFont = new Font(FontFamily.HELVETICA, 5);
						headerFont.setColor(BaseColor.WHITE);
						par = new Paragraph(cellValue.toString(),headerFont);
						headerFont.setStyle(1);
						par.setFont(headerFont);
					} else {
						par = new Paragraph(cellValue.toString(), font);
						cell.setBackgroundColor(evenColor);
					}
					
				} else { //odd rows
				
					par = new Paragraph(cellValue.toString(),font);
					cell.setBackgroundColor(oddColor);
				}
				
				cell.addElement(par);

				pdfTable.addCell(cell);

			}
		}
		return pdfTable;
	}

	@Override
	public void endDocument() {
		document.close();
	}

	@Override
	public void finish() {
		document.close();
	}
}
