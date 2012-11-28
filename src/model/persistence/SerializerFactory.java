package model.persistence;

import model.CoreObjectModel;
import model.entities.ProductContainer;
import model.entities.Item;
import model.entities.Product;
import model.persistence.DAO.ItemDAO;
import model.persistence.DAO.ProductDAO;
import model.persistence.DAO.ProductGroupDAO;
import model.persistence.DAO.SerializeItemDAO;
import model.persistence.DAO.SerializePpcDAO;
import model.persistence.DAO.SerializeProductDAO;
import model.persistence.DAO.SerializeProductGroupDAO;
import model.persistence.DAO.SerializeStorageUnitDAO;
import model.persistence.DAO.StorageUnitDAO;
import model.persistence.DAO.ppcDAO;

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
public class SerializerFactory extends PersistentFactory {

	private static final String PERSISTENT_STORE = "persistent_store.txt";

	@Override
	public CoreObjectModel getCoreObjectModel() {
		try {
			InputStream is = new FileInputStream(PERSISTENT_STORE);
			ObjectInputStream ois = new ObjectInputStream(is);
			Object o = ois.readObject();
			if(o instanceof CoreObjectModel){
				return (CoreObjectModel)o;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
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
	public void save(PersistentItem item) {
		if(item instanceof CoreObjectModel){
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

	@Override
	public StorageUnitDAO getStorageUnitDAO() {
		return new SerializeStorageUnitDAO();
	}

	@Override
	public ProductGroupDAO getProductGroupDAO() {
		// TODO Auto-generated method stub
		return new SerializeProductGroupDAO();
	}

	@Override
	public ProductDAO getProductDAO() {
		return new SerializeProductDAO();
	}

	@Override
	public ItemDAO getItemDAO() {
		// TODO Auto-generated method stub
		return new SerializeItemDAO();
	}

	@Override
	public ppcDAO getPpcDAO() {
		return new SerializePpcDAO();
	}

}
