package model.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.CoreObjectModel;
import model.controllers.ProductGroupController;
import model.controllers.StorageUnitController;
import model.entities.ProductContainer;
import model.entities.*;
import model.managers.ProductGroupManager;
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
			addProductContainers(COM);
			connectionManager.setTransactionSuccessful();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		//Close the Database and close the connection
		connectionManager.endTransaction();
		
		return COM;
	}

	private void addProductContainers(CoreObjectModel COM) throws SQLException{
		StorageUnitDAO unitDAO = new DBStorageUnitDAO();
		ProductGroupDAO groupDAO = new DBProductGroupDAO();
		StorageUnitController suController = COM.getStorageUnitController();
		
		ArrayList<StorageUnitDO> unitDOs = unitDAO.readAll();
		ArrayList<ProductGroupDO> groupDOs = groupDAO.readAll();
		
		for(StorageUnitDO unitDO : unitDOs){
			StorageUnit unit = new StorageUnit(unitDO.getName());
			suController.addStorageUnitFromDB(unit);
			
			for(ProductGroupDO groupDO : groupDOs){
				if(groupDO.getContainerId() == unitDO.getId()){
					addProductGroups(groupDO, unit, groupDOs, COM);
				}
			}
		}
	}
	
	private void addProductGroups(ProductGroupDO groupDO, ProductContainer container, 
			ArrayList<ProductGroupDO> groupDOs, CoreObjectModel COM) {
		
		ProductGroupController pgController = COM.getProductGroupController();
		
		Size supply = new Size(Unit.valueOf(groupDO.getThreeMonthSupplyUnit()), 
				groupDO.getThreeMonthSupplyVal());
		
		ProductGroup group = new ProductGroup(groupDO.getName(), container, supply);
		
		if(container instanceof StorageUnit){
			group.setStorageUnit((StorageUnit)container);
		} else {
			group.setStorageUnit(container.getStorageUnit());
		}
		
		pgController.addProductGroupFromDB(group);
		
		for(ProductGroupDO childGroupDO : groupDOs){
			if(childGroupDO.getContainerId() == groupDO.getId() && 
					childGroupDO.getId() != groupDO.getId()){
				addProductGroups(childGroupDO, group, groupDOs, COM);
			}
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
