package gui.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfBuilder extends Builder {
	private Document document;
	private PdfWriter writer;
	
	public PdfBuilder(String reportName) {
		reportName += ".pdf";
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
		
	}

	@Override
	public void drawTable(Table table) {
		
		PdfPTable tbl = buildTable(table);
		
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
		pdfTable.setTotalWidth(numCols * colWidth);
		
		for (int i = 0; i < table.size(); ++i) {
			for(int j = 0; j < table.getRow(i).size(); ++j)
			{
				PdfPCell cell = new PdfPCell();
				cell.setBorder(Rectangle.NO_BORDER);
				cell.addElement(new Paragraph("", font));
				pdfTable.addCell(cell);
			}
		}
		return pdfTable;
	}

	@Override
	public void endDocument() {
		document.close();
	}

}
