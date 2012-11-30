package model.persistence.DAO;

import java.sql.SQLException;

import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ProductDO;

public abstract class ppcDAO extends DataAccessObject {

	public abstract void delete(ProductDO product, long container_id) throws SQLException;
	
	public abstract void add(ProductDO product, long container_id) throws SQLException;
}
