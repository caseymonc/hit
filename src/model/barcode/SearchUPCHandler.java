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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author davidpatty
 */
public class SearchUPCHandler extends BarCodeLookupHandler {

	public SearchUPCHandler() {
		super();
	}
	
	public SearchUPCHandler(HandlerDescriptor descriptor) {
		super(descriptor);
	}

	@Override
	public String lookup(String barcode) {

		String productDesc = "";
		
		if(!(barcode == null || barcode.equals(""))){		
			URL url;
			HttpURLConnection connection;
			try {
				url = new URL("http://www.searchupc.com/handlers/upcsearch.ashx?request_type=" +
						"3&access_token=359D872A-2B27-4994-93A5-BEDEB613D1D5&upc=" + barcode);
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("GET");

				connection.connect();

				InputStream in = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String response = reader.readLine();

				JSONParser parser = new JSONParser();

				JSONObject obj = (JSONObject)parser.parse(response);
				JSONObject obj2 = (JSONObject)obj.get("0");
				productDesc = (String)obj2.get("productname");
				System.out.println("found in SearchUPC" + productDesc);
			} catch (IOException ex) {
				System.err.println("error with the Buffered Reader" + ex.getMessage());
			} catch (ParseException ex) {
				System.out.println("Could not find Item in SUPC handler");
			} catch (NullPointerException ex) {
				System.out.println("No Product exists in database");
			}

			if(productDesc.equals("") && next != null)
			{
				productDesc = next.lookup(barcode);
			}
		}
		
		return productDesc;
	}
}
