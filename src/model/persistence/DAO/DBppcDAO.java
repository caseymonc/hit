package model.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ItemDO;
import model.persistence.DataObjects.ProductDO;

public class DBppcDAO extends ppcDAO {

	@Override
	public void create(DataObject obj) throws SQLException {
		if(obj instanceof ProductDO){
			updateProductRelationships((ProductDO)obj);
		}else if(obj instanceof ItemDO){
			insertProductRelationships((ItemDO)obj);
		}
	}
	
	private void updateProductRelationships(ProductDO product) throws SQLException{
		deleteProductRelationships(product);
		insertProductRelationships(product);
	}
	
	private void insertProductRelationships(ProductDO product) throws SQLException{
		for(long l : product.getRelationships()){
			insertProductRelationship(product.getId(), l);
		}
	}
	
	private static final String INSERT_RELATIONSHIPS = "INSERT INTO p_pc " +
			"(product_id, container_id) VALUES (?, ?)";
	private void insertProductRelationship(long product_id, long container_id) 
															throws SQLException{
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(INSERT_RELATIONSHIPS);
		    stmt.setLong(1, product_id);
		    stmt.setLong(2, container_id);
		    int rowsAffected = stmt.executeUpdate();
		    rowsAffected = 0;
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}
	
	private void insertProductRelationships(ItemDO item) throws SQLException{
		if(!hasProductRelationship(item)){
			insertProductRelationship(item.getProductId(), item.getContainerId());
		}
	}
	
	private static final String QUERY_ITEM = "SELECT * FROM p_pc WHERE product_id=? " +
			"AND container_id=?";
	private boolean hasProductRelationship(ItemDO item) throws SQLException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		boolean hasProductRelationship = false;
		try{
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(QUERY_ITEM);
		    stmt.setLong(1, item.getProductId());
		    stmt.setLong(2, item.getContainerId());
		    ResultSet set = stmt.executeQuery();
		    hasProductRelationship = set.next();
		}catch(SQLException e){
			exception = e;
			hasProductRelationship = false;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
		return hasProductRelationship;
	}

	private static final String DELETE_RELATIONSHIPS = "DELETE FROM p_pc WHERE product_id=?";
	private void deleteProductRelationships(ProductDO product) throws SQLException{
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(DELETE_RELATIONSHIPS);
		    stmt.setLong(1, product.getId());
		    int rowsAffected = stmt.executeUpdate();
		    rowsAffected = 0;
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
		if(obj instanceof ProductDO){
			deleteProductRelationships((ProductDO)obj);
		}
	}

	@Override
	public void update(DataObject obj) throws SQLException {
		if(obj instanceof ProductDO){
			delete(obj);
			create(obj);
		}
	}

	private static final String DELETE_RELATIONSHIP = "DELETE FROM p_pc " +
			"WHERE product_id=? AND container_id=?";
	@Override
	public void delete(ProductDO product, long container_id) throws SQLException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(DELETE_RELATIONSHIP);
		    stmt.setLong(1, product.getId());
		    stmt.setLong(2, container_id);
		    int rowsAffected = stmt.executeUpdate();
		    rowsAffected = 0;
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
