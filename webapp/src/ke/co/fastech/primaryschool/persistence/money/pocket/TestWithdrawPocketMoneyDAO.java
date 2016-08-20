/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.money.pocket;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.money.pocket.Withdraw;

/**
 * @author peter
 *
 */
public class TestWithdrawPocketMoneyDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	
	private final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21";
	private final String TERM  = "1";
	private final String YEAR  = "2016";
	
	private WithdrawPocketMoneyDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.WithdrawPocketMoneyDAO#getWithdraw(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetWithdrawString() {
		store = new WithdrawPocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Withdraw> wlist = store.getWithdraw(STUDENT_UUID);
		for(Withdraw w : wlist){
			System.out.println(w);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.WithdrawPocketMoneyDAO#getWithdraw(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetWithdrawStringStringString() {
		store = new WithdrawPocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Withdraw> wlist = store.getWithdraw(STUDENT_UUID, TERM, YEAR);
		for(Withdraw w : wlist){
			System.out.println(w);
		}
	}

}
