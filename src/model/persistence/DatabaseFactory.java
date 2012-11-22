package model.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.CoreObjectModel;
import model.controllers.StorageUnitController;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;
import model.managers.StorageUnitManager;
import model.persistence.DAO.DBItemDAO;
import model.persistence.DAO.DBProductDAO;
import model.persistence.DAO.DBProductGroupDAO;
import model.persistence.DAO.DBStorageUnitDAO;
import model.persistence.DAO.ItemDAO;
import model.persistence.DAO.ProductDAO;
import model.persistence.DAO.ProductGroupDAO;
import model.persistence.DAO.StorageUnitDAO;
import model.persistence.DataObjects.*;

public class DatabaseFactory extends PersistentFactory {

	@Override
	public CoreObjectModel getCoreObjectModel() {
		
		CoreObjectModel COM = CoreObjectModel.getNewInstance();		
		
		//Open Database Connection and start transaction
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		connectionManager.startTransaction();
		
		try {
			addStorageUnits(COM);
			connectionManager.setTransactionSuccessful();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		//Close the Database and close the connection
		connectionManager.endTransaction();
		
		return COM;
	}

	private void addStorageUnits(CoreObjectModel COM) throws SQLException{
		StorageUnitDAO unitDAO = new DBStorageUnitDAO();
		ArrayList<StorageUnitDO> unitObjects;
		StorageUnitManager suManager = COM.getStorageUnitManager();
		
		unitObjects = unitDAO.readAll();
		
		for(StorageUnitDO unitObject : unitObjects){
			suManager.addStorageUnit(unitObject);
		}
	}
	
	@Override
	public void save(PersistentItem item) {
		if(item instanceof CoreObjectModel){
			return;
		}
	}

	@Override
	public StorageUnitDAO getStorageUnitDAO() {
		return new DBStorageUnitDAO();
	}

	@Override
	public ProductGroupDAO getProductGroupDAO() {
		// TODO Auto-generated method stub
		return new DBProductGroupDAO();
	}

	@Override
	public ProductDAO getProductDAO() {
		// TODO Auto-generated method stub
		return new DBProductDAO();
	}

	@Override
	public ItemDAO getItemDAO() {
		return new DBItemDAO();
	}

}
