/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.pagination.utils;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class TestStudentUtils {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;

	private StudentUtils store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.utils.StudentUtils#getStudents(java.lang.String)}.
	 */
	//@Ignore
	@Test
	public void testGetStudents() {
		store = new StudentUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
		equals(store.getStudents());
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.utils.StudentUtils#getIncomingCount(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetIncomingCount() {
		store = new StudentUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
		equals(store.getIncomingCount());
	}

}
