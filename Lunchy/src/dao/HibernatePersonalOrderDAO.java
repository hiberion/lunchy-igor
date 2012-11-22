package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.PersonalOrder;

public class HibernatePersonalOrderDAO implements IPersonalOrderDAO {
	
	int size = 0;
	private ArrayList<PersonalOrder> collection;
	
	public HibernatePersonalOrderDAO() {
		collection = getAllPersonalOrder();
	}

	@Override
	public int addPersonalOrder(PersonalOrder personalOrder) {
		collection.add(personalOrder);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(personalOrder);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Error hibPersOrdDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		size++;
		return collection.size()-1;
	}

	@Override
	public boolean updatePersonalOrder(PersonalOrder personalOrder) {
		collection.set(personalOrder.getId(), personalOrder);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(personalOrder);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Error hibPersOrdDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return false;
	}

	@Override
	public PersonalOrder getPersonalOrderByID(int ID) {
		PersonalOrder result = null;
		for (PersonalOrder personalOrder: collection) {
			if (personalOrder.getId() == ID) {
				result = personalOrder;
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
	public boolean removePersonalOrder(int ID) {
		int deletingItem = -1;
		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getId() == ID) {
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
				System.out.println("Error hibPersOrdDAO");

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
	public ArrayList<PersonalOrder> getAllPersonalOrder() {
		Session session = null;
		ArrayList<PersonalOrder> result = new ArrayList<>();
		List<PersonalOrder> personalOrders  = new ArrayList<PersonalOrder>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			//menuItems = session.createCriteria(MenuItem.class).list();
			personalOrders = session.createQuery("FROM PersonalOrder po ORDER BY po.id").list();
			
		} catch (Exception e) {
			System.out.println("Error hibPersOrdDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		result = (ArrayList<PersonalOrder>) personalOrders;
		
		size = result.size();
		return result;
	}

}
