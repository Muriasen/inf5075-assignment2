package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Student;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

public class HibernateStudentDao implements StudentDAO {

	private SessionFactory sessionFactory;
	static Logger logger = Logger.getLogger(HibernateStudentDao.class);
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int saveStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		Integer id      = -1; 
		
		try {
			
			id = (Integer) session.save(student);
			session.getTransaction().commit();
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

	public Student getStudent(int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		Student student = null;
		
		try {
			
			student = (Student) session.get(Student.class, id);
			session.getTransaction().commit();
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n" + e.getMessage());
			logger.debug("Stacktrace:\n" + e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return student;
	}

	public Student getStudentByName(String name) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		String hql      = "from Student where name = :name";
		Query query     = null;
		Student student = null; 
		
		try {
			
			query = session.createQuery(hql);
			query.setString("name", name);
			student = (Student) query.uniqueResult();
			session.getTransaction().commit();
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n" + e.getMessage());
			logger.debug("Stacktrace:\n" + e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}
		
		return student;
	}

	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		Query query = null;
		Collection<Student> students = null;

		try {
			
			query = session.createQuery("from Student");
			students = query.list();
			session.getTransaction().commit();
			session.flush();
			
		} catch (HibernateException e) {
			logger.debug("Error message:\n"+e.getMessage());
			logger.debug("Stacktrace:\n"+e.getStackTrace());
			if (tx != null) {
				tx.rollback();
			}
		}

		
		return students;
	}

	public void delStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx  = session.beginTransaction();
		
		try {
			
			session.delete(student);
			session.getTransaction().commit();
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
