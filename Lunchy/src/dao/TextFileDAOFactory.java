package dao;

public class TextFileDAOFactory extends DAOFactory {
	@Override
	public IMenuItemDAO getMenuItemDAO() {
		return new TextFileMenuItemDAO();
	}

	@Override
	public ICategoryDAO getCategoryDAO() {
		return new TextFileCategoryDAO();
	}

	@Override
	public IWorkerDAO getWorkerDAO() {
		return new TextFileWorkerDAO();
	}
	
	
}
