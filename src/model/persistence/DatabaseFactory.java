package model.persistence;

import common.util.DateUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import model.CoreObjectModel;
import model.entities.*;
import model.managers.ItemManager;
import model.managers.ProductGroupManager;
import model.managers.ProductManager;
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
		StorageUnitManager suManager = COM.getStorageUnitManager();
		
		ArrayList<StorageUnitDO> unitDOs = unitDAO.readAll();
		ArrayList<ProductGroupDO> groupDOs = groupDAO.readAll();
		
		for(StorageUnitDO unitDO : unitDOs){
			StorageUnit unit = new StorageUnit(unitDO.getName());
			suManager.doAddStorageUnit(unit);
			unit.setId(unitDO.getId());
			
			for(ProductGroupDO groupDO : groupDOs){
				if(groupDO.getContainerId() == unitDO.getId()){
					addProductGroups(groupDO, unit, groupDOs, COM);
				}
			}
			
			addProducts(unitDO.getId(), unit, COM);
		}
	}
	
	private void addProductGroups(ProductGroupDO groupDO, ProductContainer container, 
			ArrayList<ProductGroupDO> groupDOs, CoreObjectModel COM) throws SQLException {
		
		ProductGroupManager pgManager = COM.getProductGroupManager();
		
		Size supply = new Size(Unit.valueOf(groupDO.getThreeMonthSupplyUnit()), 
				groupDO.getThreeMonthSupplyVal());
		
		ProductGroup group = new ProductGroup(groupDO.getName(), container, supply);
		group.setId(groupDO.getId());
		
		if(container instanceof StorageUnit){
			group.setStorageUnit((StorageUnit)container);
		} else {
			group.setStorageUnit(container.getStorageUnit());
		}
		
		pgManager.doAddProductGroup(group);
		
		for(ProductGroupDO childGroupDO : groupDOs){
			if(childGroupDO.getContainerId() == groupDO.getId() && 
					childGroupDO.getId() != groupDO.getId()){
				addProductGroups(childGroupDO, group, groupDOs, COM);
			}
		}
		
		addProducts(groupDO.getId(), group, COM);
	}
	
	private void addProducts(long containerId, ProductContainer container, 
			CoreObjectModel COM) throws SQLException{
		ProductDAO productDAO = new DBProductDAO();
		ProductManager pManager = COM.getProductManager();
		
		ArrayList<ProductDO> productDOs = productDAO.readAllByContainer(containerId);
		
		for(ProductDO productDO : productDOs){
			BarCode barCode = new BarCode(productDO.getBarCode());
			Product product = pManager.getProductByBarCode(barCode);
			
			if(product == null) {
				String description = productDO.getDescription();
				int shelfLife = productDO.getShelfLife();
				int threeMonthSupply = productDO.getThreeMonthSupply();
				Date creationDate = DateUtils.parseSQLDateTime((productDO.getCreationDate()));
				
				Size size = new Size(Unit.valueOf(productDO.getSizeUnit()), productDO.getSizeVal());
				product = new Product(description, barCode, shelfLife, threeMonthSupply, size);
				product.setCreationDate(creationDate);
				product.setId(productDO.getId());
			}
			
			pManager.doAddProductToContainer(product, container);
			
			addItems(productDO.getId(), product, containerId, container, COM);
		}
	}
	
	private void addItems(long productId, Product product, long containerId,
			ProductContainer container, CoreObjectModel COM) throws SQLException {
		ItemDAO itemDAO = new DBItemDAO();
		ItemManager iManager = COM.getItemManager();
		
		ArrayList<ItemDO> itemDOs = itemDAO.readAllByProduct(productId, containerId);
		
		for(ItemDO itemDO : itemDOs){
			BarCode barCode = new BarCode(itemDO.getBarCode());
			Date entryDate = DateUtils.parseSQLDateTime(itemDO.getEntryDate());
			Date expirationDate = DateUtils.parseSQLDateTime(itemDO.getExpirationDate());
			Date exitDate = DateUtils.parseSQLDateTime(itemDO.getExitDate());
			Item item = new Item(barCode, entryDate, expirationDate, product, container);
			item.setExitDate(exitDate);

			StorageUnit unit = container.getStorageUnit();
			unit.doAddItem(item);
			iManager.doAddItem(item);
			COM.getProductManager().doAddItemToProduct(item.getProduct(), item);
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
