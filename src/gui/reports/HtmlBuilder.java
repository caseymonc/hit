package gui.reports;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HtmlBuilder extends Builder {

	private PrintWriter out;
	
	public HtmlBuilder(String reportName)
	{
		fileName = reportName + ".html";
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
		String htmlTitle = "<head>\n<title>\n" + title + "</title>\n " + css() + "</head>\n";
		out.println(htmlTitle);
	}
	
	private String css(){
		return "<style type=\"text/css\">" +
		"table { background:#D3E4E5;" +
		 "border:1px solid gray;" +
		 "border-collapse:collapse;" +
		 "color:#fff;" +
		 "font:normal 12px verdana, arial, helvetica, sans-serif;" +
		"}" +
		"caption { border:1px solid #5C443A;" +
		 "color:#5C443A;" +
		 "font-weight:bold;" +
		 "letter-spacing:20px;" +
		 "padding:6px 4px 8px 0px;" +
		 "text-align:center;" +
		 "text-transform:uppercase;" +
		"}" +
		"td, th { color:#363636;" +
		 "padding:.4em;" +
		"}" +
		"tr { border:1px dotted gray;" +
		"}" +
		"thead th, tfoot th { background:#5C443A;" +
		 "color:#FFFFFF;" +
		 "padding:3px 10px 3px 10px;" +
		 "text-align:left;" +
		 "text-transform:uppercase;" +
		"}" +
		"tbody td a { color:#363636;" +
		 "text-decoration:none;" +
		"}" +
		"tbody td a:visited { color:gray;" +
		 "text-decoration:line-through;" +
		"}" +
		"tbody td a:hover { text-decoration:underline;" +
		"}" +
		"tbody th a { color:#363636;" +
		 "font-weight:normal;" +
		 "text-decoration:none;" +
		"}" +
		"tbody th a:hover { color:#363636;" +
		"}" +
		"tbody td+td+td+td a { background-image:url('bullet_blue.png');" +
		 "background-position:left center;" +
		 "background-repeat:no-repeat;" +
		 "color:#03476F;" +
		 "padding-left:15px;" +
		"}" +
		"tbody td+td+td+td a:visited { background-image:url('bullet_white.png');" +
		 "background-position:left center;" +
		 "background-repeat:no-repeat;" +
		"}" +
		"tbody th, tbody td { text-align:left;" +
		 "vertical-align:top;" +
		"}" +
		"tfoot td { background:#5C443A;" +
		 "color:#FFFFFF;" +
		 "padding-top:3px;" +
		"}" +
		".odd { background:#fff;" +
		"}" +
		"tbody tr:hover { background:#99BCBF;" +
		 "border:1px solid #03476F;" +
		 "color:#000000;" +
		"}" +
		"</style>";
	}

	@Override
	public void drawText(String text, int fontSize) {
		out.println("<p size=\"" + fontSize + "\">\n" + text + "</p>\n");
	}

	boolean odd = false;
	@Override
	public void printTable(Row row) {
		int numCells = row.size();
		
		if(odd){
			out.println("<tr class=\"odd\">\n");
			odd = false;
		}else{
			out.println("<tr>\n");
			odd = true;
		}
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
		out.println("</html>");
		out.close();
	}

	
	public void finish() {
		out.println("</html>");	
		out.close();
	}

}
