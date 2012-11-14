package model.persistence;

import model.persistence.DAO.ItemDAO;
import model.persistence.DAO.ProductDAO;
import model.persistence.DAO.ProductGroupDAO;
import model.persistence.DAO.StorageUnitDAO;

public class SerializablePersistenceFactory extends AbstractPersistenceFactory {

	@Override
	public AbstractConnectionManager getConnectionManager() {
		// TODO Auto-generated method stub
		return null;
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
