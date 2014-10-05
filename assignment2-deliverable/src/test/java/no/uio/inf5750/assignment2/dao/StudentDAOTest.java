package no.uio.inf5750.assignment2.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateStudentDao;
import no.uio.inf5750.assignment2.model.Student;




import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath*:/META-INF/beans.xml"})
//@Transactional
public class StudentDAOTest {

	@Autowired
	private HibernateStudentDao studentDAO;
	//@Autowired
	//private SessionFactory sessionFactory;
	
	
	//private static Configuration config;
	//private static Session hibernateSession;
	
	//@Autowired
	//private WebApplicationContext wac;
	//private MockMvc mockMvc;
	//private BaseController baseController;
	
	@Before
	public void init() {
		/*
		 this.mockMvc =
				 MockMvcBuilders.webAppContextSetup(this.wac).build();
		*/
		
		/*
		config = new AnnotationConfiguration();
		config.configure("classpath*:/META-INF/beans.xml");
		sessionFactory = config.buildSessionFactory();
		hibernateSession = sessionFactory.openSession();
		*/
		
		
		//TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(sessionFactory.openSession()));
		
		//studentDao = new HibernateStudentDao();
		/*
		 this.mockMvc =
				 MockMvcBuilders.webAppContextSetup(this.wac).build();
		*/
		/*
		mockMvc = MockMvcBuilders.standaloneSetup((
				new no.uio.inf5750.assignment2.gui.controller.BaseController()).build();
		*/		
	}
	

	@Test
	public void testSaveStudent() {
		Student student = new Student("Alf Wiedersehen");
		int expectedId = 1;
		
		int id = studentDAO.saveStudent(student);
		
		assertEquals(expectedId, student.getId());
	}

	@Test
	public void testGetStudent() {
		Student expectedStudent = new Student("Jim Lahey");
		
		int id = studentDAO.saveStudent(expectedStudent);
		Student student = studentDAO.getStudent(id);
		
		assertNotNull(student);
		//assertThat(expectedStudent, equalTo(student));
		
	}

	@Test
	public void testGetStudentByName() {
		Student expectedStudent = new Student("Jim Lahey");
		
		int id = studentDAO.saveStudent(expectedStudent);
		Student student = studentDAO.getStudent(id);
		
		assertNotNull(student);
		//assertThat(expectedStudent, equalTo(student));
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
