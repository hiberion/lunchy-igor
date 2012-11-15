package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDAOFactory extends DAOFactory {
	
	private String DBURL = "jdbc:postgresql://localhost:5432/LUNCHY_DB";
	private String DBUser = "postgres";
	//private String DBPassword = "resolve";
	private String DBPassword = "sipher";
	private String errMessage = null;
	
	Connection connection;
	
	public String getDBUser() {
		return DBUser;
	}
	
	public String getDBPassword() {
		return DBPassword;
	}
	
	public String getDBURL() {
		return DBURL;
	}
	
	public void setDBUser(String DBUser) {
		this.DBUser = DBUser;
	}
	
	public void setDBPassword(String DBPassword) {
		this.DBPassword = DBPassword;
	}
	
	public void DBURL(String DBURL) {
		this.DBURL = DBURL;
	}
	
	public JdbcDAOFactory(String para) {
		super();
		setDBPassword(para);
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
	
	@Override
	public IGeneralOrderDAO getGeneralOrderDAO() {
		return new JDBCGeneralOrderDAO(connection); 
	}
	
	@Override
	public IPersonalOrderDAO getPersonalOrderDAO() { 
		return new JDBCPersonalOrderDAO(connection); 
	}
	
	@Override
	public IMenuItemPersonalOrderDAO getMenuItemPersonalOrderDAO() {
		return new JDBCMenuItemPersonalOrderDAO(connection);
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
