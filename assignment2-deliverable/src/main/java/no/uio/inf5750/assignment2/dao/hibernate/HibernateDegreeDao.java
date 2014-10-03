package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;

public class HibernateDegreeDao implements DegreeDAO {

private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int saveDegree(Degree degree) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Integer id = (Integer) session.save(degree); 
		session.getTransaction().commit();
		return id;
	}
	
	public Degree getDegree(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Degree degree = (Degree) session.get(Degree.class, id);
		
		return degree;
	}

	public Degree getDegreeByType(String type) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from degrees where type = :type";
		Query query = session.createQuery(hql);
		query.setString("type", type);
		
		return (Degree) query.uniqueResult();
	}

	public Collection<Degree> getAllDegrees() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from degrees";
		Query query = session.createQuery(hql);
		
		return query.list();
	}

	public void delDegree(Degree degree) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		session.delete(degree);
		session.getTransaction().commit();
	}

}
