package dao;

public class TextFileDAOFactory extends DAOFactory {
	private String menuItemFileName 				= "Data/menu.txt";
	private String categoryFileName 				= "Data/category.txt";
	private String workersFileName 					= "Data/workers.txt";
	private String generalOrderFileName 			= "Data/general_order.txt";
	private String personalOrderFileName 			= "Data/personal_order.txt";
	private String menuItemPersonalOrderFileName 	= "Data/menu_personal_order.txt";
	
	@Override
	public IMenuItemDAO getMenuItemDAO() {
		return new TextFileMenuItemDAO(menuItemFileName);
	}

	@Override
	public ICategoryDAO getCategoryDAO() {
		return new TextFileCategoryDAO(categoryFileName);
	}

	@Override
	public IWorkerDAO getWorkerDAO() {
		return new TextFileWorkerDAO(workersFileName);
	}
	
	@Override
	public IGeneralOrderDAO getGeneralOrderDAO() {
		return new TextFileGeneralOrderDAO(generalOrderFileName); 
	}
	
	@Override
	public IPersonalOrderDAO getPersonalOrderDAO() { 
		return new TextFilePersonalOrderDAO(personalOrderFileName); 
	}
	
	@Override
	public IMenuItemPersonalOrderDAO getMenuItemPersonalOrderDAO() {
		return new TextFileMenuItemPersonalOrderDAO(menuItemPersonalOrderFileName);
	}

}
