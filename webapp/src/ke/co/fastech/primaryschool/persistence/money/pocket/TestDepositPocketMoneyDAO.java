/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.money.pocket;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.money.pocket.Deposit;

/**
 * @author peter
 *
 */
public class TestDepositPocketMoneyDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21";
	private final String TERM  = "1";
	private final String YEAR  = "2016";
	
	private DepositPocketMoneyDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.DepositPocketMoneyDAO#getDeposit(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetDepositString() {
		store = new DepositPocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Deposit> dlist = store.getDeposit(STUDENT_UUID);
		for(Deposit d : dlist){
			System.out.println(d); 
		}
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.DepositPocketMoneyDAO#getDeposit(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetDepositStringStringString() {
		store = new DepositPocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Deposit> dlist = store.getDeposit(STUDENT_UUID, TERM, YEAR);
		for(Deposit d : dlist){
			System.out.println(d); 
		}
	}

}
