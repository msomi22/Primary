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

import ke.co.fastech.primaryschool.bean.money.StudentOtherMoney;

/**
 * Unit test for {@link StudentOtherMoney} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentOtherMoneyDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private StudentOtherMoneyDAO store;
	
	private final String UUID = "38B6AA29-8D63-41B8-89A2-B39B166F1D8C";
	private final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21";
	private final String OTHER_MONEY_UUID = "CAA3E91A-E324-4A18-AEE1-88BF14DDB606";
	private final String TERM = "1";
	private final String YEAR = "2016";

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentOtherMoneyDAO#getStudentOtherMoney(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentOtherMoney() {
		store = new StudentOtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentOtherMoney studentOtherMoney = new StudentOtherMoney();
		studentOtherMoney = store.getStudentOtherMoney(STUDENT_UUID, OTHER_MONEY_UUID); 
		assertEquals(studentOtherMoney.getUuid(),UUID);
		assertEquals(studentOtherMoney.getStudentUuid(),STUDENT_UUID);
		assertEquals(studentOtherMoney.getOtherMoneyUuid(),OTHER_MONEY_UUID);
		assertEquals(studentOtherMoney.getTerm(),TERM);
		assertEquals(studentOtherMoney.getYear(),YEAR); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentOtherMoneyDAO#putStudentOtherMoney(ke.co.fastech.primaryschool.bean.money.StudentOtherMoney)}.
	 */
	@Ignore
	@Test
	public final void testPutStudentOtherMoney() {
		store = new StudentOtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentOtherMoney studentOtherMoney = new StudentOtherMoney();
		//studentOtherMoney.setUuid(UUID);
		studentOtherMoney.setStudentUuid(STUDENT_UUID);
		studentOtherMoney.setOtherMoneyUuid(OTHER_MONEY_UUID);
		studentOtherMoney.setTerm(TERM);
		studentOtherMoney.setYear(YEAR);
		assertTrue(store.putStudentOtherMoney(studentOtherMoney)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentOtherMoneyDAO#deleteStudentOtherMoney(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testDeleteStudentOtherMoney() {
		store = new StudentOtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteStudentOtherMoney(STUDENT_UUID, OTHER_MONEY_UUID)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentOtherMoneyDAO#getStudentOtherMoneyList(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentOtherMoneyList() {
		store = new StudentOtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentOtherMoney> list = store.getStudentOtherMoneyList(STUDENT_UUID, TERM, YEAR); 
		for(StudentOtherMoney f : list){
			System.out.println(f); 
		}
	}

}
