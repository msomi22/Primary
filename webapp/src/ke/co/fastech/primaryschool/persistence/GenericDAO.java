/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence;

import java.sql.SQLException;

import ke.co.fastech.primaryschool.server.servlet.util.DbPoolUtil;


/**
 * What is common to all data access objects (DAOs).
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class GenericDAO {

	
	 protected DBCredentials dbutils;
	/**
	 * @throws SQLException 
	 * 
	 */
	public GenericDAO()  { 
	dbutils =  DbPoolUtil.getDBCredentials();
	}
	/**
	 * 
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public GenericDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		dbutils = new DBCredentials(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	 
	public void closeConnections() {
		dbutils.closeConnections();
	}
	

}
