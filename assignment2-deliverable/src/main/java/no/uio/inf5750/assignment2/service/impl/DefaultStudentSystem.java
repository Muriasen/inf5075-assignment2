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

/**
 * TODO: More defensive programming.
 * @author maudal
 *
 */
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
	
	/**
	 * Adds course, returns id new course.
	 */
	public int addCourse(String courseCode, String name) {
		
		Course course = new Course(courseCode, name);
		int id = courseDao.saveCourse(course);
		
		return id;
	}

	/**
	 * Updates course with the given courseId, with new courseCode and name
	 */
	public void updateCourse(int courseId, String courseCode, String name) {
		
		Course course = courseDao.getCourse(courseId);
		
		if(course != null) {
			course.setCourseCode(courseCode);
			course.setName(name);
			courseDao.saveCourse(course);
		}
	}

	public Course getCourse(int courseId) {
		return courseDao.getCourse(courseId);
	}

	public Course getCourseByCourseCode(String courseCode) {
		return courseDao.getCourseByCourseCode(courseCode);
	}

	public Course getCourseByName(String name) {
		return courseDao.getCourseByName(name);
	}

	public Collection<Course> getAllCourses() {
		return courseDao.getAllCourses();
	}

	public void delCourse(int courseId) {
		
		Course course = courseDao.getCourse(courseId);
		
		if(course != null) {
			courseDao.delCourse(course);
		}
	}

	public void addAttendantToCourse(int courseId, int studentId) {
		
		Student student = studentDao.getStudent(studentId);
		Course course   = courseDao.getCourse(courseId);
		
		if(student != null && course != null) {
			course.getAttendants().add(student);
			student.getCourses().add(course);
			courseDao.saveCourse(course);
			studentDao.saveStudent(student);
		}
	}

	public void removeAttendantFromCourse(int courseId, int studentId) {
		
		Course course   = courseDao.getCourse(courseId);
		Student student = studentDao.getStudent(studentId);
		
		if(course != null && student != null) {
			course.getAttendants().remove(student);
			student.getCourses().remove(course);
			courseDao.saveCourse(course);
			studentDao.saveStudent(student);
		}
	}

	public int addDegree(String type) {
		return degreeDao.saveDegree(new Degree(type));
	}

	public void updateDegree(int degreeId, String type) {
		
		Degree degree = degreeDao.getDegree(degreeId);
		
		if(degree != null) {
			degree.setType(type);
			degreeDao.saveDegree(degree);
		}
	}

	public Degree getDegree(int degreeId) {
		return degreeDao.getDegree(degreeId);
	}

	public Degree getDegreeByType(String type) {
		return degreeDao.getDegreeByType(type);
	}

	public Collection<Degree> getAllDegrees() {
		return degreeDao.getAllDegrees();
	}

	public void delDegree(int degreeId) {
		
		Degree degree = degreeDao.getDegree(degreeId);
		
		if(degree != null) {
			degreeDao.delDegree(degree);
		}
	}

	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		
		Course course = courseDao.getCourse(courseId);
		Degree degree = degreeDao.getDegree(degreeId);
		
		if(course != null && degree != null) {
			
			for(Course c : degree.getRequiredCourses()) {
				if(c.getId() == course.getId()) {
					return;
				}
			}
			
			degree.getRequiredCourses().add(course);
			degreeDao.saveDegree(degree);
		}
	}

	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		
		Course course = courseDao.getCourse(courseId);
		Degree degree = degreeDao.getDegree(degreeId);
		
		if(course != null && degree != null) {
			degree.getRequiredCourses().remove(course);
			degreeDao.saveDegree(degree);
		}
	}

	/**
	 * Stores student in db.
	 */
	public int addStudent(String name) {
		return studentDao.saveStudent(new Student(name));
	}

	/**
	 * Updates student with the given studentId.
	 * Does nothing if studentId is less than 1
	 * or name is empty.
	 */
	public void updateStudent(int studentId, String name) {		
		Student student = studentDao.getStudent(studentId);
		
		if(student != null) {
			student.setName(name);
			studentDao.saveStudent(student);
		}
	}
	
	
	public Student getStudent(int studentId) {		
		return studentDao.getStudent(studentId);
	}

	public Student getStudentByName(String name) {
		return studentDao.getStudentByName(name);
	}

	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	public void delStudent(int studentId) {
		Student student = studentDao.getStudent(studentId);
		if(student != null) {
			studentDao.delStudent(student);
		}
	}

	public void addDegreeToStudent(int studentId, int degreeId) {
		
		Student student = studentDao.getStudent(studentId);
		Degree degree   = degreeDao.getDegree(degreeId);
		
		if(student != null && degree != null) {
			
			for(Degree d : student.getDegrees()) {
				if(degree.getId() == d.getId()) {
					return;
				}
			}
			
			student.getDegrees().add(degree);
			studentDao.saveStudent(student);
		}
	}

	public void removeDegreeFromStudent(int studentId, int degreeId) {
		
		Student student = studentDao.getStudent(studentId);
		Set<Degree> degrees = student.getDegrees();
		
		if (student != null && degrees != null) {
			for (Degree degree : degrees) {
				if (degree.getId() == degreeId) {
					degrees.remove(degree);
				}
			}

			student.setDegrees(degrees);
			studentDao.saveStudent(student);
		}
	}

	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		
		Student student = studentDao.getStudent(studentId);
		Degree degree = degreeDao.getDegree(degreeId);
		
		if (student != null && degree != null) {
			
			for (Course course : degree.getRequiredCourses()) {
				if (student.getCourses().contains(course) == false) {
					return false;
				}
			}
			return true;
		}
		
		return false;
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
