package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateCourseDao implements CourseDAO {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int saveCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Integer id = (Integer) session.save(course); 
		session.getTransaction().commit();
		return id;
	}

	public Course getCourse(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Course course = (Course) session.get(Course.class, id);
		
		return course;
	}

	public Course getCourseByCourseCode(String courseCode) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from courses where course_code = :courseCode";
		Query query = session.createQuery(hql);
		query.setString("courseCode", courseCode);
		
		return (Course) query.uniqueResult();
	}

	public Course getCourseByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from Courses where name = :name";
		Query query = session.createQuery(hql);
		query.setString("name", name);
		
		return (Course) query.uniqueResult();
	}

	public Collection<Course> getAllCourses() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from courses";
		Query query = session.createQuery(hql);
		
		return query.list();
	}

	public void delCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		session.delete(course);
		session.getTransaction().commit();
	}

}
