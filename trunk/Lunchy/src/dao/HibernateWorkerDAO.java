package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.Worker;

public class HibernateWorkerDAO implements IWorkerDAO {
	
	int size = 0;
	private ArrayList<Worker> collection;

	 public HibernateWorkerDAO() {
			collection = getAllWorker();
		}
	
	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Worker getWorkerByID(int ID) {
		return collection.get(ID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Worker> getAllWorker() {
		Session session = null;
		ArrayList<Worker> result = new ArrayList<>();
		List<Worker> workers = new ArrayList<Worker>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			//menuItems = session.createCriteria(MenuItem.class).list();
			workers = session.createQuery("FROM Worker w ORDER BY w.id").list();
			
		} catch (Exception e) {
			System.out.println("Error hibWorkerDAO");

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		result = (ArrayList<Worker>) workers;
		size = result.size();
		
		return result;
	}

}
