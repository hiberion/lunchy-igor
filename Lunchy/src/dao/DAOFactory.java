package dao;

public class DAOFactory implements IDAOFactory {	
	
	private String param;
	
	public IMenuItemDAO getMenuItemDAO() { return null; };
	public ICategoryDAO getCategoryDAO() { return null; };
	public IWorkerDAO getWorkerDAO() { return null; };
	public IGeneralOrderDAO getGeneralOrderDAO() { return null; };
	public IPersonalOrderDAO getPersonalOrderDAO() { return null; };
	public IMenuItemPersonalOrderDAO getMenuItemPersonalOrderDAO() { return null; };
		
	
	public DAOFactory() {
	}
	
	public DAOFactory(String param) {
		this.param = param;
	}
	
	public DAOFactory getDAOFactory(int typeFactory) {
		switch (typeFactory) {
		case TEXTFILE:
			return new TextFileDAOFactory();
		case JDBC:
			return new JdbcDAOFactory(param);
		case HIBERNATE:
			return new HibernateDAOFactory();
		default:
			return null;
		}
	}
}
