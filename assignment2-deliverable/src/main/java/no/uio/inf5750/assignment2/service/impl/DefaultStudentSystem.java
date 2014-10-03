package no.uio.inf5750.assignment2.service.impl;
import java.util.Collection;
import java.util.Set;

import no.uio.inf5750.assignment2.dao.hibernate.HibernateCourseDao;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateDegreeDao;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateStudentDao;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

import org.springframework.beans.factory.annotation.Autowired;

public class DefaultStudentSystem implements StudentSystem {

	/**
	 * Mixing autowiring and xml is bad practice, but
	 * I got an error I couldn't figure out.
	 */
	@Autowired
	private HibernateCourseDao courseDao;
	@Autowired
	private HibernateStudentDao studentDao;
	@Autowired
	private HibernateDegreeDao degreeDao;
	
	public int addCourse(String courseCode, String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void updateCourse(int courseId, String courseCode, String name) {
		// TODO Auto-generated method stub
		
	}

	public Course getCourse(int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course getCourseByCourseCode(String courseCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course getCourseByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Course> getAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	public void delCourse(int courseId) {
		// TODO Auto-generated method stub
		
	}

	public void addAttendantToCourse(int courseId, int studentId) {
		// TODO Auto-generated method stub
		
	}

	public void removeAttendantFromCourse(int courseId, int studentId) {
		// TODO Auto-generated method stub
		
	}

	public int addDegree(String type) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void updateDegree(int degreeId, String type) {
		// TODO Auto-generated method stub
		
	}

	public Degree getDegree(int degreeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Degree getDegreeByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Degree> getAllDegrees() {
		// TODO Auto-generated method stub
		return null;
	}

	public void delDegree(int degreeId) {
		// TODO Auto-generated method stub
		
	}

	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		// TODO Auto-generated method stub
		
	}

	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Stores student in db.
	 */
	public int addStudent(String name) {
		
		if(name.length() == 0) {
			return -1;
		}
		
		return studentDao.saveStudent(new Student(name));
	}

	/**
	 * Updates student with the given studentId.
	 * Does nothing if studentId is less than 1
	 * or name is empty.
	 */
	public void updateStudent(int studentId, String name) {
		
		if(name.length() == 0 || !isValidId(studentId)) {
			return;
		}
		
		Student student = studentDao.getStudent(studentId);
		student.setName(name);
		
		studentDao.saveStudent(student);
		
	}
	
	
	public Student getStudent(int studentId) {
		
		Student student = studentDao.getStudent(studentId);
		
		if(student == null) {
			System.out.println("\n\nStudent not found\n\n");
		}
		
		return student;
	}

	public Student getStudentByName(String name) {
		
		if(name.length() == 0) {
			System.out.println("\n\nUser tried to search for student with no name\n\n");
		}
		
		return studentDao.getStudentByName(name);
	}

	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	public void delStudent(int studentId) {
		if(!isValidId(studentId)) {
			return;
		}
		
		Student student = studentDao.getStudent(studentId);
		studentDao.delStudent(student);
	}

	public void addDegreeToStudent(int studentId, int degreeId) {
		
		if(!isValidId(studentId) || !isValidId(degreeId)) {
			return;
		}
		
		Student student = studentDao.getStudent(studentId);
		Degree degree   = degreeDao.getDegree(degreeId);
		
		Set<Degree> degrees = student.getDegrees();
		degrees.add(degree);
		student.setDegrees(degrees);
		
		studentDao.saveStudent(student);
	}

	public void removeDegreeFromStudent(int studentId, int degreeId) {
		
		if(!isValidId(studentId) || !isValidId(degreeId)) {
			return;
		}
		
		Student student = studentDao.getStudent(studentId);
		Set<Degree> degrees = student.getDegrees();
		
		for(Degree degree : degrees) {
			if(degree.getId() == degreeId) {
				degrees.remove(degree);
			}
		}
		
		student.setDegrees(degrees);
		studentDao.saveStudent(student);
	}

	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		
		//return false;
		return true;
	}

	/**
	 * Ids less than one are not stored in the db.
	 * @param id
	 * @return
	 */
	private boolean isValidId(int id) {
		return id < 1 ? false : true;
	}
	
	
}
