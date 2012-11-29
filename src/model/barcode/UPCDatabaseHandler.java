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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author dmathis
 */
public class UPCDatabaseHandler extends BarCodeLookupHandler {

	@Override
	public String lookup(String barcode) {
		URL url;
		HttpURLConnection connection;
		String productDesc = "";
		
		try {
			url = new URL("http://upcdatabase.org/api/json/5f1b4be20dc157def65cc1e03ab7f0c7/" + barcode);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			
			connection.connect();
			
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String response = reader.readLine();
			
			JSONParser parser = new JSONParser();

			JSONObject obj = (JSONObject)parser.parse(response);
			productDesc = (String)obj.get("itemname");
			
			productDesc = URLDecoder.decode(productDesc, "UTF-8");
			productDesc = StringEscapeUtils.unescapeHtml4(productDesc);			
		} catch (IOException ex) {
			System.err.println("Unable to connect in UPCDatabaseHandler: " + ex);
		} catch (ParseException ex) {
			System.err.println("Unable to parse respone in UPCDatabaseHandler: " + ex);
		} catch (NullPointerException ex) {
			System.out.println("No product from UPCHandler");
		}

		if ((productDesc == null || productDesc.equals("")) && next != null){
			productDesc = next.lookup(barcode);
		}
		
		return productDesc;
	}
	
}
