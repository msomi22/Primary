/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

/**
 * Tests our class with database credentials.
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */

public class TestDBCredentials {
	private DBCredentials dBCredentials;
	@Test
	public void getConnection() throws SQLException {
		System.out.println("connection test"); 
		
	dBCredentials = new DBCredentials("schooldb", "localhost", "school", "AllaManO1", 5432);
		
		Connection con; 
		con = dBCredentials.getConnection();
		System.out.println("Connection is: " + con);
	}

}
