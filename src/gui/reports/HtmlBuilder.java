package gui.reports;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HtmlBuilder extends Builder {

	private PrintWriter out;
	
	public HtmlBuilder(String reportName)
	{
		String fileName = reportName + ".html";
		System.out.println(fileName);
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
		String htmlTitle = "<head>\n<title>\n" + title + css() + "</title>\n</head>\n";
		out.println(htmlTitle);
	}
	
	private String css(){
		return "";
	}

	@Override
	public void drawText(String text, int fontSize) {
		out.println("<p size=\"" + fontSize + "\">\n" + text + "</p>\n");
	}

	@Override
	public void printTable(Row row) {
		int numCells = row.size();
		out.println("<tr>\n");
		for (int i = 0; i<numCells; ++i){
			out.println("\t<td>\n"+row.getCell(i).toString()+"\t</td>\n");
		}
		out.println("</tr>\n");
	}

	@Override
	public void printTableHeader(Cell cell) {
		out.println("<th>" + cell.toString()+"</th>\n");
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
	public void endDocument() {
		// TODO Auto-generated method stub
		out.close();
	}

	
	public void finish() {
		out.println("</html>");	
		out.close();
	}

}
