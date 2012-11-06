package dao;

public class DAOFactory implements IDAOFactory {	
	
	public IMenuItemDAO getMenuItemDAO() { return null; };
	public ICategoryDAO getCategoryDAO() { return null; };
	public IWorkerDAO getWorkerDAO() { return null; };
	
	public DAOFactory getDAOFactory(int typeFactory) {
		switch (typeFactory) {
		case TEXTFILE:
			return new TextFile_DAOFactory();
		case JDBC:
			return new JDBC_DAOFactory();
		case HIBERNATE:
			return new Hibernate_DAOFactory();
		default:
			return null;
		}
	}
}
