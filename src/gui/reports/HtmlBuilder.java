package gui.reports;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HtmlBuilder extends Builder {

	private PrintWriter out;
	
	public HtmlBuilder(String reportName)
	{
		String fileName = reportName + ".html";
		try {
			out = new PrintWriter(new FileWriter(fileName));
		} catch (IOException e) 
		{
			System.out.println("Unable to write to file: " + fileName);
			e.printStackTrace();
		}
		out.println("<html>");
	}
	
	@Override
	public void drawTitle(String title) {
		String htmlTitle = "<head><title>" + title + "</title></head>";
		out.println(htmlTitle);
	}

	@Override
	public void drawText(String text, int fontSize) {
		out.println("<p size=\"" + fontSize + "\">" + text + "</p>");
	}

	@Override
	public void printTable(Row row) {
		int numCells = row.size();
		out.println("<tr>");
		for (int i = 0; i<numCells; ++i){
			out.println("<td>"+row.getCell(i).toString()+"</td>");
		}
		out.println("</tr>");
	}

	@Override
	public void printTableHeader(Cell cell) {
		out.println("<th>" + cell.toString()+"</th>");
	}

	@Override
	public void startTable() {
		out.println("<table><thead><tr>");
	}
	
	@Override
	public void endTable() {
		out.println("</table>");		
	}

	@Override
	public void endHeader() {
		out.println("</thead></tr>");	
	}

	@Override
<<<<<<< HEAD
	public void endDocument() {
		// TODO Auto-generated method stub
		out.close();
	}

	
=======
	public void finish() {
		out.close();
	}
>>>>>>> 6ba00fa85af11396cb034e9afce7d5dd6686e286

}
