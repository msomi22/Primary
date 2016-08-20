/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.money;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.money.RevertedMoney;

/**
 * Unit test for {@link StudentRevertedMoney} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentRevertedMoneyDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private StudentRevertedMoneyDAO store;
	
	private final String UUID = "93902F73-3954-4117-A1AD-717BEB9DF258";
	private final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21";
	private final String OTHER_MONEY_UUID  = "CAA3E91A-E324-4A18-AEE1-88BF14DDB606";
	private final String TERM  = "1";
	private final String YEAR  = "2016";
	
	

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentRevertedMoneyDAO#putRevertedMoney(ke.co.fastech.primaryschool.bean.money.RevertedMoney)}.
	 */
	@Ignore
	@Test
	public final void testPutRevertedMoney() {
		store = new StudentRevertedMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		RevertedMoney revertedMoney = new RevertedMoney();
		revertedMoney.setUuid(UUID);
		revertedMoney.setStudentUuid(STUDENT_UUID);
		revertedMoney.setOtherMoneyUuid(OTHER_MONEY_UUID);
		revertedMoney.setTerm(TERM);
		revertedMoney.setYear(YEAR); 
		assertTrue(store.putRevertedMoney(revertedMoney));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentRevertedMoneyDAO#getAllRevertedMoneyByTermYear(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetAllRevertedMoneyByTermYear() {
		store = new StudentRevertedMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<RevertedMoney> list =  store.getAllRevertedMoneyByTermYear(STUDENT_UUID);
		for(RevertedMoney r : list){
			System.out.println(r);
		}
		
	}

}
