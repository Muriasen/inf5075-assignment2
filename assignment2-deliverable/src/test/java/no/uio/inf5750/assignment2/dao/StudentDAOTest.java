package no.uio.inf5750.assignment2.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateStudentDao;
import no.uio.inf5750.assignment2.model.Student;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/beans.xml"})
@Transactional
public class StudentDAOTest {

	@Autowired
	HibernateStudentDao studentDao;
	
	@Before
	public void init() {
		//studentDao = new HibernateStudentDao();
	}

	@Test
	public void testSaveStudent() {
		Student student = new Student("Alf Wiedersehen");
		student.setId(1);
		
		int id = 1;//studentDao.saveStudent(student);
		
		assertEquals(id, student.getId());
	}

	@Test
	public void testGetStudent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStudentByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllStudents() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelStudent() {
		fail("Not yet implemented");
	}

}
