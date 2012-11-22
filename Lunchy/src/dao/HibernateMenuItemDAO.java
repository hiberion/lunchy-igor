package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.MenuItem;

public class HibernateMenuItemDAO implements IMenuItemDAO {
	
	private ArrayList<MenuItem> collection;
	
	public HibernateMenuItemDAO() {
		collection = getAllMenuItem();
	}

	@Override
	public int getSize () {
		return collection.size();
	}
	
	@Override
	public int addMenuItem(MenuItem menuItem) {
		collection.add(menuItem);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(menuItem);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Error hibMenuItemDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		
		return collection.size()-1;
	}

	@Override
	public boolean updateMenuItem(MenuItem menuItem) {
		collection.set(menuItem.getId(), menuItem);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(menuItem);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Error hibMenuItemDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		
		return false;
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MenuItem getMenuItemByID(int ID) {
		MenuItem result = collection.get(ID);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MenuItem> getAllMenuItem() {
		Session session = null;
		ArrayList<MenuItem> result = new ArrayList<>();
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			// this doesn't work as I need - this returns only a set with IDs
			//el = (E) session.load(elementClass, elId);
			
			//menuItems = session.createCriteria(MenuItem.class).list();
			menuItems = session.createQuery("FROM MenuItem mi ORDER BY mi.id").list();
			
		} catch (Exception e) {
			System.out.println("Error hibMenuItemDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		result = (ArrayList<MenuItem>) menuItems;
		
		return result;
	}

	@Override
	public ArrayList<MenuItem> getMenuItemByCategory(int category) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: collection) {
			if (mi.getCategory() == category)
				result.add(mi);
		}
		return result;
	}

	@Override
	public ArrayList<MenuItem> getMenuItemByAvailability(boolean avail) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: collection) {
			if (mi.getAvailability() == avail)
				result.add(mi);
		}
		return result;
	}

}
