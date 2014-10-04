package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

public class HibernateCourseDao implements CourseDAO {

	private SessionFactory sessionFactory;
	static Logger logger = Logger.getLogger(HibernateCourseDao.class);
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int saveCourse(Course course) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		Integer id = -1; 
		
		try {
			
			id = (Integer) session.save(course); 
			if(!tx.wasCommitted()) {
				tx.commit();
			}
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n"+e.getMessage());
			logger.debug("Stacktrace:\n"+e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return id;
	}

	public Course getCourse(int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();	
		
		Course course   = null;
		
		try {
			
			course = (Course) session.get(Course.class, id);
			if(!tx.wasCommitted()) {
				tx.commit();
			}
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n"+e.getMessage());
			logger.debug("Stacktrace:\n"+e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return course;
	}

	public Course getCourseByCourseCode(String courseCode) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		String hql = "from Course where courseCode = :courseCode";
		Query query = null;
		Course course = null;
		
		try {
			
			query = session.createQuery(hql);
			query.setString("courseCode", courseCode);
			course = (Course) query.uniqueResult();
			tx.commit();
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n"+e.getMessage());
			logger.debug("Stacktrace:\n"+e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return course;
	}

	public Course getCourseByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		String hql    = "from Course where name = :name";
		Query query   = null;
		Course course = null;
		
		try {
			
			query = session.createQuery(hql);
			query.setString("name", name);
			
			course = (Course) query.uniqueResult();
			tx.commit();
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n"+e.getMessage());
			logger.debug("Stacktrace:\n"+e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return course;
	}

	public Collection<Course> getAllCourses() {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		String hql = "from Course";
		Query query = null;
		List<Course> courses = null;
		
		try {
			
			query = session.createQuery(hql);
			courses = query.list();
			tx.commit();
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n"+e.getMessage());
			logger.debug("Stacktrace:\n"+e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return courses;
	}

	public void delCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		try {
			
			session.delete(course);
			if(!tx.wasCommitted()) {
				tx.commit();
			}
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n"+e.getMessage());
			logger.debug("Stacktrace:\n"+e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
	}

}
