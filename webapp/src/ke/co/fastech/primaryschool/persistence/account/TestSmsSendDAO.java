/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.account;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.school.account.SmsSend;

/**
 * @author peter
 *
 */
public class TestSmsSendDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private SmsSendDAO store;
	
	final String STATUS_PENDING = "Pending",STATUS_SUCCESS = "Success",STATUS_INVALID = "Invalid Phone Number"; 

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.account.SmsSendDAO#putSmsSend(ke.co.fastech.primaryschool.bean.school.account.SmsSend)}.
	 */
	@Ignore
	@Test
	public final void testPutSmsSend() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.account.SmsSendDAO#deleteSmsSend(java.lang.String)}.
	 */
	//@Ignore
	@Test
	public final void testDeleteSmsSend() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);	
		assertTrue(store.deleteSmsSend(STATUS_SUCCESS));
		
	}
        
	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.account.SmsSendDAO#getsmssendList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetsmssendList() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<SmsSend> smssendList = store.getsmssendList(STATUS_SUCCESS);  
		for(SmsSend sms : smssendList){
			System.out.println(sms); 
		}
	}

}
