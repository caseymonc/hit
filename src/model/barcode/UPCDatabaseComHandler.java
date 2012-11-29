/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author dmathis
 */
public class UPCDatabaseComHandler extends BarCodeLookupHandler {

	@Override
	public String lookup(String barcode) {
		URL url;
		HttpURLConnection connection;
		String productDesc = "";
		
		try {
			url = new URL("http://www.upcdatabase.com/item/" + barcode);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			
			connection.connect();
			
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String response;
			String result = "";
			while((response = reader.readLine()) != null)
			{
				if(response.contains("<tr><td>Description</td><td></td><td>")){
					result = response;
					break;
				}
			}
			
			if(!result.equals("")){
				result = result.replace("<tr>", "");
				result = result.replace("</tr>", "");
				result = result.replace("<td>", "");
				result = result.replace("</td>", "");
				result = result.replace("Description", "");
			}
			
			productDesc = URLDecoder.decode(result, "UTF-8");
			productDesc = StringEscapeUtils.unescapeHtml4(productDesc);
			
		} catch (IOException ex) {
			System.err.println("Unable to connect in UPCDatabaseComHandler: " + ex);
		}
		
		if(productDesc.equals("") && next != null){
			productDesc = next.lookup(barcode);
		}
		
		return productDesc;
	}
	
}
