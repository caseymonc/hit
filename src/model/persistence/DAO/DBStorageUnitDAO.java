package model.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.StorageUnitDO;

public class DBStorageUnitDAO extends StorageUnitDAO {

	private static final String READ_ALL = "SELECT container_id, name FROM ProductContainers " +
			"WHERE parent_id IS NULL";
	private static final String INSERT = "INSERT INTO ProductContainers (name) values (?)";
	private static final String UPDATE = "UPDATE ProductContainers SET name=?" +
			" WHERE container_id=?;";
	private static final String DELETE = "DELETE FROM ProductContainers WHERE container_id=?";
	
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
		
		ArrayList<StorageUnitDO> unitObjects = new ArrayList<StorageUnitDO>();
		
		while(results.next()){
			long id = Long.parseLong(results.getString("container_id"));
			String name = results.getString("name");
			StorageUnitDO unitDO = new StorageUnitDO(id, name);
			unitObjects.add(unitDO);
		}
		
		return unitObjects;
	}
	
	@Override
	public void create(DataObject obj) throws SQLException {
		if(!(obj instanceof StorageUnitDO))
			throw new IllegalArgumentException("Tried to create a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			StorageUnitDO unit = (StorageUnitDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(INSERT);
		    stmt.setString(1, unit.getName());
		    
		    if (stmt.executeUpdate() == 1) {
		        keyStmt = connection.createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);   // ID of the new book
		        unit.setId(id);
		    }else{
		    	throw new SQLException("Failed to insert StorageUnit");
		    }
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}

	@Override
	public void delete(DataObject obj) throws SQLException {
		if(!(obj instanceof StorageUnitDO))
			throw new IllegalArgumentException("Tried to create a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			StorageUnitDO unit = (StorageUnitDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(DELETE);
		    stmt.setLong(1, unit.getId());
		    stmt.executeUpdate();
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}

	@Override
	public void update(DataObject obj) throws SQLException {
		if(!(obj instanceof StorageUnitDO))
			throw new IllegalArgumentException("Tried to update a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			StorageUnitDO unit = (StorageUnitDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(UPDATE);
		    stmt.setString(1, unit.getName());
		    stmt.setLong(2, unit.getId());
		    stmt.executeUpdate();
		    
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}

}
