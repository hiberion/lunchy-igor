package dao;

public abstract class DAOFactory {
	public static final int TEXTFILE = 1;
	public static final int JDBC = 2;
	public static final int HIBERNATE = 3;
	
	public abstract IMenuItemDAO getMenuItemDAO();
	public abstract ICategoryDAO getCategoryDAO();
	public abstract IWorkerDAO getWorkerDAO();
	
	public static DAOFactory getDAOFactory(int typeFactory) {
		switch (typeFactory) {
		case TEXTFILE:
			return new TextFileDAOFactory();
		//case JDBC:
			//return new JDBCDAOFactory();
		//case HIBERNATE:
			//return new HibernateDAOFactory();
		default:
			return null;
		}
	}
}
