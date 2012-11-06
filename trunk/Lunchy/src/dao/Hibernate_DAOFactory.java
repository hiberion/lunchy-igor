package dao;

public class Hibernate_DAOFactory extends DAOFactory {
	@Override
	public IMenuItemDAO getMenuItemDAO() {
		return new HibernateMenuItemDAO();
	}

	@Override
	public ICategoryDAO getCategoryDAO() {
		return new HibernateCategoryDAO();
	}

	@Override
	public IWorkerDAO getWorkerDAO() {
		return new HibernateWorkerDAO();
	}
}
