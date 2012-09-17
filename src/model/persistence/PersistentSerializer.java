package model.persistence;

import model.entities.ProductContainer;
import model.entities.Item;
import model.entities.Product;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Saves the data structure as
 * serialized objects
 * @author Casey Moncur
 *
 */
public class PersistentSerializer extends PersistentStore {

	private static final String PERSISTENT_STORE = "persistent_store.txt";

	@Override
	public ProductContainer getRoot() {
		try {
			InputStream is = new FileInputStream(PERSISTENT_STORE);
			ObjectInputStream ois = new ObjectInputStream(is);
			Object o = ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductContainer> getAllContainers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(PersistentItem item) {
		try {
			OutputStream os = new FileOutputStream(PERSISTENT_STORE);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
