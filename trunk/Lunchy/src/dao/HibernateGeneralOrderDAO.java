package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import entities.GeneralOrder;

public class HibernateGeneralOrderDAO implements IGeneralOrderDAO {
	
	private ArrayList<GeneralOrder> collection;
	int size = 0;
	
	public HibernateGeneralOrderDAO() {
		collection = getAllGeneralOrder();
	}

	@Override
	public int addGeneralOrder(GeneralOrder generalOrder) {
		collection.add(generalOrder);
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(generalOrder);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Error hibGenOrdDAO");
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		size = collection.size();
		return collection.size()-1;
	}

	@Override
	public boolean updateGeneralOrder(GeneralOrder generalOrder) {
		collection.set(generalOrder.getId(), generalOrder);
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(generalOrder);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Error hibGenOrdDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return false;
	}

	@Override
	public GeneralOrder getGeneralOrderByID(int ID) {
		GeneralOrder result = null;
		for (GeneralOrder generalOrder: collection) {
			if (generalOrder.getId() == ID) {
				result = generalOrder;
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
	public GeneralOrder getGeneralOrderByDate(Date date) {
		Date modifDate = null;
		String strDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		strDate = sdf.format(date);
		try {
			modifDate = sdf.parse(strDate);
		} catch (ParseException e) {
			System.out.println("getOrderDateForm2 Error");
		}
		GeneralOrder result = null;
		for (GeneralOrder generalOrder: collection) {
			if (generalOrder.getOrderDateForm2().equals(modifDate)) {
				result = generalOrder;
				break;
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GeneralOrder> getAllGeneralOrder() {
		Session session = null;
		ArrayList<GeneralOrder> result = new ArrayList<>();
		List<GeneralOrder> generalOrders = new ArrayList<GeneralOrder>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
						
			//menuItems = session.createCriteria(MenuItem.class).list();
			generalOrders = session.createQuery("FROM GeneralOrder go ORDER BY go.id").list();
			
		} catch (Exception e) {
			System.out.println("Error hibGenOrdDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		result = (ArrayList<GeneralOrder>) generalOrders;
		
		return result;
	}

}
