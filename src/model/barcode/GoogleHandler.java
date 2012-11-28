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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author davidpatty
 */
public class GoogleHandler extends BarCodeLookupHandler {
	private int numberOfRequests;
	public GoogleHandler() {
		super();
		numberOfRequests = 0;
	}

	public GoogleHandler(HandlerDescriptor descriptor) {
		super(descriptor);
	}
	
	@Override
	public String lookup(String barcode) throws Exception {
		if(barcode == null || barcode.equals(""))
		{
			return "";			
		}
		
		URL url;
		HttpURLConnection connection;
		numberOfRequests++;
		try 
		{
			if(numberOfRequests < 2500) //Max lookups per day
			{
				url = new URL("https://www.googleapis.com/shopping/search/v1/public/products?key=AIzaSyA9LtUBnV3Cr80UqzXPMbCmBZJh31n7gLA&country=US&q=" + barcode + "&alt=json");
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				InputStream in = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String response;
				String result = "";
				//String result = "{ \"kind\": \"shopping#products\", \"etag\": \"e4hVenXC-1ooOTgmDhNhseFwuvw/FasAME6Rr03hQ249DcDlDBCMutM\", \"id\": \"tag:google.com,2010:shopping/products\", \"selfLink\": \"https://www.googleapis.com/shopping/search/v1/public/products?country=US&q=639382000393&alt=json\", \"totalItems\": 4, \"startIndex\": 1, \"itemsPerPage\": 25, \"currentItemCount\": 4, \"requestId\": \"0CND8hvmM8rMCFWp0RAod8xIAAA\", \"items\": [  {   \"kind\": \"shopping#product\",   \"id\": \"tag:google.com,2010:shopping/products/7477108/14333915021663728283\",   \"selfLink\": \"https://www.googleapis.com/shopping/search/v1/public/products?alt=json/7477108/gid/14333915021663728283\",   \"product\": {    \"googleId\": \"14333915021663728283\",    \"author\": {     \"name\": \"Bperfume\",     \"accountId\": \"7477108\"    },    \"creationTime\": \"2012-11-27T02:12:37.000Z\",    \"modificationTime\": \"2012-11-27T02:12:37.000Z\",    \"country\": \"US\",    \"language\": \"en\",    \"title\": \"Coco Perfume for Women by Chanel\",    \"description\": \"Coco Perfume for Women by Chanel\",    \"link\": \"http://www.bperfume.com/womens-perfume/coco-perfume-for-women-by-chanel/\",    \"brand\": \"Chanel\",    \"condition\": \"new\",    \"gtin\": \"00639382000393\",    \"gtins\": [     \"00639382000393\"    ],    \"mpns\": [     \"639382000393\"    ],    \"inventories\": [     {      \"channel\": \"online\",      \"availability\": \"inStock\",      \"price\": 93.99,      \"currency\": \"USD\"     }    ],    \"images\": [     {      \"link\": \"http://www.bperfume.com/images/products/4002500.jpg\",      \"status\": \"available\"     }    ]   }  },  {   \"kind\": \"shopping#product\",   \"id\": \"tag:google.com,2010:shopping/products/3879900/9448951348811188843\",   \"selfLink\": \"https://www.googleapis.com/shopping/search/v1/public/products?alt=json/3879900/gid/9448951348811188843\",   \"product\": {    \"googleId\": \"9448951348811188843\",    \"author\": {     \"name\": \"OmFragrances.com\",     \"accountId\": \"3879900\"    },    \"creationTime\": \"2012-07-26T16:07:36.000Z\",    \"modificationTime\": \"2012-11-23T23:17:43.000Z\",    \"country\": \"US\",    \"language\": \"en\",    \"title\": \"Coco by Chanel 3.4 oz EDP for Women\",    \"description\": \"Oriental, Spicy, Sweet. This perfume will place you on center stage as your elegant aroma captures their hearts. Said to reflect the spirit of Chanel, this warm, floral oriental has notes of: French angelica, Bulgarian rose, Spice Island clove bud, Indian jasmine, Caribbean cascarida, frangipani, and mimosa. Excellent for evening wear.\",    \"link\": \"http://www.omfragrances.com/chanel-coco-chanel-women-p-525.html\",    \"brand\": \"Chanel\",    \"condition\": \"new\",    \"gtin\": \"00639382000393\",    \"gtins\": [     \"00639382000393\"    ],    \"inventories\": [     {      \"channel\": \"online\",      \"availability\": \"inStock\",      \"price\": 135.0,      \"shipping\": 0.0,      \"currency\": \"USD\"     }    ],    \"images\": [     {      \"link\": \"http://www.omfragrances.com/images/ChanelC.jpg\",      \"status\": \"available\"     }    ]   }  },  {   \"kind\": \"shopping#product\",   \"id\": \"tag:google.com,2010:shopping/products/7869935/6717598454349723813\",   \"selfLink\": \"https://www.googleapis.com/shopping/search/v1/public/products?alt=json/7869935/gid/6717598454349723813\",   \"product\": {    \"googleId\": \"6717598454349723813\",    \"author\": {     \"name\": \"Bonanza - maiyede's booth\",     \"accountId\": \"7869935\"    },    \"creationTime\": \"2012-11-18T09:15:57.000Z\",    \"modificationTime\": \"2012-11-18T09:15:57.000Z\",    \"country\": \"US\",    \"language\": \"en\",    \"title\": \"New Authentic Coco by Chanel for Women, Eau De Parfum Spray\",    \"description\": \"Coco by Chanel for Women, Eau De Parfum Spray\n100% Authentic,new in box,never opened/used\nbest price online~~!\nCoco Perfume by Chanel, Chanel created coco perfume in 1984. It is the result of the following top fragrance notes: mandarin, pimento and coriander. The middle notes are: rose, carnation and cinnamon and the base of the fragrance is: amber vanilla and honey. Coco chanel perfume is labeled as a classic fragrance.\nSafety Information\nFor external use only. Avoid contact with eyes. Keep away from naked flame or direct heat sources. Do not apply to sensitive skin. Do not apply to broken or inflamed skin. If irritation develops, reduce frequency or discontinue use.Directions\nImportant: Keep in cool dry places.\",    \"link\": \"http://www.bonanza.com/listings/New-Authentic-Coco-by-Chanel-for-Women-Eau-De-Parfum-Spray/95977673\",    \"brand\": \"Chanel\",    \"condition\": \"new\",    \"gtin\": \"00639382000393\",    \"gtins\": [     \"00639382000393\"    ],    \"mpns\": [     \"115474\"    ],    \"inventories\": [     {      \"channel\": \"online\",      \"availability\": \"inStock\",      \"price\": 89.5,      \"tax\": 0.0,      \"shipping\": 0.0,      \"currency\": \"USD\"     }    ],    \"images\": [     {      \"link\": \"http://bonanzleimages.s3.amazonaws.com/afu/images/6686/9399/41avKPTOCgL__SS500_.jpg\",      \"status\": \"available\"     }    ]   }  },  {   \"kind\": \"shopping#product\",   \"id\": \"tag:google.com,2010:shopping/products/8180208/1644723698187601008\",   \"selfLink\": \"https://www.googleapis.com/shopping/search/v1/public/products?alt=json/8180208/gid/1644723698187601008\",   \"product\": {    \"googleId\": \"1644723698187601008\",    \"author\": {     \"name\": \"FestonWorks\",     \"accountId\": \"8180208\"    },    \"creationTime\": \"2012-07-08T10:02:08.000Z\",    \"modificationTime\": \"2012-11-25T10:50:32.000Z\",    \"country\": \"US\",    \"language\": \"en\",    \"title\": \"Coco by Chanel for Women, Eau De Parfum Spray, 3.4 Ounce\",    \"description\": \"\",    \"link\": \"http://www.vendio.com/stores/FestonWorks/item?lid=21933348&source=Vendio:Google%20Product%20Search\",    \"brand\": \"Chanel\",    \"condition\": \"new\",    \"gtin\": \"00639382000393\",    \"gtins\": [     \"00639382000393\"    ],    \"mpns\": [     \"PE-6408\"    ],    \"inventories\": [     {      \"channel\": \"online\",      \"availability\": \"inStock\",      \"price\": 60.0,      \"shipping\": 5.0,      \"currency\": \"USD\"     }    ]   }  } ]}";
				while((response = reader.readLine()) != null)
				{
					result += response;
				}
				
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject)parser.parse(result);
				JSONArray obj2 = (JSONArray)obj.get("items");
				JSONObject obj3 = (JSONObject) obj2.get(0);
				JSONObject finalObj = (JSONObject) obj3.get("product");
				String finalVal = (String) finalObj.get("title");
				return finalVal;			
			}
			else if(this.next!=null)
			{
				return next.lookup(barcode);
			} 
			else
			{
				return "";
			}	
		}
		catch (IOException ex) {
			return "";
		}
	}
}