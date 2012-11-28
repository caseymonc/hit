/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.client.j2se.*;

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
		numberOfRequests++;
		if(numberOfRequests < 2500) //Max lookups per day
		{
			InputStream barCodeInputStream = new FileInputStream("file.jpg");
			BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
			LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Reader reader = new MultiFormatReader();
			Result result = reader.decode(bitmap);
		}
		else
		{
			
		} 

		//System.out.println("Barcode text is " + result.getText());
		throw new UnsupportedOperationException("Not supported yet.");
	}
}