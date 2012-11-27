package model.persistence.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ProductGroupDO;

public class DBProductGroupDAO extends ProductGroupDAO {

	private static final String READ_ALL = "SELECT * FROM ProductContainers "
			+ "WHERE parent_id IS NOT NULL";
	
	/**
	 * gets a list of all ProductGroup data objects in the database
	 * @return a list of ProductGroup data objects
	 */
	@Override
	public ArrayList<ProductGroupDO> readAll() throws SQLException{
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(READ_ALL);
		
		ArrayList<ProductGroupDO> groupDos = new ArrayList();
		
		while(results.next()){
			long id = results.getLong("container_id");
			String name = results.getString("name");
			long contId = results.getLong("parent_id");
			float supplyVal = results.getFloat("three_month_supply_value");
			String supplyUnit = results.getString("three_month_supply_unit");
			ProductGroupDO groupDO = new ProductGroupDO(id, name, contId, supplyVal, supplyUnit);
			groupDos.add(groupDO);
		}
		
		return groupDos;
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
