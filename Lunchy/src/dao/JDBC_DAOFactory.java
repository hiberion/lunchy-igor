package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_DAOFactory extends DAOFactory {
	
	private String DBURL = "jdbc:postgresql://localhost:5432/LUNCHY_DB";
	private String DBUser = "postgres";
	private String DBPassword = "sipher";
	private String errMessage = null;
	
	Connection connection;
	
	public JDBC_DAOFactory() {
		connection = createConnction();		
	}
	
	@Override
	public IMenuItemDAO getMenuItemDAO() {
		return new JDBCMenuItemDAO(connection);
	}

	@Override
	public ICategoryDAO getCategoryDAO() {
		return new JDBCCategoryDAO(connection);
	}

	@Override
	public IWorkerDAO getWorkerDAO() {
		return new JDBCWorkerDAO(connection);
	}
	
	private Connection createConnction() {
		Connection result = null;
		try {
			result = DriverManager.getConnection(DBURL, DBUser, DBPassword);
		} catch (SQLException e) {
			errMessage = e.getMessage();
			System.out.println(errMessage);
		}
		return result;
	}
}
