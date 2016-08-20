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

import ke.co.fastech.primaryschool.bean.money.TermFee;

/**
 * Unit test for {@link TermFee} DAO
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestTermFeeDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private TermFeeDAO store;
	
	private String UUID = "3ED61507-2CBB-4414-83BD-3274D2FE3954";
	private final String TERM = "1";
	private final String YEAR = "2016";
	private final String STUDENT_LEVEL = "UPPER-NEW";
	private final int AMOUNT = 24000;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.TermFeeDAO#putTermFee(ke.co.fastech.primaryschool.bean.money.TermFee)}.
	 */
	@Ignore
	@Test
	public final void testPutTermFee() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TermFee fee = new TermFee();
		fee.setStudentLevel(STUDENT_LEVEL);
		fee.setTerm("1");
		fee.setYear("2016");
		fee.setAmount(AMOUNT); 
		assertTrue(store.putTermFee(fee));
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.TermFeeDAO#updateTermFee(ke.co.fastech.primaryschool.bean.money.TermFee)}.
	 */
	@Ignore
	@Test
	public final void testUpdateTermFee() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TermFee fee = new TermFee();
		fee.setUuid(UUID); 
		fee.setStudentLevel(STUDENT_LEVEL);
		fee.setTerm("11");
		fee.setYear("20166");
		fee.setAmount(AMOUNT); 
		assertTrue(store.updateTermFee(fee)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.TermFeeDAO#getTermFeeList(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetTermFeeListStringString() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TermFee> feeList = store.getTermFeeList(TERM, YEAR);
		for(TermFee fee : feeList){
			System.out.println(fee); 
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.TermFeeDAO#getTermFeeList()}.
	 */
	@Ignore
	@Test
	public final void testGetTermFeeList() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TermFee> feeList = store.getTermFeeList();
		for(TermFee fee : feeList){
			System.out.println(fee); 
		}
	}

}
