package dao;

public interface IDAOFactory {
	public static final int TEXTFILE = 1;
	public static final int JDBC = 2;
	public static final int HIBERNATE = 3;
	
	public IMenuItemDAO getMenuItemDAO();
	public ICategoryDAO getCategoryDAO();
	public IWorkerDAO getWorkerDAO();
	
	public IDAOFactory getDAOFactory(int typeFactory);
}
