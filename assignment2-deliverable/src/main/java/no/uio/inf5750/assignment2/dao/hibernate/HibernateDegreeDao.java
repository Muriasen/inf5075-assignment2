package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Degree;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

public class HibernateDegreeDao implements DegreeDAO {

	private SessionFactory sessionFactory;
	static Logger logger = Logger.getLogger(HibernateStudentDao.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public int saveDegree(Degree degree) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();

		Integer id = -1;
		
		try {

			id = (Integer) session.save(degree);
			session.getTransaction().commit();
			session.flush();

		} catch (HibernateException e) {
			logger.debug("Error message:\n" + e.getMessage());
			logger.debug("Stacktrace:\n" + e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		return id;
	}

	public Degree getDegree(int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();

		Degree degree = null;
		
		try {

			degree = (Degree) session.get(Degree.class, id);
			session.getTransaction().commit();
			session.flush();

		} catch (HibernateException e) {
			logger.debug("Error message:\n" + e.getMessage());
			logger.debug("Stacktrace:\n" + e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}

		return degree;
	}

	public Degree getDegreeByType(String type) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();

		String hql = "from Degree where type = :type";
		Query query = null;

		try {
			
			query = session.createQuery(hql);
			query.setString("type", type);
			session.getTransaction().commit();
			session.flush();

		} catch (HibernateException e) {
			logger.debug("Error message:\n" + e.getMessage());
			logger.debug("Stacktrace:\n" + e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}

		return (Degree) query.uniqueResult();
	}

	public Collection<Degree> getAllDegrees() {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();

		String hql = "from Degree";
		Query query = null;
		List<Degree> degrees = null;
		
		try {

			query = session.createQuery(hql);
			degrees = query.list();
			session.getTransaction().commit();
			session.flush();

		} catch (HibernateException e) {
			logger.debug("Error message:\n" + e.getMessage());
			logger.debug("Stacktrace:\n" + e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}

		return degrees;
	}

	public void delDegree(Degree degree) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();

		try {

			session.delete(degree);
			session.getTransaction().commit();
			session.flush();

		} catch (HibernateException e) {
			logger.debug("Error message:\n" + e.getMessage());
			logger.debug("Stacktrace:\n" + e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
	}

}
