package dao;

public class HibernateDAOFactory extends DAOFactory {
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
	
	@Override
	public IGeneralOrderDAO getGeneralOrderDAO() {
		return new HibernateGeneralOrderDAO(); 
	}
	
	@Override
	public IPersonalOrderDAO getPersonalOrderDAO() { 
		return new HibernatePersonalOrderDAO(); 
	}
	
	@Override
	public IMenuItemPersonalOrderDAO getMenuItemPersonalOrderDAO() {
		return new HibernateMenuItemPersonalOrderDAO();
	}
	
}
