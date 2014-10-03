package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Student;

public class HibernateStudentDao implements StudentDAO {

	private SessionFactory sessionFactory;
	
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
		session.getTransaction().commit();
		
		
		/**
		 * Testing!!
		 */
		Student s = getStudent(id);
		System.out.println("|||||||||||||||||||||||||||||||||||||\n\n"
							+ s.getName() + "\n\n||||||||||||||||");
		
		return id;
	}

	public Student getStudent(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, id);
		
		return student;
	}

	public Student getStudentByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from students where name = :name";
		Query query = session.createQuery(hql);
		query.setString("name", name);
		
		return (Student) query.uniqueResult();
	}

	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from students";
		Query query = session.createQuery(hql);
		
		return query.list();
	}

	public void delStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		session.delete(student);
		session.getTransaction().commit();
	}

}
