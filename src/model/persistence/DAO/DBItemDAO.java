package model.persistence.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ItemDO;

public class DBItemDAO extends ItemDAO {

	/**
	 * gets a list of all Item data objects in the database
	 * @return a list of Item data objects
	 */
	@Override
	public ArrayList<ItemDO> readAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<ItemDO> readAllByProduct(long productId, long containerId) 
			throws SQLException{
		ArrayList<ItemDO> itemDOs = new ArrayList();
		
		String query = "SELECT * FROM Items "
				+ " WHERE product_id = " + productId + " AND container_id = " + containerId;
		
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(query);
		
		while(results.next()){
			long id = results.getLong("item_id");
			String barcode = results.getString("barcode");
			String entryDate = results.getString("entry_date");
			String exitDate = results.getString("exit_date");
			String expirationDate = results.getString("expiration_date");
			ItemDO itemDo = new ItemDO(id, barcode, entryDate, exitDate, 
				expirationDate, productId, containerId);
			
			itemDOs.add(itemDo);
		}
		
		return itemDOs;
	}
	
//	@Override
//	public DataObject[] readAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void create(DataObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(DataObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(DataObject obj) {
		// TODO Auto-generated method stub

	}

}
