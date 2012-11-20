package dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * The hibernate session factory that configures hibernate by reading hibernate.cfg.xml 
 * in the src/ folder, build in and returns it. This is a static singleton class.
 * @author ag
 *
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory() {	
		/*
		 * Turning off the hibernate logging - now it shows only warnings.
		 */
		Logger logger = Logger.getLogger("org.hibernate");
		logger.setLevel(Level.SEVERE);
		
		Configuration config = new Configuration().configure()
	        	.addAnnotatedClass(entities.MenuItem.class)
	        	.addAnnotatedClass(entities.Category.class);
	        	//.addAnnotatedClass(Client.class);
				//.addAnnotatedClass(entities.Morder.class)
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
    		.applySettings(config.getProperties()).buildServiceRegistry();
		
		try {
			
			// Creating the SessionFactory from hibernate.cfg.xml
			SessionFactory sessions = config.buildSessionFactory(serviceRegistry);
			return sessions;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
