package model.persistence.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.StorageUnitDO;

public class DBStorageUnitDAO extends StorageUnitDAO {

	private static final String READ_ALL = "SELECT container_id, name FROM ProductContainers WHERE parent_id IS NULL";
	
	/**
	 * gets a list of StorageUnit data objects
	 * @return a list of StorageUnit data objects
	 */
	@Override
	public ArrayList<StorageUnitDO> readAll() throws SQLException {
		
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(READ_ALL);
		
		ArrayList<StorageUnitDO> unitObjects = new ArrayList();
		
		while(results.next()){
			long id = Long.parseLong(results.getString("container_id"));
			String name = results.getString("name");
			StorageUnitDO unitDO = new StorageUnitDO(id, name);
			unitObjects.add(unitDO);
		}
		
		return unitObjects;
	}

//	@Override
//	public DataObject[] readAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public void create(DataObject obj) {
		
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
