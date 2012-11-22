package model.persistence.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import model.persistence.DataObjects.ItemDO;

public abstract class ItemDAO extends DataAccessObject {

	public abstract ArrayList<ItemDO> readAll() throws SQLException;
}
