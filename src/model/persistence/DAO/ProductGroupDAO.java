package model.persistence.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import model.persistence.DataObjects.ProductGroupDO;

public abstract class ProductGroupDAO extends DataAccessObject {

	public abstract ArrayList<ProductGroupDO> readAll() throws SQLException;
}
