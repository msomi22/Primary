/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.money.pocket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.money.pocket.Deposit;
import ke.co.fastech.primaryschool.bean.money.pocket.PocketMoney;
import ke.co.fastech.primaryschool.bean.money.pocket.Withdraw;

/**
 * @author peter
 *
 */
public class TestPocketMoneyDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private PocketMoneyDAO store;
	
	private final String PM_UUID = "F04FB6BD-AD09-45FD-A74E-41DCDCD1CF86";
	private final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21";
	private final String TERM  = "1";
	private final String YEAR  = "2016";
	private final int BALANCE = 2000;
	//private Date DATE = new Date();

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.PocketMoneyDAO#getPocketMoney(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetPocketMoney() {
		store = new PocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		PocketMoney pMoney = new PocketMoney();
		pMoney = store.getPocketMoney(STUDENT_UUID);
		assertEquals(pMoney.getUuid(),PM_UUID);
		assertEquals(pMoney.getStudentUuid(),STUDENT_UUID); 
		assertEquals(pMoney.getBalance(),BALANCE);
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.PocketMoneyDAO#StudentExist(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testStudentExist() {
		store = new PocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.studentExist(STUDENT_UUID));
	}
	
	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.PocketMoneyDAO#hasBalance(java.lang.String, int)}.
	 */
	@Ignore
	@Test
	public final void testHasBalance() {
		store = new PocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.hasBalance(STUDENT_UUID, BALANCE)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.PocketMoneyDAO#deposit(ke.co.fastech.primaryschool.bean.money.pocket.PocketMoney, java.lang.String, int)}.
	 */
	@Ignore
	@Test
	public final void testDeposit() {
		store = new PocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Deposit d = new Deposit();
		//d.setUuid(PM_UUID);
		d.setTerm(TERM);
		d.setYear(YEAR); 
		assertTrue(store.deposit(d, STUDENT_UUID, BALANCE)); 
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.pocket.PocketMoneyDAO#withdraw(ke.co.fastech.primaryschool.bean.money.pocket.PocketMoney, java.lang.String, int)}.
	 */
	@Ignore
	@Test
	public final void testWithdraw() {
		store = new PocketMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Withdraw w = new Withdraw();
		//w.setUuid(PM_UUID);
		w.setTerm(TERM);
		w.setYear(YEAR); 
		assertTrue(store.withdraw(w, STUDENT_UUID, 6000)); 
	}

}
