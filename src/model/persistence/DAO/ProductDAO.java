package model.persistence.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import model.persistence.DataObjects.ProductDO;

public abstract class ProductDAO extends DataAccessObject {
	
	public abstract ArrayList<ProductDO> readAll() throws SQLException;
}
