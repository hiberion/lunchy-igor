package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.MenuItemPersonalOrder;

public class HibernateMenuItemPersonalOrderDAO implements
		IMenuItemPersonalOrderDAO {
	
	int size = 0;
	private ArrayList<MenuItemPersonalOrder> collection;
	
	public HibernateMenuItemPersonalOrderDAO() {
		collection = getAllMenuItemPersonalOrder();
	}

	@Override
	public int addMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		
		collection.add(menuItemPersonalOrder);
		
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(menuItemPersonalOrder);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Error hibMOPODAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		size++;
		
		return collection.size()-1;
	}

	@Override
	public boolean updateMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		
		int updatingItem = -1; 
		for (int i = 0; i < collection.size(); i++) {
			if ((collection.get(i).getMenuItemId() == menuItemPersonalOrder.getMenuItemId()) &&
				(collection.get(i).getPersonalOrderId() == menuItemPersonalOrder.getPersonalOrderId())) {
				updatingItem = i;
				break;
			}
		}
		
		if (updatingItem != -1) {
			collection.set(updatingItem, menuItemPersonalOrder);
			
			Session session = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();
				session.update(collection.get(updatingItem));
				session.getTransaction().commit();
				
			} catch (Exception e) {
				System.out.println("Error hibMIPODAO");

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
			
			return true;
		} else
			return false;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByMenuItemID(int ID) {
		MenuItemPersonalOrder result = null;
		for (MenuItemPersonalOrder menuItemPersonalOrder: collection) {
			if (menuItemPersonalOrder.getMenuItemId() == ID) {
				result = menuItemPersonalOrder;
				break;
			}
		}
		return result;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByPersonalOrderID(
			int personalOrderID) {
		MenuItemPersonalOrder result = null;
		for (MenuItemPersonalOrder menuItemPersonalOrder: collection) {
			if (menuItemPersonalOrder.getPersonalOrderId() == personalOrderID) {
				result = menuItemPersonalOrder;
				break;
			}
		}
		return result;
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMenuItemPersonalOrder(int menuItemID,
			int personalOrderID) {
		
		int deletingItem = -1;
		for (int i = 0; i < collection.size(); i++) {
			if ((collection.get(i).getMenuItemId() == menuItemID) &&
				(collection.get(i).getPersonalOrderId() == personalOrderID)) {
				deletingItem = i;
				break;
			}
		}
		
		if (deletingItem != -1) {
			
			Session session = null;
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();
				session.delete(collection.get(deletingItem));
				session.getTransaction().commit();
				
			} catch (Exception e) {
				System.out.println("Error hibMIPODAO");

			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
			
			collection.remove(deletingItem);
			return true;
		} else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MenuItemPersonalOrder> getAllMenuItemPersonalOrder() {
		
		Session session = null;
		ArrayList<MenuItemPersonalOrder> result = new ArrayList<>();
	
		List<MenuItemPersonalOrder> menuItemPersonalOrders = new ArrayList<MenuItemPersonalOrder>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			//menuItems = session.createCriteria(MenuItem.class).list();
			menuItemPersonalOrders = session.createQuery("FROM MenuItemPersonalOrder mipo").list();
			
		} catch (Exception e) {
			System.out.println("Error hibMIPODAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		result = (ArrayList<MenuItemPersonalOrder>) menuItemPersonalOrders;
		
		size = result.size();
		
		return result;
	}

}
