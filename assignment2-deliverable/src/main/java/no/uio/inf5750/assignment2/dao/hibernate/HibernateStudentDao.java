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
		session.beginTransaction();
		
		Integer id = (Integer) session.save(student); 
		
		System.out.println("\n\n\n");
		
		try {
			logger.debug("error");
			session.getTransaction().commit();
			logger.debug("error");
			
		} catch(HibernateException e) {
			System.out.println("\n\n\n\n\n\n");
			System.out.println(e.getMessage());
			System.out.println("\n\n\n\n\n\n");
		}
		
		return id;
	}

	public Student getStudent(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, id);
		session.getTransaction().commit();
		
		return student;
	}

	public Student getStudentByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from Student where name = :name";
		Query query = session.createQuery(hql);
		query.setString("name", name);
		
		session.getTransaction().commit();
		
		return (Student) query.uniqueResult();
	}

	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Student");
		
		try {
			
			session.getTransaction().commit();
			
		} catch(HibernateException e) {
			System.out.println("\n\n\n\n\n\n");
			System.out.println(e.getMessage());
			System.out.println("\n\n\n\n\n\n");
		}
		
		Collection<Student> students = query.list();
		
		/*
		System.out.println("\n\n\n\n\n\n");
		System.out.println("elements: "+ students.size());
		for(Student s : students) {
			System.out.println("id: "+ s.getId());
			System.out.println("name: "+ s.getName());
		}
		System.out.println("\n\n\n\n\n\n");
		*/
		
		return students;
	}

	public void delStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		
		session.delete(student);
		session.getTransaction().commit();
	}

}
