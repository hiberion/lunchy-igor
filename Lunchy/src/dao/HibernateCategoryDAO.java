package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.Category;

public class HibernateCategoryDAO implements ICategoryDAO {

	private ArrayList<Category> collection;
	
	public HibernateCategoryDAO() {
		collection = getAllCategory();
	}
	
	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Category getCategoryByID(int ID) {
		return collection.get(ID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Category> getAllCategory() {
		Session session = null;
		ArrayList<Category> result = new ArrayList<>();
		List<Category> categories = new ArrayList<Category>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			// this doesn't work as I need - this returns only a set with IDs
			//el = (E) session.load(elementClass, elId);
			
			//categories = session.createCriteria(Category.class).addOrder(Order.asc("id")).list();
			categories = session.createQuery("FROM Category cat ORDER BY cat.id").list();
			
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}		
		result = (ArrayList<Category>) categories;
		
		return result;
	}

}
