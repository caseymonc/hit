package model.persistence;

import java.util.List;

import model.CoreObjectModel;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;
import model.persistence.DAO.ItemDAO;
import model.persistence.DAO.ProductDAO;
import model.persistence.DAO.ProductGroupDAO;
import model.persistence.DAO.StorageUnitDAO;

public class DatabaseFactory extends PersistentFactory {

	@Override
	public CoreObjectModel getCoreObjectModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(PersistentItem item) {
		if(item instanceof CoreObjectModel){
			return;
		}
	}

	@Override
	public StorageUnitDAO getStorageUnitDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductGroupDAO getProductGroupDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDAO getProductDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDAO getItemDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
