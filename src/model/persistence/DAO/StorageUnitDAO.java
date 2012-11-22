package model.persistence.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import model.persistence.DataObjects.StorageUnitDO;

public abstract class StorageUnitDAO extends DataAccessObject {

	public abstract ArrayList<StorageUnitDO> readAll() throws SQLException;

}
