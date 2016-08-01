/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.student;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.student.Student;


/**
 * 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private StudentDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#StudentDAO(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Ignore
	@Test
	public final void testStudentDAOStringStringStringStringInt() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentByUuid(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentByUuid() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentByADMNO(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentByADMNO() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#putStudent(ke.co.fastech.primaryschool.bean.student.Student)}.
	 */
	@Ignore
	@Test
	public final void testPutStudent() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#updateStudent(ke.co.fastech.primaryschool.bean.student.Student)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStudent() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentListByAdmNo(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentListByAdmNo() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentListByAdmNo("10");
		for (Student l : list) {
			System.out.println(l);	
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentListByStreamUuid(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentListByStreamUuid() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentListByStreamUuid("2B0F8F79-DEF2-419D-9A76-17450B5CF768");
		for (Student l : list) {
			System.out.println(l);	
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentsListByLimit(int, int)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentsListByLimit() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentsListByLimit(0, 15);
		for (Student l : list) {
			System.out.println(l);	
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentsList()}.
	 */
	@Ignore
	@Test
	public final void testGetStudentsList() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentsList();
		for (Student l : list) {
					System.out.println(l);	
				}
	}

}
