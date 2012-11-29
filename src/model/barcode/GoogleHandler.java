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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Shane K. Dooley
 */
public class GoogleHandler extends BarCodeLookupHandler {
	
	private int numberOfRequests;

	public GoogleHandler() {
		super();
		this.numberOfRequests = 0;
	}

	public GoogleHandler(HandlerDescriptor descriptor) {
		super(descriptor);
		this.numberOfRequests = 0;
	}
	
	@Override
	public String lookup(String barcode) {

		String productDesc = "";
		if(!(barcode == null || barcode.equals("")))
		{
			try 
			{
				//Max lookups per day
				// There are max lookup of 2500 requests per day //if(numberOfRequests < 2500) 

				String result = "";
				String finalVal = "";
				result = getResultString(barcode, result);

				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject)parser.parse(result);
				if((Long)obj.get("totalItems") > 0)
				{
					JSONArray obj2 = (JSONArray)obj.get("items");
					if ((Long)obj.get("totalItems") > 0)
					{
						JSONObject obj3 = (JSONObject) obj2.get(0);
						JSONObject finalObj = (JSONObject) obj3.get("product");
						productDesc = (String) finalObj.get("title");
						System.out.println("found in GoogleHandler" + productDesc);
					}
				}
			} catch (IOException ex) {
				System.err.println("Error with the Buffered Reader"+ex.getMessage());
			} catch (ParseException ex) {
				System.out.println("No product in GoogleHandler");
			}
		}

		if(productDesc.equals("") && next != null)
		{
			productDesc = next.lookup(barcode);
		}
		System.out.println("description:"+ productDesc);
		return productDesc;
	}

	private String getResultString(String barcode, String result)
			throws MalformedURLException, IOException, ProtocolException {
		URL url;
		HttpURLConnection connection;
		url = new URL("https://www.googleapis.com/shopping/search/v1/" +
				"public/products?key=AIzaSyA9LtUBnV3Cr80UqzXPMbCmBZJh31n7" +
				"gLA&country=US&q=" + barcode + "&alt=json");
		connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String response;
		
		while((response = reader.readLine()) != null)
		{
			result += response;
		}
		return result;
	}
}